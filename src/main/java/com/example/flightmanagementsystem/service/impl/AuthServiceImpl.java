package com.example.flightmanagementsystem.service.impl;

import com.example.flightmanagementsystem.dto.AuthRequestDTO;
import com.example.flightmanagementsystem.dto.AuthResponseDTO;
import com.example.flightmanagementsystem.dto.RegisterRequestDTO;
import com.example.flightmanagementsystem.exception.BadRequestException;
import com.example.flightmanagementsystem.exception.UnauthorizedException;
import com.example.flightmanagementsystem.model.User;
import com.example.flightmanagementsystem.model.enums.Role;
import com.example.flightmanagementsystem.repository.UserRepository;
import com.example.flightmanagementsystem.security.JwtTokenProvider;
import com.example.flightmanagementsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    
    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        log.debug("Attempting login for: {}", authRequestDTO.getUsernameOrEmail());
        
        try {
            // Authentification avec Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequestDTO.getUsernameOrEmail(),
                    authRequestDTO.getPassword()
                )
            );
            
            // Récupérer l'utilisateur
            User user = userRepository.findByUsernameOrEmail(authRequestDTO.getUsernameOrEmail())
                    .orElseThrow(() -> new UnauthorizedException("Identifiants invalides"));
            
            // Vérifier que le compte est activé
            if (!user.isEnabled()) {
                throw new UnauthorizedException("Compte désactivé");
            }
            
            // Mettre à jour la dernière connexion
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            // Générer le token JWT
            String token = jwtTokenProvider.generateToken(authentication);
            
            log.info("Connexion réussie pour: {}", user.getUsername());
            
            return new AuthResponseDTO(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
            );
            
        } catch (AuthenticationException e) {
            log.error("Échec de connexion pour: {}", authRequestDTO.getUsernameOrEmail());
            throw new UnauthorizedException("Identifiants invalides");
        }
    }
    
    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        log.debug("Attempting registration for: {}", registerRequestDTO.getUsername());
        
        // Vérifier que les mots de passe correspondent
        if (!registerRequestDTO.isPasswordMatching()) {
            throw new BadRequestException("Les mots de passe ne correspondent pas");
        }
        
        // Vérifier que le nom d'utilisateur n'existe pas
        if (userRepository.existsByUsername(registerRequestDTO.getUsername())) {
            throw new BadRequestException("Ce nom d'utilisateur est déjà utilisé");
        }
        
        // Vérifier que l'email n'existe pas
        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new BadRequestException("Cet email est déjà utilisé");
        }
        
        // Créer le nouvel utilisateur
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setNom(registerRequestDTO.getNom());
        user.setPrenom(registerRequestDTO.getPrenom());
        user.setTelephone(registerRequestDTO.getTelephone());
        user.setRole(Role.USER); // Rôle par défaut
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        
        User savedUser = userRepository.save(user);
        
        // Générer le token JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            savedUser.getUsername(),
            registerRequestDTO.getPassword(),
            savedUser.getAuthorities()
        );
        String token = jwtTokenProvider.generateToken(authentication);
        
        log.info("Inscription réussie pour: {}", savedUser.getUsername());
        
        return new AuthResponseDTO(
            token,
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getRole()
        );
    }
    
    @Override
    public void logout(String token) {
        log.debug("Processing logout");
        // Dans une implémentation complète, vous pourriez ajouter le token à une blacklist
        // Pour l'instant, le logout est géré côté client en supprimant le token
        log.info("Déconnexion effectuée");
    }
}
