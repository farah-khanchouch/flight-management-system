# Flight Management System

API REST pour la gestion des vols, aéroports, passagers, réservations, équipages.

## Démarrage

1. Clone le repo.
2. Configure PostgreSQL avec DB `flight`.
3. Lance `mvn spring-boot:run`.
4. Accès : http://localhost:8080

## Endpoints

- /api/aeroports
- /api/vols
- /api/avions
- /api/passagers
- /api/reservations
- /api/equipages
- /auth/login

## Sécurité

Utilise JWT pour l'authentification.
