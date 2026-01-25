import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { compteAPI, transactionAPI } from '../services/api';
import { Navbar } from '../components/Navbar';
import type { Compte, Transaction } from '../types';
import '../styles/Dashboard.css';

export const Dashboard = () => {
  const { client } = useAuth();
  const [comptes, setComptes] = useState<Compte[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadData = async () => {
      try {
        const [comptesRes, transactionsRes] = await Promise.all([
          compteAPI.getAll(),
          transactionAPI.getAll(),
        ]);

        setComptes(comptesRes.data);
        setTransactions(transactionsRes.data.slice(0, 5));
      } catch (error) {
        console.error('Erreur chargement données:', error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const totalSolde = comptes.reduce((sum, compte) => sum + compte.solde, 0);

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
        <div className="dashboard-container">
          <p>Chargement...</p>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />
      <div className="dashboard-container">
        <div className="dashboard-header">
          <h1>Bonjour, {client?.prenom} !</h1>
          <p>Bienvenue sur votre espace bancaire</p>
        </div>

        <div className="dashboard-stats">
          <div className="stat-card">
            <h3>Solde total</h3>
            <p className="stat-value">{totalSolde.toFixed(2)} €</p>
          </div>

          <div className="stat-card">
            <h3>Nombre de comptes</h3>
            <p className="stat-value">{comptes.length}</p>
          </div>

          <div className="stat-card">
            <h3>Transactions</h3>
            <p className="stat-value">{transactions.length}</p>
          </div>
        </div>

        <div className="dashboard-grid">
          <div className="dashboard-section">
            <div className="section-header">
              <h2>Mes comptes</h2>
              <Link to="/comptes" className="section-link">Voir tout</Link>
            </div>

            <div className="comptes-list">
              {comptes.length === 0 ? (
                <p className="empty-message">Aucun compte</p>
              ) : (
                comptes.map((compte) => (
                  <div key={compte.id} className="compte-card">
                    <div className="compte-info">
                      <h3>{compte.type}</h3>
                      <p className="compte-num">{compte.num}</p>
                    </div>
                    <div className="compte-solde">
                      <p className="solde-label">Solde</p>
                      <p className="solde-value">{compte.solde.toFixed(2)} €</p>
                    </div>
                  </div>
                ))
              )}
            </div>
          </div>

          <div className="dashboard-section">
            <div className="section-header">
              <h2>Dernières transactions</h2>
              <Link to="/transactions" className="section-link">Voir tout</Link>
            </div>

            <div className="transactions-list">
              {transactions.length === 0 ? (
                <p className="empty-message">Aucune transaction</p>
              ) : (
                transactions.map((transaction) => (
                  <div key={transaction.id} className="transaction-item">
                    <div className="transaction-info">
                      <p className="transaction-type">
                        {getTransactionLabel(transaction.type)}
                      </p>
                      <p className="transaction-date">
                        {formatDate(transaction.date)}
                      </p>
                    </div>
                    <p className={`transaction-montant ${getTransactionClass(transaction.type)}`}>
                      {transaction.type === 'DEPOT' || transaction.type === 'VIREMENT_CREDIT' ? '+' : '-'}
                      {transaction.montant.toFixed(2)} €
                    </p>
                  </div>
                ))
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
