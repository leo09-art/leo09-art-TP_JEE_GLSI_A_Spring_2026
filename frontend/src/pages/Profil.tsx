import { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { clientAPI } from '../services/api';
import { Navbar } from '../components/Navbar';
import '../styles/Profil.css';

export const Profil = () => {
  const { client, refreshClient } = useAuth();
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({
    adresse: client?.adresse || '',
    tel: client?.tel || '',
    password: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      await clientAPI.updateMe(formData);
      setSuccess('Profil mis à jour avec succès');
      setEditing(false);
      setFormData({ ...formData, password: '' });
      await refreshClient();

      setTimeout(() => setSuccess(''), 3000);
    } catch (err: any) {
      setError(err.response?.data || 'Erreur lors de la mise à jour');
    }
  };

  const formatDate = (date: string) => {
    return new Date(date).toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  };

  return (
    <>
      <Navbar />
      <div className="profil-container">
        <div className="profil-header">
          <h1>Mon profil</h1>
          {!editing && (
            <button onClick={() => setEditing(true)} className="btn-primary">
              Modifier
            </button>
          )}
        </div>

        {success && <div className="success-message">{success}</div>}
        {error && <div className="error-message">{error}</div>}

        {editing ? (
          <form onSubmit={handleSubmit} className="profil-form">
            <div className="form-group">
              <label>Adresse</label>
              <input
                type="text"
                value={formData.adresse}
                onChange={(e) => setFormData({ ...formData, adresse: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label>Téléphone</label>
              <input
                type="tel"
                value={formData.tel}
                onChange={(e) => setFormData({ ...formData, tel: e.target.value })}
              />
            </div>

            <div className="form-group">
              <label>Mot de passe</label>
              <input
                type="password"
                value={formData.password}
                onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                required
                placeholder="Confirmez avec votre mot de passe"
              />
            </div>

            <div className="form-actions">
              <button
                type="button"
                onClick={() => {
                  setEditing(false);
                  setFormData({
                    adresse: client?.adresse || '',
                    tel: client?.tel || '',
                    password: '',
                  });
                }}
                className="btn-secondary"
              >
                Annuler
              </button>
              <button type="submit" className="btn-primary">
                Enregistrer
              </button>
            </div>
          </form>
        ) : (
          <div className="profil-info">
            <div className="info-section">
              <h2>Informations personnelles</h2>
              <div className="info-grid">
                <div className="info-item">
                  <span className="info-label">Nom</span>
                  <span className="info-value">{client?.nom}</span>
                </div>

                <div className="info-item">
                  <span className="info-label">Prénom</span>
                  <span className="info-value">{client?.prenom}</span>
                </div>

                <div className="info-item">
                  <span className="info-label">Date de naissance</span>
                  <span className="info-value">{client?.dateNais && formatDate(client.dateNais)}</span>
                </div>

                <div className="info-item">
                  <span className="info-label">Sexe</span>
                  <span className="info-value">{client?.sexe === 'M' ? 'Homme' : 'Femme'}</span>
                </div>

                <div className="info-item">
                  <span className="info-label">Nationalité</span>
                  <span className="info-value">{client?.nationalite}</span>
                </div>
              </div>
            </div>

            <div className="info-section">
              <h2>Coordonnées</h2>
              <div className="info-grid">
                <div className="info-item">
                  <span className="info-label">Email</span>
                  <span className="info-value">{client?.courriel}</span>
                </div>

                <div className="info-item">
                  <span className="info-label">Téléphone</span>
                  <span className="info-value">{client?.tel}</span>
                </div>

                <div className="info-item full-width">
                  <span className="info-label">Adresse</span>
                  <span className="info-value">{client?.adresse}</span>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
};
