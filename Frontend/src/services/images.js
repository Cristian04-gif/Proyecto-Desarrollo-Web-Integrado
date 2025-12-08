// ==================== IMÁGENES ====================
export async function getImagenes() {
  const res = await fetch(`${API_BASE}/api/images/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener imágenes');
  return await res.json();
}

export async function subirImagen(imagen) {
  const res = await fetch(`${API_BASE}/api/images/upload`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(imagen)
  });
  if (!res.ok) throw new Error('Error al subir imagen');
  return await res.json();
}

export async function getImagenesPorEntidad(entityType, referenceId) {
  const res = await fetch(`${API_BASE}/api/images/${entityType}/${referenceId}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener imágenes por entidad');
  return await res.json();
}