import { useState, useEffect } from 'react';
import '../styles/filtersStyles.css';

export default function Filters({ onChange }) {
  const [category, setCategory] = useState('all');
  const [availability, setAvailability] = useState('all');
  const [search, setSearch] = useState('');

  useEffect(() => {
    onChange({ category, availability, search });
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
          <option value="frutas">Frutas</option>
          <option value="verduras">Verduras</option>
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
          <option value="inSeason">En temporada</option>
          <option value="inStock">En stock</option>
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
