// src/services/authService.js
const API_URL = 'http://localhost:8080/api/usuarios';

export async function login(email, contraseña) {
  const params = new URLSearchParams({ email, contraseña });
  const res = await fetch(`${API_URL}/login?${params}`, { method: 'POST' });
  if (!res.ok) throw new Error('Credenciales inválidas');
  return await res.json();
}

export async function register(formData) {
  const params = new URLSearchParams(formData);
  const res = await fetch(`${API_URL}/registrar?${params}`, { method: 'POST' });
  if (!res.ok) throw new Error('Error al registrar usuario');
  return await res.json();
}



