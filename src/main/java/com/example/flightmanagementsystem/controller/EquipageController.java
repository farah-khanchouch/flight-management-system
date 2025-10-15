package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Equipage;
import com.example.flightmanagementsystem.service.EquipageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipages")
public class EquipageController {
    @Autowired
    private EquipageService service;

    @GetMapping
    public List<Equipage> getAll() {
        return service.getAllEquipages();
    }

    @GetMapping("/{id}")
    public Equipage getById(@PathVariable Long id) {
        return service.getEquipageById(id);
    }

    @PostMapping
    public Equipage create(@RequestBody Equipage equipage) {
        return service.createEquipage(equipage);
    }

    @PutMapping("/{id}")
    public Equipage update(@PathVariable Long id, @RequestBody Equipage equipage) {
        return service.updateEquipage(id, equipage);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEquipage(id);
    }
}
