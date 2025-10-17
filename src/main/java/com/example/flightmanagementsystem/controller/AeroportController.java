package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Aeroport;
import com.example.flightmanagementsystem.service.AeroportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aeroports")
@RequiredArgsConstructor
@Tag(name = "Aéroports", description = "Gestion des aéroports")
@SecurityRequirement(name = "bearerAuth")
public class AeroportController {

    private final AeroportService aeroportService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Créer un aéroport")
    public ResponseEntity<Aeroport> createAeroport(@Valid @RequestBody Aeroport aeroport) {
        return new ResponseEntity<>(aeroportService.createAeroport(aeroport), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Mettre à jour un aéroport")
    public ResponseEntity<Aeroport> updateAeroport(
            @PathVariable Long id,
            @Valid @RequestBody Aeroport aeroport) {
        return ResponseEntity.ok(aeroportService.updateAeroport(id, aeroport));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un aéroport")
    public ResponseEntity<Void> deleteAeroport(@PathVariable Long id) {
        aeroportService.deleteAeroport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un aéroport par ID")
    public ResponseEntity<Aeroport> getAeroportById(@PathVariable Long id) {
        return ResponseEntity.ok(aeroportService.getAeroportById(id));
    }

    @GetMapping
    @Operation(summary = "Lister tous les aéroports")
    public ResponseEntity<List<Aeroport>> getAllAeroports() {
        return ResponseEntity.ok(aeroportService.getAllAeroports());
    }
}
