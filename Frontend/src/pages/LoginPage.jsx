import { useState } from 'react';
import { login } from '../services/authService';
import { getUsuarios } from '../services/users';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import '../styles/loginStyles.css';

export default function LoginPage() {
  const [email, setEmail] = useState('');
  const [contraseña, setContraseña] = useState('');
  const [error, setError] = useState('');
  const [usuario, setUsuario] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const data = await login(email, contraseña);
      setError('');

      // ✅ Guardar token y clienteId
      const token = data?.token || localStorage.getItem('token');
      localStorage.setItem('token', token);
      localStorage.setItem('clienteId', data.clienteId);

      // ✅ Normalizar rol: si viene "CLIENTE", lo guardamos como "ROLE_CLIENTE"
      const normalizeRole = rol => {
        if (rol === "CLIENTE") return "ROLE_CLIENTE";
        return "ROLE_" + rol;
      };
      if (data.rol) {
        localStorage.setItem("rol", normalizeRole(data.rol));
      }

      // ✅ Decodificar token para obtener email
      let emailFromToken = email;
      try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
          atob(base64)
            .split('')
            .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
            .join('')
        );
        const payload = JSON.parse(jsonPayload);
        emailFromToken = payload?.sub || payload?.username || emailFromToken;
      } catch (err) {
        // ignore
      }

      // ✅ Buscar info completa del usuario
      let foundUserName = emailFromToken; // fallback: usa el email si no encuentra el usuario
      try {
        const list = await getUsuarios();
        const user = list.find(
          u => u.email && u.email.toLowerCase() === emailFromToken.toLowerCase()
        );
        if (user) {
          setUsuario(user);
          foundUserName =
            (user.firstName || user.nombre || '') +
            ' ' +
            (user.lastName || user.apellido || '');
        }
      } catch (err) {
        console.warn('Error obtaining user info:', err);
      }

      // ✅ Guardar nombre en localStorage
      localStorage.setItem('userName', foundUserName);

      // ✅ Notificar a la UI que la autenticación cambió
      try {
        window.dispatchEvent(new Event('authChanged'));
      } catch (e) {}

      // ✅ Redirección según dominio
      const lower = (emailFromToken || '').toLowerCase();
      if (lower.endsWith('@utp.edu.pe') || lower.includes('@biocampo')) {
        navigate('/admin');
      } else {
        navigate('/');
      }
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
          <button type="submit" className="login-button">
            Iniciar sesión
          </button>
        </form>

        {error && <div className="login-alert alert-danger">{error}</div>}
        {usuario && (
          <div className="login-alert alert-success">
            Bienvenido, {usuario.firstName || usuario.nombre}
          </div>
        )}

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
