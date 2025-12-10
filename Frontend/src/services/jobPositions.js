const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

// Obtener todos
export async function getJobPositions() {
  const res = await fetch(`${API_BASE}/api/jobPosition/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener cargos');
  return await res.json();
}

// Crear nuevo
export async function createJobPosition(payload) {
  const res = await fetch(`${API_BASE}/api/jobPosition/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al crear cargo');
  return await res.json();
}

// Eliminar
export async function deleteJobPosition(id) {
  const res = await fetch(`${API_BASE}/api/jobPosition/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar cargo');
  return true;
}