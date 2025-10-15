package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.StatutAeroport;
import com.example.flightmanagementsystem.model.enums.TypeAeroport;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AeroportDTO {
    
    private Long id;
    
    @NotBlank(message = "Le code IATA est obligatoire")
    @Size(min = 3, max = 3, message = "Le code IATA doit contenir exactement 3 caractères")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Le code IATA doit contenir 3 lettres majuscules")
    private String codeIATA;
    
    @Size(min = 4, max = 4, message = "Le code ICAO doit contenir exactement 4 caractères")
    @Pattern(regexp = "^[A-Z]{4}$", message = "Le code ICAO doit contenir 4 lettres majuscules")
    private String codeICAO;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 200, message = "Le nom doit contenir entre 3 et 200 caractères")
    private String nom;
    
    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
    private String ville;
    
    @NotBlank(message = "Le pays est obligatoire")
    @Size(max = 100, message = "Le pays ne doit pas dépasser 100 caractères")
    private String pays;
    
    @Positive(message = "La capacité de passagers doit être positive")
    private Integer capacitePassagers;
    
    @Positive(message = "Le nombre de pistes doit être positif")
    private Integer nombrePistes;
    
    @Size(max = 50, message = "Le fuseau horaire ne doit pas dépasser 50 caractères")
    private String fuseauHoraire;
    
    @DecimalMin(value = "-90.0", message = "La latitude doit être entre -90 et 90")
    @DecimalMax(value = "90.0", message = "La latitude doit être entre -90 et 90")
    private Double latitude;
    
    @DecimalMin(value = "-180.0", message = "La longitude doit être entre -180 et 180")
    @DecimalMax(value = "180.0", message = "La longitude doit être entre -180 et 180")
    private Double longitude;
    
    private Integer altitude;
    
    @NotNull(message = "Le type d'aéroport est obligatoire")
    private TypeAeroport typeAeroport;
    
    @Pattern(regexp = "^[+]?[0-9]{10,20}$", message = "Le numéro de téléphone doit être valide")
    private String telephone;
    
    @Email(message = "L'email doit être valide")
    private String email;
    
    @Size(max = 255, message = "Le site web ne doit pas dépasser 255 caractères")
    private String siteWeb;
    
    @NotNull(message = "Le statut est obligatoire")
    private StatutAeroport statut;
    
    // Méthode helper
    public String getNomComplet() {
        return nom + " (" + codeIATA + ")";
    }
}
