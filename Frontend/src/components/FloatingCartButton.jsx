import { useEffect, useState } from 'react';
import { getCount } from '../services/cartService';
import { useNavigate } from 'react-router-dom';
import '../styles/cartStyles.css';

export default function FloatingCartButton() {
  const [count, setCount] = useState(0); 
  const navigate = useNavigate();
  const orderId = localStorage.getItem("orderId"); 
  const token = localStorage.getItem("token"); 
  useEffect(() => {
    const updateCount = async () => {
      if (orderId && token) {
        try {
          const value = await getCount(orderId, token);
          setCount(value);
        } catch (err) {
          console.error("Error al obtener cantidad del carrito:", err);
        }
      }
    };

    updateCount();

    const handler = () => updateCount(); 
    window.addEventListener('storage', handler);
    const interval = setInterval(handler, 2000); 

    return () => {
      window.removeEventListener('storage', handler);
      clearInterval(interval);
    };
  }, [orderId, token]);

  return (
    <button className="floating-cart" onClick={() => navigate('/carrito')}>
      <span className="floating-cart-icon">🛒</span>
      <span className="floating-cart-count">{count}</span>
    </button>
  );
}
