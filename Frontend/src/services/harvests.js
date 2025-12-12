// src/services/harvests.js
const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getHarvests() {
  const res = await fetch(`${API_BASE}/api/harvest/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener cosechas');
  return await res.json();
}

export async function createHarvest(payload) {
  // Tu controlador espera un HarvestRequest { harvest: {...}, employees: [...] }
  const res = await fetch(`${API_BASE}/api/harvest/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar cosecha');
  return await res.json();
}

export async function deleteHarvest(id) {
  const res = await fetch(`${API_BASE}/api/harvest/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar cosecha');
  return true;
}