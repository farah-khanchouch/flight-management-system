package com.example.flightmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configure(http))
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                
                // Vols - lecture publique, modification ADMIN/MANAGER
                .requestMatchers(HttpMethod.GET, "/api/vols/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/vols/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/vols/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/vols/**").hasRole("ADMIN")
                
                // Avions - ADMIN/MANAGER uniquement
                .requestMatchers("/api/avions/**").hasAnyRole("ADMIN", "MANAGER")
                
                // Aéroports - lecture publique, modification ADMIN/MANAGER
                .requestMatchers(HttpMethod.GET, "/api/aeroports/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/aeroports/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/aeroports/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/aeroports/**").hasRole("ADMIN")
                
                // Équipages - ADMIN/MANAGER uniquement
                .requestMatchers("/api/equipages/**").hasAnyRole("ADMIN", "MANAGER")
                
                // Passagers - utilisateur peut gérer ses propres données
                .requestMatchers(HttpMethod.GET, "/api/passagers/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/passagers/me").authenticated()
                .requestMatchers("/api/passagers/**").hasAnyRole("ADMIN", "AGENT")
                
                // Réservations - utilisateur peut gérer ses réservations
                .requestMatchers(HttpMethod.GET, "/api/reservations/me/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/reservations").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/reservations/*/cancel").authenticated()
                .requestMatchers("/api/reservations/**").hasAnyRole("ADMIN", "AGENT")
                
                // Users - ADMIN uniquement
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                
                // Tout le reste nécessite une authentification
                .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
