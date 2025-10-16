package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.User;
import com.example.flightmanagementsystem.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Recherche par username
    Optional<User> findByUsername(String username);
    
    // Vérifier si username existe
    boolean existsByUsername(String username);
    
    // Recherche par email
    Optional<User> findByEmail(String email);
    
    // Vérifier si email existe
    boolean existsByEmail(String email);
    
    // Recherche par username ou email
    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.email = :identifier")
    Optional<User> findByUsernameOrEmail(@Param("identifier") String identifier);
    
    // Recherche par rôle
    List<User> findByRole(Role role);
    
    // Utilisateurs actifs
    List<User> findByEnabledTrue();
    
    // Utilisateurs inactifs
    List<User> findByEnabledFalse();
    
    // Utilisateurs par rôle et statut
    List<User> findByRoleAndEnabled(Role role, boolean enabled);
    
    // Administrateurs
    @Query("SELECT u FROM User u WHERE u.role = 'ADMIN' AND u.enabled = true")
    List<User> findAdministrateurs();
    
    // Recherche par nom
    @Query("SELECT u FROM User u WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<User> searchByNom(@Param("nom") String nom);
    
    // Recherche par prénom
    @Query("SELECT u FROM User u WHERE LOWER(u.prenom) LIKE LOWER(CONCAT('%', :prenom, '%'))")
    List<User> searchByPrenom(@Param("prenom") String prenom);
    
    // Recherche par nom complet
    @Query("SELECT u FROM User u WHERE LOWER(CONCAT(u.prenom, ' ', u.nom)) LIKE LOWER(CONCAT('%', :nomComplet, '%'))")
    List<User> searchByNomComplet(@Param("nomComplet") String nomComplet);
    
    // Utilisateurs avec réservations
    @Query("SELECT DISTINCT u FROM User u JOIN u.reservations r")
    List<User> findUsersAvecReservations();
    
    // Utilisateurs sans réservations
    @Query("SELECT u FROM User u WHERE u.reservations IS EMPTY")
    List<User> findUsersSansReservations();
    
    // Dernière connexion récente
    @Query("SELECT u FROM User u WHERE u.lastLogin >= :date ORDER BY u.lastLogin DESC")
    List<User> findUsersConnectesRecemment(@Param("date") LocalDateTime date);
    
    // Utilisateurs jamais connectés
    @Query("SELECT u FROM User u WHERE u.lastLogin IS NULL")
    List<User> findUsersJamaisConnectes();
    
    // Compter par rôle
    long countByRole(Role role);
    
    // Compter utilisateurs actifs
    long countByEnabledTrue();
    
    // Nouveaux utilisateurs (derniers 30 jours)
    @Query("SELECT u FROM User u WHERE u.createdAt >= :date ORDER BY u.createdAt DESC")
    List<User> findNouveauxUtilisateurs(@Param("date") LocalDateTime date);
    
    // Statistiques - Utilisateurs par mois
    @Query("SELECT MONTH(u.createdAt), COUNT(u) FROM User u GROUP BY MONTH(u.createdAt)")
    List<Object[]> getUtilisateursParMois();
    
    // Top utilisateurs par nombre de réservations
    @Query("SELECT u, COUNT(r) FROM User u LEFT JOIN u.reservations r " +
           "GROUP BY u ORDER BY COUNT(r) DESC")
    List<Object[]> getTopUtilisateurs();
}
