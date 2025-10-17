// src/components/Footer.jsx
import React from 'react';

export default function Footer() {
  return (
    <>
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

      {/* Newsletter */}
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
            <span><strong>Biocampo S.A.C.</strong></span>
            <span>Sede Central: Av. Alfredo Mendiola 6377, Los Olivos 15306</span>
            <span>Tel: +51 987654321</span>
            <span>Email: Biocampo@gmail.com</span>
          </div>
        </div>

        <div className="footer-bottom">
          <p>© Copyright - 2025 Biocampo</p>
          <p>P.0.FESR - 2025 - Contato</p>
        </div>
      </footer>
    </>
  );
}
