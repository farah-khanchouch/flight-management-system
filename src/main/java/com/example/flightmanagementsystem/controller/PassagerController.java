package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.PassagerDTO;
import com.example.flightmanagementsystem.service.PassagerService;
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
@RequestMapping("/api/passagers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Passagers", description = "Gestion des passagers")
public class PassagerController {
    
    private final PassagerService passagerService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Liste de tous les passagers", description = "Récupérer tous les passagers (ADMIN/AGENT)")
    public ResponseEntity<List<PassagerDTO>> getAllPassagers() {
        return ResponseEntity.ok(passagerService.getAllPassagers());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Détails d'un passager", description = "Récupérer un passager par son ID (ADMIN/AGENT)")
    public ResponseEntity<PassagerDTO> getPassagerById(@PathVariable Long id) {
        return ResponseEntity.ok(passagerService.getPassagerById(id));
    }
    
    @GetMapping("/me")
    @Operation(summary = "Mon profil passager", description = "Récupérer les informations de mon profil passager")
    public ResponseEntity<PassagerDTO> getMyPassagerProfile(Authentication authentication) {
        // Récupérer l'utilisateur connecté depuis le token
        com.example.flightmanagementsystem.model.User user = 
            (com.example.flightmanagementsystem.model.User) authentication.getPrincipal();
        return ResponseEntity.ok(passagerService.getPassagerByUserId(user.getId()));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Créer un passager", description = "Créer un nouveau passager (ADMIN/AGENT)")
    public ResponseEntity<PassagerDTO> createPassager(@Valid @RequestBody PassagerDTO passagerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passagerService.createPassager(passagerDTO));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Modifier un passager", description = "Modifier un passager existant (ADMIN/AGENT)")
    public ResponseEntity<PassagerDTO> updatePassager(
            @PathVariable Long id,
            @Valid @RequestBody PassagerDTO passagerDTO) {
        return ResponseEntity.ok(passagerService.updatePassager(id, passagerDTO));
    }
    
    @PutMapping("/me")
    @Operation(summary = "Modifier mon profil", description = "Modifier mes informations de passager")
    public ResponseEntity<PassagerDTO> updateMyProfile(
            @Valid @RequestBody PassagerDTO passagerDTO,
            Authentication authentication) {
        com.example.flightmanagementsystem.model.User user = 
            (com.example.flightmanagementsystem.model.User) authentication.getPrincipal();
        PassagerDTO existingPassager = passagerService.getPassagerByUserId(user.getId());
        return ResponseEntity.ok(passagerService.updatePassager(existingPassager.getId(), passagerDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un passager", description = "Supprimer un passager (ADMIN uniquement)")
    public ResponseEntity<Void> deletePassager(@PathVariable Long id) {
        passagerService.deletePassager(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Rechercher des passagers", description = "Rechercher des passagers par nom/prénom (ADMIN/AGENT)")
    public ResponseEntity<List<PassagerDTO>> searchPassagers(@RequestParam String keyword) {
        return ResponseEntity.ok(passagerService.searchPassagers(keyword));
    }
    
    @GetMapping("/nationalite/{nationalite}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Passagers par nationalité", description = "Récupérer les passagers par nationalité (ADMIN/AGENT)")
    public ResponseEntity<List<PassagerDTO>> getPassagersByNationalite(@PathVariable String nationalite) {
        return ResponseEntity.ok(passagerService.getPassagersByNationalite(nationalite));
    }
    
    @GetMapping("/vol/{volId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'MANAGER')")
    @Operation(summary = "Passagers d'un vol", description = "Récupérer les passagers d'un vol spécifique")
    public ResponseEntity<List<PassagerDTO>> getPassagersByVol(@PathVariable Long volId) {
        return ResponseEntity.ok(passagerService.getPassagersByVol(volId));
    }
}
