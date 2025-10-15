package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.model.Aeroport;
import com.example.flightmanagementsystem.repository.AeroportRepository;
import com.example.flightmanagementsystem.service.AeroportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AeroportServiceImpl implements AeroportService {
    @Autowired
    private AeroportRepository repository;

    @Override
    public List<Aeroport> getAllAeroports() {
        return repository.findAll();
    }

    @Override
    public Aeroport getAeroportById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Aeroport createAeroport(Aeroport aeroport) {
        return repository.save(aeroport);
    }

    @Override
    public Aeroport updateAeroport(Long id, Aeroport aeroport) {
        aeroport.setId(id);
        return repository.save(aeroport);
    }

    @Override
    public void deleteAeroport(Long id) {
        repository.deleteById(id);
    }
}
