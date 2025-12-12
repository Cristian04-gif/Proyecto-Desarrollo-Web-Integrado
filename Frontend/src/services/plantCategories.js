// src/services/plantCategories.js

const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

// GET /api/plantCategory/all
export async function getCategories() {
  const res = await fetch(`${API_BASE}/api/plantCategory/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener categorías');
  return await res.json();
}

// POST /api/plantCategory/register
export async function createCategory(categoryData) {
  const res = await fetch(`${API_BASE}/api/plantCategory/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(categoryData)
  });
  if (!res.ok) throw new Error('Error al registrar categoría');
  return await res.json();
}

// DELETE /api/plantCategory/delete/{id}
export async function deleteCategory(id) {
  const res = await fetch(`${API_BASE}/api/plantCategory/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true; // No Content
  if (!res.ok) throw new Error('Error al eliminar categoría');
  return true;
}