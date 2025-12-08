// ==================== PLANT CATEGORIES ====================
export async function getPlantCategories() {
  const res = await fetch(`${API_BASE}/api/plantCategory/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener categorías de planta');
  return await res.json();
}

export async function getPlantCategoryById(id) {
  const res = await fetch(`${API_BASE}/api/plantCategory/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener categoría');
  return await res.json();
}

export async function crearPlantCategory(category) {
  const res = await fetch(`${API_BASE}/api/plantCategory/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(category)
  });
  if (!res.ok) throw new Error('Error al crear categoría');
  return await res.json();
}

export async function actualizarPlantCategory(id, category) {
  const res = await fetch(`${API_BASE}/api/plantCategory/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(category)
  });
  if (!res.ok) throw new Error('Error al actualizar categoría');
  return await res.json();
}

export async function eliminarPlantCategory(id) {
  const res = await fetch(`${API_BASE}/api/plantCategory/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar categoría');
  return await res.json();
}
