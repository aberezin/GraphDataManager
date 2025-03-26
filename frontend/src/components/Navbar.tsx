import React from 'react';
import { Link, useLocation } from 'react-router-dom';

const Navbar: React.FC = () => {
  const location = useLocation();
  
  const isActive = (path: string): string => {
    return location.pathname === path ? 'active' : '';
  };
  
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-logo">
          Graph App
        </Link>
        <ul className="navbar-menu">
          <li className="navbar-item">
            <Link to="/" className={`navbar-link ${isActive('/')}`}>
              Home
            </Link>
          </li>
          <li className="navbar-item">
            <Link to="/graph-data" className={`navbar-link ${isActive('/graph-data')}`}>
              Graph Data
            </Link>
          </li>
          <li className="navbar-item">
            <Link to="/relational-data" className={`navbar-link ${isActive('/relational-data')}`}>
              Relational Data
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
