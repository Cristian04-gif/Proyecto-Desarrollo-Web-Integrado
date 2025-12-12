import React, { useEffect, useState } from 'react';
import { initMercadoPago, Wallet } from '@mercadopago/sdk-react';
import { crearPago } from '../Service/pago';

// 1. Obtener la clave pública desde el entorno
// (Asegúrate de configurar VITE_MP_PUBLIC_KEY en Vercel)
const PUBLIC_KEY = import.meta.env.VITE_MP_PUBLIC_KEY; 

// Inicializa Mercado Pago solo si la clave existe (protección contra errores)
if (PUBLIC_KEY) {
    initMercadoPago(PUBLIC_KEY);
} else {
    console.error("La clave pública de Mercado Pago no está definida en las variables de entorno.");
}

const App = () => {
    const [preferenceId, setPreferenceId] = useState('');
    const [error, setError] = useState('');
    
    // El useEffect se ejecuta solo en el cliente (navegador)
    useEffect(() => {
        // 2. Mover la lectura de localStorage dentro del useEffect
        const cartItems = localStorage.getItem("cartItems");
        
        // Verifica si hay items y si la clave pública está disponible
        if (!cartItems || !PUBLIC_KEY) {
            setError("Error: Carrito vacío o clave de MP no cargada.");
            return;
        }

        async function loadPreference() {
            try {
                // Asumiendo que crearPago espera un string de JSON
                const res = await crearPago(cartItems); 
                setPreferenceId(res.preferenceId);
            } catch (err) {
                // El error puede ser un objeto de respuesta de la API, ajusta según tu servicio
                setError("Error al crear la preferencia de pago."); 
                console.error("Detalle del error:", err);
            }
        }
        loadPreference();
    }, []); // Dependencia vacía para que se ejecute una sola vez al montar

    return (
        <div style={{ width: '300px' }}>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            
            {/* Solo se muestra si preferenceId existe */}
            {preferenceId && (
                <Wallet initialization={{ preferenceId }} />
            )}
        </div>
    );
};

export default App;