import { useState } from 'react';
import { login } from '../services/authService';
import { Link } from 'react-router-dom';
import '../styles/loginStyles.css';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [contraseña, setContraseña] = useState('');
  const [error, setError] = useState('');
  const [usuario, setUsuario] = useState(null);

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const user = await login(email, contraseña);
      setUsuario(user);
      setError('');
      window.location.href = 'http://localhost:5173/'; 
    } catch (err) {
      setError(err.message);
      setUsuario(null);
    }
  };

  return (
    <div className="login-wrapper">
      <div className="login-box">
        <h2 className="login-title">Inicia sesión con tus credenciales</h2>
        <form onSubmit={handleSubmit}>
          <label className="login-label">Correo Electronico</label>
          <input
            type="email"
            className="login-input"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
          />
          <label className="login-label">Contraseña</label>
          <input
            type="password"
            className="login-input"
            value={contraseña}
            onChange={e => setContraseña(e.target.value)}
            required
          />
          <button type="submit" className="login-button">Iniciar sesión</button>
        </form>

        {error && <div className="login-alert alert-danger">{error}</div>}
        {usuario && <div className="login-alert alert-success">Bienvenido, {usuario.nombre}</div>}

        <div className="login-links">
          <Link to="/forgot-password">¿Olvidaste tu contraseña?</Link>
          <Link to="/register">Regístrate</Link>
        </div>

        <p className="support-text">
          Si necesitas ayuda, contáctanos en <strong>helpdesk@agricolus.com</strong>
        </p>
      </div>
    </div>
  );
}

