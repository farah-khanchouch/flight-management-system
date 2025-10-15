package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.dto.AuthRequestDTO;
import com.example.flightmanagementsystem.dto.AuthResponseDTO;
import com.example.flightmanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {
        return service.login(request);
    }
}
