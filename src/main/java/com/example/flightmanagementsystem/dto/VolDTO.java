package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.StatutVol;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolDTO {
    
    private Long id;
    
    @NotBlank(message = "Le code IATA transporteur est obligatoire")
    @Size(min = 2, max = 3, message = "Le code IATA doit contenir 2 ou 3 caractères")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Le code IATA doit contenir uniquement des lettres majuscules et chiffres")
    private String codeIATATransporteur;
    
    @NotBlank(message = "Le numéro de vol est obligatoire")
    @Size(min = 1, max = 4, message = "Le numéro de vol doit contenir 1 à 4 caractères")
    @Pattern(regexp = "^[0-9]+$", message = "Le numéro de vol doit contenir uniquement des chiffres")
    private String numeroVol;
    
    @NotNull(message = "La date du vol est obligatoire")
    @Future(message = "La date du vol doit être dans le futur")
    private LocalDate dateVol;
    
    @NotNull(message = "L'aéroport de départ est obligatoire")
    private Long aeroportDepartId;
    
    private String aeroportDepartNom;
    private String aeroportDepartCodeIATA;
    
    @NotNull(message = "L'aéroport d'arrivée est obligatoire")
    private Long aeroportArriveeId;
    
    private String aeroportArriveeNom;
    private String aeroportArriveeCodeIATA;
    
    @NotNull(message = "L'avion est obligatoire")
    private Long avionId;
    
    private String avionType;
    private String avionModele;
    
    @NotNull(message = "L'heure de départ est obligatoire")
    private LocalTime heureDepart;
    
    @NotNull(message = "L'heure d'arrivée est obligatoire")
    private LocalTime heureArrivee;
    
    private Integer dureeVol;
    
    @NotNull(message = "Le prix de base est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prixBase;
    
    private StatutVol statut;
    
    @Min(value = 0, message = "Le nombre de places disponibles ne peut pas être négatif")
    private Integer placesDisponibles;
    
    // Code vol complet
    public String getCodeVol() {
        return codeIATATransporteur + numeroVol;
    }
}
