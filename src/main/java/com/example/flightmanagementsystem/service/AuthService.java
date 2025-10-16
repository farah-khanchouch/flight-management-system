package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.AuthRequestDTO;
import com.example.flightmanagementsystem.dto.AuthResponseDTO;
import com.example.flightmanagementsystem.dto.RegisterRequestDTO;

public interface AuthService {
    
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    
    AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
    
    void logout(String token);
}
