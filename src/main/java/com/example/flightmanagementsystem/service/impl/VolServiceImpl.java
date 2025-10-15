package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.model.Vol;
import com.example.flightmanagementsystem.repository.VolRepository;
import com.example.flightmanagementsystem.service.VolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VolServiceImpl implements VolService {
    @Autowired
    private VolRepository repository;

    @Override
    public List<Vol> getAllVols() {
        return repository.findAll();
    }

    @Override
    public Vol getVolById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Vol createVol(Vol vol) {
        return repository.save(vol);
    }

    @Override
    public Vol updateVol(Long id, Vol vol) {
        vol.setId(id);
        return repository.save(vol);
    }

    @Override
    public void deleteVol(Long id) {
        repository.deleteById(id);
    }
}
