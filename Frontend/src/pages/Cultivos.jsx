import React, { useState } from 'react';
import '../styles/Cultivos.css';

export default function Cultivos() {
  const [filter, setFilter] = useState('Todos');
  const [selectedCrop, setSelectedCrop] = useState(null);

  // Datos de cultivos
  const cultivosData = [
    { 
      id: 1, type: 'Frutas', name: 'Fresa Diamante', 
      img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiEqDgTImHMG_JCK0YuhyvzSaql86IlM4Saw&s', 
      desc: 'Fresas dulces y resistentes.',
      details: 'Variedad de día neutro, excelente sabor y vida de anaquel. Ideal para climas templados.'
    },
    { 
      id: 2, type: 'Verduras', name: 'Lechuga Romana', 
      img: 'https://camposdelabuelo.com/cdn/shop/articles/1_4.png?v=1728404650&width=1250', 
      desc: 'Crujiente y fresca para ensaladas.',
      details: 'Alta tolerancia al calor y resistencia a la subida de flor. Cosecha en 60 días.'
    },
    { 
      id: 3, type: 'Frutas', name: 'Manzana Royal', 
      img: 'https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?auto=format&fit=crop&q=80&w=600', 
      desc: 'Manzanas rojas de alta calidad.',
      details: 'Requiere horas frío. Fruto de calibre grande y coloración intensa.'
    },
    { 
      id: 4, type: 'Cereales', name: 'Trigo Sarraceno', 
      img: 'https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?auto=format&fit=crop&q=80&w=600', 
      desc: 'Ideal para rotación de cultivos.',
      details: 'Ciclo corto, mejora la estructura del suelo y atrae polinizadores.'
    },
    { 
      id: 5, type: 'Verduras', name: 'Tomate Cherry', 
      img: 'https://www.imporalaska.com/uploads/products/2019/02/pic_1550267180_1550267188.jpg', 
      desc: 'Pequeños, dulces y productivos.',
      details: 'Crecimiento indeterminado. Requiere tutorado. Alta producción por racimo.'
    },
  ];

  // Lógica de filtrado
  const filteredCrops = filter === 'Todos' 
    ? cultivosData 
    : cultivosData.filter(crop => crop.type === filter);

  return (
    <div className="page-container">
      {/* 1. Banner */}
      <div className="page-banner" style={{backgroundImage: 'url("https://images.unsplash.com/photo-1500937386664-56d1dfef3854?auto=format&fit=crop&q=80&w=1600")'}}>
        <div className="banner-overlay">
          <h1>Información de Cultivos</h1>
          <p>Explora nuestro catálogo de siembra</p>
        </div>
      </div>

      {/* 2. Filtro */}
      <section className="section-container filters-section">
        <button className={filter === 'Todos' ? 'active' : ''} onClick={() => setFilter('Todos')}>Todos</button>
        <button className={filter === 'Frutas' ? 'active' : ''} onClick={() => setFilter('Frutas')}>Frutas</button>
        <button className={filter === 'Verduras' ? 'active' : ''} onClick={() => setFilter('Verduras')}>Verduras</button>
        <button className={filter === 'Cereales' ? 'active' : ''} onClick={() => setFilter('Cereales')}>Cereales</button>
      </section>

      {/* 3. Cartas de Cultivo */}
      <section className="section-container crops-grid">
        {filteredCrops.map(crop => (
          <div key={crop.id} className="crop-card" onClick={() => setSelectedCrop(crop)}>
            <div className="crop-img-container">
               <img src={crop.img} alt={crop.name} />
            </div>
            <div className="crop-info">
              <span className="crop-type">{crop.type}</span>
              <h3>{crop.name}</h3>
              <p>{crop.desc}</p>
              <button className="btn-details">Ver detalles</button>
            </div>
          </div>
        ))}
      </section>

      {/* 4. Modal (Pop-up) */}
      {selectedCrop && (
        <div className="modal-overlay" onClick={() => setSelectedCrop(null)}>
          <div className="modal-content" onClick={e => e.stopPropagation()}>
            <button className="close-modal" onClick={() => setSelectedCrop(null)}>&times;</button>
            <div className="modal-body">
              <img src={selectedCrop.img} alt={selectedCrop.name} />
              <div className="modal-text">
                <h2>{selectedCrop.name}</h2>
                <span className="badge">{selectedCrop.type}</span>
                <p className="description">{selectedCrop.desc}</p>
                <div className="technical-details">
                  <h4>Detalles Técnicos:</h4>
                  <p>{selectedCrop.details}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}