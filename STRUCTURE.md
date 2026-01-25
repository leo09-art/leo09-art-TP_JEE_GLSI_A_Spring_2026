# Structure du projet EgaBank

```
project/
│
├── README.md                          # Documentation principale
├── QUICKSTART.md                      # Guide de démarrage rapide
├── FEATURES.md                        # Liste des fonctionnalités
├── API_DOCUMENTATION.md               # Documentation de l'API
├── STRUCTURE.md                       # Ce fichier
│
├── pom.xml                            # Configuration Maven
├── mvnw                               # Maven Wrapper (Unix)
├── mvnw.cmd                           # Maven Wrapper (Windows)
│
├── src/                               # CODE BACKEND
│   ├── main/
│   │   ├── java/com/example/bankega/
│   │   │   │
│   │   │   ├── BankegaApplication.java              # Point d'entrée
│   │   │   │
│   │   │   ├── component/                           # Composants techniques
│   │   │   │   ├── JwtAuthenticationFilter.java    # Filtre JWT
│   │   │   │   └── JwtUtil.java                    # Utilitaire JWT
│   │   │   │
│   │   │   ├── config/                              # Configuration
│   │   │   │   ├── SecurityConfig.java             # Config Spring Security
│   │   │   │   └── DataInitializer.java            # Init données (admin)
│   │   │   │
│   │   │   ├── controller/                          # Contrôleurs REST
│   │   │   │   ├── Login.java                      # Auth endpoints
│   │   │   │   ├── ClientController.java           # CRUD clients
│   │   │   │   ├── CompteController.java           # CRUD comptes
│   │   │   │   ├── TransactionController.java      # Operations bancaires
│   │   │   │   └── UserController.java             # Gestion users
│   │   │   │
│   │   │   ├── dto/                                 # Data Transfer Objects
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── ClientDTO.java
│   │   │   │   ├── ClientUpdateRequest.java
│   │   │   │   ├── CompteResponseDTO.java
│   │   │   │   ├── CompteRequest.java
│   │   │   │   ├── CompteUpdateRequest.java
│   │   │   │   ├── CreatedCompteRequest.java
│   │   │   │   ├── TransactionResponseDTO.java
│   │   │   │   ├── DepotRequest.java
│   │   │   │   ├── VirementRequest.java
│   │   │   │   ├── DeleteRequest.java
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── InscriptionRequest.java
│   │   │   │   └── RefreshRequest.java
│   │   │   │
│   │   │   ├── entity/                              # Entités JPA
│   │   │   │   ├── Client.java                     # Table clients
│   │   │   │   ├── Compte.java                     # Table comptes
│   │   │   │   ├── Transaction.java                # Table transactions
│   │   │   │   └── User.java                       # Table users
│   │   │   │
│   │   │   ├── enums/                               # Énumérations
│   │   │   │   ├── Role.java                       # ADMIN, CLIENT
│   │   │   │   ├── TypeCompte.java                 # COURANT, EPARGNE
│   │   │   │   └── TypeTransaction.java            # DEPOT, RETRAIT, etc.
│   │   │   │
│   │   │   ├── exception/                           # Gestion erreurs
│   │   │   │   ├── GlobalExceptionHandler.java     # Handler global
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── UnauthorizedAccessException.java
│   │   │   │   ├── InsufficientBalanceException.java
│   │   │   │   └── EmailAlreadyExistsException.java
│   │   │   │
│   │   │   ├── mapper/                              # Mappers DTO/Entity
│   │   │   │   ├── ClientMapper.java
│   │   │   │   ├── CompteMapper.java
│   │   │   │   ├── TransactionMapper.java
│   │   │   │   └── UserMapper.java
│   │   │   │
│   │   │   ├── repository/                          # Repositories JPA
│   │   │   │   ├── ClientRepository.java
│   │   │   │   ├── CompteRepository.java
│   │   │   │   ├── TransactionRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   │
│   │   │   └── service/                             # Logique métier
│   │   │       ├── IbanService.java                # Génération IBAN
│   │   │       ├── InscriptionService.java         # Inscription clients
│   │   │       ├── ClientService.java              # Gestion clients
│   │   │       ├── CompteService.java              # Gestion comptes
│   │   │       └── TransactionService.java         # Operations bancaires
│   │   │
│   │   └── resources/
│   │       └── application.properties               # Configuration app
│   │
│   └── test/                                        # Tests
│       └── java/com/example/bankega/
│           └── BankegaApplicationTests.java
│
└── frontend/                          # CODE FRONTEND
    │
    ├── README.md                      # Documentation frontend
    ├── package.json                   # Dépendances npm
    ├── vite.config.ts                 # Configuration Vite
    ├── tsconfig.json                  # Configuration TypeScript
    ├── index.html                     # Point d'entrée HTML
    │
    └── src/
        │
        ├── main.tsx                   # Point d'entrée React
        ├── App.tsx                    # Composant racine + routes
        ├── index.css                  # Styles globaux
        │
        ├── components/                # Composants réutilisables
        │   ├── Navbar.tsx            # Barre de navigation
        │   └── ProtectedRoute.tsx    # HOC pour routes protégées
        │
        ├── contexts/                  # Contextes React
        │   └── AuthContext.tsx       # État global authentification
        │
        ├── pages/                     # Pages de l'application
        │   ├── Login.tsx             # Page connexion
        │   ├── Register.tsx          # Page inscription
        │   ├── Dashboard.tsx         # Tableau de bord
        │   ├── Comptes.tsx           # Gestion des comptes
        │   ├── Transactions.tsx      # Operations et historique
        │   └── Profil.tsx            # Profil utilisateur
        │
        ├── services/                  # Services API
        │   └── api.ts                # Configuration Axios + endpoints
        │
        ├── styles/                    # Fichiers CSS
        │   ├── Navbar.css
        │   ├── Auth.css
        │   ├── Dashboard.css
        │   ├── Comptes.css
        │   ├── Transactions.css
        │   └── Profil.css
        │
        └── types/                     # Types TypeScript
            └── index.ts              # Interfaces et types
```

