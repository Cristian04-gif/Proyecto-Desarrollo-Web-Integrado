// ==================== CLIENTES ====================
export async function getClientes() {
  const res = await fetch(`${API_BASE}/api/customer/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener clientes');
  return await res.json();
}

export async function getClienteById(id) {
  const res = await fetch(`${API_BASE}/api/customer/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cliente');
  return await res.json();
}

export async function crearCliente(cliente) {
  const res = await fetch(`${API_BASE}/api/customer/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cliente)
  });
  if (!res.ok) throw new Error('Error al crear cliente');
  return await res.json();
}

export async function actualizarCliente(id, cliente) {
  const res = await fetch(`${API_BASE}/api/customer/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cliente)
  });
  if (!res.ok) throw new Error('Error al actualizar cliente');
  return await res.json();
}

export async function eliminarCliente(id) {
  const res = await fetch(`${API_BASE}/api/customer/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cliente');
  return await res.json();
}