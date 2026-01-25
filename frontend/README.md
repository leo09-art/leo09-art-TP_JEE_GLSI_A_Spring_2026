# EgaBank - Frontend

Application bancaire moderne construite avec React, TypeScript et Vite.

## Fonctionnalités

- Authentification JWT (login/register)
- Dashboard avec statistiques
- Gestion des comptes bancaires
- Transactions (versement, retrait, virement)
- Gestion du profil utilisateur
- Interface responsive et moderne

## Technologies

- React 19
- TypeScript
- Vite
- React Router v7
- Axios
- CSS modules

## Installation

```bash
npm install
```

## Démarrage

```bash
npm run dev
```

L'application sera accessible sur http://localhost:5173

## Configuration

Le frontend se connecte au backend Spring Boot sur http://localhost:8080. Le proxy est configuré automatiquement dans `vite.config.ts`.

## Comptes de test

Utilisez le compte admin créé automatiquement:
- Email: admin@bank.com
- Mot de passe: admin123

Ou créez un nouveau compte via la page d'inscription.

## Structure du projet

```
src/
├── components/      # Composants réutilisables
├── contexts/        # Contextes React (Auth)
├── pages/           # Pages de l'application
├── services/        # Services API
├── styles/          # Fichiers CSS
└── types/           # Types TypeScript
```

## Build

```bash
npm run build
```

Les fichiers de production seront générés dans le dossier `dist/`.
