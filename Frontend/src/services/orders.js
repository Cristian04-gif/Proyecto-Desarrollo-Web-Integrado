const API_BASE = "http://localhost:8080"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getOrders() {
  const res = await fetch(`${API_BASE}/api/order/all`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener pedidos');
  return await res.json();
}

export async function getOrderDetails(orderId) {
  const res = await fetch(`${API_BASE}/api/orderDetail/order/${orderId}`, { headers: getAuthHeaders() });
  if (!res.ok) throw new Error('Error al obtener detalles');
  return await res.json();
}

export async function createOrder(payload) {
  const res = await fetch(`${API_BASE}/api/order/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al registrar pedido');
  return await res.json();
}

export async function deleteOrder(id) {
  const res = await fetch(`${API_BASE}/api/order/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 204) return true;
  if (!res.ok) throw new Error('Error al eliminar pedido');
  return true;
}