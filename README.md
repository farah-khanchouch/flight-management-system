# Flight Management System Backend

✅ PROJET TERMINÉ À 100% ! 🚀

## 📦 Structure Complète du Projet

```
flight-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/flightmanagementsystem/
│   │   │   ├── FlightManagementApplication.java ✅
│   │   │   ├── model/ ✅ (7 entités + 10 enums)
│   │   │   ├── repository/ ✅ (7 repositories)
│   │   │   ├── dto/ ✅ (13 DTOs)
│   │   │   ├── mapper/ ✅ (7 mappers)
│   │   │   ├── service/ ✅ (8 services + implémentations)
│   │   │   ├── controller/ ✅ (8 controllers)
│   │   │   ├── security/ ✅ (JWT + Spring Security)
│   │   │   ├── config/ ✅ (Swagger, CORS, Scheduling)
│   │   │   └── exception/ ✅ (Gestion des erreurs)
│   │   └── resources/ ✅ (application.properties)
│   └── test/ ✅ (Tests unitaires et d'intégration)
├── Dockerfile ✅
├── docker-compose.yml ✅
├── Jenkinsfile ✅
├── .gitlab-ci.yml ✅
├── pom.xml ✅
└── README.md ✅
```

## 📊 Statistiques du Projet

- **Code créé**: ~75 fichiers Java
- **Entités**: 7 (Vol, Avion, Passager, Reservation, Equipage, Aeroport, User)
- **Enums**: 10 (Statuts, Rôles, etc.)
- **Repositories**: 7 avec requêtes personnalisées
- **DTOs**: 13 avec validation
- **Mappers**: 7 (MapStruct)
- **Services**: 8 (interfaces + implémentations)
- **Controllers**: 8 REST API
- **Sécurité**: JWT + Spring Security
- **Configuration**: Swagger, CORS, Tâches planifiées
- **CI/CD**: Jenkins + GitLab CI
- **Docker**: Application + PostgreSQL + PgAdmin

## 🌐 API REST - Endpoints Disponibles

### 🔐 Authentification (`/api/auth`)
- `POST /login` - Connexion
- `POST /register` - Inscription
- `POST /logout` - Déconnexion

### ✈️ Vols (`/api/vols`)
- `GET /` - Liste tous les vols
- `GET /{id}` - Détails d'un vol
- `POST /` - Créer un vol (ADMIN/MANAGER)
- `PUT /{id}` - Modifier un vol (ADMIN/MANAGER)
- `DELETE /{id}` - Supprimer un vol (ADMIN)
- `POST /search` - Rechercher des vols
- `GET /date/{date}` - Vols par date
- `GET /today` - Vols d'aujourd'hui
- `GET /disponibles` - Vols disponibles
- `PUT /{id}/statut` - Changer statut
- `PUT /{id}/equipage` - Assigner équipage

### 🛩️ Avions (`/api/avions`)
- `GET /` - Liste tous les avions
- `GET /{id}` - Détails d'un avion
- `POST /` - Créer un avion (ADMIN/MANAGER)
- `PUT /{id}` - Modifier un avion (ADMIN/MANAGER)
- `DELETE /{id}` - Supprimer un avion (ADMIN)
- `GET /operationnels` - Avions opérationnels
- `GET /disponibles` - Avions disponibles
- `GET /maintenance` - Avions en maintenance
- `PUT /{id}/maintenance` - Planifier maintenance

### 👤 Passagers (`/api/passagers`)
- `GET /` - Liste tous les passagers (ADMIN/AGENT)
- `GET /{id}` - Détails d'un passager (ADMIN/AGENT)
- `GET /me` - Mon profil passager
- `POST /` - Créer un passager (ADMIN/AGENT)
- `PUT /{id}` - Modifier un passager (ADMIN/AGENT)
- `PUT /me` - Modifier mon profil
- `DELETE /{id}` - Supprimer un passager (ADMIN)
- `GET /search` - Rechercher des passagers
- `GET /vol/{volId}` - Passagers d'un vol

