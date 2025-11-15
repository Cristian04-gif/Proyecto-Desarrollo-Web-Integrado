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
      await addToCart(orderId, product.id_producto, qty, token);
      alert(`Se añadió ${qty} x ${product.etiqueta} al carrito`);
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
          src={product.img_producto || '/placeholder.png'}
          alt={product.descripcion}
        />
        {!product.disponible && <span className="badge badge-out">Sin stock</span>}
      </div>

      <div className="product-content">
        <h3 className="product-title">{product.etiqueta}</h3>
        <p className="product-details">{product.descripcion}</p>

        <div className="product-meta">
          <span className="product-unit">{product.unidad_medida}</span>
          <span className="product-price">S/ {product.precio?.toFixed(2)}</span>
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
            disabled={!product.disponible}
          >
            Añadir al carrito
          </button>
        </div>
      </div>
    </div>
  );
}
