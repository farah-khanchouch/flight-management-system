package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.AvionDTO;
import com.example.flightmanagementsystem.model.enums.StatutAvion;
import com.example.flightmanagementsystem.service.AvionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/avions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Avions", description = "Gestion des avions")
public class AvionController {
    
    private final AvionService avionService;
    
    @GetMapping
    @Operation(summary = "Liste de tous les avions", description = "Récupérer tous les avions")
    public ResponseEntity<List<AvionDTO>> getAllAvions() {
        return ResponseEntity.ok(avionService.getAllAvions());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un avion", description = "Récupérer un avion par son ID")
    public ResponseEntity<AvionDTO> getAvionById(@PathVariable Long id) {
        return ResponseEntity.ok(avionService.getAvionById(id));
    }
    
    @GetMapping("/serie/{numeroSerie}")
    @Operation(summary = "Avion par numéro de série", description = "Récupérer un avion par son numéro de série")
    public ResponseEntity<AvionDTO> getAvionByNumeroSerie(@PathVariable String numeroSerie) {
        return ResponseEntity.ok(avionService.getAvionByNumeroSerie(numeroSerie));
    }
    
    @PostMapping
    @Operation(summary = "Créer un avion", description = "Créer un nouvel avion")
    public ResponseEntity<AvionDTO> createAvion(@Valid @RequestBody AvionDTO avionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avionService.createAvion(avionDTO));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un avion", description = "Modifier un avion existant")
    public ResponseEntity<AvionDTO> updateAvion(@PathVariable Long id, @Valid @RequestBody AvionDTO avionDTO) {
        return ResponseEntity.ok(avionService.updateAvion(id, avionDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un avion", description = "Supprimer un avion (ADMIN uniquement)")
    public ResponseEntity<Void> deleteAvion(@PathVariable Long id) {
        avionService.deleteAvion(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/type/{typeAvion}")
    @Operation(summary = "Avions par type", description = "Récupérer les avions par type")
    public ResponseEntity<List<AvionDTO>> getAvionsByType(@PathVariable String typeAvion) {
        return ResponseEntity.ok(avionService.getAvionsByType(typeAvion));
    }
    
    @GetMapping("/statut/{statut}")
    @Operation(summary = "Avions par statut", description = "Récupérer les avions par statut")
    public ResponseEntity<List<AvionDTO>> getAvionsByStatut(@PathVariable StatutAvion statut) {
        return ResponseEntity.ok(avionService.getAvionsByStatut(statut));
    }
    
    @GetMapping("/operationnels")
    @Operation(summary = "Avions opérationnels", description = "Récupérer tous les avions opérationnels")
    public ResponseEntity<List<AvionDTO>> getAvionsOperationnels() {
        return ResponseEntity.ok(avionService.getAvionsOperationnels());
    }
    
    @GetMapping("/disponibles")
    @Operation(summary = "Avions disponibles", description = "Récupérer les avions disponibles")
    public ResponseEntity<List<AvionDTO>> getAvionsDisponibles() {
        return ResponseEntity.ok(avionService.getAvionsDisponibles());
    }
    
    @GetMapping("/maintenance")
    @Operation(summary = "Avions nécessitant maintenance", description = "Récupérer les avions nécessitant une maintenance")
    public ResponseEntity<List<AvionDTO>> getAvionsNecessitantMaintenance() {
        return ResponseEntity.ok(avionService.getAvionsNecessitantMaintenance());
    }
    
    @PutMapping("/{id}/statut")
    @Operation(summary = "Changer le statut", description = "Modifier le statut d'un avion")
    public ResponseEntity<AvionDTO> updateStatut(@PathVariable Long id, @RequestParam StatutAvion statut) {
        return ResponseEntity.ok(avionService.updateStatut(id, statut));
    }
    
    @PutMapping("/{id}/maintenance")
    @Operation(summary = "Planifier maintenance", description = "Planifier une maintenance pour un avion")
    public ResponseEntity<AvionDTO> planifierMaintenance(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateMaintenance) {
        return ResponseEntity.ok(avionService.planifierMaintenance(id, dateMaintenance));
    }
    
    @GetMapping("/statistiques/capacite")
    @Operation(summary = "Capacité totale", description = "Obtenir la capacité totale de la flotte")
    public ResponseEntity<Integer> getTotalCapaciteFlotte() {
        return ResponseEntity.ok(avionService.getTotalCapaciteFlotte());
    }
}
