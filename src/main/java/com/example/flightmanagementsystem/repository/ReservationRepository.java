package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
