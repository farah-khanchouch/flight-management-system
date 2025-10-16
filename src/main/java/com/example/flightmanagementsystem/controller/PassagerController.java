package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.PassagerDTO;
import com.example.flightmanagementsystem.service.PassagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
public class PassagerController {

    @Autowired
    private PassagerService service;

    // CRUD Operations
    @GetMapping
    public ResponseEntity<List<PassagerDTO>> getAllPassagers() {
        return ResponseEntity.ok(service.getAllPassagers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagerDTO> getPassagerById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPassagerById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PassagerDTO> getPassagerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getPassagerByEmail(email));
    }

    @PostMapping
    public ResponseEntity<PassagerDTO> createPassager(@Valid @RequestBody PassagerDTO passagerDTO) {
        return ResponseEntity.ok(service.createPassager(passagerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassagerDTO> updatePassager(@PathVariable Long id, @Valid @RequestBody PassagerDTO passagerDTO) {
        return ResponseEntity.ok(service.updatePassager(id, passagerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassager(@PathVariable Long id) {
        service.deletePassager(id);
        return ResponseEntity.noContent().build();
    }

    // Business Logic Endpoints
    @GetMapping("/passeport/{numeroPasseport}")
    public ResponseEntity<PassagerDTO> getPassagerByPasseport(@PathVariable String numeroPasseport) {
        return ResponseEntity.ok(service.getPassagerByPasseport(numeroPasseport));
    }

    @GetMapping("/carte-identite/{numeroCarteIdentite}")
    public ResponseEntity<PassagerDTO> getPassagerByCarteIdentite(@PathVariable String numeroCarteIdentite) {
        return ResponseEntity.ok(service.getPassagerByCarteIdentite(numeroCarteIdentite));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PassagerDTO>> searchPassagers(@RequestParam String keyword) {
        return ResponseEntity.ok(service.searchPassagers(keyword));
    }

    @GetMapping("/nationalite/{nationalite}")
    public ResponseEntity<List<PassagerDTO>> getPassagersByNationalite(@PathVariable String nationalite) {
        return ResponseEntity.ok(service.getPassagersByNationalite(nationalite));
    }

    @GetMapping("/vol/{volId}")
    public ResponseEntity<List<PassagerDTO>> getPassagersByVol(@PathVariable Long volId) {
        return ResponseEntity.ok(service.getPassagersByVol(volId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PassagerDTO> getPassagerByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getPassagerByUserId(userId));
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.existsByEmail(email));
    }

    @GetMapping("/exists/passeport/{numeroPasseport}")
    public ResponseEntity<Boolean> existsByPasseport(@PathVariable String numeroPasseport) {
        return ResponseEntity.ok(service.existsByPasseport(numeroPasseport));
    }
}
