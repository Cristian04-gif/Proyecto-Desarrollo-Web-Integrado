// ==================== PLANTS ====================
export async function getPlants() {
  const res = await fetch(`${API_BASE}/api/plant/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener plantas');
  return await res.json();
}

export async function getPlantById(id) {
  const res = await fetch(`${API_BASE}/api/plant/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener planta');
  return await res.json();
}

export async function crearPlant(plant) {
  const res = await fetch(`${API_BASE}/api/plant/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(plant)
  });
  if (!res.ok) throw new Error('Error al crear planta');
  return await res.json();
}

export async function actualizarPlant(id, plant) {
  const res = await fetch(`${API_BASE}/api/plant/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(plant)
  });
  if (!res.ok) throw new Error('Error al actualizar planta');
  return await res.json();
}

export async function eliminarPlant(id) {
  const res = await fetch(`${API_BASE}/api/plant/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar planta');
  return await res.json();
}
