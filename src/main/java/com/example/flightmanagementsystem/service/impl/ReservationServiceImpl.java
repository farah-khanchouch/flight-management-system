package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.model.Reservation;
import com.example.flightmanagementsystem.repository.ReservationRepository;
import com.example.flightmanagementsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository repository;

    @Override
    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        reservation.setId(id);
        return repository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        repository.deleteById(id);
    }
}
