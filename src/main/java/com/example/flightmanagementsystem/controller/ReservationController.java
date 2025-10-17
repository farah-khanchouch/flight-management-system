package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.CreateReservationDTO;
import com.example.flightmanagementsystem.dto.ReservationDTO;
import com.example.flightmanagementsystem.model.enums.StatutReservation;
import com.example.flightmanagementsystem.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Réservations", description = "Gestion des réservations")
public class ReservationController {
    
    private final ReservationService reservationService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Liste de toutes les réservations", description = "Récupérer toutes les réservations (ADMIN/AGENT)")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Détails d'une réservation", description = "Récupérer une réservation par son ID (ADMIN/AGENT)")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }
    
    @GetMapping("/numero/{numeroReservation}")
    @Operation(summary = "Réservation par numéro", description = "Récupérer une réservation par son numéro")
    public ResponseEntity<ReservationDTO> getReservationByNumero(@PathVariable String numeroReservation) {
        return ResponseEntity.ok(reservationService.getReservationByNumero(numeroReservation));
    }
    
    @GetMapping("/me")
    @Operation(summary = "Mes réservations", description = "Récupérer mes réservations")
    public ResponseEntity<List<ReservationDTO>> getMyReservations(Authentication authentication) {
        com.example.flightmanagementsystem.model.User user = 
            (com.example.flightmanagementsystem.model.User) authentication.getPrincipal();
        return ResponseEntity.ok(reservationService.getReservationsByUser(user.getId()));
    }
    
    @PostMapping
    @Operation(summary = "Créer une réservation", description = "Créer une nouvelle réservation")
    public ResponseEntity<ReservationDTO> createReservation(
            @Valid @RequestBody CreateReservationDTO createReservationDTO,
            Authentication authentication) {
        com.example.flightmanagementsystem.model.User user = 
            (com.example.flightmanagementsystem.model.User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reservationService.createReservation(createReservationDTO, user.getId()));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Modifier une réservation", description = "Modifier une réservation existante (ADMIN/AGENT)")
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une réservation", description = "Supprimer une réservation (ADMIN uniquement)")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/confirmer")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Confirmer réservation", description = "Confirmer une réservation avec paiement (ADMIN/AGENT)")
    public ResponseEntity<ReservationDTO> confirmerReservation(
            @PathVariable Long id,
            @RequestParam String methodePaiement,
            @RequestParam String referencePaiement) {
        return ResponseEntity.ok(reservationService.confirmerReservation(id, methodePaiement, referencePaiement));
    }
    
    @PutMapping("/{id}/annuler")
    @Operation(summary = "Annuler réservation", description = "Annuler une réservation")
    public ResponseEntity<ReservationDTO> annulerReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.annulerReservation(id));
    }
    
    @GetMapping("/vol/{volId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'MANAGER')")
    @Operation(summary = "Réservations d'un vol", description = "Récupérer les réservations d'un vol")
    public ResponseEntity<List<ReservationDTO>> getReservationsByVol(@PathVariable Long volId) {
        return ResponseEntity.ok(reservationService.getReservationsByVol(volId));
    }
    
    @GetMapping("/statut/{statut}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Réservations par statut", description = "Récupérer les réservations par statut")
    public ResponseEntity<List<ReservationDTO>> getReservationsByStatut(@PathVariable StatutReservation statut) {
        return ResponseEntity.ok(reservationService.getReservationsByStatut(statut));
    }
    
    @GetMapping("/en-attente")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Réservations en attente", description = "Récupérer les réservations en attente")
    public ResponseEntity<List<ReservationDTO>> getReservationsEnAttente() {
        return ResponseEntity.ok(reservationService.getReservationsEnAttente());
    }
    
    @PostMapping("/calculer-prix")
    @Operation(summary = "Calculer prix", description = "Calculer le prix d'une réservation")
    public ResponseEntity<Double> calculerPrixReservation(@Valid @RequestBody CreateReservationDTO createReservationDTO) {
        return ResponseEntity.ok(reservationService.calculerPrixReservation(createReservationDTO));
    }
    
    @GetMapping("/statistiques/revenue")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Chiffre d'affaires", description = "Obtenir le chiffre d'affaires total")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(reservationService.getTotalRevenue());
    }
}
