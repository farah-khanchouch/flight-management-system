package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.dto.UpdatePasswordDTO;
import com.example.flightmanagementsystem.dto.UserDTO;
import com.example.flightmanagementsystem.model.enums.Role;
import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO);
    List<UserDTO> getUsersByRole(Role role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
