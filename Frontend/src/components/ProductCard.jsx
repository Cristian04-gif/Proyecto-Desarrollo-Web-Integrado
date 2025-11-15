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
      await addToCart(orderId, product.idProducto, qty, token);
      alert(`Se añadió ${qty} x ${product.nombre} al carrito`);
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
          src={product.imagen || '/placeholder.png'}
          alt={product.nombre}
        />
        {!product.activo && <span className="badge badge-out">Sin stock</span>}
        {product.enTemporada && <span className="badge badge-season">En temporada</span>}
      </div>

      <div className="product-content">
        <h3 className="product-title">{product.nombre}</h3>
        <p className="product-details">{product.descripcion}</p>

        <div className="product-meta">
          <span className="product-category">{product.categoria}</span>
          <span className="product-price">S/ {product.precio?.toFixed(2)}</span>
        </div>

        <div className="product-actions">
          <div className="qty-control">
            <button className="qty-btn" onClick={decrement} aria-label="Disminuir cantidad">-</button>
            <span className="qty-value">{qty}</span>
            <button className="qty-btn" onClick={increment} aria-label="Aumentar cantidad">+</button>
          </div>

          <button
            className="add-btn"
            onClick={handleAdd}
            disabled={!product.activo}
            aria-disabled={!product.activo}
          >
            Añadir al carrito
          </button>
        </div>
      </div>
    </div>
  );
}
