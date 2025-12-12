const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

// GET /api/customer/all
export async function getCustomers() {
  const res = await fetch(`${API_BASE}/api/customer/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener clientes');
  return await res.json();
}

// POST /api/customer/register
export async function createCustomer(customerData) {
  const res = await fetch(`${API_BASE}/api/customer/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(customerData)
  });
  if (!res.ok) throw new Error('Error al registrar cliente');
  return await res.json();
}

// DELETE /api/customer/delete/{id}
export async function deleteCustomer(id) {
  const res = await fetch(`${API_BASE}/api/customer/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar cliente');
  return true;
}