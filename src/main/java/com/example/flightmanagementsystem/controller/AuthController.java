package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.AuthRequestDTO;
import com.example.flightmanagementsystem.dto.AuthResponseDTO;
import com.example.flightmanagementsystem.dto.RegisterRequestDTO;
import com.example.flightmanagementsystem.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentification", description = "Endpoints pour l'authentification et l'inscription")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Connecter un utilisateur et obtenir un token JWT")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        log.debug("Login attempt for user: {}", authRequestDTO.getUsernameOrEmail());
        AuthResponseDTO response = authService.login(authRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription", description = "Créer un nouveau compte utilisateur")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        log.debug("Registration attempt for user: {}", registerRequestDTO.getUsername());
        AuthResponseDTO response = authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Déconnecter l'utilisateur (côté client)")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        log.debug("Logout attempt");
        authService.logout(token);
        return ResponseEntity.ok("Déconnexion réussie");
    }
}
