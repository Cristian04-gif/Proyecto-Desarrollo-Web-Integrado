// ==================== POST HARVESTS ====================
export async function getPostHarvests() {
  const res = await fetch(`${API_BASE}/api/postHarvest/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener post-harvests');
  return await res.json();
}

export async function getPostHarvestById(id) {
  const res = await fetch(`${API_BASE}/api/postHarvest/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener post-harvest');
  return await res.json();
}

export async function crearPostHarvest(requestBody) {
  // requestBody debe seguir la forma PostHarvestRequest { postHarvest, employees }
  const res = await fetch(`${API_BASE}/api/postHarvest/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(requestBody)
  });
  if (!res.ok) throw new Error('Error al crear post-harvest');
  return await res.json();
}

export async function actualizarPostHarvest(id, body) {
  const res = await fetch(`${API_BASE}/api/postHarvest/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(body)
  });
  if (!res.ok) throw new Error('Error al actualizar post-harvest');
  return await res.json();
}

export async function eliminarPostHarvest(id) {
  const res = await fetch(`${API_BASE}/api/postHarvest/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar post-harvest');
  return await res.json();
}
