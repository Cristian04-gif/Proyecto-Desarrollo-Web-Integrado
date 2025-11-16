import { useState } from 'react';
import { addToCart, createCart } from '../services/cartService';
import { useCart } from '../services/cartContext';
import '../styles/productCardStyles.css';

export default function ProductCard({ product }) {
  const [qty, setQty] = useState(1);
  const { addItem } = useCart();

  const increment = () => {
    if (qty < product.stock) {
      setQty(q => q + 1);
    }
  };

  const decrement = () => setQty(q => Math.max(1, q - 1));

const handleAdd = async () => {
  let orderId = localStorage.getItem("orderId");
  const token = localStorage.getItem("token");

  if (!token) {
    alert("Debes iniciar sesión");
    return;
  }
console.log("Token:", token);
  const clienteId = localStorage.getItem("clienteId");

if (!orderId) {
  try {
    const nuevaVenta = await createCart(token, clienteId);
    orderId = nuevaVenta.idVenta;
    localStorage.setItem("orderId", orderId);
  } catch (err) {
    console.error("Error al crear carrito:", err);
    alert("No se pudo crear el carrito");
    return;
  }
}

  try {
    await addToCart(orderId, product.productId, qty, token);
    addItem(product, qty);
    alert(`Se añadió ${qty} x ${product.name || "Producto"} al carrito`);
  } catch (err) {
    console.error("Error al añadir al carrito:", err);
    alert("No se pudo añadir al carrito");
  }
};

  return (
    <div className="product-card">
      <div className="product-image-wrap">
        <img
          className="product-image"
          src={product.imageUrl || '/placeholder.png'}
          alt={product.description || "Producto sin descripción"}
        />
        {product.stock === 0 && (
          <span className="badge badge-out">Sin stock</span>
        )}
      </div>

      <div className="product-content">
        <h3 className="product-title">{product.name || "Sin nombre"}</h3>
        <p className="product-details">{product.description || "Sin descripción"}</p>

        <div className="product-meta">
          <span className="product-unit">
            {product.stock != null ? `${product.stock} disponibles` : "Stock no disponible"}
          </span>
          <span className="product-price">
            {product.price != null
              ? `S/ ${product.price.toFixed(2)}`
              : "Precio no disponible"}
          </span>
        </div>

        <div className="product-actions">
          <div className="qty-control">
            <button className="qty-btn" onClick={decrement}>-</button>
            <span className="qty-value">{qty}</span>
            <button
              className="qty-btn"
              onClick={increment}
              disabled={qty >= product.stock}
            >
              +
            </button>
          </div>

          <button
            className="add-btn"
            onClick={handleAdd}
            disabled={product.stock === 0}
          >
            Añadir al carrito
          </button>
        </div>
      </div>
    </div>
  );
}
