package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.CreateReservationDTO;
import com.example.flightmanagementsystem.dto.ReservationDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.ResourceNotFoundException;
import com.example.flightmanagementsystem.mapper.ReservationMapper;
import com.example.flightmanagementsystem.model.Passager;
import com.example.flightmanagementsystem.model.Reservation;
import com.example.flightmanagementsystem.model.User;
import com.example.flightmanagementsystem.model.Vol;
import com.example.flightmanagementsystem.model.enums.StatutReservation;
import com.example.flightmanagementsystem.repository.PassagerRepository;
import com.example.flightmanagementsystem.repository.ReservationRepository;
import com.example.flightmanagementsystem.repository.UserRepository;
import com.example.flightmanagementsystem.repository.VolRepository;
import com.example.flightmanagementsystem.service.ReservationService;
import com.example.flightmanagementsystem.service.VolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReservationServiceImpl implements ReservationService {
    
    private final ReservationRepository reservationRepository;
    private final VolRepository volRepository;
    private final PassagerRepository passagerRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final VolService volService;
    
    private static final double TAXE_POURCENTAGE = 0.15; // 15% de taxes
    
    @Override
    public ReservationDTO createReservation(CreateReservationDTO createDTO, Long userId) {
        log.debug("Creating reservation for user: {}", userId);
        
        // Récupérer le vol
        Vol vol = volRepository.findById(createDTO.getVolId())
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé"));
        
        // Récupérer l'utilisateur
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        // Récupérer les passagers
        List<Passager> passagers = passagerRepository.findAllById(createDTO.getPassagerIds());
        if (passagers.size() != createDTO.getPassagerIds().size()) {
            throw new BadRequestException("Certains passagers n'ont pas été trouvés");
        }
        
        // Vérifier la disponibilité
        if (!volService.verifierDisponibilite(vol.getId(), passagers.size())) {
            throw new BadRequestException("Places insuffisantes pour ce vol");
        }
        
        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setVol(vol);
        reservation.setUser(user);
        reservation.setPassagers(new HashSet<>(passagers));
        reservation.setNombrePassagers(passagers.size());
        reservation.setClasse(createDTO.getClasse());
        reservation.setCommentaire(createDTO.getCommentaire());
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        
        // Calculer le prix
        double prixUnitaire = vol.getPrixBase() * createDTO.getClasse().getMultiplicateur();
        double montantBase = prixUnitaire * passagers.size();
        double taxes = montantBase * TAXE_POURCENTAGE;
        
        reservation.setPrixUnitaire(prixUnitaire);
        reservation.setTaxes(taxes);
        reservation.setReduction(0.0);
        reservation.setPrixTotal(montantBase + taxes);
        
        // Sauvegarder
        Reservation savedReservation = reservationRepository.save(reservation);
        
        // Mettre à jour les places disponibles
        volService.updatePlacesDisponibles(vol.getId(), -passagers.size());
        
        log.info("Réservation créée avec succès: {}", savedReservation.getNumeroReservation());
        return reservationMapper.toDto(savedReservation);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationById(Long id) {
        log.debug("Fetching reservation with id: {}", id);
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'id: " + id));
        return reservationMapper.toDto(reservation);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ReservationDTO getReservationByNumero(String numeroReservation) {
        log.debug("Fetching reservation by numero: {}", numeroReservation);
        Reservation reservation = reservationRepository.findByNumeroReservation(numeroReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec le numéro: " + numeroReservation));
        return reservationMapper.toDto(reservation);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getAllReservations() {
        log.debug("Fetching all reservations");
        return reservationMapper.toDtoList(reservationRepository.findAll());
    }
    
    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        log.debug("Updating reservation with id: {}", id);
        
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'id: " + id));
        
        // On ne peut modifier que certaines réservations
        if (reservation.getStatut() == StatutReservation.CONFIRMEE) {
            throw new BadRequestException("Impossible de modifier une réservation confirmée");
        }
        
        reservationMapper.updateEntityFromDto(reservationDTO, reservation);
        Reservation updatedReservation = reservationRepository.save(reservation);
        
        log.info("Réservation mise à jour avec succès: {}", id);
        return reservationMapper.toDto(updatedReservation);
    }
    
    @Override
    public void deleteReservation(Long id) {
        log.debug("Deleting reservation with id: {}", id);
        
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'id: " + id));
        
        // Libérer les places
        volService.updatePlacesDisponibles(reservation.getVol().getId(), reservation.getNombrePassagers());
        
        reservationRepository.deleteById(id);
        log.info("Réservation supprimée avec succès: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsByUser(Long userId) {
        log.debug("Fetching reservations for user: {}", userId);
        return reservationMapper.toDtoList(reservationRepository.findByUserId(userId));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsByVol(Long volId) {
        log.debug("Fetching reservations for vol: {}", volId);
        return reservationMapper.toDtoList(reservationRepository.findByVolId(volId));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsByStatut(StatutReservation statut) {
        log.debug("Fetching reservations by statut: {}", statut);
        return reservationMapper.toDtoList(reservationRepository.findByStatut(statut));
    }
    
    @Override
    public ReservationDTO confirmerReservation(Long id, String methodePaiement, String referencePaiement) {
        log.debug("Confirming reservation: {}", id);
        
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée"));
        
        if (reservation.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new BadRequestException("Seules les réservations en attente peuvent être confirmées");
        }
        
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setDatePaiement(LocalDateTime.now());
        reservation.setMethodePaiement(methodePaiement);
        reservation.setReferencePaiement(referencePaiement);
        
        Reservation confirmedReservation = reservationRepository.save(reservation);
        log.info("Réservation confirmée: {}", reservation.getNumeroReservation());
        
        return reservationMapper.toDto(confirmedReservation);
    }
    
    @Override
    public ReservationDTO annulerReservation(Long id) {
        log.debug("Cancelling reservation: {}", id);
        
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée"));
        
        if (reservation.getStatut() == StatutReservation.ANNULEE) {
            throw new BadRequestException("La réservation est déjà annulée");
        }
        
        reservation.setStatut(StatutReservation.ANNULEE);
        
        // Libérer les places
        volService.updatePlacesDisponibles(reservation.getVol().getId(), reservation.getNombrePassagers());
        
        Reservation cancelledReservation = reservationRepository.save(reservation);
        log.info("Réservation annulée: {}", reservation.getNumeroReservation());
        
        return reservationMapper.toDto(cancelledReservation);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getReservationsEnAttente() {
        log.debug("Fetching pending reservations");
        return reservationMapper.toDtoList(reservationRepository.findReservationsEnAttente());
    }
    
    @Override
    public void traiterReservationsExpirees() {
        log.debug("Processing expired reservations");
        
        LocalDateTime limitDate = LocalDateTime.now().minusHours(24);
        List<Reservation> reservationsExpirees = reservationRepository.findReservationsExpirees(limitDate);
        
        for (Reservation reservation : reservationsExpirees) {
            reservation.setStatut(StatutReservation.EXPIREE);
            volService.updatePlacesDisponibles(reservation.getVol().getId(), reservation.getNombrePassagers());
        }
        
        reservationRepository.saveAll(reservationsExpirees);
        log.info("Processed {} expired reservations", reservationsExpirees.size());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Double calculerPrixReservation(CreateReservationDTO createDTO) {
        Vol vol = volRepository.findById(createDTO.getVolId())
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé"));
        
        double prixUnitaire = vol.getPrixBase() * createDTO.getClasse().getMultiplicateur();
        double montantBase = prixUnitaire * createDTO.getPassagerIds().size();
        double taxes = montantBase * TAXE_POURCENTAGE;
        
        return montantBase + taxes;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Double getTotalRevenue() {
        log.debug("Calculating total revenue");
        Double revenue = reservationRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countReservationsByUser(Long userId) {
        return reservationRepository.countByUserId(userId);
    }
}
