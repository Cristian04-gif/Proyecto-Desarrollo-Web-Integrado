import { useCart } from '../services/useCart';
import "../styles/cartStyles.css";
import MercadoPago from '../components/MercadoPago';
import { getEmail } from '../services/authService';   // ✅ quitamos getRole
import { customerService } from '../services/customerService';
import { useState, useEffect } from 'react';

export default function CartPage() {
  const { cartItems, removeItem, clearCart } = useCart();
  const [isClient, setIsClient] = useState(false);
  const [loading, setLoading] = useState(true);

  const total = cartItems.reduce((sum, item) => sum + item.price * item.qty, 0);

  useEffect(() => {
    async function checkClient() {
      const email = getEmail();

      console.log("Email obtenido:", email);

      if (!email) {
        setIsClient(false);
        setLoading(false);
        return;
      }

      try {
        const { exists } = await customerService.existsByEmail(email);
        console.log("¿Cliente existe?", exists);
        setIsClient(exists);
      } catch (err) {
        console.error("Error verificando cliente:", err);
        setIsClient(false);
      } finally {
        setLoading(false);
      }
    }
    checkClient();
  }, []);

  console.log("Render -> isClient:", isClient, "loading:", loading);

  return (
    <div className="cart-page">
      <h2>Resumen de tu carrito</h2>
      {cartItems.length === 0 ? (
        <p className="cart-empty">Carrito vacío</p>
      ) : (
        <>
          <table className="cart-table">
            <thead>
              <tr>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Precio unitario</th>
                <th>Subtotal</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {cartItems.map((item) => (
                <tr key={item.productId}>
                  <td>{item.name}</td>
                  <td>{item.qty}</td>
                  <td>S/ {item.price.toFixed(2)}</td>
                  <td>S/ {(item.price * item.qty).toFixed(2)}</td>
                  <td>
                    <button
                      className="cart-remove-btn-table"
                      onClick={() => removeItem(item.productId)}
                    >
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          <div className="cart-summary">
            <div className="cart-actions">
              <button className="cart-clear-btn" onClick={clearCart}>
                Vaciar carrito
              </button>

              {/* Mostrar MercadoPago solo si es cliente */}
              {loading ? (
                <p>Cargando validación...</p>
              ) : isClient ? (
                <MercadoPago />
              ) : (
                <>
                  <p className="warning">
                    Debes completar tu perfil de cliente para pagar
                  </p>
                  <button
                    className="cart-register-btn"
                    onClick={() => window.location.href = "/register-client"}
                  >
                    Completar perfil de cliente
                  </button>
                </>
              )}
            </div>
            <div className="cart-total">
              Total: S/ {total.toFixed(2)}
            </div>
          </div>
        </>
      )}
    </div>
  );
}
