package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Le nom d'utilisateur ne doit contenir que des lettres, chiffres et underscores")
    private String username;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    private String email;
    
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;
    
    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String prenom;
    
    @Pattern(regexp = "^[+]?[0-9]{10,20}$", message = "Le numéro de téléphone doit être valide")
    private String telephone;
    
    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
    
    private boolean enabled;
    
    private LocalDateTime lastLogin;
    
    private LocalDateTime createdAt;
    
    // Méthode helper
    public String getNomComplet() {
        if (prenom != null && nom != null) {
            return prenom + " " + nom;
        }
        return username;
    }
}
