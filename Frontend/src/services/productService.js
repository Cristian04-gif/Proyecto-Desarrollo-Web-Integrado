// Mockable product service. Replace fetchMockProducts() with API call later.
const MOCK_PRODUCTS = [
  {
    id: 'prd-001',
    name: 'Manzana Orgánica',
    category: 'frutas',
    price: 4.5,
    inSeason: true,
    inStock: true,
    details: 'Manzanas frescas de productores locales, certificadas.',
    image: './assets/products/manzana.jpg'
  },
  {
    id: 'prd-002',
    name: 'Lechuga',
    category: 'verduras',
    price: 2.2,
    inSeason: true,
    inStock: true,
    details: 'Lechuga crujiente, ideal para ensaladas.',
    image: '/assets/products/lechuga.jpg'
  },
  {
    id: 'prd-003',
    name: 'Plátano',
    category: 'frutas',
    price: 3.0,
    inSeason: false,
    inStock: true,
    details: 'Dulce y energético, perfecto para snacks.',
    image: '/assets/products/platano.jpg'
  },
  {
    id: 'prd-004',
    name: 'Papa Andina',
    category: 'verduras',
    price: 1.8,
    inSeason: true,
    inStock: false,
    details: 'Papa nativa, sabor intenso.',
    image: '/assets/products/papa.jpg'
  }
];

export async function fetchMockProducts() {
  // Simulate async
  return new Promise(resolve => setTimeout(() => resolve(MOCK_PRODUCTS), 300));
}

// Future: export async function fetchProducts() { const res = await fetch('/api/products'); return res.json(); }
