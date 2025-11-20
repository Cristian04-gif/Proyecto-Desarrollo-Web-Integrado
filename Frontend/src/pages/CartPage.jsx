import { useCart } from '../services/useCart';
import "../styles/cartStyles.css";
import MercadoPago from '../components/MercadoPago'

export default function CartPage() {
  const { cartItems, removeItem, clearCart } = useCart();

  const total = cartItems.reduce((sum, item) => sum + item.price * item.qty, 0);

  const handleCheckout = () => {
    alert("Compra finalizada");
    clearCart();
  };

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
              <button className="cart-clear-btn" onClick={clearCart}>Vaciar carrito</button>
              <MercadoPago></MercadoPago>
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
