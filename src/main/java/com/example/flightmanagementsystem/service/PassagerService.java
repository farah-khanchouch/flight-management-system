package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.PassagerDTO;

import java.util.List;

public interface PassagerService {
    
    // CRUD Operations
    PassagerDTO createPassager(PassagerDTO passagerDTO);
    
    PassagerDTO getPassagerById(Long id);
    
    PassagerDTO getPassagerByEmail(String email);
    
    List<PassagerDTO> getAllPassagers();
    
    PassagerDTO updatePassager(Long id, PassagerDTO passagerDTO);
    
    void deletePassager(Long id);
    
    // Business Logic
    PassagerDTO getPassagerByPasseport(String numeroPasseport);
    
    PassagerDTO getPassagerByCarteIdentite(String numeroCarteIdentite);
    
    List<PassagerDTO> searchPassagers(String keyword);
    
    List<PassagerDTO> getPassagersByNationalite(String nationalite);
    
    List<PassagerDTO> getPassagersByVol(Long volId);
    
    PassagerDTO getPassagerByUserId(Long userId);
    
    boolean existsByEmail(String email);
    
    boolean existsByPasseport(String numeroPasseport);
}
