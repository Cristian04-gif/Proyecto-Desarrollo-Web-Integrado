// ==================== PRODUCTOS ====================
export async function getProductos() {
  const res = await fetch(`${API_BASE}/api/product/all`, { headers: getAuthHeaders() });
  return await handleResponse(res, 'Error al obtener productos');
}

export async function getProductoById(id) {
  const res = await fetch(`${API_BASE}/api/product/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener producto');
  return await res.json();
}

export async function crearProducto(producto) {
  const headers = getAuthHeaders();
  console.debug('crearProducto headers:', headers);
  const res = await fetch(`${API_BASE}/api/product/register`, {
    method: 'POST',
    headers,
    body: JSON.stringify(producto)
  });
  return await handleResponse(res, 'Error al crear producto');
}

export async function actualizarProducto(id, producto) {
  const res = await fetch(`${API_BASE}/api/product/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(producto)
  });
  if (!res.ok) throw new Error('Error al actualizar producto');
  return await res.json();
}

export async function eliminarProducto(id) {
  const res = await fetch(`${API_BASE}/api/product/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar producto');
  return await res.json();
}