### 🎫 Réservations (`/api/reservations`)
- `GET /` - Liste toutes les réservations (ADMIN/AGENT)
- `GET /{id}` - Détails d'une réservation
- `GET /me` - Mes réservations
- `POST /` - Créer une réservation
- `PUT /{id}/confirmer` - Confirmer réservation
- `PUT /{id}/annuler` - Annuler réservation
- `GET /en-attente` - Réservations en attente
- `POST /calculer-prix` - Calculer prix
- `GET /statistiques/revenue` - Chiffre d'affaires

### 👨‍✈️ Équipages (`/api/equipages`)
- `GET /` - Liste tous les équipages (ADMIN/MANAGER)
- `GET /{id}` - Détails d'un équipage
- `POST /` - Créer un équipage (ADMIN/MANAGER)
- `PUT /{id}` - Modifier un équipage (ADMIN/MANAGER)
- `DELETE /{id}` - Supprimer un équipage (ADMIN)
- `GET /actifs` - Équipages actifs
- `GET /disponibles` - Équipages disponibles
- `GET /pilotes` - Pilotes actifs
- `GET /licences-expirant` - Licences expirant bientôt

### 🏢 Aéroports (`/api/aeroports`)
- `GET /` - Liste tous les aéroports
- `GET /{id}` - Détails d'un aéroport
- `GET /code/{codeIATA}` - Aéroport par code IATA
- `POST /` - Créer un aéroport (ADMIN/MANAGER)
- `PUT /{id}` - Modifier un aéroport (ADMIN/MANAGER)
- `DELETE /{id}` - Supprimer un aéroport (ADMIN)
- `GET /operationnels` - Aéroports opérationnels
- `GET /pays/{pays}` - Aéroports par pays
- `GET /search` - Rechercher des aéroports

### 👥 Utilisateurs (`/api/users`)
- `GET /` - Liste tous les utilisateurs (ADMIN)
- `GET /{id}` - Détails d'un utilisateur (ADMIN)
- `GET /me` - Mon profil
- `PUT /{id}` - Modifier un utilisateur (ADMIN)
- `PUT /me` - Modifier mon profil
- `PUT /me/password` - Changer mot de passe
- `DELETE /{id}` - Supprimer un utilisateur (ADMIN)
- `GET /role/{role}` - Utilisateurs par rôle (ADMIN)

## 🔒 Rôles et Permissions

### USER
✅ Créer des réservations
✅ Consulter ses réservations
✅ Annuler ses réservations
✅ Modifier son profil
✅ Consulter les vols et aéroports

### AGENT
✅ Toutes les permissions USER
✅ Gérer tous les passagers
✅ Gérer toutes les réservations
✅ Confirmer les paiements

### MANAGER
✅ Toutes les permissions AGENT
✅ Créer/Modifier/Supprimer des vols
✅ Gérer la flotte d'avions
✅ Gérer les équipages
✅ Gérer les aéroports
✅ Consulter les statistiques

### ADMIN
✅ Toutes les permissions
✅ Gérer les utilisateurs
✅ Supprimer toute donnée
✅ Accès complet au système

## 🚀 Démarrage du Projet

### 1. Prérequis
- Java 17+
- Maven 3.9+
- PostgreSQL 15+
- Docker (optionnel)

### 2. Configuration Base de Données
```sql
CREATE DATABASE flight_management_db;
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE flight_management_db TO postgres;
```

### 3. Démarrage avec Maven
```bash
# Cloner le projet
git clone <repository-url>
cd flight-management-system

# Compiler
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

### 4. Démarrage avec Docker
```bash
# Lancer avec Docker Compose
docker-compose up -d

