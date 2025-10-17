// src/components/Navbar.jsx
import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav className="navbar">
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
      />
      <Link to="/" className="biocampo-logo">
        <div className="navbar-logo">
          <h1 className="logo-title">BioCampo</h1>
        </div>
      </Link>
      <div className="nav-links">
        <a href="#inicio">Inicio</a>
        <a href="#nosotros">Sobre Nosotros</a>
        <a href="#servicios">Servicios</a>
        <a href="#contacto">Contáctanos</a>
        <div className="auth-buttons">
          <Link to="/login" className="btn-login">Iniciar sesión</Link>
          <Link to="/register" className="btn-register">Registrarse</Link>
        </div>
      </div>
    </nav>
  );
}
