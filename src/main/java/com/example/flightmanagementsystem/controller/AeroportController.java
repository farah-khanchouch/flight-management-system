package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Aeroport;
import com.example.flightmanagementsystem.service.AeroportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aeroports")
public class AeroportController {
    @Autowired
    private AeroportService service;

    @GetMapping
    public List<Aeroport> getAll() {
        return service.getAllAeroports();
    }

    @GetMapping("/{id}")
    public Aeroport getById(@PathVariable Long id) {
        return service.getAeroportById(id);
    }

    @PostMapping
    public Aeroport create(@RequestBody Aeroport aeroport) {
        return service.createAeroport(aeroport);
    }

    @PutMapping("/{id}")
    public Aeroport update(@PathVariable Long id, @RequestBody Aeroport aeroport) {
        return service.updateAeroport(id, aeroport);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAeroport(id);
    }
}
