import { useState, useEffect } from 'react';
import '../styles/filtersStyles.css';

export default function Filters({ onChange }) {
  const [category, setCategory] = useState('all');
  const [availability, setAvailability] = useState('all');
  const [search, setSearch] = useState('');
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetch('https://servidor-biocampo-latest.onrender.com/api/plantCategory/all')
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP error ${res.status}`);
        return res.json();
      })
      .then((data) => {
        console.log('Categorías recibidas:', data);
        setCategories(data);
      })
      .catch((err) => console.error('Error al cargar categorías:', err));
  }, []);

  useEffect(() => {
    const parsedCategory = category === 'all' ? 'all' : parseInt(category);
    onChange({ category: parsedCategory, availability, search });
  }, [category, availability, search, onChange]);

  return (
    <div className="filters">
      <div className="filters-group">
        <label className="filters-label">Categoría</label>
        <select
          className="filters-select"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        >
          <option value="all">Todas</option>
          {categories.map((cat) => (
            <option key={cat.categoryId} value={cat.categoryId}>
              {cat.categoryName || `Categoría ${cat.categoryId}`}
            </option>
          ))}
        </select>
      </div>

      <div className="filters-group">
        <label className="filters-label">Disponibilidad</label>
        <select
          className="filters-select"
          value={availability}
          onChange={(e) => setAvailability(e.target.value)}
        >
          <option value="all">Todas</option>
          <option value="inStock">En stock</option>
          <option value="noStock">Sin stock</option>
        </select>
      </div>

      <div className="filters-group">
        <label className="filters-label">Buscar</label>
        <input
          className="filters-input"
          type="text"
          placeholder="Buscar producto..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>
    </div>
  );
}
