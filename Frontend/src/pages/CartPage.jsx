import { useEffect, useState } from "react";
import {
  getCartItems,
  removeFromCart,
  completeCart,
} from "../services/cartService";

export default function CartPage() {
  const [items, setItems] = useState([]);
  const orderId = localStorage.getItem("orderId"); // ID de la venta
  const token = localStorage.getItem("token"); // Token JWT

  // Cargar carrito al montar
  useEffect(() => {
    if (orderId && token) {
      getCartItems(orderId, token).then(setItems).catch(console.error);
    }
  }, [orderId, token]);

  // Eliminar un producto del carrito
  const handleRemove = async (detalleId) => {
    try {
      await removeFromCart(detalleId, token);
      const updated = await getCartItems(orderId, token);
      setItems(updated);
    } catch (err) {
      console.error("Error al eliminar producto:", err);
    }
  };

  // Finalizar compra
  const handleCheckout = async () => {
    try {
      await completeCart(orderId, { metodoPago: "EFECTIVO" }, token);
      alert("Compra finalizada");
      setItems([]);
      localStorage.removeItem("orderId");
    } catch (err) {
      console.error("Error al finalizar compra:", err);
    }
  };

  return (
    <div className="cart-page">
      <h2>Tu carrito</h2>
      {items.length === 0 ? (
        <p>Carrito vacío</p>
      ) : (
        <ul>
          {items.map((item) => (
            <li key={item.idDetalleVenta}>
              {item.input?.nombre} x {item.cantidad}
              <button onClick={() => handleRemove(item.idDetalleVenta)}>
                Eliminar
              </button>
            </li>
          ))}
        </ul>
      )}
      {items.length > 0 && (
        <button onClick={handleCheckout}>Finalizar compra</button>
      )}
    </div>
  );
}
