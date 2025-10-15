package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Reservation;
import com.example.flightmanagementsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;

    @GetMapping
    public List<Reservation> getAll() {
        return service.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return service.getReservationById(id);
    }

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation) {
        return service.createReservation(reservation);
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return service.updateReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteReservation(id);
    }
}
