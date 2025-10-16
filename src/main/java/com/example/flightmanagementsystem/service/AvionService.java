package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.AvionDTO;
import com.example.flightmanagementsystem.model.enums.StatutAvion;

import java.time.LocalDateTime;
import java.util.List;

public interface AvionService {
    
    // CRUD Operations
    AvionDTO createAvion(AvionDTO avionDTO);
    
    AvionDTO getAvionById(Long id);
    
    AvionDTO getAvionByNumeroSerie(String numeroSerie);
    
    List<AvionDTO> getAllAvions();
    
    AvionDTO updateAvion(Long id, AvionDTO avionDTO);
    
    void deleteAvion(Long id);
    
    // Business Logic
    List<AvionDTO> getAvionsByType(String typeAvion);
    
    List<AvionDTO> getAvionsByStatut(StatutAvion statut);
    
    List<AvionDTO> getAvionsOperationnels();
    
    List<AvionDTO> getAvionsDisponibles();
    
    List<AvionDTO> getAvionsNecessitantMaintenance();
    
    AvionDTO updateStatut(Long id, StatutAvion statut);
    
    AvionDTO planifierMaintenance(Long id, LocalDateTime dateMaintenance);
    
    Integer getTotalCapaciteFlotte();
    
    long countByStatut(StatutAvion statut);
}
