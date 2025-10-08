import { Carousel } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import '../styles/homeStyles.css';

export default function HomePage() {
  return (
    <>
      {/* Navbar */}
      <nav className="navbar">
        <link
          rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
        />

        <Link to="/" className="biocampo-logo">
          <div className="navbar-logo">
            <h1 className="logo-title">BioCampo</h1>
          </div>
        </Link>
        <div className="nav-links">
          <a href="#inicio">Inicio</a>
          <a href="#nosotros">Sobre Nosotros</a>
          <a href="#servicios">Servicios</a>
          <a href="#contacto">Contáctanos</a>
          <div className="auth-buttons">
            <Link to="/login" className="btn-login">Iniciar sesión</Link>
            <Link to="/register" className="btn-register">Registrarse</Link>
          </div>
        </div>
      </nav>

      <div className="home-container">

        {/* Carrusel */}
        <section id="inicio">
          <Carousel fade className="custom-carousel">
            <Carousel.Item>
              <img src="https://themewagon.github.io/AgriCulture/assets/img/hero_4.jpg" className="d-block w-100" alt="Cultivo 1" />
              <Carousel.Caption>
                <h3>Plantación Responsable</h3>
                <p>Optimiza tus cultivos desde la raíz con BioCampo.</p>
              </Carousel.Caption>
            </Carousel.Item>
            <Carousel.Item>
              <img src="https://themewagon.github.io/AgriCulture/assets/img/hero_2.jpg" className="d-block w-100" alt="Cultivo 2" />
              <Carousel.Caption>
                <h3>Riego Inteligente</h3>
                <p>Controla tus recursos y mejora la productividad.</p>
              </Carousel.Caption>
            </Carousel.Item>
            <Carousel.Item>
              <img src="https://themewagon.github.io/AgriCulture/assets/img/hero_1.jpg" className="d-block w-100" alt="Cultivo 3" />
              <Carousel.Caption>
                <h3>Venta Transparente</h3>
                <p>Conecta con clientes y lleva trazabilidad real.</p>
              </Carousel.Caption>
            </Carousel.Item>
          </Carousel>
        </section>

        {/* Tecnologías Innovadoras */}
        <section id="tecnologias" className="tech-section">
          <div className="tech-header">
            <h2 className="tech-title">Tecnologías innovadoras para las necesidades de la explotación</h2>
            <p className="tech-subtitle">Todo a tu disposición en una única plataforma</p>
          </div>
          <div className="tech-grid">
            {[
              { icon: '🗺️', title: 'Mapeo de campo', desc: 'Trazamos mapas y geolocalizamos toda la información.', link: 'https://example.com/mapeo' },
              { icon: '🛰️', title: 'Seguimiento', desc: 'Monitoreamos el crecimiento de cultivos con imágenes satelitales.', link: 'https://example.com/seguimiento' },
              { icon: '🛡️', title: 'Defensa y análisis', desc: 'Analizamos amenazas como hongos, malezas e insectos.', link: 'https://example.com/defensa' },
              { icon: '📊', title: 'DSS', desc: 'Tomamos decisiones óptimas con información integrada.', link: 'https://example.com/dss' },
              { icon: '📱', title: 'Exploración de cultivos', desc: 'Registramos y geolocalizamos muestras desde el móvil.', link: 'https://example.com/exploracion' },
              { icon: '🌡️', title: 'Conexión con sensores', desc: 'Conectamos sensores para monitorear parámetros ambientales.', link: 'https://example.com/sensores' },
              { icon: '🧬', title: 'Agricultura de precisión', desc: 'Recomendaciones agronómicas basadas en mapas variables.', link: 'https://example.com/precision' }
            ].map((tech, index) => (
              <div key={index} className="tech-card">
                <div className="tech-icon">{tech.icon}</div>
                <h3 className="tech-card-title">{tech.title}</h3>
                <p className="tech-card-desc">{tech.desc}</p>
                <a href={tech.link} target="_blank" rel="noopener noreferrer" className="tech-button">Ver más</a>
              </div>
            ))}
          </div>
        </section>

        {/* Herramientas y servicios digitales */}
        <section id="herramientas" className="tools-section">
          <h2 className="tools-title"> Herramientas y servicios digitales</h2>
          <div className="tools-grid">
            {[
              { label: 'Granja', image: 'https://www.fondsdedotationroullier.org/wp-content/uploads/2024/01/homme-prendre-soin-sien-ferme-gros-plan-scaled.jpg' },
              { label: 'Asociación Cooperativa', image: 'https://cdn.britannica.com/83/7083-050-EAE0CF87/wheat-farm-grain-belt-Saskatoon-Saskatchewan-Canada.jpg' },
              { label: 'Profesional', image: 'https://global.nmsu.edu/wp-content/uploads/sites/2/2025/01/Majors-to-Consider-for-a-Career-in-Ag-Food-and-Natural-Sciences-blog-800x534-1.webp' },
              { label: 'Empresa transformadora', image: 'https://eige.europa.eu/sites/default/files/styles/full/public/images/agriculture.jpg?itok=ydTFpCrV' },
              { label: 'Distribuidor', image: 'https://images.stockcake.com/public/8/c/7/8c7936bc-2068-4f27-8387-6b7e617c6b8c_large/agriculture-meets-industry-stockcake.jpg' }
            ].map((item, index) => (
              <div
                key={index}
                className="tool-card"
                style={{ backgroundImage: `url(${item.image})` }}
              >
                <div className="tool-overlay">
                  <h3 className="tool-label">{item.label}</h3>
                </div>
              </div>
            ))}
          </div>
        </section>

        <section className="testimonial-section">
          <div className="testimonial-grid">
            {/* Testimonio */}
            <div className="testimonial-text">
              <p className="quote">
                “Recomendamos <strong>Agricultor</strong> a todos los agricultores, agrónomos e instituciones gubernamentales, ya que permite una gestión eficiente de las operaciones de cultivo y los costos principales. Estas tecnologías serán la base de la agricultura del futuro.”
              </p>
              <div className="author-info">
                <img
                  src="https://elements-resized.envatousercontent.com/elements-video-cover-images/22e92b1a-2356-44bb-a3cb-1585c2f8b6f2/video_preview/video_preview_0000.jpg?w=500&cf_fit=cover&q=85&format=auto&s=16526e1cc3f273505631295b6f69ac271bd5609a15121426d966cb6e6f713368"
                  alt="Foto de Aurel Grozavciuc"
                  className="author-photo"
                />
                <p className="author">
                  <strong>Aurel Grozavciuc</strong><br />
                  CEO de EEA (Agencia de Desarrollo Regional) – Iacara (Acacia)
                </p>
              </div>
            </div>

            {/* Imagen del agricultor */}
            <div className="testimonial-image">
              <img
                src="https://cdn.wikifarmer.com/images/detailed/2024/06/Untitled-design-24.jpg"
                alt="Agricultor inspeccionando el campo"
              />
            </div>
          </div>
        </section>
      </div>

      {/* Beneficios de AGRICOLUS */}
      <section className="agricolus-benefits">
        <h2 className="benefits-title">Beneficios de la Agricultura de Precisión</h2>
        <div className="benefits-row">
          <div className="benefit-item">
            <i className="icon">🌾</i>
            <p>Aumentar el rendimiento y la calidad del producto final.</p>
          </div>
          <div className="benefit-item">
            <i className="icon">🪲</i>
            <p>Prevenir y monitorear plagas y enfermedades de los cultivos.</p>
          </div>
          <div className="benefit-item">
            <i className="icon">💧</i>
            <p>Reducir el uso de suministros como agua, tratamientos y fertilizantes.</p>
          </div>
          <div className="benefit-item">
            <i className="icon">📡</i>
            <p>Aplicar agricultura de precisión con apoyo agronómico especializado.</p>
          </div>
          <div className="benefit-item">
            <i className="icon">📊</i>
            <p>Recopilar datos de productos a lo largo de la cadena de suministro.</p>
          </div>
          <div className="benefit-item">
            <i className="icon">🌍</i>
            <p>Alcanzar objetivos de sostenibilidad económica y medioambiental.</p>
          </div>
        </div>
      </section>

      {/* Impacto en números */}
      <section className="impact-stats">
        <h2 className="stats-title">Los números importan</h2>
        <div className="stats-row">
          <div className="stat-item">
            <i className="icon">🛰️</i>
            <p><strong>2,304,000</strong><br />Imágenes satelitales al mes</p>
          </div>
          <div className="stat-item">
            <i className="icon">🌐</i>
            <p><strong>10</strong><br />Idiomas disponibles</p>
          </div>
          <div className="stat-item">
            <i className="icon">🌎</i>
            <p><strong>95</strong><br />Naciones alcanzadas</p>
          </div>
          <div className="stat-item">
            <i className="icon">👩‍🌾</i>
            <p><strong>5,394</strong><br />Profesionales formados</p>
          </div>
        </div>
      </section>


      {/* Institucional footer */}
      <section className="biocampo-footer">
        <div className="footer-header">
          <h2>MAKING AGRITECH SUSTAINABLE</h2>
        </div>

        <div className="footer-columns">
          <div className="footer-column">
            <h4>Investigación y desarrollo</h4>
            <ul>
              <li><a href="#">Ecosistema internacional</a></li>
              <li><a href="#">Proyectos de investigación</a></li>
            </ul>
          </div>

          <div className="footer-column">
            <h4>Trabaja con nosotros</h4>
            <ul>
              <li><a href="#">Programa de Distribuidores</a></li>
              <li><a href="#">Programa de Partner</a></li>
              <li><a href="#">Posiciones abiertas</a></li>
            </ul>
          </div>

          <div className="footer-column">
            <h4>Recursos</h4>
            <ul>
              <li><a href="#">Catálogo</a></li>
              <li><a href="#">Blog</a></li>
              <li><a href="#">Prensa</a></li>
              <li><a href="#">Eventos</a></li>
              <li><a href="#">Pruebas</a></li>
            </ul>
          </div>

          <div className="footer-column">
            <h4>Soporte</h4>
            <ul>
              <li><a href="#">Novedades</a></li>
              <li><a href="#">Soporte</a></li>
            </ul>
          </div>

          <div className="footer-column logo-column">
            <div className="biocampo-logo-circle">BIOCAMPO</div>
            <p>CERTIFICACIÓN DIGITAL PARTNER</p>
          </div>
        </div>
      </section>


      {/* newsletter */}
      <section className="newsletter-section">
        <h2>Newsletter</h2>
        <p>¿Quieres profundizar en el mundo de la agricultura de precisión?</p>

        <form className="newsletter-form">
          <div className="input-row">
            <input type="text" placeholder="Su nombre:" required />
            <input type="email" placeholder="Tu correo electrónico:" required />
          </div>

          <label className="privacy-check">
            <input type="checkbox" required />
            He leído y acepto la Política de Privacidad
          </label>

          <button type="submit">ENVIAR</button>

          <div className="footer-row">
            <div className="legal-links">
              <a href="#">Copyright</a>
              <a href="#">Política de Privacidad</a>
              <a href="#">Ley de Cookies</a>
            </div>
            <div className="social-icons">
              <a href="#"><i className="fab fa-facebook-f"></i></a>
              <a href="#"><i className="fab fa-twitter"></i></a>
              <a href="#"><i className="fab fa-youtube"></i></a>
              <a href="#"><i className="fab fa-linkedin-in"></i></a>
            </div>
          </div>
        </form>
      </section>




      {/* Footer */}
      <footer className="agriplus-footer">
        <div className="footer-contact">
          <div className="contact-line">
            <span><strong>Agriplus s.r.l.</strong></span>
            <span>Head Office: Salezze 620 - 45030 Ceneselli (RO) - Italy</span>
            <span>Tel: +39 0425 769355</span>
            <span>Email: donato@agriplus.com</span>
            <span>PEC: codfis.p.iva01166680295</span>
          </div>
        </div>

        <div className="footer-bottom">
          <p>© Copyright 1985 - 2023 Agriplus</p>
          <p>P.0.FESR 2014 - 2020 - Contattaci</p>
        </div>
      </footer>

    </>
  );
}




