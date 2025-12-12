// ==================== PÉRDIDAS ====================
export async function getPerdidas() {
  const res = await fetch(`${API_BASE}/api/loss/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener pérdidas');
  return await res.json();
}

export async function getPerdidaById(id) {
  const res = await fetch(`${API_BASE}/api/loss/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener pérdida');
  return await res.json();
}

export async function crearPerdida(perdida) {
  const res = await fetch(`${API_BASE}/api/loss/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(perdida)
  });
  if (!res.ok) throw new Error('Error al crear pérdida');
  return await res.json();
}

export async function actualizarPerdida(id, perdida) {
  const res = await fetch(`${API_BASE}/api/loss/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(perdida)
  });
  if (!res.ok) throw new Error('Error al actualizar pérdida');
  return await res.json();
}

export async function eliminarPerdida(id) {
  const res = await fetch(`${API_BASE}/api/loss/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar pérdida');
  return await res.json();
}
