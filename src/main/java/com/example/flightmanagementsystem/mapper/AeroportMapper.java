package com.example.flightmanagementsystem.mapper;

import com.example.flightmanagementsystem.dto.AeroportDTO;
import com.example.flightmanagementsystem.model.Aeroport;
import org.springframework.stereotype.Component;

@Component
public class AeroportMapper {
    public AeroportDTO toDTO(Aeroport aeroport) {
        AeroportDTO dto = new AeroportDTO();
        dto.setId(aeroport.getId());
        dto.setCodeIATA(aeroport.getCodeIATA());
        dto.setNom(aeroport.getNom());
        dto.setVille(aeroport.getVille());
        dto.setPays(aeroport.getPays());
        dto.setCapacitePassagers(aeroport.getCapacitePassagers());
        return dto;
    }

    public Aeroport toEntity(AeroportDTO dto) {
        Aeroport aeroport = new Aeroport();
        aeroport.setId(dto.getId());
        aeroport.setCodeIATA(dto.getCodeIATA());
        aeroport.setNom(dto.getNom());
        aeroport.setVille(dto.getVille());
        aeroport.setPays(dto.getPays());
        aeroport.setCapacitePassagers(dto.getCapacitePassagers());
        return aeroport;
    }
}
