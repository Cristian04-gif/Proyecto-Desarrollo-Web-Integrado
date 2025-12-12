import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/seguimiento.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function Seguimiento() {
  
  // Scroll al inicio al cargar la página
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="seguimiento-page">
      
      {/* 1. Hero Banner: Vista orbital */}
      <section className="seguimiento-hero">
        <div className="seguimiento-overlay">
          <span className="hero-badge">🛰️ Monitoreo Remoto</span>
          <h1>Seguimiento Satelital de Cultivos</h1>
          <p>Tu campo visto desde el espacio. Detecta anomalías invisibles al ojo humano y reacciona a tiempo.</p>
        </div>
      </section>

      {/* 2. Contenido Principal */}
      <section className="seguimiento-content section-container">
        <div className="content-grid">
          
          <div className="text-column">
            <h2>El pulso de tu cultivo, actualizado cada 5 días</h2>
            <p className="intro-text">
              Monitoreamos el crecimiento de tus cultivos integrando <strong>imágenes satelitales de alta resolución</strong>. Esto nos permite calcular índices de vegetación que revelan la salud real de las plantas antes de que los síntomas sean visibles a simple vista.
            </p>
            <p>
              El seguimiento continuo crea una "película" de la evolución de tu campo, permitiéndote <strong>comparar campañas</strong>, identificar zonas de estrés hídrico o nutricional y dirigir a tus exploradores exactamente donde hay problemas.
            </p>
            
            <div className="highlight-box">
              <h4><i className="fas fa-eye"></i> Visión Espectral</h4>
              <p>Utilizamos índices como <strong>NDVI y NDRE</strong> para medir el vigor y la clorofila, detectando problemas semanas antes de perder rendimiento.</p>
            </div>
          </div>

          <div className="image-column">
            <img 
              src="https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&q=80&w=800" 
              alt="Vista satelital de la tierra y campos agrícolas" 
              className="main-img"
            />
            <div className="img-caption">Análisis multiespectral desde órbita</div>
          </div>
        </div>
      </section>

      {/* 3. Características / Funcionalidades */}
      <section className="seguimiento-features">
        <div className="section-container">
          <h2>Tecnología de Observación</h2>
          <div className="features-grid">
            
            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-layer-group"></i></div>
              <h3>Índices de Vigor (NDVI)</h3>
              <p>Mapas de colores que indican la biomasa y salud de la planta. Verde intenso significa cultivo sano; rojo indica problemas.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-tint-slash"></i></div>
              <h3>Estrés Hídrico</h3>
              <p>Detecta sectores donde el cultivo sufre por falta de agua mucho antes de que las hojas se marchiten visiblemente.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-chart-area"></i></div>
              <h3>Zonificación de Ambientes</h3>
              <p>Clasifica tu lote en zonas de alto, medio y bajo potencial productivo basándose en el historial de imágenes.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-clock"></i></div>
              <h3>Comparativa Histórica</h3>
              <p>Viaja en el tiempo y compara el desarrollo actual de tu cultivo con el mismo periodo de años anteriores.</p>
            </div>

          </div>
        </div>
      </section>

      {/* 4. CTA Final */}
      <section className="seguimiento-cta">
        <h2>Vigila tus hectáreas sin recorrerlas todas</h2>
        <p>Optimiza el tiempo de tu equipo dirigiendo las visitas a donde realmente importa.</p>
        <div className="cta-buttons">
          <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
          <Link to="/" className="btn-secondary">Volver al Inicio</Link>
        </div>
      </section>

    </div>
  );
}