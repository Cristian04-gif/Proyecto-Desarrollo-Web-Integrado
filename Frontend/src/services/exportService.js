// src/services/exportService.js
const API_BASE = "https://servidor2-biocampo-latest.onrender.com";

const getAuthHeaders = () => {
  const token = localStorage.getItem("token");
  return {
    "Authorization": `Bearer ${token}`
    // No ponemos Content-Type application/json porque esperamos un archivo binario
  };
};

export async function downloadExcel(endpoint, filename) {
  try {
    const res = await fetch(`${API_BASE}${endpoint}`, {
      method: 'GET',
      headers: getAuthHeaders()
    });

    if (!res.ok) throw new Error('Error al generar el reporte');

    // 1. Convertir la respuesta a un Blob (archivo binario)
    const blob = await res.blob();

    // 2. Crear una URL temporal para ese blob
    const url = window.URL.createObjectURL(blob);

    // 3. Crear un enlace invisible <a>, forzar el click y borrarlo
    const a = document.createElement('a');
    a.href = url;
    a.download = filename; // Nombre del archivo
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url); // Limpiar memoria

  } catch (error) {
    console.error("Error exportando Excel:", error);
    alert("No se pudo descargar el reporte. Revisa la consola.");
  }
}