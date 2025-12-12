const API_BASE = "https://servidor-biocampo-latest.onrender.com";

// Obtener token desde localStorage
function getToken() {
  return localStorage.getItem("token");
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
    throw new Error("Error al finalizar compra: " + errorText);
  }
}
