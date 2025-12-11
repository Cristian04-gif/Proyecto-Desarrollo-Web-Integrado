import React, { useState } from 'react';
import '../styles/Nosotros.css';

export default function Nosotros() {
  // Datos simulados para el Carrusel
  const plantios = [
    { id: 1, img: "https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&q=80&w=1000", title: "Campos de Maíz" },
    { id: 2, img: "https://static.plataformatierra.es/strapi-uploads/assets/viticultura_sostenible_calidad_1_4eb3fc0e33", title: "Viñedos Sustentables" },
    { id: 3, img: "https://st.depositphotos.com/4252389/61442/i/450/depositphotos_614422514-stock-photo-fresh-apples-orchard-apple-harvest.jpg", title: "Huertos de Manzana" },
  ];

  // Datos simulados para el Equipo (IMÁGENES ACTUALIZADAS Y PROFESIONALES)
  const equipo = [
    { 
      name: "Juan Pérez", 
      role: "CEO & Fundador", 
      img: "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&q=80&w=800" // Hombre con traje, look ejecutivo
    },
    { 
      name: "Maria Garcia", 
      role: "Ingeniera Agrónoma", 
      img: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&q=80&w=800" // Mujer profesional, look confiable
    },
    { 
      name: "Carlos Lopez", 
      role: "Director de Operaciones", 
      img: "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&q=80&w=800" // Hombre smart-casual, look moderno
    },
    { 
      name: "Ana Torres", 
      role: "Especialista en Riego", 
      img: "https://images.unsplash.com/photo-1580489944761-15a19d654956?auto=format&fit=crop&q=80&w=800" // Mujer joven profesional, excelente iluminación
    }
  ];

  // Lógica simple para el carrusel
  const [currentSlide, setCurrentSlide] = useState(0);
  const nextSlide = () => setCurrentSlide((prev) => (prev === plantios.length - 1 ? 0 : prev + 1));
  const prevSlide = () => setCurrentSlide((prev) => (prev === 0 ? plantios.length - 1 : prev - 1));

  return (
    <div className="page-container">
      {/* 1. Banner de Presentación */}
      <div className="page-banner" style={{backgroundImage: 'url("https://images.unsplash.com/photo-1464226184884-fa280b87c399?auto=format&fit=crop&q=80&w=1600")'}}>
        <div className="banner-overlay">
          <h1>Sobre Nosotros</h1>
          <p>Cultivando el futuro con tecnología y tradición</p>
        </div>
      </div>

      {/* 2. Historia */}
      <section className="section-container history-section">
        <div className="text-content">
          <h2>Nuestra Historia</h2>
          <p>
            Fundada en 2010, BioCampo nació con la visión de transformar la agricultura tradicional 
            mediante el uso de tecnologías sostenibles. Lo que comenzó como un pequeño proyecto familiar 
            en el norte del país, hoy es un referente en agritech, ayudando a cientos de agricultores 
            a maximizar sus cosechas respetando el medio ambiente.
          </p>
        </div>
        <div className="image-content">
          <img src="https://img.freepik.com/foto-gratis/granjero-sonriente-campo-tractor_23-2151983452.jpg?semt=ais_se_enriched&w=740&q=80" alt="Historia BioCampo" />
        </div>
      </section>

      {/* 3. Misión, Visión y Valores */}
      <section className="section-container mvv-section">
        <div className="mvv-card">
          <i className="fas fa-bullseye"></i>
          <h3>Misión</h3>
          <p>Proveer soluciones agrícolas integrales que garanticen la seguridad alimentaria y el desarrollo sostenible.</p>
        </div>
        <div className="mvv-card">
          <i className="fas fa-eye"></i>
          <h3>Visión</h3>
          <p>Ser líderes mundiales en innovación agrícola, conectando la naturaleza con la tecnología.</p>
        </div>
        <div className="mvv-card">
          <i className="fas fa-heart"></i>
          <h3>Valores</h3>
          <p>Integridad, Innovación, Sostenibilidad y Compromiso con nuestra comunidad.</p>
        </div>
      </section>

      {/* 4. Carrusel de Plantíos */}
      <section className="section-container carousel-section">
        <h2>Nuestros Plantíos</h2>
        <div className="carousel-container">
          <button onClick={prevSlide} className="carousel-btn prev"><i className="fas fa-chevron-left"></i></button>
          <div className="carousel-slide">
            <img src={plantios[currentSlide].img} alt={plantios[currentSlide].title} />
            <div className="slide-caption">
              <h3>{plantios[currentSlide].title}</h3>
            </div>
          </div>
          <button onClick={nextSlide} className="carousel-btn next"><i className="fas fa-chevron-right"></i></button>
        </div>
      </section>

      {/* 5. Cartas de Presentación del Equipo */}
      <section className="section-container team-section">
        <h2>Nuestro Equipo</h2>
        <div className="team-grid">
          {equipo.map((member, index) => (
            <div key={index} className="team-card">
              <img src={member.img} alt={member.name} />
              <div className="team-info">
                <h4>{member.name}</h4>
                <span>{member.role}</span>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}