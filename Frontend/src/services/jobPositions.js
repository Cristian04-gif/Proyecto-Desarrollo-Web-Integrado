// ==================== JOB POSITIONS ====================
export async function getJobPositions() {
  const res = await fetch(`${API_BASE}/api/jobPosition/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener puestos');
  return await res.json();
}

export async function getJobPositionById(id) {
  const res = await fetch(`${API_BASE}/api/jobPosition/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener puesto');
  return await res.json();
}

export async function crearJobPosition(jobPosition) {
  const res = await fetch(`${API_BASE}/api/jobPosition/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(jobPosition)
  });
  if (!res.ok) throw new Error('Error al crear puesto');
  return await res.json();
}

export async function actualizarJobPosition(id, jobPosition) {
  const res = await fetch(`${API_BASE}/api/jobPosition/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(jobPosition)
  });
  if (!res.ok) throw new Error('Error al actualizar puesto');
  return await res.json();
}

export async function eliminarJobPosition(id) {
  const res = await fetch(`${API_BASE}/api/jobPosition/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar puesto');
  return await res.json();
}
