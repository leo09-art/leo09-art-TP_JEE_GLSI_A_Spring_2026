# Guide de démarrage rapide - EgaBank

## Étapes pour lancer l'application

### 1. Créer la base de données

```sql
CREATE DATABASE egabank_db;
```

### 2. Lancer le backend Spring Boot

Depuis la racine du projet:

```bash
./mvnw spring-boot:run
```

Le backend démarre sur http://localhost:8080

### 3. Installer et lancer le frontend

Dans un nouveau terminal:

```bash
cd frontend
npm install
npm run dev
```

Le frontend démarre sur http://localhost:5173

### 4. Se connecter

Ouvrez http://localhost:5173 dans votre navigateur.

Utilisez le compte admin créé automatiquement:
- Email: `admin@bank.com`
- Mot de passe: `admin123`

Ou créez un nouveau compte via le bouton "S'inscrire".

## Fonctionnalités à tester

### Dashboard
- Vue d'ensemble des comptes et transactions
- Statistiques en temps réel

### Mes comptes
- Créer un nouveau compte (courant ou épargne)
- Consulter les détails de chaque compte
- Supprimer un compte (solde doit être à 0)

### Transactions
- Effectuer un versement
- Effectuer un retrait
- Faire un virement vers un autre compte
- Consulter l'historique complet

### Profil
- Voir ses informations personnelles
- Modifier son adresse et téléphone

## Ports utilisés

- Backend: http://localhost:8080
- Frontend: http://localhost:5173
- MySQL: localhost:3306

## Dépannage

### Le backend ne démarre pas
- Vérifiez que MySQL est en cours d'exécution
- Vérifiez les paramètres de connexion dans `application.properties`
- Assurez-vous que la base de données `egabank_db` existe

### Le frontend ne se connecte pas
- Vérifiez que le backend est bien démarré sur le port 8080
- Vérifiez la console du navigateur pour les erreurs
- Le proxy Vite devrait automatiquement rediriger `/api` vers le backend

### Erreur d'authentification
- Supprimez les tokens du localStorage du navigateur
- Déconnectez-vous et reconnectez-vous
