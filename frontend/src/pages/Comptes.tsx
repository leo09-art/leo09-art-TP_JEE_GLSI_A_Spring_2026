import { useState, useEffect } from 'react';
import { compteAPI } from '../services/api';
import { Navbar } from '../components/Navbar';
import type { Compte } from '../types';
import '../styles/Comptes.css';

export const Comptes = () => {
  const [comptes, setComptes] = useState<Compte[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedCompte, setSelectedCompte] = useState<string>('');
  const [accountForm, setAccountForm] = useState<'COURANT' | 'EPARGNE'>('COURANT');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const loadComptes = async () => {
    try {
      const { data } = await compteAPI.getAll();
      setComptes(data);
    } catch (error) {
      console.error('Erreur chargement comptes:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadComptes();
  }, []);

  const handleCreateCompte = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      await compteAPI.create({ accountForm, password });
      setShowModal(false);
      setPassword('');
      setAccountForm('COURANT');
      await loadComptes();
    } catch (err: any) {
      setError(err.response?.data || 'Erreur lors de la création du compte');
    }
  };

  const handleDeleteCompte = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      await compteAPI.delete({ numCompte: selectedCompte, password });
      setShowDeleteModal(false);
      setPassword('');
      setSelectedCompte('');
      await loadComptes();
    } catch (err: any) {
      setError(err.response?.data || 'Erreur lors de la suppression du compte');
    }
  };

  const formatDate = (date: string) => {
    return new Date(date).toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  };

  if (loading) {
    return (
      <>
        <Navbar />
        <div className="comptes-container">
          <p>Chargement...</p>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />
      <div className="comptes-container">
        <div className="comptes-header">
          <h1>Mes comptes</h1>
          <button onClick={() => setShowModal(true)} className="btn-primary">
            Créer un compte
          </button>
        </div>

        <div className="comptes-grid">
          {comptes.length === 0 ? (
            <p className="empty-message">Aucun compte. Créez votre premier compte !</p>
          ) : (
            comptes.map((compte) => (
              <div key={compte.id} className="compte-card-large">
                <div className="compte-card-header">
                  <h2>{compte.type}</h2>
                  <span className={`compte-badge ${compte.actif ? 'actif' : 'inactif'}`}>
                    {compte.actif ? 'Actif' : 'Inactif'}
                  </span>
                </div>

                <div className="compte-details">
                  <div className="detail-row">
                    <span className="detail-label">IBAN</span>
                    <span className="detail-value">{compte.num}</span>
                  </div>

                  <div className="detail-row">
                    <span className="detail-label">Date de création</span>
                    <span className="detail-value">{formatDate(compte.dateCreation)}</span>
                  </div>

                  <div className="detail-row solde-row">
                    <span className="detail-label">Solde</span>
                    <span className="solde-large">{compte.solde.toFixed(2)} €</span>
                  </div>
                </div>

                <button
                  onClick={() => {
                    setSelectedCompte(compte.num);
                    setShowDeleteModal(true);
                  }}
                  className="btn-danger"
                  disabled={compte.solde !== 0}
                >
                  {compte.solde !== 0 ? 'Solde non nul' : 'Supprimer'}
                </button>
              </div>
            ))
          )}
        </div>

        {showModal && (
          <div className="modal-overlay" onClick={() => setShowModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h2>Créer un nouveau compte</h2>

              {error && <div className="error-message">{error}</div>}

              <form onSubmit={handleCreateCompte}>
                <div className="form-group">
                  <label>Type de compte</label>
                  <select
                    value={accountForm}
                    onChange={(e) => setAccountForm(e.target.value as 'COURANT' | 'EPARGNE')}
                  >
                    <option value="COURANT">Compte courant</option>
                    <option value="EPARGNE">Compte épargne</option>
                  </select>
                </div>

                <div className="form-group">
                  <label>Mot de passe</label>
                  <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    placeholder="Confirmez avec votre mot de passe"
                  />
                </div>

                <div className="modal-actions">
                  <button type="button" onClick={() => setShowModal(false)} className="btn-secondary">
                    Annuler
                  </button>
                  <button type="submit" className="btn-primary">
                    Créer
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}

        {showDeleteModal && (
          <div className="modal-overlay" onClick={() => setShowDeleteModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h2>Supprimer le compte</h2>
              <p>Êtes-vous sûr de vouloir supprimer ce compte ?</p>

              {error && <div className="error-message">{error}</div>}

              <form onSubmit={handleDeleteCompte}>
                <div className="form-group">
                  <label>Mot de passe</label>
                  <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    placeholder="Confirmez avec votre mot de passe"
                  />
                </div>

                <div className="modal-actions">
                  <button type="button" onClick={() => setShowDeleteModal(false)} className="btn-secondary">
                    Annuler
                  </button>
                  <button type="submit" className="btn-danger">
                    Supprimer
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </>
  );
};
