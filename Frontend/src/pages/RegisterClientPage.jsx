import React, { useState } from "react";
import { getEmail } from "../services/authService";
import { customerService } from "../services/customerService";
import "../styles/registerClient.css";

export default function RegisterClientPage() {
  const email = getEmail(); 
  const [form, setForm] = useState({
    edad: "",
    telefono: "",
    direccion: "",
    tipo: "MINORISTA",
  });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // ✅ enviar el objeto con "user.email"
      await customerService.createCustomer({
        age: form.edad,
        phone: form.telefono,
        address: form.direccion,
        type: form.tipo,
        user: { email } 
      });

      setMessage("Perfil de cliente creado correctamente. Ahora puedes comprar.");
      setTimeout(() => {
        window.location.href = "/carrito"; // usa la ruta definida en App.jsx
      }, 2000);
    } catch (err) {
      console.error(err);
      setMessage("Error al crear perfil de cliente. Intenta de nuevo.");
    }
  };

  return (
    <div className="register-client-wrapper">
      <div className="register-client-box">
        <h2 className="register-client-title">Completa tu perfil de cliente</h2>
        <p>Tu cuenta está registrada con el correo: <strong>{email}</strong></p>

        <form onSubmit={handleSubmit} className="register-client-form">
          <div className="register-client-field">
            <label className="register-client-label">Edad</label>
            <input
              type="number"
              name="edad"
              className="register-client-input"
              value={form.edad}
              onChange={handleChange}
              required
            />
          </div>

          <div className="register-client-field">
            <label className="register-client-label">Teléfono</label>
            <input
              type="tel"
              name="telefono"
              className="register-client-input"
              value={form.telefono}
              onChange={handleChange}
              required
            />
          </div>

          <div className="register-client-field">
            <label className="register-client-label">Dirección</label>
            <input
              type="text"
              name="direccion"
              className="register-client-input"
              value={form.direccion}
              onChange={handleChange}
              required
            />
          </div>

          <div className="register-client-field">
            <label className="register-client-label">Tipo de cliente</label>
            <select
              name="tipo"
              className="register-client-select"
              value={form.tipo}
              onChange={handleChange}
            >
              <option value="MINORISTA">Minorista</option>
              <option value="MAYORISTA">Mayorista</option>
            </select>
          </div>

          <button type="submit" className="register-client-button">
            Guardar perfil
          </button>
        </form>

        {message && (
          <p
            className={`register-client-message ${
              message.includes("Error")
                ? "register-client-error"
                : "register-client-success"
            }`}
          >
            {message}
          </p>
        )}
      </div>
    </div>
  );
}
