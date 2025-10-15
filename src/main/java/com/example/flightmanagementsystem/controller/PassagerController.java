package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Passager;
import com.example.flightmanagementsystem.service.PassagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passagers")
public class PassagerController {
    @Autowired
    private PassagerService service;

    @GetMapping
    public List<Passager> getAll() {
        return service.getAllPassagers();
    }

    @GetMapping("/{id}")
    public Passager getById(@PathVariable Long id) {
        return service.getPassagerById(id);
    }

    @PostMapping
    public Passager create(@RequestBody Passager passager) {
        return service.createPassager(passager);
    }

    @PutMapping("/{id}")
    public Passager update(@PathVariable Long id, @RequestBody Passager passager) {
        return service.updatePassager(id, passager);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePassager(id);
    }
}
