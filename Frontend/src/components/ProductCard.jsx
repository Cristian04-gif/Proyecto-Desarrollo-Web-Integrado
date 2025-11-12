import { useState } from 'react';
import { addToCart } from '../services/cartService';
import '../styles/productCardStyles.css';

export default function ProductCard({ product }) {
  const [qty, setQty] = useState(1);

  const increment = () => setQty(q => q + 1);
  const decrement = () => setQty(q => Math.max(1, q - 1));

  const handleAdd = () => {
    addToCart(product, qty);
  };

  return (
    <div className="product-card">
      <div className="product-image-wrap">
        <img className="product-image" src={product.image} alt={product.name} />
        {!product.inStock && <span className="badge badge-out">Sin stock</span>}
        {product.inSeason && <span className="badge badge-season">En temporada</span>}
      </div>

      <div className="product-content">
        <h3 className="product-title">{product.name}</h3>
        <p className="product-details">{product.details}</p>

        <div className="product-meta">
          <span className="product-category">{product.category}</span>
          <span className="product-price">S/ {product.price.toFixed(2)}</span>
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
            disabled={!product.inStock}
            aria-disabled={!product.inStock}
          >
            Añadir al carrito
          </button>
        </div>
      </div>
    </div>
  );
}
