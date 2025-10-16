package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.dto.SearchVolDTO;
import com.example.flightmanagementsystem.model.enums.StatutVol;

import java.time.LocalDate;
import java.util.List;

public interface VolService {
    
    // CRUD Operations
    VolDTO createVol(VolDTO volDTO);
    
    VolDTO getVolById(Long id);
    
    List<VolDTO> getAllVols();
    
    VolDTO updateVol(Long id, VolDTO volDTO);
    
    void deleteVol(Long id);
    
    // Business Logic
    VolDTO getVolByCode(String codeIATA, String numeroVol, LocalDate dateVol);
    
    List<VolDTO> searchVols(SearchVolDTO searchDTO);
    
    List<VolDTO> getVolsByDate(LocalDate date);
    
    List<VolDTO> getVolsByRoute(Long aeroportDepartId, Long aeroportArriveeId);
    
    List<VolDTO> getVolsByStatut(StatutVol statut);
    
    List<VolDTO> getVolsDisponibles(LocalDate date);
    
    List<VolDTO> getVolsAujourdhui();
    
    List<VolDTO> getVolsByAvion(Long avionId);
    
    VolDTO updateStatutVol(Long id, StatutVol statut);
    
    VolDTO assignEquipage(Long volId, List<Long> equipageIds);
    
    boolean verifierDisponibilite(Long volId, Integer nombrePassagers);
    
    void updatePlacesDisponibles(Long volId, Integer places);
}
