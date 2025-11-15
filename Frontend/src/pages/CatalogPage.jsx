import { useEffect, useMemo, useState } from 'react';
import { fetchProducts } from '../services/productService'; 
import ProductCard from '../components/ProductCard';
import FloatingCartButton from '../components/FloatingCartButton';
import Filters from '../components/Filters';
import '../styles/catalogStyles.css';

export default function CatalogPage() {
  const [products, setProducts] = useState([]);
  const [filters, setFilters] = useState({ category: 'all', availability: 'all', search: '' });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let mounted = true;
    fetchProducts()
      .then(data => {
        if (mounted) {
          console.log("Productos desde backend:", data);
          setProducts(data);
          setLoading(false);
        }
      })
      .catch(err => {
        console.error("Error cargando productos:", err);
        setLoading(false);
      });
    return () => { mounted = false; };
  }, []);

  const filtered = useMemo(() => {
    let result = [...products];

    // Filtrar por categoría (usa id_categoria_planta)
    if (filters.category !== 'all') {
      result = result.filter(p => String(p.id_categoria_planta) === filters.category);
    }

    // Filtrar por disponibilidad
    if (filters.availability === 'inStock') {
      result = result.filter(p => p.disponible); 
    }

    // Filtrar por búsqueda (usa etiqueta y descripcion)
    if (filters.search.trim()) {
      const q = filters.search.toLowerCase();
      result = result.filter(p =>
        p.etiqueta?.toLowerCase().includes(q) || p.descripcion?.toLowerCase().includes(q)
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
          filtered.map(p => <ProductCard key={p.id_producto} product={p} />)
        )}
      </section>

      <FloatingCartButton />
    </div>
  );
}
