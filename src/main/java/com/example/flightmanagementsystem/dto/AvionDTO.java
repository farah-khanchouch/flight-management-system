package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.StatutAvion;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvionDTO {
    
    private Long id;
    
    @NotBlank(message = "Le numéro de série est obligatoire")
    @Size(max = 50, message = "Le numéro de série ne doit pas dépasser 50 caractères")
    private String numeroSerie;
    
    @NotBlank(message = "Le type d'avion est obligatoire")
    @Size(max = 50, message = "Le type d'avion ne doit pas dépasser 50 caractères")
    private String typeAvion;
    
    @NotBlank(message = "Le modèle est obligatoire")
    @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
    private String modele;
    
    @NotNull(message = "La capacité est obligatoire")
    @Min(value = 1, message = "La capacité doit être au minimum 1")
    @Max(value = 1000, message = "La capacité ne peut pas dépasser 1000")
    private Integer capacite;
    
    @NotNull(message = "L'année de fabrication est obligatoire")
    @Min(value = 1950, message = "L'année de fabrication doit être au minimum 1950")
    @Max(value = 2100, message = "L'année de fabrication ne peut pas dépasser 2100")
    private Integer anneeFabrication;
    
    @Positive(message = "La capacité de carburant doit être positive")
    private Double capaciteCarburant;
    
    @Positive(message = "L'autonomie doit être positive")
    private Integer autonomie;
    
    @NotNull(message = "Le statut est obligatoire")
    private StatutAvion statut;
    
    private LocalDateTime derniereMaintenace;
    
    private LocalDateTime prochaineMaintenace;
}