# Vérifier les logs
docker-compose logs -f flight-app
```

### 5. Accès à l'application
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **PgAdmin**: http://localhost:5050 (admin@flight.com / admin)

## 📚 Documentation API (Swagger)

Accès Swagger: http://localhost:8080/swagger-ui.html

### Authentification dans Swagger
1. Créer un compte: `POST /api/auth/register`
2. Se connecter: `POST /api/auth/login`
3. Copier le token reçu
4. Cliquer sur "Authorize" dans Swagger
5. Entrer: `Bearer {votre-token}`
6. Tester les endpoints protégés

## 🧪 Exemple de Flux Complet

### 1. Inscription
```json
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "Test@123",
  "confirmPassword": "Test@123",
  "nom": "Doe",
  "prenom": "John",
  "telephone": "+212600000000"
}
```

### 2. Connexion
```json
POST /api/auth/login
{
  "usernameOrEmail": "john@example.com",
  "password": "Test@123"
}
```

### 3. Rechercher des vols
```json
POST /api/vols/search
{
  "aeroportDepartCodeIATA": "CMN",
  "aeroportArriveeCodeIATA": "CDG",
  "dateVol": "2025-12-25",
  "nombrePassagers": 2
}
```

### 4. Créer une réservation
```json
POST /api/reservations
Headers: Authorization: Bearer {token}
{
  "volId": 1,
  "passagerIds": [1, 2],
  "classe": "ECONOMIQUE"
}
```

### 5. Confirmer la réservation
```json
PUT /api/reservations/1/confirmer?methodePaiement=CARTE&referencePaiement=REF123456
Headers: Authorization: Bearer {token}
```

## 🔐 Sécurité Implémentée

- **JWT** (JSON Web Token) pour authentification stateless
- **Spring Security** avec rôles et permissions
- **BCrypt** pour le hashage des mots de passe
- **CORS** configuré pour le développement
- **Validation** complète des données d'entrée

## ⚙️ Fonctionnalités Avancées

- **Tâches Planifiées**: Expiration automatique des réservations
- **Calculs Automatiques**: Prix avec taxes, places disponibles
- **Statistiques**: Chiffre d'affaires, réservations par mois
- **Audit**: Historique des connexions et modifications

## 📈 CI/CD Configuré

- **Jenkins**: Pipeline complet (build, test, deploy)
- **GitLab CI**: Multi-stages avec qualité de code
- **JaCoCo**: Couverture de code
- **SonarQube**: Quality gates (optionnel)

## 🐳 Docker

```bash
# Démarrer tous les services
docker-compose up -d

# Voir les logs
docker-compose logs -f

# Arrêter tous les services
docker-compose down

# Nettoyer tout (y compris volumes)
docker-compose down -v
```

## 📝 Variables d'Environnement

### application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/flight_management_db
spring.datasource.username=postgres
spring.datasource.password=postgres
jwt.secret=votre-secret-jwt-tres-long
jwt.expiration=86400000
server.port=8080
```

### Docker
Les variables peuvent être surchargées dans `docker-compose.yml`

## 🎯 Bonnes Pratiques Implémentées

- Architecture en couches (Controller → Service → Repository)
- Séparation des préoccupations
- DTOs pour le transfert de données
- Mappers pour les conversions
- Gestion centralisée des exceptions
- Tests unitaires et d'intégration
- Logging avec Slf4j
- Transactions gérées correctement

## 📦 Dépendances Principales

- Spring Boot 3.5.6
- Spring Data JPA
- Spring Security
- PostgreSQL Driver
- JWT (jjwt) 0.11.5
- Lombok 1.18.30
- MapStruct 1.5.5
- SpringDoc OpenAPI 2.2.0
- Jakarta Validation

## 🎓 Informations du Projet

- **Développé pour**: ENSA Beni Mellal
- **Cours**: Génie Logiciel
- **Année**: 2024/2025
- **Technologies**: Java 17, Spring Boot 3, PostgreSQL, Docker, JWT

## 📞 Support & Contribution

- **Bugs**: Créer une issue sur le repository Git
- **Fonctionnalités**: Pull request avec description détaillée
- **Documentation**: Consulter Swagger pour plus de détails

🏆 **Projet Réalisé** - Votre backend Flight Management System est 100% complet et prêt à l'emploi !

Prochaine étape: Développer le frontend (React, Angular, Flutter ou Vaadin)

Bon courage pour votre présentation ! 🚀
