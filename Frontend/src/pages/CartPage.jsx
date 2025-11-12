import { useEffect, useState } from 'react';
import { getCart, updateQuantity, removeFromCart, clearCart } from '../services/cartService';
import '../styles/cartStyles.css';

export default function CartPage() {
  const [cart, setCart] = useState(getCart());

  useEffect(() => {
    const interval = setInterval(() => setCart(getCart()), 800);
    return () => clearInterval(interval);
  }, []);

  const total = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const inc = (id) => setCart(updateQuantity(id, (cart.find(i => i.id === id)?.quantity || 0) + 1));
  const dec = (id) => setCart(updateQuantity(id, (cart.find(i => i.id === id)?.quantity || 0) - 1));
  const remove = (id) => setCart(removeFromCart(id));
  const clear = () => { clearCart(); setCart([]); };

  return (
    <div className="cart-page">
      <h1>Carrito</h1>

      {cart.length === 0 ? (
        <p className="cart-empty">Tu carrito está vacío.</p>
      ) : (
        <div className="cart-list">
          {cart.map(item => (
            <div className="cart-item" key={item.id}>
              <img className="cart-item-image" src={item.image} alt={item.name} />
              <div className="cart-item-info">
                <h3 className="cart-item-title">{item.name}</h3>
                <p className="cart-item-price">S/ {item.price.toFixed(2)}</p>
                <div className="cart-item-actions">
                  <button className="cart-btn" onClick={() => dec(item.id)}>-</button>
                  <span className="cart-qty">{item.quantity}</span>
                  <button className="cart-btn" onClick={() => inc(item.id)}>+</button>
                  <button className="cart-remove-btn" onClick={() => remove(item.id)}>Eliminar</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="cart-summary">
        <span className="cart-total-label">Total:</span>
        <span className="cart-total-value">S/ {total.toFixed(2)}</span>
        <button className="cart-clear-btn" onClick={clear}>Vaciar carrito</button>
        <button className="cart-checkout-btn" disabled={cart.length === 0}>Continuar compra</button>
      </div>
    </div>
  );
}
