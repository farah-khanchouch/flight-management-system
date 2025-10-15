package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Equipage;
import java.util.List;

public interface EquipageService {
    List<Equipage> getAllEquipages();
    Equipage getEquipageById(Long id);
    Equipage createEquipage(Equipage equipage);
    Equipage updateEquipage(Long id, Equipage equipage);
    void deleteEquipage(Long id);
}
