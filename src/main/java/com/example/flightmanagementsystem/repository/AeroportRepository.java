package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Aeroport;
import com.example.flightmanagementsystem.model.enums.StatutAeroport;
import com.example.flightmanagementsystem.model.enums.TypeAeroport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
    
    // Recherche par code IATA
    Optional<Aeroport> findByCodeIATA(String codeIATA);
    
    // Vérifier si code IATA existe
    boolean existsByCodeIATA(String codeIATA);
    
    // Recherche par code ICAO
    Optional<Aeroport> findByCodeICAO(String codeICAO);
    
    // Vérifier si code ICAO existe
    boolean existsByCodeICAO(String codeICAO);
    
    // Recherche par nom (insensible à la casse)
    @Query("SELECT a FROM Aeroport a WHERE LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Aeroport> searchByNom(@Param("nom") String nom);
    
    // Recherche par ville
    List<Aeroport> findByVille(String ville);
    
    // Recherche par pays
    List<Aeroport> findByPays(String pays);
    
    // Recherche par ville et pays
    List<Aeroport> findByVilleAndPays(String ville, String pays);
    
    // Recherche par type
    List<Aeroport> findByTypeAeroport(TypeAeroport typeAeroport);
    
    // Recherche par statut
    List<Aeroport> findByStatut(StatutAeroport statut);
    
    // Aéroports opérationnels
    @Query("SELECT a FROM Aeroport a WHERE a.statut = 'OPERATIONNEL'")
    List<Aeroport> findAeroportsOperationnels();
    
    // Aéroports internationaux
    @Query("SELECT a FROM Aeroport a WHERE a.typeAeroport = 'INTERNATIONAL'")
    List<Aeroport> findAeroportsInternationaux();
    
    // Aéroports par capacité minimale
    @Query("SELECT a FROM Aeroport a WHERE a.capacitePassagers >= :capaciteMin")
    List<Aeroport> findByCapaciteMinimum(@Param("capaciteMin") Integer capaciteMin);
    
    // Aéroports dans une zone géographique
    @Query("SELECT a FROM Aeroport a WHERE a.latitude BETWEEN :latMin AND :latMax " +
           "AND a.longitude BETWEEN :lonMin AND :lonMax")
    List<Aeroport> findInZoneGeographique(
        @Param("latMin") Double latMin,
        @Param("latMax") Double latMax,
        @Param("lonMin") Double lonMin,
        @Param("lonMax") Double lonMax
    );
    
    // Compter par pays
    @Query("SELECT COUNT(a) FROM Aeroport a WHERE a.pays = :pays")
    long countByPays(@Param("pays") String pays);
    
    // Compter par statut
    long countByStatut(StatutAeroport statut);
    
    // Compter par type
    long countByTypeAeroport(TypeAeroport typeAeroport);
    
    // Aéroports avec vols de départ aujourd'hui
    @Query("SELECT DISTINCT a FROM Aeroport a JOIN a.volsDepart v WHERE v.dateVol = CURRENT_DATE")
    List<Aeroport> findAeroportsAvecVolsAujourdhui();
    
    // Top aéroports par nombre de vols
    @Query("SELECT a, COUNT(v) FROM Aeroport a LEFT JOIN a.volsDepart v " +
           "GROUP BY a ORDER BY COUNT(v) DESC")
    List<Object[]> getTopAeroportsByVols();
    
    // Statistiques - Nombre de vols par aéroport
    @Query("SELECT a.nom, COUNT(v) FROM Aeroport a LEFT JOIN a.volsDepart v " +
           "GROUP BY a.nom")
    List<Object[]> getVolsParAeroport();
}
