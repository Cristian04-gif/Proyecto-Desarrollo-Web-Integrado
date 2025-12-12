import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/precision.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function Precision() {
  
  // Scroll al inicio al cargar la página
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="precision-page">
      
      {/* 1. Hero Banner: Enfoque en la dosis exacta */}
      <section className="precision-hero">
        <div className="precision-overlay">
          <span className="hero-badge">🧬 Tecnología VRA</span>
          <h1>Agricultura de Precisión</h1>
          <p>La dosis exacta, en el lugar correcto y en el momento oportuno. Maximiza cada metro cuadrado de tu campo.</p>
        </div>
      </section>

      {/* 2. Contenido Principal */}
      <section className="precision-content section-container">
        <div className="content-grid">
          
          <div className="text-column">
            <h2>Eficiencia grano por grano</h2>
            <p className="intro-text">
              Tratar a todo el lote por igual es cosa del pasado. La <strong>Agricultura de Precisión</strong> te permite gestionar la heterogeneidad de tu campo aplicando recomendaciones agronómicas específicas para cada zona.
            </p>
            <p>
              Mediante mapas de prescripción, conectamos con tu maquinaria para realizar <strong>Dosis Variable (VRA)</strong> de semillas, fertilizantes y fitosanitarios. El resultado: cultivos más uniformes, menor desperdicio y un cuidado ambiental superior.
            </p>
            
            <div className="highlight-box">
              <h4><i className="fas fa-seedling"></i> Sostenibilidad Rentable</h4>
              <p>Reduce tus costos de insumos hasta un <strong>15-20%</strong> mientras aumentas los rendimientos en las zonas de alto potencial.</p>
            </div>
          </div>

          <div className="image-column">
            <img 
              src="https://terrasat.com.mx/wp-content/uploads/2021/09/Terrasat-Fotos-Agricultura-de-Precisio%CC%81n-06.jpg" 
              alt="Maquinaria agrícola inteligente en acción" 
              className="main-img"
            />
            <div className="img-caption">Aplicación de dosis variable en tiempo real</div>
          </div>
        </div>
      </section>

      {/* 3. Características / Funcionalidades */}
      <section className="precision-features">
        <div className="section-container">
          <h2>El ciclo de la precisión</h2>
          <div className="features-grid">
            
            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-map"></i></div>
              <h3>Mapas de Prescripción</h3>
              <p>Archivos digitales compatibles con las principales marcas de monitores (John Deere, Trimble, etc.) para guiar a tu tractor.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-dna"></i></div>
              <h3>Siembra Variable</h3>
              <p>Ajusta la densidad de semillas según el potencial productivo de cada ambiente del suelo.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-flask"></i></div>
              <h3>Fertilización Inteligente</h3>
              <p>Aplica nitrógeno y fósforo solo donde el cultivo lo demanda, evitando la lixiviación y el gasto innecesario.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-chart-line"></i></div>
              <h3>Análisis de Retorno (ROI)</h3>
              <p>Evalúa al final de la campaña si la estrategia de precisión generó el beneficio económico esperado.</p>
            </div>

          </div>
        </div>
      </section>

      {/* 4. CTA Final */}
      <section className="precision-cta">
        <h2>Pasa al siguiente nivel productivo</h2>
        <p>Tu campo tiene más potencial del que imaginas. Descúbrelo con BioCampo.</p>
        <div className="cta-buttons">
          <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
          <Link to="/" className="btn-secondary">Volver al Inicio</Link>
        </div>
      </section>

    </div>
  );
}