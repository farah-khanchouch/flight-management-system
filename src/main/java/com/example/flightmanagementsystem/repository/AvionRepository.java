package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvionRepository extends JpaRepository<Avion, Long> {
}
