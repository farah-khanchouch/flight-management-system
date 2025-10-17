package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.UpdatePasswordDTO;
import com.example.flightmanagementsystem.dto.UserDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.ResourceNotFoundException;
import com.example.flightmanagementsystem.mapper.UserMapper;
import com.example.flightmanagementsystem.model.User;
import com.example.flightmanagementsystem.model.enums.Role;
import com.example.flightmanagementsystem.repository.UserRepository;
import com.example.flightmanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id: " + id));
        return userMapper.toDto(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + username));
        return userMapper.toDto(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email: " + email));
        return userMapper.toDto(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
    
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        // Vérifier l'unicité du username si modifié
        if (userDTO.getUsername() != null && 
            !userDTO.getUsername().equals(user.getUsername()) &&
            userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Ce nom d'utilisateur est déjà utilisé");
        }
        
        // Vérifier l'unicité de l'email si modifié
        if (userDTO.getEmail() != null && 
            !userDTO.getEmail().equals(user.getEmail()) &&
            userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Cet email est déjà utilisé");
        }
        
        userMapper.updateEntityFromDto(userDTO, user);
        User updatedUser = userRepository.save(user);
        
        log.info("Utilisateur mis à jour: {}", id);
        return userMapper.toDto(updatedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        if (!user.getReservations().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer un utilisateur ayant des réservations");
        }
        
        userRepository.deleteById(id);
        log.info("Utilisateur supprimé: {}", id);
    }
    
    @Override
    public UserDTO updatePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        
        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("L'ancien mot de passe est incorrect");
        }
        
        // Vérifier que les nouveaux mots de passe correspondent
        if (!updatePasswordDTO.isPasswordMatching()) {
            throw new BadRequestException("Les nouveaux mots de passe ne correspondent pas");
        }
        
        // Encoder et sauvegarder le nouveau mot de passe
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        User updatedUser = userRepository.save(user);
        
        log.info("Mot de passe mis à jour pour l'utilisateur: {}", id);
        return userMapper.toDto(updatedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRole(Role role) {
        return userMapper.toDtoList(userRepository.findByRole(role));
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
