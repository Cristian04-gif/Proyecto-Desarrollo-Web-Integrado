import React, { useState } from 'react';
import '../styles/contactos.css';

export default function Contacto() {
  const [formData, setFormData] = useState({
    nombre: '',
    email: '',
    asunto: '',
    mensaje: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    alert('Mensaje enviado con éxito (Simulación)');
    // Aquí iría la lógica para conectar con tu backend
  };

  return (
    <div className="page-container">
      {/* 1. Banner Header */}
      <div className="page-banner" style={{backgroundImage: 'url("https://images.unsplash.com/photo-1516251193000-18e6586ee186?auto=format&fit=crop&q=80&w=1600")'}}>
        <div className="banner-overlay">
          <h1>Contáctanos</h1>
          <p>Estamos aquí para ayudarte a crecer</p>
        </div>
      </div>

      <div className="section-container contact-wrapper">
        {/* 2. Columna Izquierda: Formulario */}
        <div className="contact-form-container">
          <h2>Envíanos un mensaje</h2>
          <p>¿Tienes dudas sobre nuestros servicios o cultivos? Escríbenos.</p>
          
          <form onSubmit={handleSubmit} className="contact-form">
            <div className="form-group">
              <label htmlFor="nombre">Nombre Completo</label>
              <input 
                type="text" 
                id="nombre" 
                name="nombre" 
                placeholder="Ej. Juan Pérez" 
                value={formData.nombre} 
                onChange={handleChange} 
                required 
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Correo Electrónico</label>
              <input 
                type="email" 
                id="email" 
                name="email" 
                placeholder="ejemplo@correo.com" 
                value={formData.email} 
                onChange={handleChange} 
                required 
              />
            </div>

            <div className="form-group">
              <label htmlFor="asunto">Asunto</label>
              <select 
                id="asunto" 
                name="asunto" 
                value={formData.asunto} 
                onChange={handleChange}
                required
              >
                <option value="">Selecciona un motivo</option>
                <option value="cotizacion">Solicitar Cotización</option>
                <option value="soporte">Soporte Técnico</option>
                <option value="informacion">Información General</option>
                <option value="distribucion">Ser Distribuidor</option>
              </select>
            </div>

            <div className="form-group">
              <label htmlFor="mensaje">Mensaje</label>
              <textarea 
                id="mensaje" 
                name="mensaje" 
                rows="5" 
                placeholder="Escribe tu mensaje aquí..." 
                value={formData.mensaje} 
                onChange={handleChange} 
                required 
              ></textarea>
            </div>

            <button type="submit" className="btn-submit">Enviar Mensaje</button>
          </form>
        </div>

        {/* 3. Columna Derecha: Info de Contacto y Redes */}
        <div className="contact-info-container">
          <div className="info-card">
            <h3>Información de Contacto</h3>
            <p>Ponte en contacto directo con nuestras oficinas centrales.</p>
            
            <div className="info-item">
              <i className="fas fa-map-marker-alt"></i>
              <div>
                <strong>Dirección:</strong>
                <p>Av. Alfredo Mendiola 6377, Los Olivos 15306<br/>Lima, Perú</p>
              </div>
            </div>

            <div className="info-item">
              <i className="fas fa-phone-alt"></i>
              <div>
                <strong>Teléfono:</strong>
                <p>+51 987 654 321</p>
              </div>
            </div>

            <div className="info-item">
              <i className="fas fa-envelope"></i>
              <div>
                <strong>Email:</strong>
                <p>contacto@biocampo.com</p>
              </div>
            </div>

            {/* Redes Sociales */}
            <div className="social-links-section">
              <h4>Síguenos en redes:</h4>
              <div className="social-icons-row">
                <a href="#" className="social-btn facebook"><i className="fab fa-facebook-f"></i></a>
                <a href="#" className="social-btn instagram"><i className="fab fa-instagram"></i></a>
                <a href="#" className="social-btn linkedin"><i className="fab fa-linkedin-in"></i></a>
                <a href="#" className="social-btn whatsapp"><i className="fab fa-whatsapp"></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* 4. Sección de Ubicación (Mapa) */}
      <section className="map-section">
        <h2 className="section-title">Nuestra Ubicación</h2>
        <div className="map-container">
          {/* Iframe de Google Maps apuntando a Los Olivos, Lima (Genérico) */}
          <iframe 
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3902.664683344654!2d-77.0620806856179!3d-11.961625043868846!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x9105ce3199859c25%3A0xe67750c371077366!2sAv.%20Alfredo%20Mendiola%206377%2C%20Los%20Olivos%2015306!5e0!3m2!1ses-419!2spe!4v1625687456321!5m2!1ses-419!2spe" 
            width="100%" 
            height="450" 
            style={{border:0}} 
            allowFullScreen="" 
            loading="lazy"
            title="Mapa de Ubicación"
          ></iframe>
        </div>
      </section>
    </div>
  );
}