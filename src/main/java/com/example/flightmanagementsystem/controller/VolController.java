package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.SearchVolDTO;
import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.model.enums.StatutVol;
import com.example.flightmanagementsystem.service.VolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vols")
@RequiredArgsConstructor
@Tag(name = "Vols", description = "Gestion des vols")
public class VolController {
    
    private final VolService volService;
    
    @GetMapping
    @Operation(summary = "Liste de tous les vols", description = "Récupérer tous les vols")
    public ResponseEntity<List<VolDTO>> getAllVols() {
        return ResponseEntity.ok(volService.getAllVols());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un vol", description = "Récupérer un vol par son ID")
    public ResponseEntity<VolDTO> getVolById(@PathVariable Long id) {
        return ResponseEntity.ok(volService.getVolById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Créer un vol", description = "Créer un nouveau vol (ADMIN/MANAGER)")
    public ResponseEntity<VolDTO> createVol(@Valid @RequestBody VolDTO volDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(volService.createVol(volDTO));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Modifier un vol", description = "Modifier un vol existant (ADMIN/MANAGER)")
    public ResponseEntity<VolDTO> updateVol(@PathVariable Long id, @Valid @RequestBody VolDTO volDTO) {
        return ResponseEntity.ok(volService.updateVol(id, volDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Supprimer un vol", description = "Supprimer un vol (ADMIN uniquement)")
    public ResponseEntity<Void> deleteVol(@PathVariable Long id) {
        volService.deleteVol(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/search")
    @Operation(summary = "Rechercher des vols", description = "Rechercher des vols selon des critères")
    public ResponseEntity<List<VolDTO>> searchVols(@RequestBody SearchVolDTO searchDTO) {
        return ResponseEntity.ok(volService.searchVols(searchDTO));
    }
    
    @GetMapping("/date/{date}")
    @Operation(summary = "Vols par date", description = "Récupérer les vols d'une date spécifique")
    public ResponseEntity<List<VolDTO>> getVolsByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(volService.getVolsByDate(date));
    }
    
    @GetMapping("/today")
    @Operation(summary = "Vols d'aujourd'hui", description = "Récupérer les vols du jour")
    public ResponseEntity<List<VolDTO>> getVolsToday() {
        return ResponseEntity.ok(volService.getVolsAujourdhui());
    }
    
    @GetMapping("/disponibles")
    @Operation(summary = "Vols disponibles", description = "Récupérer les vols avec places disponibles")
    public ResponseEntity<List<VolDTO>> getVolsDisponibles(@RequestParam LocalDate date) {
        return ResponseEntity.ok(volService.getVolsDisponibles(date));
    }
    
    @GetMapping("/statut/{statut}")
    @Operation(summary = "Vols par statut", description = "Récupérer les vols par statut")
    public ResponseEntity<List<VolDTO>> getVolsByStatut(@PathVariable StatutVol statut) {
        return ResponseEntity.ok(volService.getVolsByStatut(statut));
    }
    
    @PutMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Changer le statut", description = "Modifier le statut d'un vol (ADMIN/MANAGER)")
    public ResponseEntity<VolDTO> updateStatut(@PathVariable Long id, @RequestParam StatutVol statut) {
        return ResponseEntity.ok(volService.updateStatutVol(id, statut));
    }
    
    @PutMapping("/{id}/equipage")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Assigner équipage", description = "Assigner un équipage à un vol (ADMIN/MANAGER)")
    public ResponseEntity<VolDTO> assignEquipage(
            @PathVariable Long id,
            @RequestBody List<Long> equipageIds) {
        return ResponseEntity.ok(volService.assignEquipage(id, equipageIds));
    }
    
    @GetMapping("/route")
    @Operation(summary = "Vols par route", description = "Récupérer les vols pour une route spécifique")
    public ResponseEntity<List<VolDTO>> getVolsByRoute(
            @RequestParam Long departId,
            @RequestParam Long arriveeId) {
        return ResponseEntity.ok(volService.getVolsByRoute(departId, arriveeId));
    }
}
