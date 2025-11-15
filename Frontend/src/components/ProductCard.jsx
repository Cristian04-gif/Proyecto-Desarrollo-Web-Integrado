import { useState } from 'react';
import { addToCart } from '../services/cartService';
import '../styles/productCardStyles.css';

export default function ProductCard({ product }) {
  const [qty, setQty] = useState(1);

  const increment = () => setQty(q => q + 1);
  const decrement = () => setQty(q => Math.max(1, q - 1));

  const handleAdd = async () => {
    const orderId = localStorage.getItem("orderId");
    const token = localStorage.getItem("token");

    if (!orderId || !token) {
      alert("Debes iniciar sesión y tener un carrito activo");
      return;
    }

    try {
      await addToCart(orderId, product.productId, qty, token);
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
          src={product.img || '/placeholder.png'}
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
          <span className="product-unit">{product.unit || "Sin unidad"}</span>
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
            <button className="qty-btn" onClick={increment}>+</button>
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
