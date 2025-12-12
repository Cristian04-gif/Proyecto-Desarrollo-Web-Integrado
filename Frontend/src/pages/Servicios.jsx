import React from 'react';
import '../styles/Servicios.css';

export default function Servicios() {
  const serviciosList = [
    { icon: "fa-tractor", title: "Maquinaria Inteligente", desc: "Alquiler y venta de maquinaria con GPS y automatización." },
    { icon: "fa-seedling", title: "Asesoría Agronómica", desc: "Consultoría experta para maximizar el rendimiento de tu suelo." },
    { icon: "fa-tint", title: "Sistemas de Riego", desc: "Instalación de riego por goteo y sistemas hidropónicos eficientes." },
    { icon: "fa-drone", title: "Monitoreo con Drones", desc: "Análisis espectral de cultivos para detección temprana de plagas." },
    { icon: "fa-flask", title: "Análisis de Suelos", desc: "Laboratorio certificado para análisis físico-químico de tierras." },
    { icon: "fa-truck", title: "Logística y Distribución", desc: "Red de transporte para llevar tus productos al mercado fresco." },
  ];

  return (
    <div className="page-container">
      {/* 1. Banner */}
      <div className="page-banner" style={{backgroundImage: 'url("https://images.unsplash.com/photo-1605000797499-95a51c5269ae?auto=format&fit=crop&q=80&w=1600")'}}>
        <div className="banner-overlay">
          <h1>Nuestros Servicios</h1>
          <p>Soluciones integrales para el agricultor moderno</p>
        </div>
      </div>

      {/* 2. Servicios que se ofrece */}
      <section className="section-container services-section">
        <div className="services-grid">
          {serviciosList.map((servicio, index) => (
            <div key={index} className="service-card">
              <div className="icon-box">
                <i className={`fas ${servicio.icon}`}></i>
              </div>
              <h3>{servicio.title}</h3>
              <p>{servicio.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* 3. Experiencia */}
      <section className="section-container experience-section">
        <div className="exp-content">
          <h2>Experiencia Comprobada</h2>
          <p>Con más de 15 años en el mercado, hemos transformado más de 50,000 hectáreas en terrenos productivos.</p>
          
          <div className="stats-grid">
            <div className="stat-item">
              <span className="number">15+</span>
              <span className="label">Años de experiencia</span>
            </div>
            <div className="stat-item">
              <span className="number">500+</span>
              <span className="label">Proyectos exitosos</span>
            </div>
            <div className="stat-item">
              <span className="number">100%</span>
              <span className="label">Clientes satisfechos</span>
            </div>
          </div>
        </div>
        <div className="exp-image">
           <img src="https://images.unsplash.com/photo-1530836369250-ef72a3f5cda8?auto=format&fit=crop&q=80&w=800" alt="Experiencia Agrícola" />
        </div>
      </section>
    </div>
  );
}