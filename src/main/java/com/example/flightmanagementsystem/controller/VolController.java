package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.VolDTO;
import com.example.flightmanagementsystem.dto.SearchVolDTO;
import com.example.flightmanagementsystem.model.enums.StatutVol;
import com.example.flightmanagementsystem.service.VolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vols")
public class VolController {

    @Autowired
    private VolService service;

    // CRUD Operations
    @GetMapping
    public ResponseEntity<List<VolDTO>> getAllVols() {
        return ResponseEntity.ok(service.getAllVols());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolDTO> getVolById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getVolById(id));
    }

    @PostMapping
    public ResponseEntity<VolDTO> createVol(@Valid @RequestBody VolDTO volDTO) {
        return ResponseEntity.ok(service.createVol(volDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolDTO> updateVol(@PathVariable Long id, @Valid @RequestBody VolDTO volDTO) {
        return ResponseEntity.ok(service.updateVol(id, volDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVol(@PathVariable Long id) {
        service.deleteVol(id);
        return ResponseEntity.noContent().build();
    }

    // Business Logic Endpoints
    @GetMapping("/search")
    public ResponseEntity<List<VolDTO>> searchVols(@Valid @ModelAttribute SearchVolDTO searchDTO) {
        return ResponseEntity.ok(service.searchVols(searchDTO));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<VolDTO>> getVolsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getVolsByDate(date));
    }

    @GetMapping("/by-route")
    public ResponseEntity<List<VolDTO>> getVolsByRoute(@RequestParam Long aeroportDepartId, @RequestParam Long aeroportArriveeId) {
        return ResponseEntity.ok(service.getVolsByRoute(aeroportDepartId, aeroportArriveeId));
    }

    @GetMapping("/by-statut")
    public ResponseEntity<List<VolDTO>> getVolsByStatut(@RequestParam StatutVol statut) {
        return ResponseEntity.ok(service.getVolsByStatut(statut));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<VolDTO>> getVolsDisponibles(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.getVolsDisponibles(date));
    }

    @GetMapping("/aujourdhui")
    public ResponseEntity<List<VolDTO>> getVolsAujourdhui() {
        return ResponseEntity.ok(service.getVolsAujourdhui());
    }

    @GetMapping("/by-avion/{avionId}")
    public ResponseEntity<List<VolDTO>> getVolsByAvion(@PathVariable Long avionId) {
        return ResponseEntity.ok(service.getVolsByAvion(avionId));
    }

    @GetMapping("/{id}/disponibilite")
    public ResponseEntity<Boolean> verifierDisponibilite(@PathVariable Long id, @RequestParam Integer nombrePassagers) {
        return ResponseEntity.ok(service.verifierDisponibilite(id, nombrePassagers));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<VolDTO> updateStatutVol(@PathVariable Long id, @RequestParam StatutVol statut) {
        return ResponseEntity.ok(service.updateStatutVol(id, statut));
    }

    @PutMapping("/{id}/equipage")
    public ResponseEntity<VolDTO> assignEquipage(@PathVariable Long id, @RequestBody List<Long> equipageIds) {
        return ResponseEntity.ok(service.assignEquipage(id, equipageIds));
    }

    @PutMapping("/{id}/places")
    public ResponseEntity<Void> updatePlacesDisponibles(@PathVariable Long id, @RequestParam Integer places) {
        service.updatePlacesDisponibles(id, places);
        return ResponseEntity.noContent().build();
    }
}
