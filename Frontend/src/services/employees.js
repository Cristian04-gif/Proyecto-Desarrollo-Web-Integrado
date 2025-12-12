const API_BASE = "https://servidor-biocampo-latest.onrender.com"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getEmployees() {
  const res = await fetch(`${API_BASE}/api/employee/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener empleados');
  return await res.json();
}

export async function createEmployee(payload) {
  const res = await fetch(`${API_BASE}/api/employee/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar empleado');
  return await res.json();
}

export async function deleteEmployee(id) {
  const res = await fetch(`${API_BASE}/api/employee/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar empleado');
  return true;
}