// src/services/sales.js
const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

// Obtener todas las ventas
export async function getSales() {
  const res = await fetch(`${API_BASE}/api/sale/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener ventas');
  return await res.json();
}

// Obtener detalles de una venta (Items)
export async function getSaleDetails(saleId) {
  // Usamos el endpoint que definiste en SaleDatailsController
  const res = await fetch(`${API_BASE}/api/saleDetails/sale/${saleId}`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener detalles');
  return await res.json();
}

// Registrar venta
export async function createSale(payload) {
  // Tu SaleController espera: { email: "...", details: [...] }
  const res = await fetch(`${API_BASE}/api/sale/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar venta');
  return await res.json();
}

// Eliminar venta
export async function deleteSale(id) {
  const res = await fetch(`${API_BASE}/api/sale/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar venta');
  return true;
}