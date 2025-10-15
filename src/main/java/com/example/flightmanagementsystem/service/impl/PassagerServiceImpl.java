package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.model.Passager;
import com.example.flightmanagementsystem.repository.PassagerRepository;
import com.example.flightmanagementsystem.service.PassagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassagerServiceImpl implements PassagerService {
    @Autowired
    private PassagerRepository repository;

    @Override
    public List<Passager> getAllPassagers() {
        return repository.findAll();
    }

    @Override
    public Passager getPassagerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Passager createPassager(Passager passager) {
        return repository.save(passager);
    }

    @Override
    public Passager updatePassager(Long id, Passager passager) {
        passager.setId(id);
        return repository.save(passager);
    }

    @Override
    public void deletePassager(Long id) {
        repository.deleteById(id);
    }
}
