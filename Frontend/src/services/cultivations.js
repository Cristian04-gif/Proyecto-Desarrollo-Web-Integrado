// src/services/cultivations.js

// Ajusta esta URL si tu backend corre en otro puerto
const API_BASE = "http://localhost:8080"; 

// Helper para el token
const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getCultivos() {
  const res = await fetch(`${API_BASE}/api/cultivation/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cultivos');
  return await res.json();
}

export async function getCultivoById(id) {
  const res = await fetch(`${API_BASE}/api/cultivation/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cultivo');
  return await res.json();
}

// ✅ ESTA ES LA FUNCIÓN QUE TE DABA ERROR. Asegúrate que diga 'export'
export async function crearCultivo(cultivo) {
  const res = await fetch(`${API_BASE}/api/cultivation/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cultivo)
  });
  if (!res.ok) throw new Error('Error al crear cultivo');
  return await res.json();
}

export async function actualizarCultivo(id, cultivo) {
  const res = await fetch(`${API_BASE}/api/cultivation/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cultivo)
  });
  if (!res.ok) throw new Error('Error al actualizar cultivo');
  return await res.json();
}

export async function eliminarCultivo(id) {
  const res = await fetch(`${API_BASE}/api/cultivation/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  // Nota: Si tu backend devuelve 204 No Content, res.json() fallará.
  // Es mejor verificar res.ok y retornar true.
  if (!res.ok) throw new Error('Error al eliminar cultivo');
  
  // Si tu backend devuelve void o no content, usa esto:
  if (res.status === 204) return true;
  
  return await res.json();
}