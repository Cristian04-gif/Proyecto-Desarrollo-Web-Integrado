// ==================== VENTAS ====================
export async function getVentas() {
  const res = await fetch(`${API_BASE}/api/sale/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener ventas');
  return await res.json();
}

export async function getVentaById(id) {
  const res = await fetch(`${API_BASE}/api/sale/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener venta');
  return await res.json();
}

export async function crearVenta(venta) {
  const res = await fetch(`${API_BASE}/api/sale/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(venta)
  });
  if (!res.ok) throw new Error('Error al crear venta');
  return await res.json();
}

export async function actualizarVenta(id, venta) {
  const res = await fetch(`${API_BASE}/api/sale/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(venta)
  });
  if (!res.ok) throw new Error('Error al actualizar venta');
  return await res.json();
}

export async function eliminarVenta(id) {
  const res = await fetch(`${API_BASE}/api/sale/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar venta');
  return await res.json();
}