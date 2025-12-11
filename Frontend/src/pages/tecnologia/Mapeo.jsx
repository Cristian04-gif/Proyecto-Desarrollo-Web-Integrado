import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Mapeo.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function Mapeo() {
  
  // Scroll al inicio al cargar
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="mapeo-page">
      
      {/* 1. Hero Banner: Vista aérea/digital */}
      <section className="mapeo-hero">
        <div className="mapeo-overlay">
          <span className="hero-badge">🗺️ Topografía Digital</span>
          <h1>Mapeo de Campo y Georreferenciación</h1>
          <p>La base de la agricultura de precisión. Digitaliza tus parcelas para gestionar la variabilidad de tu suelo.</p>
        </div>
      </section>

      {/* 2. Contenido Principal */}
      <section className="mapeo-content section-container">
        <div className="content-grid">
          
          <div className="text-column">
            <h2>Conoce tu terreno, centímetro a centímetro</h2>
            <p className="intro-text">
              Mapear su propio terreno es el primer paso fundamental para una gestión eficaz. Una finca no es uniforme; la 
              <strong> variabilidad espacio-temporal</strong> caracteriza a todos los ambientes agrícolas.
            </p>
            <p>
              Nuestra tecnología te permite crear una "gemelo digital" de tu campo. Al identificar las diferencias en el terreno, 
              puedes modular las intervenciones (riego, siembra, fertilización) para optimizar la producción y 
              <strong> minimizar el impacto ambiental</strong>.
            </p>
            
            <div className="highlight-box">
              <h4><i className="fas fa-layer-group"></i> Datos Estratificados</h4>
              <p>Cruzamos datos de límites catastrales, tipos de suelo y elevación para crear el mapa maestro de tu productividad.</p>
            </div>
          </div>

          <div className="image-column">
            <img 
              src="https://images.unsplash.com/photo-1524661135-423995f22d0b?auto=format&fit=crop&q=80&w=800" 
              alt="Mapeo digital de campo en tablet" 
              className="main-img"
            />
            <div className="img-caption">Visualización de parcelas por satélite</div>
          </div>
        </div>
      </section>

      {/* 3. Características / Funcionalidades */}
      <section className="mapeo-features">
        <div className="section-container">
          <h2>Funcionalidades del Mapeo</h2>
          <div className="features-grid">
            
            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-draw-polygon"></i></div>
              <h3>Delimitación de Lotes</h3>
              <p>Dibuja y edita los límites exactos de tus parcelas mediante GPS de alta precisión o importando archivos KML.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-mountain"></i></div>
              <h3>Topografía 3D</h3>
              <p>Analiza las pendientes y curvas de nivel para diseñar sistemas de riego eficientes y evitar la erosión.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-history"></i></div>
              <h3>Historial de Cultivos</h3>
              <p>Asigna un registro histórico a cada polígono mapeado para entender la rotación y el desgaste del suelo.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-globe-americas"></i></div>
              <h3>Integración GIS</h3>
              <p>Compatible con sistemas de información geográfica para exportar datos a maquinaria agrícola (Shapefiles).</p>
            </div>

          </div>
        </div>
      </section>

      {/* 4. CTA Final */}
      <section className="mapeo-cta">
        <h2>Empieza a digitalizar tu finca</h2>
        <p>El control total de tu producción comienza con un buen mapa.</p>
        <div className="cta-buttons">
          <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
          <Link to="/" className="btn-secondary">Volver al Inicio</Link>
        </div>
      </section>

    </div>
  );
}