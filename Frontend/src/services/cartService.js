const CART_KEY = 'biocampo_cart';

export function getCart() {
  try {
    const raw = localStorage.getItem(CART_KEY);
    return raw ? JSON.parse(raw) : [];
  } catch {
    return [];
  }
}

export function saveCart(cart) {
  localStorage.setItem(CART_KEY, JSON.stringify(cart));
}

export function addToCart(product, quantity = 1) {
  const cart = getCart();
  const idx = cart.findIndex(item => item.id === product.id);
  if (idx > -1) {
    cart[idx].quantity += quantity;
  } else {
    cart.push({ id: product.id, name: product.name, price: product.price, image: product.image, quantity });
  }
  saveCart(cart);
  return cart;
}

export function updateQuantity(productId, quantity) {
  const cart = getCart().map(item =>
    item.id === productId ? { ...item, quantity: Math.max(0, quantity) } : item
  ).filter(item => item.quantity > 0);
  saveCart(cart);
  return cart;
}

export function removeFromCart(productId) {
  const cart = getCart().filter(item => item.id !== productId);
  saveCart(cart);
  return cart;
}

export function getCount() {
  return getCart().reduce((sum, item) => sum + item.quantity, 0);
}

export function clearCart() {
  saveCart([]);
}
