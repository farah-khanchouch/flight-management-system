package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import com.example.flightmanagementsystem.model.enums.StatutEquipage;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipageDTO {
    
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    private String prenom;
    
    @NotNull(message = "La fonction est obligatoire")
    private FonctionEquipage fonction;
    
    @Size(max = 50, message = "Le numéro de licence ne doit pas dépasser 50 caractères")
    private String numeroLicence;
    
    @NotBlank(message = "La nationalité est obligatoire")
    @Size(max = 50, message = "La nationalité ne doit pas dépasser 50 caractères")
    private String nationalite;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    private String email;
    
    @Pattern(regexp = "^[+]?[0-9]{10,20}$", message = "Le numéro de téléphone doit être valide")
    private String telephone;
    
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    private String adresse;
    
    private LocalDate dateEmbauche;
    
    @Min(value = 0, message = "Les heures de vol doivent être positives")
    private Integer heuresVol;
    
    private LocalDate dateExpirationLicence;
    
    @Size(max = 500, message = "Les certifications ne doivent pas dépasser 500 caractères")
    private String certifications;
    
    @NotNull(message = "Le statut est obligatoire")
    private StatutEquipage statut;
    
    // Méthode helper
    public String getNomComplet() {
        return prenom + " " + nom;
    }
    
    public boolean isLicenceValide() {
        if (dateExpirationLicence == null) {
            return false;
        }
        return LocalDate.now().isBefore(dateExpirationLicence);
    }
}
