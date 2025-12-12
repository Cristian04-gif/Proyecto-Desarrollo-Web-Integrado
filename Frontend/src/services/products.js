const API_BASE = "https://servidor2-biocampo-latest.onrender.com"; 
const getAuthHeaders = () => ({ "Content-Type": "application/json", "Authorization": `Bearer ${localStorage.getItem("token")}` });

export async function getProducts() {
  const res = await fetch(`${API_BASE}/api/product/all`, { headers: getAuthHeaders() });
  return await res.json();
}