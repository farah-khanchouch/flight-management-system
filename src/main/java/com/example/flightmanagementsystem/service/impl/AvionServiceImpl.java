package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.model.Avion;
import com.example.flightmanagementsystem.repository.AvionRepository;
import com.example.flightmanagementsystem.service.AvionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AvionServiceImpl implements AvionService {
    @Autowired
    private AvionRepository repository;

    @Override
    public List<Avion> getAllAvions() {
        return repository.findAll();
    }

    @Override
    public Avion getAvionById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Avion createAvion(Avion avion) {
        return repository.save(avion);
    }

    @Override
    public Avion updateAvion(Long id, Avion avion) {
        avion.setId(id);
        return repository.save(avion);
    }

    @Override
    public void deleteAvion(Long id) {
        repository.deleteById(id);
    }
}
