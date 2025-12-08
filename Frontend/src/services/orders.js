// ==================== ORDERS ====================
export async function getOrders() {
  const res = await fetch(`${API_BASE}/api/order/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener órdenes');
  return await res.json();
}

export async function getOrderById(id) {
  const res = await fetch(`${API_BASE}/api/order/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener orden');
  return await res.json();
}

export async function crearOrder(requestBody) {
  // requestBody debe tener la forma { order, details }
  const res = await fetch(`${API_BASE}/api/order/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(requestBody)
  });
  if (!res.ok) throw new Error('Error al crear orden');
  return await res.json();
}

export async function eliminarOrder(id) {
  const res = await fetch(`${API_BASE}/api/order/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar orden');
  return await res.json();
}
