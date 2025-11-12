import { useEffect, useState } from 'react';
import { getCount } from '../services/cartService';
import { useNavigate } from 'react-router-dom';
import '../styles/cartStyles.css';

export default function FloatingCartButton() {
  const [count, setCount] = useState(getCount());
  const navigate = useNavigate();

  useEffect(() => {
    const handler = () => setCount(getCount());
    window.addEventListener('storage', handler);
    const interval = setInterval(handler, 800); 
    return () => {
      window.removeEventListener('storage', handler);
      clearInterval(interval);
    };
  }, []);

  return (
    <button className="floating-cart" onClick={() => navigate('/cart')}>
      <span className="floating-cart-icon">🛒</span>
      <span className="floating-cart-count">{count}</span>
    </button>
  );
}
