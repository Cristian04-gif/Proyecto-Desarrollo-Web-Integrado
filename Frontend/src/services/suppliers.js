const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getSuppliers() {
  const res = await fetch(`${API_BASE}/api/supplier/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener proveedores');
  return await res.json();
}

export async function createSupplier(payload) {
  const res = await fetch(`${API_BASE}/api/supplier/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar proveedor');
  return await res.json();
}

export async function deleteSupplier(id) {
  const res = await fetch(`${API_BASE}/api/supplier/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar proveedor');
  return true;
}