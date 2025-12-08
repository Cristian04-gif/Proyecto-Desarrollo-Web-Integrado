// ==================== INSUMOS ====================
export async function getInsumos() {
  const res = await fetch(`${API_BASE}/api/input/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener insumos');
  return await res.json();
}

export async function getInsumoById(id) {
  const res = await fetch(`${API_BASE}/api/input/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener insumo');
  return await res.json();
}

export async function crearInsumo(insumo) {
  const res = await fetch(`${API_BASE}/api/input/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(insumo)
  });
  if (!res.ok) throw new Error('Error al crear insumo');
  return await res.json();
}

export async function actualizarInsumo(id, insumo) {
  const res = await fetch(`${API_BASE}/api/input/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(insumo)
  });
  if (!res.ok) throw new Error('Error al actualizar insumo');
  return await res.json();
}

export async function eliminarInsumo(id) {
  const res = await fetch(`${API_BASE}/api/input/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar insumo');
  return await res.json();
}
