// ==================== SALE DETAILS ====================
export async function getSaleDetails() {
  const res = await fetch(`${API_BASE}/api/saleDetails/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener detalles de venta');
  return await res.json();
}

export async function getSaleDetailById(id) {
  const res = await fetch(`${API_BASE}/api/saleDetails/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener detalle de venta');
  return await res.json();
}

export async function getSaleDetailsBySale(saleId) {
  const res = await fetch(`${API_BASE}/api/saleDetails/sale/${saleId}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener detalles por venta');
  return await res.json();
}
