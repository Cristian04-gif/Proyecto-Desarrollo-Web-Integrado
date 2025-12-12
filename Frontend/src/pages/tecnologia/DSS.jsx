import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/dss.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function DSS() {
  
  // Scroll al inicio al cargar la página
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="dss-page">
      
      {/* 1. Hero Banner: Enfoque en datos y tecnología */}
      <section className="dss-hero">
        <div className="dss-overlay">
          <span className="hero-badge">📊 Inteligencia Agrícola</span>
          <h1>Sistema de Soporte a Decisiones (DSS)</h1>
          <p>Deja de adivinar. Transforma datos complejos en acciones rentables para tu cultivo.</p>
        </div>
      </section>

      {/* 2. Contenido Principal */}
      <section className="dss-content section-container">
        <div className="content-grid">
          
          <div className="text-column">
            <h2>Decisiones basadas en datos, no en intuición</h2>
            <p className="intro-text">
              El DSS de BioCampo es el cerebro digital de tu operación. Integramos <strong>datos agronómicos, meteorológicos y económicos</strong> en una sola plataforma para responder a las preguntas críticas del día a día.
            </p>
            <p>
              Nuestros algoritmos procesan millones de variables para recomendarte el momento exacto de siembra, la dosis precisa de fertilización y la ventana óptima de cosecha, <strong>reduciendo la incertidumbre</strong> climática y de mercado.
            </p>
            
            <div className="highlight-box">
              <h4><i className="fas fa-chart-pie"></i> Impacto Real</h4>
              <p>Los agricultores que utilizan sistemas DSS aumentan su margen de beneficio promedio en un <strong>25%</strong> gracias a la optimización de recursos.</p>
            </div>
          </div>

          <div className="image-column">
            <img 
              src="https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&q=80&w=800" 
              alt="Análisis de datos agrícolas en tablet" 
              className="main-img"
            />
            <div className="img-caption">Tableros de control en tiempo real</div>
          </div>
        </div>
      </section>

      {/* 3. Características / Funcionalidades */}
      <section className="dss-features">
        <div className="section-container">
          <h2>¿Qué puedes resolver con nuestro DSS?</h2>
          <div className="features-grid">
            
            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-calendar-alt"></i></div>
              <h3>Planificación de Campaña</h3>
              <p>Simula escenarios de rendimiento basados en el clima histórico y elige la variedad de semilla perfecta.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-tint"></i></div>
              <h3>Riego y Nutrición</h3>
              <p>Calcula las necesidades hídricas y nutricionales exactas para evitar el desperdicio y el estrés de la planta.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-tractor"></i></div>
              <h3>Logística de Cosecha</h3>
              <p>Predice la maduración del fruto para organizar maquinaria y mano de obra sin tiempos muertos.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-file-invoice-dollar"></i></div>
              <h3>Análisis de Costos</h3>
              <p>Monitorea el margen bruto por hectárea en tiempo real y detecta desviaciones en el presupuesto.</p>
            </div>

          </div>
        </div>
      </section>

      {/* 4. CTA Final */}
      <section className="dss-cta">
        <h2>Toma el control total de tu campo</h2>
        <p>La tecnología para maximizar tu rentabilidad está al alcance de tu mano.</p>
        <div className="cta-buttons">
          <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
          <Link to="/" className="btn-secondary">Volver al Inicio</Link>
        </div>
      </section>

    </div>
  );
}