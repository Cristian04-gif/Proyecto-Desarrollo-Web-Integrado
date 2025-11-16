import { useEffect } from "react";
import { useCart } from "../services/cartContext";
import { getCartItems, removeFromCart, completeCart } from "../services/cartService";

export default function CartPage() {
  const { cartItems, removeItem, clearCart, setCartItems } = useCart();
  const orderId = localStorage.getItem("orderId");
  const token = localStorage.getItem("token");

  // Cargar carrito desde backend al montar
  useEffect(() => {
    if (orderId && token) {
      getCartItems(orderId).then(setCartItems).catch(console.error);
    }
  }, [orderId, token, setCartItems]);

  const handleRemove = async (detalleId, productId) => {
    try {
      await removeFromCart(detalleId);
      removeItem(productId); // sincroniza frontend
    } catch (err) {
      console.error("Error al eliminar producto:", err);
    }
  };

  const handleCheckout = async () => {
    try {
      await completeCart(orderId, { metodoPago: "EFECTIVO" });
      alert("Compra finalizada");
      clearCart();
      localStorage.removeItem("orderId");
    } catch (err) {
      console.error("Error al finalizar compra:", err);
    }
  };

  const total = cartItems.reduce((sum, item) => sum + item.price * item.qty, 0);

  return (
    <div className="cart-page">
      <h2>Tu carrito</h2>
      {cartItems.length === 0 ? (
        <p>Carrito vacío</p>
      ) : (
        <ul>
          {cartItems.map((item) => (
            <li key={item.productId}>
              {item.name} — {item.qty} unidades — S/ {(item.price * item.qty).toFixed(2)}
              <button onClick={() => handleRemove(item.idDetalleVenta, item.productId)}>
                Eliminar
              </button>
            </li>
          ))}
        </ul>
      )}
      {cartItems.length > 0 && (
        <>
          <h3>Total: S/ {total.toFixed(2)}</h3>
          <button onClick={handleCheckout}>Finalizar compra</button>
        </>
      )}
    </div>
  );
}
