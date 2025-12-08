// ==================== PROVEEDORES ====================
export async function getProveedores() {
  const res = await fetch(`${API_BASE}/api/supplier/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener proveedores');
  return await res.json();
}

export async function getProveedorById(id) {
  const res = await fetch(`${API_BASE}/api/supplier/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener proveedor');
  return await res.json();
}

export async function crearProveedor(proveedor) {
  const res = await fetch(`${API_BASE}/api/supplier/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(proveedor)
  });
  if (!res.ok) throw new Error('Error al crear proveedor');
  return await res.json();
}

export async function actualizarProveedor(id, proveedor) {
  const res = await fetch(`${API_BASE}/api/supplier/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(proveedor)
  });
  if (!res.ok) throw new Error('Error al actualizar proveedor');
  return await res.json();
}

export async function eliminarProveedor(id) {
  const res = await fetch(`${API_BASE}/api/supplier/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar proveedor');
  return await res.json();
}