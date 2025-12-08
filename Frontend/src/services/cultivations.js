// ==================== CULTIVOS ====================
export async function getCultivos() {
  const res = await fetch(`${API_BASE}/api/cultivation/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cultivos');
  return await res.json();
}

export async function getCultivoById(id) {
  const res = await fetch(`${API_BASE}/api/cultivation/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cultivo');
  return await res.json();
}

export async function crearCultivo(cultivo) {
  const res = await fetch(`${API_BASE}/api/cultivation/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cultivo)
  });
  if (!res.ok) throw new Error('Error al crear cultivo');
  return await res.json();
}

export async function actualizarCultivo(id, cultivo) {
  const res = await fetch(`${API_BASE}/api/cultivation/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cultivo)
  });
  if (!res.ok) throw new Error('Error al actualizar cultivo');
  return await res.json();
}

export async function eliminarCultivo(id) {
  const res = await fetch(`${API_BASE}/api/cultivation/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cultivo');
  return await res.json();
}