package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Vol;
import com.example.flightmanagementsystem.service.VolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vols")
public class VolController {
    @Autowired
    private VolService service;

    @GetMapping
    public List<Vol> getAll() {
        return service.getAllVols();
    }

    @GetMapping("/{id}")
    public Vol getById(@PathVariable Long id) {
        return service.getVolById(id);
    }

    @PostMapping
    public Vol create(@RequestBody Vol vol) {
        return service.createVol(vol);
    }

    @PutMapping("/{id}")
    public Vol update(@PathVariable Long id, @RequestBody Vol vol) {
        return service.updateVol(id, vol);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteVol(id);
    }
}
