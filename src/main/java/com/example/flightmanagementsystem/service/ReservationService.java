package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.CreateReservationDTO;
import com.example.flightmanagementsystem.dto.ReservationDTO;
import com.example.flightmanagementsystem.model.enums.StatutReservation;

import java.util.List;

public interface ReservationService {
    
    // CRUD Operations
    ReservationDTO createReservation(CreateReservationDTO createReservationDTO, Long userId);
    
    ReservationDTO getReservationById(Long id);
    
    ReservationDTO getReservationByNumero(String numeroReservation);
    
    List<ReservationDTO> getAllReservations();
    
    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);
    
    void deleteReservation(Long id);
    
    // Business Logic
    List<ReservationDTO> getReservationsByUser(Long userId);
    
    List<ReservationDTO> getReservationsByVol(Long volId);
    
    List<ReservationDTO> getReservationsByStatut(StatutReservation statut);
    
    ReservationDTO confirmerReservation(Long id, String methodePaiement, String referencePaiement);
    
    ReservationDTO annulerReservation(Long id);
    
    List<ReservationDTO> getReservationsEnAttente();
    
    void traiterReservationsExpirees();
    
    Double calculerPrixReservation(CreateReservationDTO createReservationDTO);
    
    Double getTotalRevenue();
    
    long countReservationsByUser(Long userId);
}
