import Layout from '../components/Layout';
import '../styles/servicesSection.css';

export default function Servicios() {
  const servicios = [
    {
      icon: '🗺️',
      titulo: 'Mapeo de Campo',
      descripcion: 'Digitalización geoespacial y trazabilidad de tus terrenos.',
      detalles: [
        'Mapas interactivos y detallados',
        'Geolocalización de parcelas',
        'Historial de cambios',
        'Integración con SIG'
      ]
    },
    {
      icon: '🛰️',
      titulo: 'Seguimiento Satelital',
      descripcion: 'Monitoreo continuo con imágenes satelitales de alta resolución.',
      detalles: [
        'Índice NDVI en tiempo real',
        'Detección de estrés hídrico',
        'Evolución fenológica',
        'Predicción de cosecha'
      ]
    },
    {
      icon: '🛡️',
      titulo: 'Defensa y Análisis',
      descripcion: 'Alertas tempranas de amenazas para tus cultivos.',
      detalles: [
        'Detección de plagas',
        'Identificación de enfermedades',
        'Control de malezas',
        'Análisis de riesgo'
      ]
    },
    {
      icon: '📊',
      titulo: 'Sistema DSS',
      descripcion: 'Soporte inteligente para tomar mejores decisiones.',
      detalles: [
        'Modelos predictivos',
        'Recomendaciones agronómicas',
        'Análisis de rentabilidad',
        'Optimización de recursos'
      ]
    },
    {
      icon: '📱',
      titulo: 'App de Exploración',
      descripcion: 'Registro y análisis de muestras desde tu móvil.',
      detalles: [
        'Geolocalización automática',
        'Fotografía y anotaciones',
        'Datos offline',
        'Sincronización en la nube'
      ]
    },
    {
      icon: '🌡️',
      titulo: 'Conexión de Sensores',
      descripcion: 'Integración con dispositivos para monitoreo continuo.',
      detalles: [
        'Sensores de clima',
        'Medidores de humedad',
        'Análisis de suelo',
        'Alertas automáticas'
      ]
    },
    {
      icon: '🧬',
      titulo: 'Agricultura de Precisión',
      descripcion: 'Aplicación variable de insumos basada en datos.',
      detalles: [
        'Mapas de prescripción',
        'Recomendaciones por zona',
        'Optimización de fertilizantes',
        'Reducción de costos'
      ]
    },
    {
      icon: '📈',
      titulo: 'Reportes y Analytics',
      descripcion: 'Análisis detallado del desempeño de tus operaciones.',
      detalles: [
        'Dashboards personalizados',
        'Métricas de rendimiento',
        'Comparativas temporales',
        'Exportación de datos'
      ]
    }
  ];

  const casos = [
    {
      empresa: 'Asociación de Agricultores del Valle',
      resultado: '35% aumento en productividad',
      descripcion: 'Implementamos mapeo y DSS para optimizar riego.',
      imagen: 'https://images.unsplash.com/photo-1574943320219-553eb213f72d?w=300&h=300&fit=crop'
    },
    {
      empresa: 'Empresa Agroexportadora XYZ',
      resultado: '25% reducción de pérdidas',
      descripcion: 'Defensa agrícola temprana redujo plagas significativamente.',
      imagen: 'https://images.unsplash.com/photo-1595433707802-6b2626ef1c91?w=300&h=300&fit=crop'
    },
    {
      empresa: 'Cooperativa Agrícola Regional',
      resultado: '40% mejora en eficiencia',
      descripcion: 'DSS y reportes optimizaron la toma de decisiones.',
      imagen: 'https://images.unsplash.com/photo-1559027615-cd4628902d4a?w=300&h=300&fit=crop'
    }
  ];

  return (
    <Layout>
      <div className="servicios-container">
        {/* Hero Banner */}
        <section className="hero-banner-servicios">
          <div className="hero-content-servicios">
            <h1>Nuestros Servicios</h1>
            <p>Soluciones tecnológicas integrales para la agricultura moderna</p>
          </div>
        </section>

        {/* Intro Section */}
        <section className="intro-servicios">
          <h2>Portafolio de Soluciones</h2>
          <p>
            En BioCampo, ofrecemos un conjunto completo de herramientas digitales diseñadas 
            para cada etapa de tus operaciones agrícolas. Desde la planificación hasta la comercialización, 
            contamos con soluciones que aumentan la productividad y garantizan sostenibilidad.
          </p>
        </section>

        {/* Servicios Grid */}
        <section className="servicios-grid-section">
          <div className="servicios-grid">
            {servicios.map((servicio, idx) => (
              <div key={idx} className="servicio-card">
                <div className="servicio-icon">{servicio.icon}</div>
                <h3>{servicio.titulo}</h3>
                <p className="servicio-desc">{servicio.descripcion}</p>
                <ul className="servicio-features">
                  {servicio.detalles.map((detalle, i) => (
                    <li key={i}>✓ {detalle}</li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
        </section>

        {/* Casos de Éxito */}
        <section className="casos-section">
          <h2>Casos de Éxito</h2>
          <p className="casos-intro">
            Nuestras soluciones han transformado operaciones en más de 50 unidades productivas, 
            generando resultados medibles en productividad y rentabilidad.
          </p>
          <div className="casos-grid">
            {casos.map((caso, idx) => (
              <div key={idx} className="caso-card">
                <img src={caso.imagen} alt={caso.empresa} />
                <div className="caso-content">
                  <h3>{caso.empresa}</h3>
                  <p className="caso-resultado">{caso.resultado}</p>
                  <p className="caso-desc">{caso.descripcion}</p>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* Ventajas Competitivas */}
        <section className="ventajas-section">
          <h2>¿Por qué BioCampo?</h2>
          <div className="ventajas-grid">
            <div className="ventaja-card">
              <div className="ventaja-icon">💡</div>
              <h3>Innovación Constante</h3>
              <p>Tecnología de punta adaptada a las necesidades del agro.</p>
            </div>
            <div className="ventaja-card">
              <div className="ventaja-icon">🤝</div>
              <h3>Soporte Especializado</h3>
              <p>Equipo de agrónomos y técnicos disponibles para asistirte.</p>
            </div>
            <div className="ventaja-card">
              <div className="ventaja-icon">🌍</div>
              <h3>Sostenibilidad</h3>
              <p>Herramientas que optimizan recursos y minimizan impacto ambiental.</p>
            </div>
            <div className="ventaja-card">
              <div className="ventaja-icon">📱</div>
              <h3>Fácil de Usar</h3>
              <p>Interfaces intuitivas accesibles desde cualquier dispositivo.</p>
            </div>
            <div className="ventaja-card">
              <div className="ventaja-icon">🔒</div>
              <h3>Seguridad de Datos</h3>
              <p>Tus datos están protegidos con encriptación de nivel empresarial.</p>
            </div>
            <div className="ventaja-card">
              <div className="ventaja-icon">📊</div>
              <h3>Análisis Profundo</h3>
              <p>Reportes detallados que te ayudan a entender tu negocio.</p>
            </div>
          </div>
        </section>

        {/* CTA Section */}
        <section className="cta-servicios">
          <h2>¿Listo para transformar tu agricultura?</h2>
          <p>Contáctanos hoy y descubre cómo BioCampo puede aumentar tu rentabilidad</p>
          <button className="cta-button">Solicitar Demostración</button>
        </section>
      </div>
    </Layout>
  );
}
