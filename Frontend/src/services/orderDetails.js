// ==================== ORDER DETAILS ====================
export async function getOrderDetails() {
  const res = await fetch(`${API_BASE}/api/orderDetail/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener detalles de orden');
  return await res.json();
}

export async function getOrderDetailsByOrder(orderId) {
  const res = await fetch(`${API_BASE}/api/orderDetail/order/${orderId}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener detalles por orden');
  return await res.json();
}

export async function getOrderDetailById(id) {
  const res = await fetch(`${API_BASE}/api/orderDetail/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener detalle');
  return await res.json();
}

export async function updateOrderDetail(id, body) {
  const res = await fetch(`${API_BASE}/api/orderDetail/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(body)
  });
  if (!res.ok) throw new Error('Error al actualizar detalle');
  return await res.json();
}

export async function deleteOrderDetail(id) {
  const res = await fetch(`${API_BASE}/api/orderDetail/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar detalle');
  return await res.json();
}
