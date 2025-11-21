import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/PagoExitoso.css';

const PagoExitoso = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate('/');
    }, 5000);

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="pago-container">
      <h2 className="pago-title">¡Gracias por tu compra!</h2>
      <p className="pago-message">Tu pago fue procesado con éxito.</p>
      <p className="pago-message">Serás redirigido al inicio en unos segundos...</p>
    </div>
  );
};

export default PagoExitoso;
