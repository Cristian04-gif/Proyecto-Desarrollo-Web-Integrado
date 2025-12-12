const API_BASE = "https://servidor-biocampo-latest.onrender.com"; 

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`
  };
};

export async function getJobPositions() {
  const res = await fetch(`${API_BASE}/api/jobPosition/all`, { headers: getAuthHeaders() });
  return await res.json();
}

export async function createJobPosition(payload) {
  const res = await fetch(`${API_BASE}/api/jobPosition/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(payload)
  });
  if (!res.ok) throw new Error('Error al crear cargo');
  return await res.json();
}

export async function deleteJobPosition(id) {
  const res = await fetch(`${API_BASE}/api/jobPosition/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cargo');
  return true;
}