package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.AvionDTO;
import com.example.flightmanagementsystem.model.enums.StatutAvion;
import com.example.flightmanagementsystem.service.AvionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/avions")
public class AvionController {

    @Autowired
    private AvionService service;

    // CRUD Operations
    @GetMapping
    public ResponseEntity<List<AvionDTO>> getAllAvions() {
        return ResponseEntity.ok(service.getAllAvions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvionDTO> getAvionById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAvionById(id));
    }

    @GetMapping("/numero-serie/{numeroSerie}")
    public ResponseEntity<AvionDTO> getAvionByNumeroSerie(@PathVariable String numeroSerie) {
        return ResponseEntity.ok(service.getAvionByNumeroSerie(numeroSerie));
    }

    @PostMapping
    public ResponseEntity<AvionDTO> createAvion(@Valid @RequestBody AvionDTO avionDTO) {
        return ResponseEntity.ok(service.createAvion(avionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvionDTO> updateAvion(@PathVariable Long id, @Valid @RequestBody AvionDTO avionDTO) {
        return ResponseEntity.ok(service.updateAvion(id, avionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvion(@PathVariable Long id) {
        service.deleteAvion(id);
        return ResponseEntity.noContent().build();
    }

    // Business Logic Endpoints
    @GetMapping("/by-type")
    public ResponseEntity<List<AvionDTO>> getAvionsByType(@RequestParam String typeAvion) {
        return ResponseEntity.ok(service.getAvionsByType(typeAvion));
    }

    @GetMapping("/by-statut")
    public ResponseEntity<List<AvionDTO>> getAvionsByStatut(@RequestParam StatutAvion statut) {
        return ResponseEntity.ok(service.getAvionsByStatut(statut));
    }

    @GetMapping("/operationnels")
    public ResponseEntity<List<AvionDTO>> getAvionsOperationnels() {
        return ResponseEntity.ok(service.getAvionsOperationnels());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<AvionDTO>> getAvionsDisponibles() {
        return ResponseEntity.ok(service.getAvionsDisponibles());
    }

    @GetMapping("/maintenance")
    public ResponseEntity<List<AvionDTO>> getAvionsNecessitantMaintenance() {
        return ResponseEntity.ok(service.getAvionsNecessitantMaintenance());
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<AvionDTO> updateStatut(@PathVariable Long id, @RequestParam StatutAvion statut) {
        return ResponseEntity.ok(service.updateStatut(id, statut));
    }

    @PutMapping("/{id}/maintenance")
    public ResponseEntity<AvionDTO> planifierMaintenance(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateMaintenance) {
        return ResponseEntity.ok(service.planifierMaintenance(id, dateMaintenance));
    }

    @GetMapping("/capacite-totale")
    public ResponseEntity<Integer> getTotalCapaciteFlotte() {
        return ResponseEntity.ok(service.getTotalCapaciteFlotte());
    }

    @GetMapping("/count-by-statut")
    public ResponseEntity<Long> countByStatut(@RequestParam StatutAvion statut) {
        return ResponseEntity.ok(service.countByStatut(statut));
    }
}
