package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.CreateReservationDTO;
import com.example.flightmanagementsystem.dto.ReservationDTO;
import com.example.flightmanagementsystem.model.enums.StatutReservation;
import com.example.flightmanagementsystem.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService service;

    // CRUD Operations
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(service.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getReservationById(id));
    }

    @GetMapping("/numero/{numeroReservation}")
    public ResponseEntity<ReservationDTO> getReservationByNumero(@PathVariable String numeroReservation) {
        return ResponseEntity.ok(service.getReservationByNumero(numeroReservation));
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(
            @Valid @RequestBody CreateReservationDTO createReservationDTO,
            @RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok(service.createReservation(createReservationDTO, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(service.updateReservation(id, reservationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // Business Logic Endpoints
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getReservationsByUser(userId));
    }

    @GetMapping("/vol/{volId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByVol(@PathVariable Long volId) {
        return ResponseEntity.ok(service.getReservationsByVol(volId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByStatut(@PathVariable StatutReservation statut) {
        return ResponseEntity.ok(service.getReservationsByStatut(statut));
    }

    @GetMapping("/attente")
    public ResponseEntity<List<ReservationDTO>> getReservationsEnAttente() {
        return ResponseEntity.ok(service.getReservationsEnAttente());
    }

    @PostMapping("/{id}/confirmer")
    public ResponseEntity<ReservationDTO> confirmerReservation(@PathVariable Long id, @RequestParam String methodePaiement, @RequestParam String referencePaiement) {
        return ResponseEntity.ok(service.confirmerReservation(id, methodePaiement, referencePaiement));
    }

    @PostMapping("/{id}/annuler")
    public ResponseEntity<ReservationDTO> annulerReservation(@PathVariable Long id) {
        return ResponseEntity.ok(service.annulerReservation(id));
    }

    @PostMapping("/traiter-expirees")
    public ResponseEntity<Void> traiterReservationsExpirees() {
        service.traiterReservationsExpirees();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculer-prix")
    public ResponseEntity<Double> calculerPrixReservation(@Valid @RequestBody CreateReservationDTO createReservationDTO) {
        return ResponseEntity.ok(service.calculerPrixReservation(createReservationDTO));
    }

    @GetMapping("/revenue-total")
    public ResponseEntity<Double> getTotalRevenue() {
        return ResponseEntity.ok(service.getTotalRevenue());
    }

    @GetMapping("/count/user/{userId}")
    public ResponseEntity<Long> countReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.countReservationsByUser(userId));
    }
}
