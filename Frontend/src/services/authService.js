// src/services/authService.js
const API_URL = 'https://servidor2-biocampo-latest.onrender.com/auth';
const API_BASE = 'https://servidor2-biocampo-latest.onrender.com';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
};


// ==================== AUTH ====================
export async function login(correo, contraseña) {
  const res = await fetch(`${API_URL}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email: correo, password: contraseña })
  });

  if (!res.ok) throw new Error('Credenciales inválidas');

  const data = await res.json();
  console.log("Respuesta del backend:", data);

  if (data.token && data.email) {
    localStorage.setItem('token', data.token);
    localStorage.setItem("user", JSON.stringify({
      email: data.email
    }));
  }

  return data; 
}


export async function register(formData) {
  const res = await fetch(`${API_URL}/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  });
  if (!res.ok) throw new Error('Error al registrar usuario');
  return await res.json().then(window.location.href = 'http://localhost:5173/login');
}

// ==================== CREAR USUARIO (ALIAS PARA REGISTRO) ====================
export async function crearUsuario(usuario) {
  const res = await fetch(`${API_URL}/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(usuario)
  });
  if (!res.ok) throw new Error('Error al crear usuario');
  return await res.json();
}

// ==================== HELPERS ====================

// Recuperar token actual
export function getToken() {
  return localStorage.getItem('token');
}

// Saber si el usuario está logeado
export function isLoggedIn() {
  return !!getToken();
}

// Cerrar sesión
export function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
}

export function getEmail() {
  const user = JSON.parse(localStorage.getItem('user'));
  return user?.email || null;
}