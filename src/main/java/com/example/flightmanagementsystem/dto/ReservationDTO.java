package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.ClasseVol;
import com.example.flightmanagementsystem.model.enums.StatutReservation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    
    private Long id;
    
    private String numeroReservation;
    
    @NotNull(message = "Le vol est obligatoire")
    private Long volId;
    
    private String volCodeVol;
    private String volAeroportDepart;
    private String volAeroportArrivee;
    
    @NotEmpty(message = "Au moins un passager est requis")
    private List<Long> passagerIds;
    
    @NotNull(message = "L'utilisateur est obligatoire")
    private Long userId;
    
    private String userUsername;
    
    private LocalDateTime dateReservation;
    
    @NotNull(message = "Le statut est obligatoire")
    private StatutReservation statut;
    
    @NotNull(message = "Le prix total est obligatoire")
    @Positive(message = "Le prix total doit être positif")
    private Double prixTotal;
    
    @NotNull(message = "Le nombre de passagers est obligatoire")
    @Min(value = 1, message = "Le nombre de passagers doit être au minimum 1")
    private Integer nombrePassagers;
    
    @NotNull(message = "La classe de vol est obligatoire")
    private ClasseVol classe;
    
    @Positive(message = "Le prix unitaire doit être positif")
    private Double prixUnitaire;
    
    @PositiveOrZero(message = "Les taxes doivent être positives ou nulles")
    private Double taxes;
    
    @PositiveOrZero(message = "La réduction doit être positive ou nulle")
    private Double reduction;
    
    @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
    private String commentaire;
    
    private LocalDateTime datePaiement;
    
    @Size(max = 50, message = "La méthode de paiement ne doit pas dépasser 50 caractères")
    private String methodePaiement;
    
    @Size(max = 100, message = "La référence de paiement ne doit pas dépasser 100 caractères")
    private String referencePaiement;
}
