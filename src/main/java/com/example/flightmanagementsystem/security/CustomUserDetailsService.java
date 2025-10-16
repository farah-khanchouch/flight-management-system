package com.example.flightmanagementsystem.security;

import com.example.flightmanagementsystem.model.User;
import com.example.flightmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur ou l'email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }
}
