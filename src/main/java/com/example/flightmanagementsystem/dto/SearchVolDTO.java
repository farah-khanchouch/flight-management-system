package com.example.flightmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVolDTO {
    
    private String aeroportDepartCodeIATA;
    
    private String aeroportArriveeCodeIATA;
    
    private LocalDate dateVol;
    
    private Integer nombrePassagers;
    
    private String classeVol;
}
