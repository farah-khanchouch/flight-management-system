package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.EquipageDTO;
import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import com.example.flightmanagementsystem.service.EquipageService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/equipages")
@RequiredArgsConstructor
@Tag(name = "Équipages", description = "Gestion des membres d'équipage")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class EquipageController {

    private final EquipageService equipageService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un membre d'équipage")
    public ResponseEntity<EquipageDTO> createEquipage(@Valid @RequestBody EquipageDTO equipageDTO) {
        return new ResponseEntity<>(equipageService.createEquipage(equipageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un membre d'équipage")
    public ResponseEntity<EquipageDTO> updateEquipage(
            @PathVariable Long id,
            @Valid @RequestBody EquipageDTO equipageDTO) {
        return ResponseEntity.ok(equipageService.updateEquipage(id, equipageDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un membre d'équipage")
    public ResponseEntity<Void> deleteEquipage(@PathVariable Long id) {
        equipageService.deleteEquipage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un membre d'équipage par ID")
    public ResponseEntity<EquipageDTO> getEquipageById(@PathVariable Long id) {
        return ResponseEntity.ok(equipageService.getEquipageById(id));
    }

    @GetMapping
    @Operation(summary = "Lister tous les membres d'équipage")
    public ResponseEntity<List<EquipageDTO>> getAllEquipages() {
        return ResponseEntity.ok(equipageService.getAllEquipages());
    }

    @GetMapping("/fonction/{fonction}")
    @Operation(summary = "Rechercher les équipages par fonction")
    public ResponseEntity<List<EquipageDTO>> getEquipagesByFonction(@PathVariable FonctionEquipage fonction) {
        return ResponseEntity.ok(equipageService.getEquipagesByFonction(fonction));
    }

    @GetMapping("/actifs")
    @Operation(summary = "Obtenir les équipages actifs")
    public ResponseEntity<List<EquipageDTO>> getEquipagesActifs() {
        return ResponseEntity.ok(equipageService.getEquipagesActifs());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Obtenir les membres d'équipage disponibles")
    public ResponseEntity<List<EquipageDTO>> getEquipagesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(equipageService.getEquipagesDisponibles(date));
    }

    @GetMapping("/pilotes/actifs")
    @Operation(summary = "Obtenir les pilotes actifs")
    public ResponseEntity<List<EquipageDTO>> getPilotesActifs() {
        return ResponseEntity.ok(equipageService.getPilotesActifs());
    }

    @GetMapping("/licences/expirant-bientot")
    @Operation(summary = "Obtenir les licences expirant bientôt")
    public ResponseEntity<List<EquipageDTO>> getLicencesExpirantBientot() {
        return ResponseEntity.ok(equipageService.getLicencesExpirantBientot());
    }
}
