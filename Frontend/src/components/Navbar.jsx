// src/components/Navbar.jsx
import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
<<<<<<< HEAD
import { userService } from '../services/authService'; // usamos el objeto userService
=======
import { getUsuarios } from '../services/authService';
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b

export default function Navbar() {
  const [userName, setUserName] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // primero, si ya hay un nombre en localStorage, úsalo inmediatamente
    const storedName = localStorage.getItem('userName');
    if (storedName) {
      setUserName(storedName);
      return;
    }

    const token = localStorage.getItem('token');
    if (!token) return;

<<<<<<< HEAD
    // parse token para obtener email y luego buscar nombre en la API
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
          })
          .join('')
      );
      const payload = JSON.parse(jsonPayload);
      const email = payload?.sub || payload?.username || payload?.email;
      if (email) {
        userService.list()
          .then(list => {
            const u = list.find(
              x => x.email && x.email.toLowerCase() === email.toLowerCase()
            );
            if (u) {
              const full =
                (u.firstName || u.nombre || '') +
                ' ' +
                (u.lastName || u.apellido || '');
              setUserName(full.trim() || email);
              localStorage.setItem('userName', full.trim() || email);
            }
          })
          .catch(() => {
            // si falla, dejamos el email como fallback
            setUserName(email);
          });
=======
    // parse token to get email y obtener nombre desde API si es necesario
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));
      const payload = JSON.parse(jsonPayload);
      const email = payload?.sub || payload?.username || payload?.email;
      if (email) {
        getUsuarios().then(list => {
          const u = list.find(x => x.email && x.email.toLowerCase() === email.toLowerCase());
          if (u) {
            const full = (u.firstName || u.nombre) + ' ' + (u.lastName || u.apellido);
            setUserName(full);
            localStorage.setItem('userName', full);
          }
        }).catch(() => {
          // ignore
        });
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
      }
    } catch (e) {
      // ignore
    }
  }, []);

  // escuchar cambios de autenticación dentro de la misma pestaña
  useEffect(() => {
    const handler = () => {
      const name = localStorage.getItem('userName');
      setUserName(name);
    };
    window.addEventListener('authChanged', handler);
<<<<<<< HEAD
=======
    // también escucha cambios en storage (otra pestaña o evento storage)
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
    window.addEventListener('storage', handler);
    return () => {
      window.removeEventListener('authChanged', handler);
      window.removeEventListener('storage', handler);
    };
  }, []);

<<<<<<< HEAD
  // revisa si hay userName al montar
=======
  // revisa si hay userName al montar, en caso de que ya esté en localStorage
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
  useEffect(() => {
    const name = localStorage.getItem('userName');
    if (name) {
      setUserName(name);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userName');
    setUserName(null);
    navigate('/');
  };

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
        <Link to="/">Inicio</Link>
        <Link to="/nosotros">Nosotros</Link>
        <Link to="/servicios">Servicios</Link>
        <Link to="/cultivos">Cultivos</Link>
        <Link to="/contacto">Contáctanos</Link>
        <div className="auth-buttons">
          {userName ? (
            <>
              <span className="welcome">Bienvenido, {userName}</span>
<<<<<<< HEAD
              <button className="btn-logout" onClick={handleLogout}>
                Cerrar sesión
              </button>
=======
              <button className="btn-logout" onClick={handleLogout}>Cerrar sesión</button>
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
            </>
          ) : (
            <>
              <Link to="/login" className="btn-login">Iniciar sesión</Link>
              <Link to="/register" className="btn-register">Registrarse</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}
