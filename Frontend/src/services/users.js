const API_BASE = "https://servidor2-biocampo-latest.onrender.com";

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
};

export async function getUsuarios() {
  const res = await fetch(`${API_BASE}/api/users/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener usuarios');
  return await res.json();
}

export async function crearUsuario(payload) {
  const res = await fetch(`${API_BASE}/auth/register`, { 
    method: 'POST',
    headers: { 'Content-Type': 'application/json' }, 
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al crear usuario');
  return await res.json();
}

export async function actualizarUsuario(email, datos) {
  const res = await fetch(`${API_BASE}/api/users/update/${email}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(datos)
  });
  if (!res.ok) throw new Error('Error al actualizar usuario');
  return await res.json();
}

export async function eliminarUsuario(id) {
  const res = await fetch(`${API_BASE}/api/users/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar usuario');
  return true; // Retornamos true si salió bien
}