// src/services/plants.js

const API_BASE = "https://servidor2-biocampo-latest.onrender.com"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

// GET /api/plant/all
export async function getPlants() {
  const res = await fetch(`${API_BASE}/api/plant/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener plantas');
  return await res.json();
}

// POST /api/plant/register
export async function createPlant(plantData) {
  const res = await fetch(`${API_BASE}/api/plant/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(plantData)
  });
  if (!res.ok) throw new Error('Error al registrar planta');
  return await res.json();
}

// DELETE /api/plant/delete/{id}
export async function deletePlant(id) {
  const res = await fetch(`${API_BASE}/api/plant/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  // Si devuelve 204 No Content, retornamos true directamente
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar planta');
  return true;
}