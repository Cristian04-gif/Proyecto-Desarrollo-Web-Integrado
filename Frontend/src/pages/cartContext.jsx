import { createContext } from 'react';
import { useCartLogic } from '../services/cartLogic';

const CartContext = createContext();

export function CartProvider({ children }) {
  const cart = useCartLogic();

  return (
    <CartContext.Provider value={cart}>
      {children}
    </CartContext.Provider>
  );
}

export default CartContext;
