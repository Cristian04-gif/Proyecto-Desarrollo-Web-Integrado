import React, { useEffect, useState } from 'react';
import { initMercadoPago, Wallet } from '@mercadopago/sdk-react';
import { crearPago } from '../Service/pago'

initMercadoPago('APP_USR-04798869-a63e-489b-9884-027d4f456d8d');
const App = () => {
    const [preferenceId, setPreferenceId] = useState('');
    const [error, setError] = useState('');
    
    useEffect(() => {
        const cartItems = localStorage.getItem("cartItems");
        async function loadPreference() {
            /*Esta parte se reemplaza, en la funcion crear pago se ingresa el
            email de usuario que se guarda en el localstorage y lo mismo con los productos*/
            try {
                const res = await crearPago(cartItems);
                setPreferenceId(res.preferenceId);
            } catch (err) {
                setError(err.message)
            }
            /**************************************************************************/
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
