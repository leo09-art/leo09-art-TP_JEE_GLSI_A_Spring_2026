# EgaBank - Application Bancaire Complète

Application bancaire full-stack avec backend Spring Boot et frontend React.

## Architecture

- **Backend**: Spring Boot 4.0.1 + MySQL + JWT Authentication
- **Frontend**: React 19 + TypeScript + Vite

## Prérequis

- Java 17+
- Maven 3.9+
- Node.js 18+
- MySQL 8.0+

## Installation et démarrage

### 1. Configuration de la base de données

Créez une base de données MySQL:

```sql
CREATE DATABASE egabank_db;
```

Configurez les paramètres dans `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/egabank_db
spring.datasource.username=root
spring.datasource.password=12345678
```

### 2. Démarrage du backend

```bash
# Depuis la racine du projet
./mvnw spring-boot:run
```

Le backend sera accessible sur http://localhost:8080

### 3. Démarrage du frontend

```bash
# Accédez au dossier frontend
cd frontend

# Installez les dépendances
npm install

# Lancez le serveur de développement
npm run dev
```

Le frontend sera accessible sur http://localhost:5173

## Compte administrateur

Un compte admin est créé automatiquement au démarrage:
- Email: admin@bank.com
- Mot de passe: admin123

## Fonctionnalités

### Authentification
- Inscription avec validation
- Connexion JWT
- Refresh token automatique

### Gestion des clients
- Profil utilisateur
- Modification des coordonnées

### Gestion des comptes
- Création de comptes (courant/épargne)
- Consultation des soldes
- Suppression de comptes

### Transactions
- Versements
- Retraits
- Virements entre comptes
- Historique des transactions

## Structure du projet

```
.
├── src/                          # Backend Spring Boot
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/bankega/
│   │   │       ├── component/    # Filtres JWT
│   │   │       ├── config/       # Configuration
│   │   │       ├── controller/   # API REST
│   │   │       ├── dto/          # Data Transfer Objects
│   │   │       ├── entity/       # Entités JPA
│   │   │       ├── enums/        # Énumérations
│   │   │       ├── exception/    # Gestion des erreurs
│   │   │       ├── mapper/       # Mappers DTO
│   │   │       ├── repository/   # Repositories JPA
│   │   │       └── service/      # Services métier
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── frontend/                     # Frontend React
│   ├── src/
│   │   ├── components/          # Composants réutilisables
│   │   ├── contexts/            # Contextes React
│   │   ├── pages/               # Pages de l'application
│   │   ├── services/            # Services API
│   │   ├── styles/              # Fichiers CSS
│   │   └── types/               # Types TypeScript
│   └── package.json
└── pom.xml
```

## API Endpoints

### Authentification
- POST `/api/login` - Connexion
- POST `/api/register` - Inscription
- POST `/api/refresh` - Refresh token

### Clients (authentification requise)
- GET `/api/auth/clients/me` - Profil actuel
- PUT `/api/auth/clients/me` - Modifier profil
- GET `/api/auth/clients` - Liste des clients (admin)

### Comptes (authentification requise)
- GET `/api/auth/comptes` - Mes comptes
- POST `/api/auth/comptes` - Créer un compte
- POST `/api/auth/comptes/delete` - Supprimer un compte
- GET `/api/auth/comptes/count` - Nombre de comptes

### Transactions (authentification requise)
- POST `/api/auth/transactions/versement` - Effectuer un versement
- POST `/api/auth/transactions/retrait` - Effectuer un retrait
- POST `/api/auth/transactions/virement` - Effectuer un virement
- GET `/api/auth/transactions/all` - Historique complet

## Technologies utilisées

### Backend
- Spring Boot 4.0.1
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Lombok
- IBAN4J

### Frontend
- React 19
- TypeScript
- Vite
- React Router v7
- Axios
- CSS Modules

## Sécurité

- Authentification JWT avec access et refresh tokens
- Hashage des mots de passe avec BCrypt
- Protection CSRF désactivée (API REST)
- CORS configuré pour localhost:5173
- Validation des entrées côté backend
- Gestion sécurisée des comptes et transactions
