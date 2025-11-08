import React, { useEffect, useState } from 'react';
import { initMercadoPago, Wallet } from '@mercadopago/sdk-react';
import { crearPago } from './Service/pago'

initMercadoPago('APP_USR-f621e718-bb03-42f4-83c0-cc29dec0ffd6');
const App = () => {
  const [preferenceId, setPreferenceId] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    async function loadPreference() {
      try {
        const sale = {
          "customer": { "user": { "email": "E654654251@biocampo.com" } },
          "paymentMethod": "TARJETA"
        };
        const details = [{
          "product": { "productId": 7 },
          "quantity": 35
        }, {
          "product": { "productId": 9 },
          "quantity": 64
        }];

        const res = await crearPago(sale, details);
        setPreferenceId(res.preferenceId);
      } catch (err) {
        setError(err.message)
      }
    }
    loadPreference();
  }, []);

  return (

    <div style={{ width: '300px' }}>
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {preferenceId && (
        <Wallet initialization={{ preferenceId }} />
      )}
    </div>
  );
};

export default App;
