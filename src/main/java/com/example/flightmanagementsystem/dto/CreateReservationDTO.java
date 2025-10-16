package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.ClasseVol;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDTO {
    
    @NotNull(message = "Le vol est obligatoire")
    private Long volId;
    
    @NotEmpty(message = "Au moins un passager est requis")
    private List<Long> passagerIds;
    
    @NotNull(message = "La classe de vol est obligatoire")
    private ClasseVol classe;
    
    @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
    private String commentaire;
    
    private String codePromo;
}
