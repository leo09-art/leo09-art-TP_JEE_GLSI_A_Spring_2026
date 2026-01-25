import { useState, useEffect } from 'react';
import { compteAPI, transactionAPI } from '../services/api';
import { Navbar } from '../components/Navbar';
import type { Compte, Transaction } from '../types';
import '../styles/Transactions.css';

type OperationType = 'versement' | 'retrait' | 'virement';

export const Transactions = () => {
  const [comptes, setComptes] = useState<Compte[]>([]);
  const [allComptes, setAllComptes] = useState<Compte[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [operationType, setOperationType] = useState<OperationType>('versement');
  const [formData, setFormData] = useState({
    numCompte: '',
    numCompteDest: '',
    montant: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const loadData = async () => {
    try {
      const [comptesRes, allComptesRes, transactionsRes] = await Promise.all([
        compteAPI.getAll(),
        compteAPI.getAllComptes(),
        transactionAPI.getAll(),
      ]);

      setComptes(comptesRes.data);
      setAllComptes(allComptesRes.data);
      setTransactions(transactionsRes.data);
    } catch (error) {
      console.error('Erreur chargement données:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      if (operationType === 'virement') {
        await transactionAPI.virement({
          numCompteSource: formData.numCompte,
          numCompteDest: formData.numCompteDest,
          montant: parseFloat(formData.montant),
          password: formData.password,
        });
      } else {
        const request = {
          numCompte: formData.numCompte,
          montant: parseFloat(formData.montant),
          password: formData.password,
        };

        if (operationType === 'versement') {
          await transactionAPI.versement(request);
        } else {
          await transactionAPI.retrait(request);
        }
      }

      setSuccess('Opération effectuée avec succès');
      setShowModal(false);
      setFormData({ numCompte: '', numCompteDest: '', montant: '', password: '' });
      await loadData();

      setTimeout(() => setSuccess(''), 3000);
    } catch (err: any) {
      setError(err.response?.data || 'Erreur lors de l\'opération');
    }
  };

  const formatDate = (date: string) => {
    return new Date(date).toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const getTransactionClass = (type: string) => {
    if (type === 'DEPOT' || type === 'VIREMENT_CREDIT') return 'transaction-credit';
    return 'transaction-debit';
  };

  const getTransactionLabel = (type: string) => {
    const labels: Record<string, string> = {
      DEPOT: 'Dépôt',
      RETRAIT: 'Retrait',
      VIREMENT_DEBIT: 'Virement envoyé',
      VIREMENT_CREDIT: 'Virement reçu',
    };
    return labels[type] || type;
  };

  if (loading) {
    return (
      <>
        <Navbar />
        <div className="transactions-container">
          <p>Chargement...</p>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />
      <div className="transactions-container">
        <div className="transactions-header">
          <h1>Transactions</h1>
          <div className="transaction-buttons">
            <button
              onClick={() => {
                setOperationType('versement');
                setShowModal(true);
              }}
              className="btn-success"
            >
              Versement
            </button>
            <button
              onClick={() => {
                setOperationType('retrait');
                setShowModal(true);
              }}
              className="btn-warning"
            >
              Retrait
            </button>
            <button
              onClick={() => {
                setOperationType('virement');
                setShowModal(true);
              }}
              className="btn-primary"
            >
              Virement
            </button>
          </div>
        </div>

        {success && <div className="success-message">{success}</div>}

        <div className="transactions-list-large">
          {transactions.length === 0 ? (
            <p className="empty-message">Aucune transaction</p>
          ) : (
            <table className="transactions-table">
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Type</th>
                  <th>Compte</th>
                  <th>Montant</th>
                </tr>
              </thead>
              <tbody>
                {transactions.map((transaction) => (
                  <tr key={transaction.id}>
                    <td>{formatDate(transaction.date)}</td>
                    <td>{getTransactionLabel(transaction.type)}</td>
                    <td>{transaction.compteNum}</td>
                    <td className={getTransactionClass(transaction.type)}>
                      {transaction.type === 'DEPOT' || transaction.type === 'VIREMENT_CREDIT' ? '+' : '-'}
                      {transaction.montant.toFixed(2)} €
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>

        {showModal && (
          <div className="modal-overlay" onClick={() => setShowModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h2>
                {operationType === 'versement' && 'Effectuer un versement'}
                {operationType === 'retrait' && 'Effectuer un retrait'}
                {operationType === 'virement' && 'Effectuer un virement'}
              </h2>

              {error && <div className="error-message">{error}</div>}

              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label>Compte source</label>
                  <select
                    value={formData.numCompte}
                    onChange={(e) => setFormData({ ...formData, numCompte: e.target.value })}
                    required
                  >
                    <option value="">Sélectionner un compte</option>
                    {comptes.map((compte) => (
                      <option key={compte.id} value={compte.num}>
                        {compte.type} - {compte.num} ({compte.solde.toFixed(2)} €)
                      </option>
                    ))}
                  </select>
                </div>

                {operationType === 'virement' && (
                  <div className="form-group">
                    <label>Compte destination</label>
                    <select
                      value={formData.numCompteDest}
                      onChange={(e) => setFormData({ ...formData, numCompteDest: e.target.value })}
                      required
                    >
                      <option value="">Sélectionner un compte</option>
                      {allComptes
                        .filter((c) => c.num !== formData.numCompte)
                        .map((compte) => (
                          <option key={compte.id} value={compte.num}>
                            {compte.num}
                          </option>
                        ))}
                    </select>
                  </div>
                )}

                <div className="form-group">
                  <label>Montant</label>
                  <input
                    type="number"
                    step="0.01"
                    min="0.01"
                    value={formData.montant}
                    onChange={(e) => setFormData({ ...formData, montant: e.target.value })}
                    required
                    placeholder="0.00"
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

                <div className="modal-actions">
                  <button type="button" onClick={() => setShowModal(false)} className="btn-secondary">
                    Annuler
                  </button>
                  <button type="submit" className="btn-primary">
                    Confirmer
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
