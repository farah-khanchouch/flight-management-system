package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.AvionDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.ResourceNotFoundException;
import com.example.flightmanagementsystem.mapper.AvionMapper;
import com.example.flightmanagementsystem.model.Avion;
import com.example.flightmanagementsystem.model.enums.StatutAvion;
import com.example.flightmanagementsystem.repository.AvionRepository;
import com.example.flightmanagementsystem.service.AvionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AvionServiceImpl implements AvionService {
    
    private final AvionRepository avionRepository;
    private final AvionMapper avionMapper;
    
    @Override
    public AvionDTO createAvion(AvionDTO avionDTO) {
        log.debug("Creating avion: {}", avionDTO);
        
        // Vérifier si le numéro de série existe déjà
        if (avionRepository.existsByNumeroSerie(avionDTO.getNumeroSerie())) {
            throw new BadRequestException("Un avion avec ce numéro de série existe déjà");
        }
        
        Avion avion = avionMapper.toEntity(avionDTO);
        Avion savedAvion = avionRepository.save(avion);
        
        log.info("Avion créé avec succès: {}", savedAvion.getId());
        return avionMapper.toDto(savedAvion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AvionDTO getAvionById(Long id) {
        log.debug("Fetching avion with id: {}", id);
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'id: " + id));
        return avionMapper.toDto(avion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AvionDTO getAvionByNumeroSerie(String numeroSerie) {
        log.debug("Fetching avion by numero serie: {}", numeroSerie);
        Avion avion = avionRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec le numéro de série: " + numeroSerie));
        return avionMapper.toDto(avion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> getAllAvions() {
        log.debug("Fetching all avions");
        return avionMapper.toDtoList(avionRepository.findAll());
    }
    
    @Override
    public AvionDTO updateAvion(Long id, AvionDTO avionDTO) {
        log.debug("Updating avion with id: {}", id);
        
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'id: " + id));
        
        // Vérifier si le numéro de série a changé et s'il existe déjà
        if (avionDTO.getNumeroSerie() != null && 
            !avionDTO.getNumeroSerie().equals(avion.getNumeroSerie()) &&
            avionRepository.existsByNumeroSerie(avionDTO.getNumeroSerie())) {
            throw new BadRequestException("Un autre avion avec ce numéro de série existe déjà");
        }
        
        avionMapper.updateEntityFromDto(avionDTO, avion);
        Avion updatedAvion = avionRepository.save(avion);
        
        log.info("Avion mis à jour avec succès: {}", id);
        return avionMapper.toDto(updatedAvion);
    }
    
    @Override
    public void deleteAvion(Long id) {
        log.debug("Deleting avion with id: {}", id);
        
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'id: " + id));
        
        // Vérifier qu'il n'y a pas de vols associés
        if (!avion.getVols().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer un avion ayant des vols associés");
        }
        
        avionRepository.deleteById(id);
        log.info("Avion supprimé avec succès: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> getAvionsByType(String typeAvion) {
        log.debug("Fetching avions by type: {}", typeAvion);
        return avionMapper.toDtoList(avionRepository.findByTypeAvion(typeAvion));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> getAvionsByStatut(StatutAvion statut) {
        log.debug("Fetching avions by statut: {}", statut);
        return avionMapper.toDtoList(avionRepository.findByStatut(statut));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> getAvionsOperationnels() {
        log.debug("Fetching operational avions");
        return avionMapper.toDtoList(avionRepository.findAvionsOperationnels());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> getAvionsDisponibles() {
        log.debug("Fetching available avions");
        return avionMapper.toDtoList(avionRepository.findAvionsDisponibles());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<AvionDTO> getAvionsNecessitantMaintenance() {
        log.debug("Fetching avions needing maintenance");
        return avionMapper.toDtoList(avionRepository.findAvionsNecessitantMaintenance(LocalDateTime.now()));
    }
    
    @Override
    public AvionDTO updateStatut(Long id, StatutAvion statut) {
        log.debug("Updating avion statut: {} to {}", id, statut);
        
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'id: " + id));
        
        avion.setStatut(statut);
        Avion updatedAvion = avionRepository.save(avion);
        
        log.info("Avion statut updated: {} -> {}", id, statut);
        return avionMapper.toDto(updatedAvion);
    }
    
    @Override
    public AvionDTO planifierMaintenance(Long id, LocalDateTime dateMaintenance) {
        log.debug("Scheduling maintenance for avion: {} on {}", id, dateMaintenance);
        
        Avion avion = avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avion non trouvé avec l'id: " + id));
        
        avion.setProchaineMaintenace(dateMaintenance);
        Avion updatedAvion = avionRepository.save(avion);
        
        log.info("Maintenance scheduled for avion: {}", id);
        return avionMapper.toDto(updatedAvion);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Integer getTotalCapaciteFlotte() {
        log.debug("Calculating total fleet capacity");
        Integer capacite = avionRepository.getTotalCapaciteFlotte();
        return capacite != null ? capacite : 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countByStatut(StatutAvion statut) {
        log.debug("Counting avions by statut: {}", statut);
        return avionRepository.countByStatut(statut);
    }
}
