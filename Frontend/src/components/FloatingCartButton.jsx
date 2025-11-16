import { useNavigate } from 'react-router-dom';
import { useCart, CartProvider } from '../services/cartContext';
import '../styles/cartStyles.css';

export default function FloatingCartButton() {
  const { totalQty } = useCart(); // viene del contexto
  const navigate = useNavigate();

  return (
    <button className="floating-cart" onClick={() => navigate('/carrito')}>
      <span className="floating-cart-icon">🛒</span>
      <span className="floating-cart-count">{totalQty}</span>
    </button>
  );
}
