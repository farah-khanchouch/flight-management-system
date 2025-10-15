package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Avion;
import com.example.flightmanagementsystem.service.AvionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avions")
public class AvionController {
    @Autowired
    private AvionService service;

    @GetMapping
    public List<Avion> getAll() {
        return service.getAllAvions();
    }

    @GetMapping("/{id}")
    public Avion getById(@PathVariable Long id) {
        return service.getAvionById(id);
    }

    @PostMapping
    public Avion create(@RequestBody Avion avion) {
        return service.createAvion(avion);
    }

    @PutMapping("/{id}")
    public Avion update(@PathVariable Long id, @RequestBody Avion avion) {
        return service.updateAvion(id, avion);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAvion(id);
    }
}
