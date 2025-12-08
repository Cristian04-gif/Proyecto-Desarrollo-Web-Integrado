// ==================== COSECHAS ====================
export async function getCosechas() {
  const res = await fetch(`${API_BASE}/api/harvest/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cosechas');
  return await res.json();
}

export async function getCosechaById(id) {
  const res = await fetch(`${API_BASE}/api/harvest/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cosecha');
  return await res.json();
}

export async function crearCosecha(cosecha) {
  const res = await fetch(`${API_BASE}/api/harvest/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cosecha)
  });
  if (!res.ok) throw new Error('Error al crear cosecha');
  return await res.json();
}

export async function actualizarCosecha(id, cosecha) {
  const res = await fetch(`${API_BASE}/api/harvest/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cosecha)
  });
  if (!res.ok) throw new Error('Error al actualizar cosecha');
  return await res.json();
}

export async function eliminarCosecha(id) {
  const res = await fetch(`${API_BASE}/api/harvest/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cosecha');
  return await res.json();
}
