import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Exploracion.css'; // Asegúrate de tener un archivo CSS para estilos específicos

export default function Exploracion() {
  
  // Scroll al inicio al cargar
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <div className="exploracion-page">
      
      {/* 1. Hero Banner: Enfoque en movilidad */}
      <section className="exploracion-hero">
        <div className="exploracion-overlay">
          <span className="hero-badge">📱 Campo Conectado</span>
          <h1>Exploración Digital de Cultivos</h1>
          <p>Lleva la oficina al campo. Registra, analiza y comparte información agronómica directamente desde tu bolsillo.</p>
        </div>
      </section>

      {/* 2. Contenido Principal */}
      <section className="exploracion-content section-container">
        <div className="content-grid">
          
          <div className="text-column">
            <h2>Tu cuaderno de campo, ahora inteligente</h2>
            <p className="intro-text">
              Olvídate del papel y el lápiz. Con la herramienta de <strong>Exploración de BioCampo</strong>, cada visita al terreno se convierte en datos valiosos. Desde tu móvil, puedes registrar incidencias, estado fenológico y conteos de plagas con precisión GPS.
            </p>
            <p>
              Esta tecnología facilita la <strong>trazabilidad completa</strong> del cultivo y mejora la comunicación entre técnicos y gerentes, ya que toda la información se sincroniza en la nube en tiempo real.
            </p>
            
            <div className="highlight-box">
              <h4><i className="fas fa-wifi"></i> Funciona Offline</h4>
              <p>¿No hay señal en el lote? No hay problema. La app guarda los datos y los sube automáticamente cuando recuperas la conexión.</p>
            </div>
          </div>

          <div className="image-column">
            <img 
              src="https://regaber.com/wp-content/uploads/2024/07/regaber_agricultura-digital-ai-01.jpg" 
              alt="Agricultor usando celular en el campo" 
              className="main-img"
            />
            <div className="img-caption">Georreferenciación de muestras en sitio</div>
          </div>
        </div>
      </section>

      {/* 3. Características / Funcionalidades */}
      <section className="exploracion-features">
        <div className="section-container">
          <h2>Herramientas de Scouting</h2>
          <div className="features-grid">
            
            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-map-marker-alt"></i></div>
              <h3>Geolocalización Exacta</h3>
              <p>Cada nota, foto o muestra queda registrada con coordenadas GPS precisas para volver al punto exacto.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-camera"></i></div>
              <h3>Evidencia Multimedia</h3>
              <p>Adjunta fotografías y videos de alta resolución a tus reportes para documentar visualmente el problema.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-microphone"></i></div>
              <h3>Notas de Voz</h3>
              <p>Ahorra tiempo escribiendo. Graba tus observaciones mientras caminas y el sistema las transcribe.</p>
            </div>

            <div className="feature-card">
              <div className="icon-wrapper"><i className="fas fa-file-pdf"></i></div>
              <h3>Reportes Instantáneos</h3>
              <p>Genera informes en PDF de tu visita con un solo clic y compártelos por WhatsApp o correo al instante.</p>
            </div>

          </div>
        </div>
      </section>

      {/* 4. CTA Final */}
      <section className="exploracion-cta">
        <h2>Digitaliza tu trabajo de campo</h2>
        <p>Prueba la herramienta que usan los agrónomos más eficientes.</p>
        <div className="cta-buttons">
          <Link to="/contacto" className="btn-primary">Solicitar Demo</Link>
          <Link to="/" className="btn-secondary">Volver al Inicio</Link>
        </div>
      </section>

    </div>
  );
}