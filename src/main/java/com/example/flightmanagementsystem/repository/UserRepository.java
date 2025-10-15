package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
