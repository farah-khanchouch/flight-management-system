package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Aeroport;
import java.util.List;

public interface AeroportService {
    List<Aeroport> getAllAeroports();
    Aeroport getAeroportById(Long id);
    Aeroport createAeroport(Aeroport aeroport);
    Aeroport updateAeroport(Long id, Aeroport aeroport);
    void deleteAeroport(Long id);
}
