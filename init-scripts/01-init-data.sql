-- Initialisation de la base de données Flight Management System
-- Ce script est exécuté automatiquement lors du premier démarrage de PostgreSQL

-- Création de la base de données (si elle n'existe pas déjà)
-- Note: Dans docker-compose, la base est déjà créée via POSTGRES_DB

-- Création d'utilisateurs de test
INSERT INTO users (username, email, password, nom, prenom, telephone, role, enabled, created_at, updated_at)
VALUES
    ('admin', 'admin@flight.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeEwEr8kNqJZ9kNtgjVq9lSz1zZiLrq./', 'Administrateur', 'Système', '+212600000000', 'ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('manager1', 'manager@flight.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeEwEr8kNqJZ9kNtgjVq9lSz1zZiLrq./', 'Dupont', 'Jean', '+212611111111', 'MANAGER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('agent1', 'agent@flight.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeEwEr8kNqJZ9kNtgjVq9lSz1zZiLrq./', 'Martin', 'Sophie', '+212622222222', 'AGENT', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('user1', 'user@flight.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeEwEr8kNqJZ9kNtgjVq9lSz1zZiLrq./', 'Benali', 'Ahmed', '+212633333333', 'USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (username) DO NOTHING;

-- Création d'aéroports de test
INSERT INTO aeroports (code_iata, nom, ville, pays, statut, created_at, updated_at)
VALUES
    ('CMN', 'Mohammed V International Airport', 'Casablanca', 'Maroc', 'OPERATIONNEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('CDG', 'Charles de Gaulle Airport', 'Paris', 'France', 'OPERATIONNEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('JFK', 'John F. Kennedy International Airport', 'New York', 'États-Unis', 'OPERATIONNEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('LHR', 'London Heathrow Airport', 'Londres', 'Royaume-Uni', 'OPERATIONNEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (code_iata) DO NOTHING;

-- Création d'avions de test
INSERT INTO avions (type, modele, capacite, statut, created_at, updated_at)
VALUES
    ('Boeing', '737-800', 180, 'OPERATIONNEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Airbus', 'A320', 150, 'OPERATIONNEL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Boeing', '777-300ER', 350, 'MAINTENANCE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (type, modele) DO NOTHING;

-- Création de passagers de test
INSERT INTO passagers (cin, nom, prenom, email, telephone, date_naissance, genre, nationalite, created_at, updated_at)
VALUES
    ('EE123456', 'Tazi', 'Fatima', 'fatima@email.com', '+212644444444', '1990-05-15', 'FEMININ', 'Marocaine', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EE789012', 'Alaoui', 'Youssef', 'youssef@email.com', '+212655555555', '1985-12-10', 'MASCULIN', 'Marocaine', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (cin) DO NOTHING;

-- Création d'équipages de test
INSERT INTO equipages (nom, fonction, statut, created_at, updated_at)
VALUES
    ('Équipage Alpha', 'PILOTE', 'ACTIF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Équipage Bravo', 'COPILOTE', 'ACTIF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Équipage Charlie', 'HOTESSE', 'ACTIF', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (nom) DO NOTHING;

-- Note: Les vols et réservations nécessitent des données plus complexes et sont créés via l'API
-- Ces données de base permettent de tester l'application immédiatement

COMMIT;
