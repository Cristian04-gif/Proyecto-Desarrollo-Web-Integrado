import { useContext } from 'react';
import CartContext from '../pages/cartContext';

export function useCart() {
  return useContext(CartContext);
}
