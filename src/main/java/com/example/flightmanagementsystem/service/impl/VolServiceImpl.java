package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.dto.SearchVolDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.ResourceNotFoundException;
import com.example.flightmanagementsystem.mapper.VolMapper;
import com.example.flightmanagementsystem.model.Avion;
import com.example.flightmanagementsystem.model.Equipage;
import com.example.flightmanagementsystem.model.Vol;
import com.example.flightmanagementsystem.model.enums.StatutVol;
import com.example.flightmanagementsystem.repository.AeroportRepository;
import com.example.flightmanagementsystem.repository.AvionRepository;
import com.example.flightmanagementsystem.repository.EquipageRepository;
import com.example.flightmanagementsystem.repository.VolRepository;
import com.example.flightmanagementsystem.service.VolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VolServiceImpl implements VolService {
    
    private final VolRepository volRepository;
    private final AvionRepository avionRepository;
    private final AeroportRepository aeroportRepository;
    private final EquipageRepository equipageRepository;
    private final VolMapper volMapper;
    
    @Override
    public VolDTO createVol(VolDTO volDTO) {
        log.debug("Creating vol: {}", volDTO);
        
        // Vérifier si le vol existe déjà
        if (volRepository.existsByCodeIATATransporteurAndNumeroVolAndDateVol(
                volDTO.getCodeIATATransporteur(),
                volDTO.getNumeroVol(),
                volDTO.getDateVol())) {
            throw new BadRequestException("Un vol avec ce code existe déjà pour cette date");
        }
        
        // Vérifier que l'avion existe et est disponible
        Avion avion = avionRepository.findById(volDTO.getAvionId())
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'id: " + volDTO.getAvionId()));
        
        if (!avion.isDisponible()) {
            throw new BadRequestException("L'avion n'est pas disponible");
        }
        
        // Vérifier que les aéroports existent
        if (!aeroportRepository.existsById(volDTO.getAeroportDepartId())) {
            throw new ResourceNotFoundException("Aéroport de départ non trouvé");
        }
        
        if (!aeroportRepository.existsById(volDTO.getAeroportArriveeId())) {
            throw new ResourceNotFoundException("Aéroport d'arrivée non trouvé");
        }
        
        // Vérifier que les aéroports sont différents
        if (volDTO.getAeroportDepartId().equals(volDTO.getAeroportArriveeId())) {
            throw new BadRequestException("L'aéroport de départ et d'arrivée doivent être différents");
        }
        
        Vol vol = volMapper.toEntity(volDTO);
        vol.setPlacesDisponibles(avion.getCapacite());
        vol.setStatut(StatutVol.PROGRAMME);
        
        Vol savedVol = volRepository.save(vol);
        log.info("Vol créé avec succès: {}", savedVol.getId());
        
        return volMapper.toDto(savedVol);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VolDTO getVolById(Long id) {
        log.debug("Fetching vol with id: {}", id);
        Vol vol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'id: " + id));
        return volMapper.toDto(vol);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getAllVols() {
        log.debug("Fetching all vols");
        return volMapper.toDtoList(volRepository.findAll());
    }
    
    @Override
    public VolDTO updateVol(Long id, VolDTO volDTO) {
        log.debug("Updating vol with id: {}", id);
        
        Vol vol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'id: " + id));
        
        // Vérifier si l'avion a changé
        if (volDTO.getAvionId() != null && !volDTO.getAvionId().equals(vol.getAvion().getId())) {
            Avion newAvion = avionRepository.findById(volDTO.getAvionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé"));
            
            if (!newAvion.isDisponible()) {
                throw new BadRequestException("Le nouvel avion n'est pas disponible");
            }
        }
        
        volMapper.updateEntityFromDto(volDTO, vol);
        Vol updatedVol = volRepository.save(vol);
        log.info("Vol mis à jour avec succès: {}", id);
        
        return volMapper.toDto(updatedVol);
    }
    
    @Override
    public void deleteVol(Long id) {
        log.debug("Deleting vol with id: {}", id);
        
        if (!volRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vol non trouvé avec l'id: " + id);
        }
        
        volRepository.deleteById(id);
        log.info("Vol supprimé avec succès: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public VolDTO getVolByCode(String codeIATA, String numeroVol, LocalDate dateVol) {
        log.debug("Fetching vol by code: {}{} on {}", codeIATA, numeroVol, dateVol);
        
        Vol vol = volRepository.findByCodeIATATransporteurAndNumeroVolAndDateVol(codeIATA, numeroVol, dateVol)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé"));
        
        return volMapper.toDto(vol);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> searchVols(SearchVolDTO searchDTO) {
        log.debug("Searching vols with criteria: {}", searchDTO);
        
        Long departId = null;
        Long arriveeId = null;
        
        if (searchDTO.getAeroportDepartCodeIATA() != null) {
            departId = aeroportRepository.findByCodeIATA(searchDTO.getAeroportDepartCodeIATA())
                    .orElseThrow(() -> new ResourceNotFoundException("Aéroport de départ non trouvé"))
                    .getId();
        }
        
        if (searchDTO.getAeroportArriveeCodeIATA() != null) {
            arriveeId = aeroportRepository.findByCodeIATA(searchDTO.getAeroportArriveeCodeIATA())
                    .orElseThrow(() -> new ResourceNotFoundException("Aéroport d'arrivée non trouvé"))
                    .getId();
        }
        
        List<Vol> vols;
        if (searchDTO.getDateVol() != null) {
            vols = volRepository.findByRouteAndDate(departId, arriveeId, searchDTO.getDateVol());
        } else {
            vols = volRepository.findByRoute(departId, arriveeId);
        }
        
        // Filtrer par nombre de passagers si spécifié
        if (searchDTO.getNombrePassagers() != null) {
            vols = vols.stream()
                    .filter(v -> v.getPlacesDisponibles() >= searchDTO.getNombrePassagers())
                    .toList();
        }
        
        return volMapper.toDtoList(vols);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getVolsByDate(LocalDate date) {
        log.debug("Fetching vols by date: {}", date);
        return volMapper.toDtoList(volRepository.findByDateVol(date));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getVolsByRoute(Long aeroportDepartId, Long aeroportArriveeId) {
        log.debug("Fetching vols by route: {} -> {}", aeroportDepartId, aeroportArriveeId);
        return volMapper.toDtoList(volRepository.findByRoute(aeroportDepartId, aeroportArriveeId));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getVolsByStatut(StatutVol statut) {
        log.debug("Fetching vols by statut: {}", statut);
        return volMapper.toDtoList(volRepository.findByStatut(statut));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getVolsDisponibles(LocalDate date) {
        log.debug("Fetching available vols for date: {}", date);
        return volMapper.toDtoList(volRepository.findVolsDisponibles(date));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getVolsAujourdhui() {
        log.debug("Fetching today's vols");
        return volMapper.toDtoList(volRepository.findVolsAujourdhui());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VolDTO> getVolsByAvion(Long avionId) {
        log.debug("Fetching vols by avion: {}", avionId);
        return volMapper.toDtoList(volRepository.findByAvion(avionId));
    }
    
    @Override
    public VolDTO updateStatutVol(Long id, StatutVol statut) {
        log.debug("Updating vol statut: {} to {}", id, statut);
        
        Vol vol = volRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé avec l'id: " + id));
        
        vol.setStatut(statut);
        Vol updatedVol = volRepository.save(vol);
        
        log.info("Vol statut updated: {} -> {}", id, statut);
        return volMapper.toDto(updatedVol);
    }
    
    @Override
    public VolDTO assignEquipage(Long volId, List<Long> equipageIds) {
        log.debug("Assigning equipage to vol: {}", volId);
        
        Vol vol = volRepository.findById(volId)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé"));
        
        // Vérifier qu'il y a au moins un pilote et un copilote
        List<Equipage> equipages = equipageRepository.findAllById(equipageIds);
        
        long pilotes = equipages.stream()
                .filter(Equipage::isPilote)
                .count();
        
        if (pilotes < 2) {
            throw new BadRequestException("Un vol doit avoir au moins un pilote et un copilote");
        }
        
        // Assigner l'équipage
        vol.getEquipages().clear();
        equipages.forEach(vol::addEquipage);
        
        Vol savedVol = volRepository.save(vol);
        log.info("Equipage assigned to vol: {}", volId);
        
        return volMapper.toDto(savedVol);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean verifierDisponibilite(Long volId, Integer nombrePassagers) {
        Vol vol = volRepository.findById(volId)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé"));
        
        return vol.getPlacesDisponibles() >= nombrePassagers;
    }
    
    @Override
    public void updatePlacesDisponibles(Long volId, Integer places) {
        Vol vol = volRepository.findById(volId)
                .orElseThrow(() -> new ResourceNotFoundException("Vol non trouvé"));
        
        vol.setPlacesDisponibles(vol.getPlacesDisponibles() + places);
        volRepository.save(vol);
        
        log.info("Places disponibles updated for vol {}: {}", volId, vol.getPlacesDisponibles());
    }
}
