import { useState } from 'react';
import { register } from '../services/authService';
import { Link } from 'react-router-dom';
import '../styles/registerStyles.css';

export default function RegisterPage() {
  const [form, setForm] = useState({
    nombre: '', apellido: '', pais: '',
    email: '', contraseña: '', repetir: ''
  });
  const [error, setError] = useState('');
  const [usuario, setUsuario] = useState(null);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();

    // Validaciones
    if (!form.nombre || !form.apellido || !form.pais || !form.email || !form.contraseña || !form.repetir) {
      setError('Todos los campos son obligatorios.');
      return;
    }

    if (!form.email.includes('@')) {
      setError('El correo electrónico no es válido.');
      return;
    }

    if (form.contraseña.length < 6) {
      setError('La contraseña debe tener al menos 6 caracteres.');
      return;
    }

    if (form.contraseña !== form.repetir) {
      setError('Las contraseñas no coinciden.');
      return;
    }

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
    <div className="register-wrapper">
      <div className="register-box">
        <h1 className="register-title">Registro de usuario</h1>

        <form className="register-card" onSubmit={handleSubmit}>
          <div className="register-field">
            <label className="register-label">Nombre*</label>
            <input name="nombre" type="text" value={form.nombre} onChange={handleChange} className="register-input" required />
          </div>

          <div className="register-field">
            <label className="register-label">Apellido*</label>
            <input name="apellido" type="text" value={form.apellido} onChange={handleChange} className="register-input" required />
          </div>

          <div className="register-field">
            <label className="register-label">Nación*</label>
            <select name="pais" value={form.pais} onChange={handleChange} className="register-input" required>
              <option value="">Selecciona tu país</option>
              <option value="Perú">Perú</option>
              <option value="México">México</option>
              <option value="Argentina">Argentina</option>
              <option value="Colombia">Colombia</option>
              <option value="Chile">Chile</option>
              <option value="España">España</option>
              <option value="Otro">Otro</option>
            </select>
          </div>

          <div className="register-field">
            <label className="register-label">Correo electrónico*</label>
            <input name="email" type="email" value={form.email} onChange={handleChange} className="register-input" required />
          </div>

          <div className="register-field">
            <label className="register-label">Contraseña*</label>
            <input name="contraseña" type="password" value={form.contraseña} onChange={handleChange} className="register-input" required />
          </div>

          <div className="register-field">
            <label className="register-label">Repite la contraseña*</label>
            <input name="repetir" type="password" value={form.repetir} onChange={handleChange} className="register-input" required />
          </div>

          <button type="submit" className="register-button">Confirmar</button>

          {error && <div className="register-alert alert-danger">{error}</div>}
          {usuario && <div className="register-alert alert-success">Usuario registrado: {usuario.nombre}</div>}
        </form>

        <div className="register-links">
          <Link to="/login">¿Ya tienes cuenta? Inicia sesión</Link>
        </div>

        <p className="support-text">
          Si necesitas ayuda, contáctanos en <strong>helpdesk@agricolus.com</strong>
        </p>
      </div>
    </div>
  );
}

