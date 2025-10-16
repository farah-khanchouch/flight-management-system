package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, Long> {
    
    // Recherche par email
    Optional<Passager> findByEmail(String email);
    
    // Vérifier si email existe
    boolean existsByEmail(String email);
    
    // Recherche par numéro de passeport
    Optional<Passager> findByNumeroPasseport(String numeroPasseport);
    
    // Vérifier si passeport existe
    boolean existsByNumeroPasseport(String numeroPasseport);
    
    // Recherche par numéro de carte d'identité
    Optional<Passager> findByNumeroCarteIdentite(String numeroCarteIdentite);
    
    // Vérifier si carte d'identité existe
    boolean existsByNumeroCarteIdentite(String numeroCarteIdentite);
    
    // Recherche par nom et prénom
    List<Passager> findByNomAndPrenom(String nom, String prenom);
    
    // Recherche par nom (insensible à la casse)
    @Query("SELECT p FROM Passager p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Passager> searchByNom(@Param("nom") String nom);
    
    // Recherche par prénom (insensible à la casse)
    @Query("SELECT p FROM Passager p WHERE LOWER(p.prenom) LIKE LOWER(CONCAT('%', :prenom, '%'))")
    List<Passager> searchByPrenom(@Param("prenom") String prenom);
    
    // Recherche par nom ou prénom
    @Query("SELECT p FROM Passager p WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.prenom) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Passager> searchByNomOrPrenom(@Param("keyword") String keyword);
    
    // Recherche par nationalité
    List<Passager> findByNationalite(String nationalite);
    
    // Recherche par téléphone
    Optional<Passager> findByTelephone(String telephone);
    
    // Recherche par date de naissance
    List<Passager> findByDateNaissance(LocalDate dateNaissance);
    
    // Recherche par pays
    List<Passager> findByPays(String pays);
    
    // Recherche par ville
    List<Passager> findByVille(String ville);
    
    // Passagers avec réservations
    @Query("SELECT DISTINCT p FROM Passager p JOIN p.reservations r")
    List<Passager> findPassagersAvecReservations();
    
    // Passagers d'un vol spécifique
    @Query("SELECT DISTINCT p FROM Passager p JOIN p.reservations r WHERE r.vol.id = :volId")
    List<Passager> findPassagersByVol(@Param("volId") Long volId);
    
    // Passagers par utilisateur
    @Query("SELECT p FROM Passager p WHERE p.user.id = :userId")
    Optional<Passager> findByUserId(@Param("userId") Long userId);
    
    // Compter passagers par nationalité
    @Query("SELECT COUNT(p) FROM Passager p WHERE p.nationalite = :nationalite")
    long countByNationalite(@Param("nationalite") String nationalite);
    
    // Statistiques - Passagers enregistrés par mois
    @Query("SELECT MONTH(p.createdAt), COUNT(p) FROM Passager p GROUP BY MONTH(p.createdAt)")
    List<Object[]> getPassagersParMois();
}
