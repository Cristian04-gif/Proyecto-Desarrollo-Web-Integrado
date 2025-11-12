// src/pages/Servicios.jsx
import Layout from '../components/Layout';
import '../styles/servicesSection.css';

export default function Servicios() {
  return (
    <Layout>
      {/* Banner institucional */}
      <section className="services-banner">
        <img
          src="https://blog.wearedrew.co/hubfs/DDA4324416166.jpg"
          alt="Banner de servicios"
          className="banner-image"
        />
        <div className="banner-overlay">
          <h1 className="banner-title">Soluciones Estratégicas para el Agro</h1>
          <p className="banner-subtitle">Tecnología aplicada a cada etapa del cultivo</p>
        </div>
      </section>

      {/* Servicios ofrecidos */}
      <section className="services-section fade-in-up">
        <h2 className="section-heading">Nuestros Servicios</h2>
        <p className="services-intro">
          En <strong>BioCampo</strong>, ofrecemos un portafolio de soluciones digitales diseñadas para optimizar la gestión agrícola, mejorar la toma de decisiones y garantizar la trazabilidad de los cultivos. Nuestro enfoque combina tecnología, agronomía y sostenibilidad.
        </p>

        <div className="card-grid">
          {[
            { icon: '🗺️', title: 'Mapeo de Campo', desc: 'Digitalización geoespacial para planificación agronómica.' },
            { icon: '🛰️', title: 'Seguimiento Satelital', desc: 'Monitoreo NDVI, estrés hídrico y evolución fenológica.' },
            { icon: '📱', title: 'Exploración Móvil', desc: 'Registro de muestras y observaciones desde dispositivos móviles.' },
            { icon: '🌡️', title: 'Sensores Ambientales', desc: 'Integración de estaciones para clima, humedad y suelo.' },
            { icon: '🛡️', title: 'Defensa Agrícola', desc: 'Alertas tempranas de plagas, enfermedades y malezas.' },
            { icon: '📊', title: 'DSS (Soporte a Decisiones)', desc: 'Modelos predictivos y recomendaciones agronómicas.' }
          ].map((s, i) => (
            <div key={i} className="process-card">
              <div className="card-icon">{s.icon}</div>
              <div className="card-title">{s.title}</div>
              <div className="card-desc">{s.desc}</div>
            </div>
          ))}
        </div>
      </section>

      {/* Experiencia institucional */}
      <section className="experience-section fade-in-up">
        <h2 className="section-heading">Experiencia y Resultados</h2>
        <p className="experience-text">
          Nuestro equipo ha implementado soluciones en más de <strong>50 unidades productivas</strong> en Perú, generando mejoras en rendimiento, eficiencia operativa y sostenibilidad. Trabajamos con productores, cooperativas y empresas agroexportadoras, adaptando nuestras herramientas a cada contexto.
        </p>
      </section>
    </Layout>
  );
}
