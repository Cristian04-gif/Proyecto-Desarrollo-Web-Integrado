// src/services/postHarvests.js
const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getPostHarvests() {
  const res = await fetch(`${API_BASE}/api/postHarvest/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener post-cosechas');
  return await res.json();
}

export async function createPostHarvest(payload) {
  // Tu controlador espera PostHarvestRequest { postHarvest: {...}, employees: [...] }
  const res = await fetch(`${API_BASE}/api/postHarvest/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar post-cosecha');
  return await res.json();
}

export async function deletePostHarvest(id) {
  const res = await fetch(`${API_BASE}/api/postHarvest/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar post-cosecha');
  return true;
}