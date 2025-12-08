export const API_BASE = "http://localhost:8080";

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

export async function getUsuarioById(id) {
  const res = await fetch(`${API_BASE}/api/users/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener usuario');
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
  return await res.json();
}