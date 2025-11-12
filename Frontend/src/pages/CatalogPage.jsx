import { useEffect, useMemo, useState } from 'react';
import { fetchMockProducts } from '../services/productService';
import Filters from '../components/Filters';
import ProductCard from '../components/ProductCard';
import FloatingCartButton from '../components/FloatingCartButton';
import '../styles/catalogStyles.css';

export default function CatalogPage() {
  const [products, setProducts] = useState([]);
  const [filters, setFilters] = useState({ category: 'all', availability: 'all', search: '' });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let mounted = true;
    fetchMockProducts().then(data => {
      if (mounted) {
        setProducts(data);
        setLoading(false);
      }
    });
    return () => { mounted = false; };
  }, []);

  const filtered = useMemo(() => {
    let result = [...products];

    if (filters.category !== 'all') {
      result = result.filter(p => p.category === filters.category);
    }
    if (filters.availability === 'inSeason') {
      result = result.filter(p => p.inSeason);
    } else if (filters.availability === 'inStock') {
      result = result.filter(p => p.inStock);
    }
    if (filters.search.trim()) {
      const q = filters.search.toLowerCase();
      result = result.filter(p =>
        p.name.toLowerCase().includes(q) || p.details.toLowerCase().includes(q)
      );
    }
    return result;
  }, [products, filters]);

  return (
    <div className="catalog-page">
      <section className="catalog-hero">
        <div className="catalog-hero-content">
          <h1>Catálogo BioCampo</h1>
          <p>Productos frescos y sostenibles directamente del campo.</p>
        </div>
      </section>

      <section className="catalog-controls">
        <Filters onChange={setFilters} />
      </section>

      <section className="catalog-grid">
        {loading ? (
          <div className="catalog-loading">Cargando productos...</div>
        ) : filtered.length === 0 ? (
          <div className="catalog-empty">No se encontraron productos con esos filtros.</div>
        ) : (
          filtered.map(p => <ProductCard key={p.id} product={p} />)
        )}
      </section>

      <FloatingCartButton />
    </div>
  );
}
