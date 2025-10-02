// src/components/RegisterForm.jsx
import { useState } from 'react';
import { register } from '../services/authService';
import '../styles/registerStyles.css';

export default function RegisterForm() {
  const [form, setForm] = useState({
    nombre: '', apellido: '', email: '',
    contraseña: '', pais: ''
  });
  const [error, setError] = useState('');
  const [usuario, setUsuario] = useState(null);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const user = await register(form);
      setUsuario(user);
      setError('');
    } catch (err) {
      setError(err.message);
      setUsuario(null);
    }
  };

  return (
    <form className="register-card" onSubmit={handleSubmit}>
      <h2 className="register-title">Registro de usuario</h2>
      {Object.entries(form).map(([key, value]) => (
        <div key={key}>
          <label className="register-label">{key.charAt(0).toLowerCase() + key.slice(1)}</label>
          <input
            name={key}
            type={key === 'email' ? 'email' : key === 'contraseña' ? 'password' : 'text'}
            value={value}
            onChange={handleChange}
            className="register-input"
            required
          />
        </div>
      ))}
      <button type="submit" className="register-button btn btn-success mt-3">Registrarse</button>
      {error && <div className="register-alert alert alert-danger">{error}</div>}
      {usuario && <div className="register-alert alert alert-success">Usuario registrado: {usuario.nombre}</div>}
    </form>
  );
}

