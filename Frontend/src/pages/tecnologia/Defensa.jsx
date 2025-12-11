import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Defensa.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function Defensa() {
  
  // Hacemos que la página inicie desde arriba siempre
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    
      <div className="defensa-page">
        
        {/* 1. Hero Banner: Impacto visual inmediato */}
        <section className="defensa-hero">
          <div className="defensa-overlay">
            <span className="hero-badge">🛡️ Protección Inteligente</span>
            <h1>Defensa y Análisis de Cultivos</h1>
            <p>Anticípate a las amenazas. Detecta plagas y enfermedades antes de que afecten tu rentabilidad.</p>
          </div>
        </section>

        {/* 2. Contenido Principal: Explicación + Imagen */}
        <section className="defensa-content section-container">
          <div className="content-grid">
            
            <div className="text-column">
              <h2>Gestión Fitosanitaria de Precisión</h2>
              <p className="intro-text">
                En la agricultura moderna, reaccionar ya no es suficiente. Nuestro sistema de defensa utiliza 
                <strong> modelos predictivos e Inteligencia Artificial</strong> para analizar las condiciones climáticas 
                y biológicas que favorecen la aparición de hongos, malezas e insectos.
              </p>
              <p>
                Al monitorear parámetros en tiempo real, generamos <strong>mapas de riesgo</strong> que te indican 
                exactamente dónde y cuándo aplicar tratamientos, permitiéndote actuar de forma localizada y preventiva.
              </p>
              
              <div className="highlight-box">
                <h4><i className="fas fa-check-circle"></i> ¿Por qué es vital?</h4>
                <p>Una detección temprana puede salvar hasta el <strong>40% de la cosecha</strong> y reducir drásticamente el gasto en agroquímicos.</p>
              </div>
            </div>

            <div className="image-column">
              <img 
                src="https://cdn.wikifarmer.com/images/thumbnail/2023/04/agricultura-de-precision-tecnologias-1200x630.jpg" 
                alt="Ingeniero agrónomo analizando hoja con tablet" 
                className="main-img"
              />
              <div className="img-caption">Análisis de patógenos en tiempo real</div>
            </div>
          </div>
        </section>

        {/* 3. Características / Funcionalidades */}
        <section className="defensa-features">
          <div className="section-container">
            <h2>Herramientas de Defensa</h2>
            <div className="features-grid">
              
              <div className="feature-card">
                <div className="icon-wrapper"><i className="fas fa-chart-line"></i></div>
                <h3>Modelos Predictivos</h3>
                <p>Algoritmos que calculan la probabilidad de infección fúngica basándose en la humedad y temperatura.</p>
              </div>

              <div className="feature-card">
                <div className="icon-wrapper"><i className="fas fa-map-marked-alt"></i></div>
                <h3>Mapas de Riesgo</h3>
                <p>Visualiza en colores (rojo, amarillo, verde) las zonas de tu campo más vulnerables al ataque de plagas.</p>
              </div>

              <div className="feature-card">
                <div className="icon-wrapper"><i className="fas fa-bell"></i></div>
                <h3>Alertas Automáticas</h3>
                <p>Recibe notificaciones en tu móvil cuando se superen los umbrales de daño económico.</p>
              </div>

              <div className="feature-card">
                <div className="icon-wrapper"><i className="fas fa-leaf"></i></div>
                <h3>Reducción de Químicos</h3>
                <p>Aplica fitosanitarios solo donde es necesario (Spot Spraying), cuidando el medio ambiente y tu bolsillo.</p>
              </div>

            </div>
          </div>
        </section>

        {/* 4. CTA Final */}
        <section className="defensa-cta">
          <h2>Protege tu inversión hoy mismo</h2>
          <p>Deja de adivinar y empieza a gestionar la sanidad de tu campo con datos.</p>
          <div className="cta-buttons">
            <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
            <Link to="/" className="btn-secondary">Volver al Inicio</Link>
          </div>
        </section>

      </div>
    
  );
}