## Organisation par fonctionnalité

### Backend

**Authentification**
- Component: JwtUtil, JwtAuthenticationFilter
- Config: SecurityConfig
- Controller: Login
- Service: InscriptionService

**Clients**
- Entity: Client
- DTO: ClientDTO, ClientUpdateRequest
- Controller: ClientController
- Service: ClientService
- Repository: ClientRepository

**Comptes**
- Entity: Compte
- DTO: CompteResponseDTO, CreatedCompteRequest
- Controller: CompteController
- Service: CompteService, IbanService
- Repository: CompteRepository

**Transactions**
- Entity: Transaction
- DTO: TransactionResponseDTO, DepotRequest, VirementRequest
- Controller: TransactionController
- Service: TransactionService
- Repository: TransactionRepository

### Frontend

**Authentification**
- Context: AuthContext
- Pages: Login, Register
- Component: ProtectedRoute

**Navigation**
- Component: Navbar
- App: Routing principal

**Pages principales**
- Dashboard: Vue d'ensemble
- Comptes: Gestion des comptes
- Transactions: Opérations bancaires
- Profil: Informations utilisateur

**Infrastructure**
- Services: API centralisée avec Axios
- Types: Interfaces TypeScript
- Styles: CSS modulaire par page

## Points d'entrée

### Backend
- Main: `BankegaApplication.java`
- Port: 8080
- Base path: `/api`

### Frontend
- Main: `main.tsx`
- Port: 5173
- Proxy vers backend configuré

## Base de données

```
egabank_db
├── clients          (id, nom, prenom, email, etc.)
├── users            (id, username, password, role, client_id)
├── comptes          (id, num, type, solde, client_id)
└── transactions     (id, type, montant, date, compte_id)
```

## Conventions de nommage

### Backend
- Classes: PascalCase
- Méthodes: camelCase
- Packages: lowercase
- Constants: UPPER_SNAKE_CASE

### Frontend
- Composants: PascalCase
- Fichiers: PascalCase.tsx
- Fonctions: camelCase
- CSS classes: kebab-case

## Flux de données

```
Frontend              Backend                Database
--------              -------                --------
   |                     |                       |
   |---(HTTP/JSON)------>|                       |
   |                     |---(JPA/JDBC)--------->|
   |                     |<---(ResultSet)--------|
   |<---(JSON/JWT)-------|                       |
   |                     |                       |
```
