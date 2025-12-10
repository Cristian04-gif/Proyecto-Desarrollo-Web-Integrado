const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

// Obtener todos los productos
export async function fetchProducts() {
  const res = await fetch(`${API_BASE}/api/product/all`, { 
    headers: getAuthHeaders() 
  });
  if (!res.ok) throw new Error('Error al obtener productos');
  return await res.json();
}

// Crear producto nuevo
export async function createProduct(payload) {
  const res = await fetch(`${API_BASE}/api/product/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar producto');
  return await res.json();
}

// Eliminar producto
export async function deleteProduct(id) {
  const res = await fetch(`${API_BASE}/api/product/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar producto');
  return true;
}