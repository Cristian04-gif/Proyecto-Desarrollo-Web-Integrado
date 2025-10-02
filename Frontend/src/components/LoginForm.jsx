// src/components/LoginForm.jsx
import { useState } from 'react';
import { login } from '../services/authService';
import '../styles/loginStyles.css';

export default function LoginForm() {
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
    } catch (err) {
      setError(err.message);
      setUsuario(null);
    }
  };

  return (
    <form className="login-card" onSubmit={handleSubmit}>
      <h2 className="login-title">Iniciar sesión</h2>
      <label className="login-label">Correo electrónico</label>
      <input type="email" className="login-input" value={email} onChange={e => setEmail(e.target.value)} required />
      <label className="login-label">Contraseña</label>
      <input type="password" className="login-input" value={contraseña} onChange={e => setContraseña(e.target.value)} required />
      <button type="submit" className="login-button btn btn-primary mt-3">Entrar</button>
      {error && <div className="login-alert alert alert-danger">{error}</div>}
      {usuario && <div className="login-alert alert alert-success">Bienvenido, {usuario.nombre}</div>}
    </form>
  );
}

