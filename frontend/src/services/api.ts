import axios from 'axios';
import type {
  AuthResponse,
  LoginRequest,
  RegisterRequest,
  Client,
  Compte,
  Transaction,
  CompteCreateRequest,
  TransactionRequest,
  VirementRequest
} from '../types';

const api = axios.create({
  baseURL: '/api',
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = localStorage.getItem('refreshToken');
        const { data } = await axios.post<{ accessToken: string }>('/api/refresh', {
          refreshToken
        });

        localStorage.setItem('accessToken', data.accessToken);
        originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;

        return api(originalRequest);
      } catch {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        window.location.href = '/login';
      }
    }

    return Promise.reject(error);
  }
);

export const authAPI = {
  login: (data: LoginRequest) =>
    axios.post<AuthResponse>('/api/login', data),

  register: (data: RegisterRequest) =>
    axios.post<Client>('/api/register', data),
};

export const clientAPI = {
  getMe: () =>
    api.get<Client>('/auth/clients/me'),

  updateMe: (data: { adresse?: string; tel?: string; password: string }) =>
    api.put<Client>('/auth/clients/me', data),
};

export const compteAPI = {
  getAll: () =>
    api.get<Compte[]>('/auth/comptes'),

  getAllComptes: () =>
    api.get<Compte[]>('/auth/comptes/all'),

  create: (data: CompteCreateRequest) =>
    api.post<Compte>('/auth/comptes', data),

  delete: (data: { numCompte: string; password: string }) =>
    api.post<string>('/auth/comptes/delete', data),

  count: () =>
    api.get<number>('/auth/comptes/count'),
};

export const transactionAPI = {
  versement: (data: TransactionRequest) =>
    api.post<Transaction>('/auth/transactions/versement', data),

  retrait: (data: TransactionRequest) =>
    api.post<Transaction>('/auth/transactions/retrait', data),

  virement: (data: VirementRequest) =>
    api.post<{ message: string }>('/auth/transactions/virement', data),

  getAll: () =>
    api.get<Transaction[]>('/auth/transactions/all'),

  getByDateRange: (dateDebut: string, dateFin: string) =>
    api.get<Transaction[]>('/auth/transactions', {
      params: { dateDebut, dateFin }
    }),
};

export default api;
