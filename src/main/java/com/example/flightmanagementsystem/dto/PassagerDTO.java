package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.Genre;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassagerDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    private String prenom;
    
    @Size(max = 20, message = "Le numéro de passeport ne doit pas dépasser 20 caractères")
    private String numeroPasseport;
    
    @Size(max = 20, message = "Le numéro de carte d'identité ne doit pas dépasser 20 caractères")
    private String numeroCarteIdentite;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    
    @NotBlank(message = "La nationalité est obligatoire")
    @Size(max = 50, message = "La nationalité ne doit pas dépasser 50 caractères")
    private String nationalite;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;
    
    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[0-9]{10,20}$", message = "Le numéro de téléphone doit être valide")
    private String telephone;
    
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    private String adresse;
    
    @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
    private String ville;
    
    @Size(max = 100, message = "Le pays ne doit pas dépasser 100 caractères")
    private String pays;
    
    @Size(max = 20, message = "Le code postal ne doit pas dépasser 20 caractères")
    private String codePostal;
    
    private Genre genre;
    
    private Long userId;
    
    // Méthode helper
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public int getAge() {
        return LocalDate.now().getYear() - dateNaissance.getYear();
    }
}
