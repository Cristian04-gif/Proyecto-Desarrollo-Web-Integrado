// ==================== INPUT CULTIVATIONS ====================
export async function getInputCultivations() {
  const res = await fetch(`${API_BASE}/api/inputCultivation/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener aplicaciones de insumos');
  return await res.json();
}

export async function getInputCultivationById(idCultivo, idInsumo) {
  const res = await fetch(`${API_BASE}/api/inputCultivation/id/${idCultivo}/${idInsumo}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener aplicación de insumo');
  return await res.json();
}

export async function getInputCultivationsByCultivation(id) {
  const res = await fetch(`${API_BASE}/api/inputCultivation/cultivation/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener aplicaciones por cultivo');
  return await res.json();
}

export async function updateInputCultivation(idCultivo, idInsumo, body) {
  const res = await fetch(`${API_BASE}/api/inputCultivation/update/${idCultivo}/${idInsumo}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(body)
  });
  if (!res.ok) throw new Error('Error al actualizar aplicación');
  return await res.json();
}

export async function deleteInputCultivation(idCultivo, idInsumo) {
  const res = await fetch(`${API_BASE}/api/inputCultivation/delete/${idCultivo}/${idInsumo}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar aplicación');
  return await res.json();
}
