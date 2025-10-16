package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.EquipageDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.ResourceNotFoundException;
import com.example.flightmanagementsystem.mapper.EquipageMapper;
import com.example.flightmanagementsystem.model.Equipage;
import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import com.example.flightmanagementsystem.model.enums.StatutEquipage;
import com.example.flightmanagementsystem.repository.EquipageRepository;
import com.example.flightmanagementsystem.service.EquipageService;
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
public class EquipageServiceImpl implements EquipageService {
    
    private final EquipageRepository equipageRepository;
    private final EquipageMapper equipageMapper;
    
    @Override
    public EquipageDTO createEquipage(EquipageDTO equipageDTO) {
        log.debug("Creating equipage: {}", equipageDTO);
        
        if (equipageDTO.getNumeroLicence() != null && 
            equipageRepository.existsByNumeroLicence(equipageDTO.getNumeroLicence())) {
            throw new BadRequestException("Un membre d'équipage avec ce numéro de licence existe déjà");
        }
        
        if (equipageDTO.getEmail() != null && 
            equipageRepository.existsByEmail(equipageDTO.getEmail())) {
            throw new BadRequestException("Un membre d'équipage avec cet email existe déjà");
        }
        
        Equipage equipage = equipageMapper.toEntity(equipageDTO);
        Equipage savedEquipage = equipageRepository.save(equipage);
        
        log.info("Equipage créé avec succès: {}", savedEquipage.getId());
        return equipageMapper.toDto(savedEquipage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public EquipageDTO getEquipageById(Long id) {
        Equipage equipage = equipageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipage non trouvé avec l'id: " + id));
        return equipageMapper.toDto(equipage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public EquipageDTO getEquipageByLicence(String numeroLicence) {
        Equipage equipage = equipageRepository.findByNumeroLicence(numeroLicence)
                .orElseThrow(() -> new ResourceNotFoundException("Equipage non trouvé avec cette licence"));
        return equipageMapper.toDto(equipage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EquipageDTO> getAllEquipages() {
        return equipageMapper.toDtoList(equipageRepository.findAll());
    }
    
    @Override
    public EquipageDTO updateEquipage(Long id, EquipageDTO equipageDTO) {
        Equipage equipage = equipageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipage non trouvé"));
        
        equipageMapper.updateEntityFromDto(equipageDTO, equipage);
        Equipage updatedEquipage = equipageRepository.save(equipage);
        
        log.info("Equipage mis à jour: {}", id);
        return equipageMapper.toDto(updatedEquipage);
    }
    
    @Override
    public void deleteEquipage(Long id) {
        if (!equipageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipage non trouvé");
        }
        equipageRepository.deleteById(id);
        log.info("Equipage supprimé: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EquipageDTO> getEquipagesByFonction(FonctionEquipage fonction) {
        return equipageMapper.toDtoList(equipageRepository.findByFonction(fonction));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EquipageDTO> getEquipagesActifs() {
        return equipageMapper.toDtoList(equipageRepository.findByStatut(StatutEquipage.ACTIF));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EquipageDTO> getEquipagesDisponibles(LocalDate date) {
        return equipageMapper.toDtoList(equipageRepository.findEquipageDisponible(date));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EquipageDTO> getPilotesActifs() {
        return equipageMapper.toDtoList(equipageRepository.findPilotesActifs());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EquipageDTO> getLicencesExpirantBientot() {
        LocalDate now = LocalDate.now();
        LocalDate dans30Jours = now.plusDays(30);
        return equipageMapper.toDtoList(
            equipageRepository.findLicencesExpirantBientot(now, dans30Jours)
        );
    }
}
