package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.PassagerDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.ResourceNotFoundException;
import com.example.flightmanagementsystem.mapper.PassagerMapper;
import com.example.flightmanagementsystem.model.Passager;
import com.example.flightmanagementsystem.repository.PassagerRepository;
import com.example.flightmanagementsystem.service.PassagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PassagerServiceImpl implements PassagerService {
    
    private final PassagerRepository passagerRepository;
    private final PassagerMapper passagerMapper;
    
    @Override
    public PassagerDTO createPassager(PassagerDTO passagerDTO) {
        log.debug("Creating passager: {}", passagerDTO);
        
        // Vérifier que l'email n'existe pas déjà
        if (passagerRepository.existsByEmail(passagerDTO.getEmail())) {
            throw new BadRequestException("Un passager avec cet email existe déjà");
        }
        
        // Vérifier le passeport s'il est fourni
        if (passagerDTO.getNumeroPasseport() != null && 
            passagerRepository.existsByNumeroPasseport(passagerDTO.getNumeroPasseport())) {
            throw new BadRequestException("Un passager avec ce numéro de passeport existe déjà");
        }
        
        // Vérifier la carte d'identité si fournie
        if (passagerDTO.getNumeroCarteIdentite() != null && 
            passagerRepository.existsByNumeroCarteIdentite(passagerDTO.getNumeroCarteIdentite())) {
            throw new BadRequestException("Un passager avec ce numéro de carte d'identité existe déjà");
        }
        
        Passager passager = passagerMapper.toEntity(passagerDTO);
        Passager savedPassager = passagerRepository.save(passager);
        
        log.info("Passager créé avec succès: {}", savedPassager.getId());
        return passagerMapper.toDto(savedPassager);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PassagerDTO getPassagerById(Long id) {
        log.debug("Fetching passager with id: {}", id);
        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec l'id: " + id));
        return passagerMapper.toDto(passager);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PassagerDTO getPassagerByEmail(String email) {
        log.debug("Fetching passager by email: {}", email);
        Passager passager = passagerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec l'email: " + email));
        return passagerMapper.toDto(passager);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PassagerDTO> getAllPassagers() {
        log.debug("Fetching all passagers");
        return passagerMapper.toDtoList(passagerRepository.findAll());
    }
    
    @Override
    public PassagerDTO updatePassager(Long id, PassagerDTO passagerDTO) {
        log.debug("Updating passager with id: {}", id);
        
        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec l'id: " + id));
        
        // Vérifier l'email si changé
        if (passagerDTO.getEmail() != null && 
            !passagerDTO.getEmail().equals(passager.getEmail()) &&
            passagerRepository.existsByEmail(passagerDTO.getEmail())) {
            throw new BadRequestException("Un autre passager avec cet email existe déjà");
        }
        
        passagerMapper.updateEntityFromDto(passagerDTO, passager);
        Passager updatedPassager = passagerRepository.save(passager);
        
        log.info("Passager mis à jour avec succès: {}", id);
        return passagerMapper.toDto(updatedPassager);
    }
    
    @Override
    public void deletePassager(Long id) {
        log.debug("Deleting passager with id: {}", id);
        
        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec l'id: " + id));
        
        // Vérifier qu'il n'y a pas de réservations actives
        if (!passager.getReservations().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer un passager ayant des réservations");
        }
        
        passagerRepository.deleteById(id);
        log.info("Passager supprimé avec succès: {}", id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PassagerDTO getPassagerByPasseport(String numeroPasseport) {
        log.debug("Fetching passager by passeport: {}", numeroPasseport);
        Passager passager = passagerRepository.findByNumeroPasseport(numeroPasseport)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec ce numéro de passeport"));
        return passagerMapper.toDto(passager);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PassagerDTO getPassagerByCarteIdentite(String numeroCarteIdentite) {
        log.debug("Fetching passager by carte identite: {}", numeroCarteIdentite);
        Passager passager = passagerRepository.findByNumeroCarteIdentite(numeroCarteIdentite)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec ce numéro de carte d'identité"));
        return passagerMapper.toDto(passager);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PassagerDTO> searchPassagers(String keyword) {
        log.debug("Searching passagers with keyword: {}", keyword);
        return passagerMapper.toDtoList(passagerRepository.searchByNomOrPrenom(keyword));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PassagerDTO> getPassagersByNationalite(String nationalite) {
        log.debug("Fetching passagers by nationalite: {}", nationalite);
        return passagerMapper.toDtoList(passagerRepository.findByNationalite(nationalite));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PassagerDTO> getPassagersByVol(Long volId) {
        log.debug("Fetching passagers by vol: {}", volId);
        return passagerMapper.toDtoList(passagerRepository.findPassagersByVol(volId));
    }
    
    @Override
    @Transactional(readOnly = true)
    public PassagerDTO getPassagerByUserId(Long userId) {
        log.debug("Fetching passager by user id: {}", userId);
        Passager passager = passagerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé pour cet utilisateur"));
        return passagerMapper.toDto(passager);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return passagerRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByPasseport(String numeroPasseport) {
        return passagerRepository.existsByNumeroPasseport(numeroPasseport);
    }
}
