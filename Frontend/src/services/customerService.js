const BASE = 'https://servidor2-biocampo-latest.onrender.com/api/customer';

export const customerService = {
  // ✅ Verifica si existe un cliente por email
  async existsByEmail(email) {
    try {
      const token = localStorage.getItem("token");

      const res = await fetch(`${BASE}/user/${encodeURIComponent(email)}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}` // ✅ obligatorio para evitar 403
        }
      });

      if (!res.ok) {
        console.warn("Error en existsByEmail:", res.status, res.statusText);
        return { exists: false };
      }

      const data = await res.json();
      console.log("Respuesta completa del backend:", data);

      // valida con customerId (porque tu clase Customer usa ese nombre)
      return { exists: !!data && !!data.customerId };
    } catch (err) {
      console.error("Error en existsByEmail:", err);
      return { exists: false };
    }
  },

  // ✅ Crea un nuevo cliente
  async createCustomer(payload) {
    try {
      const token = localStorage.getItem("token");

      const res = await fetch(`${BASE}/register`, {
        method: 'POST',
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        console.warn("Error en createCustomer:", res.status, res.statusText);
        throw new Error('Error al guardar el cliente');
      }

      return await res.json();
    } catch (err) {
      console.error("Error en createCustomer:", err);
      throw err;
    }
  }
};
