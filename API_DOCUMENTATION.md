# Documentation API - EgaBank

Base URL: `http://localhost:8080`

## Authentification

### POST /api/login
Connexion utilisateur

**Body:**
```json
{
  "username": "admin@bank.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

### POST /api/register
Inscription d'un nouveau client

**Body:**
```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "dateNais": "1990-01-15T00:00:00",
  "sexe": "M",
  "adresse": "123 Rue de la Paix",
  "tel": "0612345678",
  "courriel": "jean.dupont@email.com",
  "nationalite": "Française",
  "password": "motdepasse123"
}
```

**Response:**
```json
{
  "id": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "courriel": "jean.dupont@email.com",
  ...
}
```

### POST /api/refresh
Rafraîchir le access token

**Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs..."
}
```

---

## Clients (Authentification requise)

**Header requis:** `Authorization: Bearer {accessToken}`

### GET /api/auth/clients/me
Récupérer le profil du client connecté

**Response:**
```json
{
  "id": 1,
  "nom": "Dupont",
  "prenom": "Jean",
  "dateNais": "1990-01-15T00:00:00",
  "sexe": "M",
  "adresse": "123 Rue de la Paix",
  "tel": "0612345678",
  "nationalite": "Française",
  "courriel": "jean.dupont@email.com",
  "actif": true
}
```

### PUT /api/auth/clients/me
Modifier son profil

**Body:**
```json
{
  "adresse": "456 Avenue des Champs",
  "tel": "0687654321",
  "password": "motdepasse123"
}
```

**Response:** Client DTO mis à jour

### GET /api/auth/clients
Liste de tous les clients (Admin)

**Response:** Array de Client DTO

### GET /api/auth/clients/user
Récupérer les informations de l'utilisateur connecté

**Response:**
```json
{
  "username": "jean.dupont@email.com",
  "password": "hashedPassword"
}
```

---

## Comptes (Authentification requise)

### GET /api/auth/comptes
Liste des comptes du client connecté

**Response:**
```json
[
  {
    "id": 1,
    "num": "FR7630001007941234567890185",
    "solde": 1500.50,
    "type": "COURANT",
    "dateCreation": "2024-01-15T10:30:00",
    "actif": true
  }
]
```

### GET /api/auth/comptes/all
Liste de tous les comptes actifs (tous clients)

**Response:** Array de Compte DTO

### POST /api/auth/comptes
Créer un nouveau compte

**Body:**
```json
{
  "accountForm": "COURANT",
  "password": "motdepasse123"
}
```

**Response:** Compte DTO créé

### POST /api/auth/comptes/delete
Supprimer un compte (solde doit être 0)

**Body:**
```json
{
  "numCompte": "FR7630001007941234567890185",
  "password": "motdepasse123"
}
```

**Response:** "Compte désactivé avec succès"

### GET /api/auth/comptes/count
Nombre de comptes actifs du client

**Response:** `3`

---

## Transactions (Authentification requise)

### POST /api/auth/transactions/versement
Effectuer un versement

**Body:**
```json
{
  "numCompte": "FR7630001007941234567890185",
  "montant": 500.00,
  "password": "motdepasse123"
}
```

**Response:**
```json
{
  "id": 1,
  "type": "DEPOT",
  "montant": 500.00,
  "date": "2024-01-15T14:30:00",
  "compteNum": "FR7630001007941234567890185"
}
```

### POST /api/auth/transactions/retrait
Effectuer un retrait

**Body:**
```json
{
  "numCompte": "FR7630001007941234567890185",
  "montant": 200.00,
  "password": "motdepasse123"
}
```

**Response:** Transaction DTO

### POST /api/auth/transactions/virement
Effectuer un virement

**Body:**
```json
{
  "numCompteSource": "FR7630001007941234567890185",
  "numCompteDest": "FR7630001007942345678901296",
  "montant": 300.00,
  "password": "motdepasse123"
}
```

**Response:**
```json
{
  "message": "Virement effectué avec succès"
}
```

### GET /api/auth/transactions/all
Historique complet des transactions

**Response:** Array de Transaction DTO

### GET /api/auth/transactions
Transactions par plage de dates

**Query Params:**
- `dateDebut`: ISO 8601 DateTime (ex: 2024-01-01T00:00:00)
- `dateFin`: ISO 8601 DateTime (ex: 2024-12-31T23:59:59)

**Response:** Array de Transaction DTO

---

## Codes d'erreur

### 401 Unauthorized
Token JWT invalide ou expiré

### 403 Forbidden
- Mot de passe incorrect
- Accès non autorisé au compte

### 404 Not Found
- Client introuvable
- Compte introuvable
- Transaction introuvable

### 400 Bad Request
- Solde insuffisant
- Champs requis manquants
- Format de données invalide

### 409 Conflict
- Email déjà utilisé

### 500 Internal Server Error
- Erreur serveur

---

## Modèles de données

### Client
```typescript
{
  id: number;
  nom: string;
  prenom: string;
  dateNais: string;  // ISO DateTime
  sexe: string;      // "M" ou "F"
  adresse: string;
  tel: string;
  nationalite: string;
  courriel: string;
  actif: boolean;
}
```

### Compte
```typescript
{
  id: number;
  num: string;           // IBAN
  solde: number;
  type: "COURANT" | "EPARGNE";
  dateCreation: string;  // ISO DateTime
  actif: boolean;
}
```

### Transaction
```typescript
{
  id: number;
  type: "DEPOT" | "RETRAIT" | "VIREMENT_DEBIT" | "VIREMENT_CREDIT";
  montant: number;
  date: string;          // ISO DateTime
  compteNum: string;     // IBAN
}
```

---

## Sécurité

### JWT Tokens
- **Access Token**: Expire après 15 minutes
- **Refresh Token**: Expire après 24 heures
- Algorithme: HS256
- Header requis: `Authorization: Bearer {token}`

### Validation
- Tous les endpoints (sauf login/register) requièrent l'authentification
- Chaque opération sensible requiert le mot de passe
- Vérification de propriété des comptes avant toute opération
- Validation des montants (> 0)
- Vérification du solde avant retrait/virement

### CORS
- Origine autorisée: http://localhost:5173
- Méthodes: GET, POST, PUT, DELETE, OPTIONS
- Headers autorisés: *
- Credentials: true
