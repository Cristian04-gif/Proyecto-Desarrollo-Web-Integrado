// src/components/admin/AdminLayout.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import './admin.css';

export default function AdminLayout({ children }) {
  const userName = localStorage.getItem('userName');

  return (
    <div className="admin-shell">
      {/* Navbar superior */}
      <header className="admin-navbar">
        <div className="navbar-left">
          <Link to="/" className="admin-logo">🌱 BioCampo</Link>
          <span className="admin-title">Panel de Administradores</span>
        </div>
        <div className="navbar-right">
          {userName && <span className="admin-user">👤 {userName}</span>}
        </div>
      </header>

      {/* Cuerpo del panel */}
      <div className="admin-body">
        {children}
      </div>
    </div>
  );
}
