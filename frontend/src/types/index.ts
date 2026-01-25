export interface Client {
  id: number;
  nom: string;
  prenom: string;
  dateNais: string;
  sexe: string;
  adresse: string;
  tel: string;
  nationalite: string;
  courriel: string;
  actif: boolean;
}

export interface Compte {
  id: number;
  num: string;
  solde: number;
  type: 'COURANT' | 'EPARGNE';
  dateCreation: string;
  actif: boolean;
}

export interface Transaction {
  id: number;
  type: 'DEPOT' | 'RETRAIT' | 'VIREMENT_DEBIT' | 'VIREMENT_CREDIT';
  montant: number;
  date: string;
  compteNum: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  nom: string;
  prenom: string;
  dateNais: string;
  sexe: string;
  adresse: string;
  tel: string;
  courriel: string;
  nationalite: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
}

export interface CompteCreateRequest {
  accountForm: 'COURANT' | 'EPARGNE';
  password: string;
}

export interface TransactionRequest {
  numCompte: string;
  montant: number;
  password: string;
}

export interface VirementRequest {
  numCompteSource: string;
  numCompteDest: string;
  montant: number;
  password: string;
}
