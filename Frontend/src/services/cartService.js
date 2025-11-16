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
  if (!res.ok) {
  const errorText = await res.text();
  console.error("Respuesta del backend:", errorText);
  throw new Error("Error al crear carrito: " + errorText);
}

}

// 🔹 Obtener cantidad de productos en el carrito
export async function getCount(idVenta) {
  const res = await fetch(`${API_BASE}/api/detalleVenta/venta/${idVenta}`, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
  if (!res.ok) {
  const errorText = await res.text();
  console.error("Respuesta del backend:", errorText);
  throw new Error("Error al crear carrito: " + errorText);
}

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
  if (!res.ok) {
  const errorText = await res.text();
  console.error("Respuesta del backend:", errorText);
  throw new Error("Error al crear carrito: " + errorText);
}

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
  if (!res.ok) {
  const errorText = await res.text();
  console.error("Respuesta del backend:", errorText);
  throw new Error("Error al crear carrito: " + errorText);
}

}
export async function createCart(token, clienteId) {
  const res = await fetch(`${API_BASE}/api/venta/create`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      cliente: { idCliente: Number(clienteId) },
      pago: null
    }),
  });
  if (!res.ok) {
  const errorText = await res.text();
  console.error("Respuesta del backend:", errorText);
  throw new Error("Error al crear carrito: " + errorText);
}

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
  if (!res.ok) {
  const errorText = await res.text();
  console.error("Respuesta del backend:", errorText);
  throw new Error("Error al crear carrito: " + errorText);
}

}
