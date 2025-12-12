import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/PagoExitoso.css';
import '../Service/pago'
const token = localStorage.getItem("token");
const user = JSON.parse(localStorage.getItem("user"));

async function comprobantePago() {
  const res = await fetch(`https://servidor2-biocampo-latest.onrender.com/sale/customer/${user.email}`, {
    method: 'GET',
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    }
  });

  if (!res.ok) throw new Error("No se descargar el comprobante de pago");
  return res.json();
}

const PagoExitoso = () => {
  const navigate = useNavigate();


  useEffect(() => {
    const timer = setTimeout(() => {
      navigate('/');
    }, 10000);

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="pago-container">
      <h2 className="pago-title">¡Gracias por tu compra!</h2>
      <p className="pago-message">Tu pago fue procesado con éxito.</p>
      <p className="pago-message">Serás redirigido al inicio en unos segundos...</p>
      <button onClick={comprobantePago()}>Descargar comprobante de pago</button>
    </div>
  );
};

export default PagoExitoso;
