package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.EquipageDTO;
import com.example.flightmanagementsystem.model.enums.FonctionEquipage;
import java.time.LocalDate;
import java.util.List;

public interface EquipageService {
    EquipageDTO createEquipage(EquipageDTO equipageDTO);
    EquipageDTO getEquipageById(Long id);
    EquipageDTO getEquipageByLicence(String numeroLicence);
    List<EquipageDTO> getAllEquipages();
    EquipageDTO updateEquipage(Long id, EquipageDTO equipageDTO);
    void deleteEquipage(Long id);
    List<EquipageDTO> getEquipagesByFonction(FonctionEquipage fonction);
    List<EquipageDTO> getEquipagesActifs();
    List<EquipageDTO> getEquipagesDisponibles(LocalDate date);
    List<EquipageDTO> getPilotesActifs();
    List<EquipageDTO> getLicencesExpirantBientot();
}
