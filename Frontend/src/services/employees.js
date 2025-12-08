// ==================== EMPLEADOS ====================
export async function getEmpleados() {
  const res = await fetch(`${API_BASE}/api/employee/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener empleados');
  return await res.json();
}

export async function getEmpleadoById(id) {
  const res = await fetch(`${API_BASE}/api/employee/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener empleado');
  return await res.json();
}

export async function crearEmpleado(empleado) {
  const res = await fetch(`${API_BASE}/api/employee/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(empleado)
  });
  if (!res.ok) throw new Error('Error al crear empleado');
  return await res.json();
}

export async function actualizarEmpleado(id, empleado) {
  const res = await fetch(`${API_BASE}/api/employee/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(empleado)
  });
  if (!res.ok) throw new Error('Error al actualizar empleado');
  return await res.json();
}

export async function eliminarEmpleado(id) {
  const res = await fetch(`${API_BASE}/api/employee/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar empleado');
  return await res.json();
}