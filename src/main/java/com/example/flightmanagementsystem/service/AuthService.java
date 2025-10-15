package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.AuthRequestDTO;
import com.example.flightmanagementsystem.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO request);
}
