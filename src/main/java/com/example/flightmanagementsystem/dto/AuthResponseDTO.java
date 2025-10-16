package com.example.flightmanagementsystem.dto;

import com.example.flightmanagementsystem.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    
    private String token;
    
    private String type = "Bearer";
    
    private Long userId;
    
    private String username;
    
    private String email;
    
    private Role role;
    
    public AuthResponseDTO(String token, Long userId, String username, String email, Role role) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
