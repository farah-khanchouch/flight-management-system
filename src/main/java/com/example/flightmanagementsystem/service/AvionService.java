package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Avion;
import java.util.List;

public interface AvionService {
    List<Avion> getAllAvions();
    Avion getAvionById(Long id);
    Avion createAvion(Avion avion);
    Avion updateAvion(Long id, Avion avion);
    void deleteAvion(Long id);
}
