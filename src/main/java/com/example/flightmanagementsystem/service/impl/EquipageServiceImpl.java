package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.model.Equipage;
import com.example.flightmanagementsystem.repository.EquipageRepository;
import com.example.flightmanagementsystem.service.EquipageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipageServiceImpl implements EquipageService {
    @Autowired
    private EquipageRepository repository;

    @Override
    public List<Equipage> getAllEquipages() {
        return repository.findAll();
    }

    @Override
    public Equipage getEquipageById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Equipage createEquipage(Equipage equipage) {
        return repository.save(equipage);
    }

    @Override
    public Equipage updateEquipage(Long id, Equipage equipage) {
        equipage.setId(id);
        return repository.save(equipage);
    }

    @Override
    public void deleteEquipage(Long id) {
        repository.deleteById(id);
    }
}
