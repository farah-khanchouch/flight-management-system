package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.EquipageDTO;
import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import com.example.flightmanagementsystem.service.EquipageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/equipages")
public class EquipageController {

    @Autowired
    private EquipageService service;

    // CRUD Operations
    @GetMapping
    public ResponseEntity<List<EquipageDTO>> getAllEquipages() {
        return ResponseEntity.ok(service.getAllEquipages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipageDTO> getEquipageById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEquipageById(id));
    }

    @GetMapping("/licence/{numeroLicence}")
    public ResponseEntity<EquipageDTO> getEquipageByLicence(@PathVariable String numeroLicence) {
        return ResponseEntity.ok(service.getEquipageByLicence(numeroLicence));
    }

    @PostMapping
    public ResponseEntity<EquipageDTO> createEquipage(@Valid @RequestBody EquipageDTO equipageDTO) {
        return ResponseEntity.ok(service.createEquipage(equipageDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipageDTO> updateEquipage(@PathVariable Long id, @Valid @RequestBody EquipageDTO equipageDTO) {
        return ResponseEntity.ok(service.updateEquipage(id, equipageDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipage(@PathVariable Long id) {
        service.deleteEquipage(id);
        return ResponseEntity.noContent().build();
    }

    // Business Logic Endpoints
    @GetMapping("/fonction/{fonction}")
    public ResponseEntity<List<EquipageDTO>> getEquipagesByFonction(@PathVariable FonctionEquipage fonction) {
        return ResponseEntity.ok(service.getEquipagesByFonction(fonction));
    }

    @GetMapping("/actifs")
    public ResponseEntity<List<EquipageDTO>> getEquipagesActifs() {
        return ResponseEntity.ok(service.getEquipagesActifs());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<EquipageDTO>> getEquipagesDisponibles(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getEquipagesDisponibles(date));
    }

    @GetMapping("/pilotes/actifs")
    public ResponseEntity<List<EquipageDTO>> getPilotesActifs() {
        return ResponseEntity.ok(service.getPilotesActifs());
    }

    @GetMapping("/licences/expirant-bientot")
    public ResponseEntity<List<EquipageDTO>> getLicencesExpirantBientot() {
        return ResponseEntity.ok(service.getLicencesExpirantBientot());
    }
}
