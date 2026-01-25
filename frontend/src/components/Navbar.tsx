import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import '../styles/Navbar.css';

export const Navbar = () => {
  const { client, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-logo">
          EgaBank
        </Link>

        <ul className="navbar-menu">
          <li>
            <Link to="/" className="navbar-link">Tableau de bord</Link>
          </li>
          <li>
            <Link to="/comptes" className="navbar-link">Mes comptes</Link>
          </li>
          <li>
            <Link to="/transactions" className="navbar-link">Transactions</Link>
          </li>
          <li>
            <Link to="/profil" className="navbar-link">Profil</Link>
          </li>
        </ul>

        <div className="navbar-user">
          <span className="navbar-username">
            {client?.prenom} {client?.nom}
          </span>
          <button onClick={handleLogout} className="navbar-logout">
            Déconnexion
          </button>
        </div>
      </div>
    </nav>
  );
};
