// src/pages/LoginPage.jsx
import LoginForm from '../components/LoginForm';
import { Link } from 'react-router-dom';

export default function LoginPage() {
  return (
    <div className="login-container">
      <h1>Biocampo</h1>
      <LoginForm />
      <div className="text-center mt-3">
        <Link to="/register">¿No tienes cuenta? Regístrate aquí</Link>
      </div>
    </div>
  );
}

