import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Sensores.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function Sensores() {
  
  // Scroll al inicio al cargar la página
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="sensores-page">
      
      {/* 1. Hero Banner: Conectividad y Datos */}
      <section className="sensores-hero">
        <div className="sensores-overlay">
          <span className="hero-badge">📡 IoT Agrícola</span>
          <h1>Conexión con Sensores</h1>
          <p>Escucha lo que tu cultivo tiene que decir. Monitoreo de clima y suelo en tiempo real, las 24 horas.</p>
        </div>
      </section>

      {/* 2. Contenido Principal */}
      <section className="sensores-content section-container">
        <div className="content-grid">
          
          <div className="text-column">
            <h2>Datos precisos, decisiones instantáneas</h2>
            <p className="intro-text">
              Ya no necesitas ir hasta el lote para saber si hace falta agua. Integramos una red de <strong>sensores ambientales IoT</strong> que miden humedad, temperatura, radiación solar y conductividad eléctrica directo desde el campo a tu celular.
            </p>
            <p>
              Esta información vital te permite automatizar el riego, recibir <strong>alertas de heladas</strong> al instante y entender el microclima exacto de tu finca, eliminando las suposiciones de tu gestión diaria.
            </p>
            
            <div className="highlight-box">
              <h4><i className="fas fa-wifi"></i> Conectividad Total</h4>
              <p>Nuestros equipos utilizan redes de bajo consumo (LoRaWAN / Sigfox) para transmitir datos incluso en zonas sin cobertura celular tradicional.</p>
            </div>
          </div>

          <div className="image-column">
            <img 
              src="https://images.unsplash.com/photo-1563986768609-322da13575f3?auto=format&fit=crop&q=80&w=800" 
              alt="Estación meteorológica en campo de cultivo" 
              className="main-img"
            />
            <div className="img-caption">Estación agrometeorológica autónoma</div>
          </div>
        </div>
      </section>

      {/* 3. Características / Funcionalidades */}
      <section className="sensores-features">
        <div className="section-container">
          <h2>Ecosistema de Sensores</h2>
          <div className="features-grid">
            
            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-cloud-sun-rain"></i></div>
              <h3>Estaciones Meteorológicas</h3>
              <p>Registra lluvia, viento y temperatura local para calcular la evapotranspiración real de tu cultivo.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-water"></i></div>
              <h3>Sondas de Humedad</h3>
              <p>Sensores capacitivos multinivel que te muestran cómo se mueve el agua en las raíces a diferentes profundidades.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-leaf"></i></div>
              <h3>Mojado de Hoja</h3>
              <p>Detecta la presencia de agua libre en la hoja para predecir con exactitud el riesgo de enfermedades fúngicas.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-bell"></i></div>
              <h3>Alertas Automatizadas</h3>
              <p>Configura umbrales críticos (ej. -1°C) y recibe notificaciones SMS o WhatsApp antes de que ocurra el daño.</p>
            </div>

          </div>
        </div>
      </section>

      {/* 4. CTA Final */}
      <section className="sensores-cta">
        <h2>Conecta tu campo al futuro</h2>
        <p>Empieza a ahorrar agua e insumos con tecnología de medición precisa.</p>
        <div className="cta-buttons">
          <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
          <Link to="/" className="btn-secondary">Volver al Inicio</Link>
        </div>
      </section>

    </div>
  );
}