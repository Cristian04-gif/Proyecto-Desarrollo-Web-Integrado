import { useEffect, useState } from 'react';

export function useCartLogic() {
  const [cartItems, setCartItems] = useState([]);

  // Cargar carrito desde localStorage
  useEffect(() => {
    const stored = localStorage.getItem('cartItems');
    if (stored) {
      setCartItems(JSON.parse(stored));
    }
  }, []);

  // Guardar carrito en localStorage
  useEffect(() => {
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
  }, [cartItems]);

  // Añadir producto
  const addItem = (product, qty) => {
  setCartItems(prev => {
    const existing = prev.find(item => item.productId === product.productId);
    if (existing) {
      return prev.map(item =>
        item.productId === product.productId
          ? { ...item, qty: item.qty + qty } 
          : item
      );
    } else {
      return [...prev, { ...product, qty }];
    }
  });
};


  // Eliminar producto
  const removeItem = (productId) => {
    setCartItems(prev => prev.filter(item => item.productId !== productId));
  };

  // Vaciar carrito
  const clearCart = () => {
    setCartItems([]);
  };

  // Total de unidades
  const totalQty = cartItems.reduce((sum, item) => sum + item.qty, 0);

  return { cartItems, addItem, removeItem, clearCart, totalQty };
}
