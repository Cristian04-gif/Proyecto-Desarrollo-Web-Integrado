// src/pages/RegisterPage.jsx
import RegisterForm from '../components/RegisterForm';
import { Link } from 'react-router-dom';

export default function RegisterPage() {
  return (
    <div className="register-container">
      <h1>Biocampo</h1>
      <RegisterForm />
      <div className="text-center mt-3">
        <Link to="/login">¿Ya tienes cuenta? Inicia sesión</Link>
      </div>
    </div>
  );
}
