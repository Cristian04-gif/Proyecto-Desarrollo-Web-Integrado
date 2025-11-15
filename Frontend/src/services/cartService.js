const API_BASE = "http://localhost:8080";

// Obtener token desde localStorage
function getToken() {
  return localStorage.getItem("token");
}

// 🔹 Obtener todos los detalles de la venta (carrito)
export async function getCartItems(idVenta) {
  const res = await fetch(`${API_BASE}/api/detalleVenta/venta/${idVenta}`, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
  if (!res.ok) throw new Error("Error al cargar carrito");
  return res.json();
}

// 🔹 Obtener cantidad de productos en el carrito
export async function getCount(idVenta) {
  const res = await fetch(`${API_BASE}/api/detalleVenta/venta/${idVenta}`, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
  if (!res.ok) throw new Error("Error al obtener cantidad");
  const detalles = await res.json();
  return detalles.length; // cada detalle = un producto en el carrito
}

// 🔹 Eliminar producto del carrito (ajustar cantidad a 0)
export async function removeFromCart(idDetalleVenta) {
  const res = await fetch(`${API_BASE}/api/detalleVenta/update/${idDetalleVenta}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${getToken()}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ cantidad: 0 }), // ⚠️ tu backend debe interpretar esto como eliminar
  });
  if (!res.ok) throw new Error("Error al eliminar producto");
  return res.json();
}

// 🔹 Finalizar compra (actualizar venta con método de pago)
export async function completeCart(idVenta, payload) {
  const res = await fetch(`${API_BASE}/api/venta/update/${idVenta}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${getToken()}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload), // ej: { metodoPago: "EFECTIVO" }
  });
  if (!res.ok) throw new Error("Error al finalizar compra");
  return res.json();
}
// 🔹 Añadir producto al carrito (detalleVenta)
export async function addToCart(idVenta, idProducto, cantidad, token) {
  const res = await fetch(`${API_BASE}/api/detalleVenta/update/${idProducto}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      venta: { idVenta },
      cantidad,
    }),
  });
  if (!res.ok) throw new Error("Error al añadir producto al carrito");
  return res.json();
}
