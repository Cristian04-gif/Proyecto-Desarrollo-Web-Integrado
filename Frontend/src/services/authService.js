// src/services/authService.js
const API_URL = 'http://localhost:8080/auth';

export async function login(correo, contraseña) {
  //const params = new URLSearchParams({ email, contraseña });
  const res = await fetch(`${API_URL}/login`, {
    method: 'POST', headers: {
      'Content-Type': 'application/json'
    }, body: JSON.stringify({ email: correo, password: contraseña })
  });
  if (!res.ok) throw new Error('Credenciales inválidas');
  return await res.json().then(data => {
    if (data.token) {
      localStorage.setItem('token', data.token);
      window.location.href = 'http://localhost:5173/';
    }
  });
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



