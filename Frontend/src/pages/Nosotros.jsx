import { useState, useEffect } from 'react';
<<<<<<< HEAD
import { cultivationService, productService, userService, saleService } from '../services/authService';
=======
import { getCultivos, getProductos, getUsuarios, getVentas } from '../services/authService';
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
import Layout from '../components/Layout';
import '../styles/aboutSection.css';

export default function Nosotros() {
  const [currentTeamIndex, setCurrentTeamIndex] = useState(0);
<<<<<<< HEAD
  const [stats, setStats] = useState({ cultivos: 0, productos: 0, usuarios: 0, ventas: 0 });
  const [sampleCultivos, setSampleCultivos] = useState([]);

  // Equipo fijo (puedes ampliarlo con más miembros)
=======

  const plantios = [
    {
      id: 1,
      nombre: 'Tomate',
      imagen: 'https://images.unsplash.com/photo-1592924357615-1b475dcb0b2b?w=400&h=300&fit=crop',
      descripcion: 'Cultivo de tomate de alta calidad'
    },
    {
      id: 2,
      nombre: 'Lechuga',
      imagen: 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=400&h=300&fit=crop',
      descripcion: 'Lechuga fresca y orgánica'
    },
    {
      id: 3,
      nombre: 'Maíz',
      imagen: 'https://images.unsplash.com/photo-1542202229-7d93c4f5d07b?w=400&h=300&fit=crop',
      descripcion: 'Maíz de grano dorado'
    },
    {
      id: 4,
      nombre: 'Papas',
      imagen: 'https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?w=400&h=300&fit=crop',
      descripcion: 'Papas de primera calidad'
    },
    {
      id: 5,
      nombre: 'Cebolla',
      imagen: 'https://images.unsplash.com/photo-1599599810694-b3b81c547dcd?w=400&h=300&fit=crop',
      descripcion: 'Cebolla dulce y sabrosa'
    }
  ];

>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
  const equipo = [
    {
      nombre: 'Carlos Rodríguez',
      cargo: 'Director Ejecutivo',
      especialidad: 'Agricultura de Precisión',
      imagen: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300&h=300&fit=crop'
    },
    {
<<<<<<< HEAD
      nombre: 'María Fernández',
      cargo: 'Agrónoma',
      especialidad: 'Cultivos Orgánicos',
      imagen: 'https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?w=300&h=300&fit=crop'
    }
  ];

  useEffect(() => {
  Promise.allSettled([
    cultivationService.list(),
    productService.list(),
    userService.list(),
    saleService.list()
  ]).then(results => {
    const [rCult, rProd, rUser, rSale] = results;
    setStats({
      cultivos: rCult.status === 'fulfilled' ? (rCult.value?.length || 0) : 0,
      productos: rProd.status === 'fulfilled' ? (rProd.value?.length || 0) : 0,
      usuarios: rUser.status === 'fulfilled' ? (rUser.value?.length || 0) : 0,
      ventas: rSale.status === 'fulfilled' ? (rSale.value?.length || 0) : 0
    });
    if (rCult.status === 'fulfilled') setSampleCultivos(rCult.value.slice(0, 3));
  }).catch(() => {});
}, []);

const nextTeam = () => {
  setCurrentTeamIndex((prev) => (prev + 1) % Math.ceil(equipo.length / 2));
};

const prevTeam = () => {
  setCurrentTeamIndex((prev) => (prev - 1 + Math.ceil(equipo.length / 2)) % Math.ceil(equipo.length / 2));
};

const displayedTeam = equipo.slice(currentTeamIndex * 2, currentTeamIndex * 2 + 2);

return (
  <Layout>
    <div className="nosotros-container">
      {/* Hero Banner */}
      <section className="hero-banner">
        <div className="hero-content">
          <h1>Sobre BioCampo</h1>
          <p>Innovación y tecnología al servicio de la agricultura sostenible</p>
        </div>
      </section>

      {/* Historia */}
      <section className="historia-section">
        <div className="section-content">
          <h2>Nuestra Historia</h2>
          <p>
            BioCampo nace en 2015 con la visión de transformar la agricultura tradicional
            mediante la implementación de tecnologías inteligentes. Desde nuestros inicios,
            hemos trabajado con agricultores, cooperativas y empresas para desarrollar soluciones
            prácticas que aumentan la productividad y sostenibilidad.
          </p>
          <p>
            Hoy contamos con más de 10,000 usuarios activos y una red global de aliados
            que confían en nuestras plataformas para optimizar sus operaciones agrícolas.
          </p>
        </div>
        <div className="historia-image">
          <img
            src="https://images.unsplash.com/photo-1599599810694-b3b81c547dcd?w=500&h=400&fit=crop"
            alt="Historia de BioCampo"
          />
        </div>
      </section>

      {/* Misión, Visión y Valores */}
      <section className="mvv-section">
        <div className="mvv-grid">
          <div className="mvv-card">
            <div className="mvv-icon">🎯</div>
            <h3>Misión</h3>
            <p>
              Proporcionar herramientas tecnológicas innovadoras que permitan a los
              agricultores optimizar sus procesos, aumentar la rentabilidad y
              contribuir a la seguridad alimentaria global.
            </p>
          </div>
          <div className="mvv-card">
            <div className="mvv-icon">🌟</div>
            <h3>Visión</h3>
            <p>
              Ser la plataforma líder de agricultura digital en América Latina,
              generando un impacto positivo en la economía rural y la sostenibilidad
              ambiental.
            </p>
          </div>
          <div className="mvv-card">
            <div className="mvv-icon">💎</div>
            <h3>Valores</h3>
            <ul className="valores-list">
              <li>Innovación constante</li>
              <li>Sostenibilidad</li>
              <li>Transparencia</li>
              <li>Excelencia</li>
              <li>Compromiso social</li>
            </ul>
          </div>
        </div>
      </section>

      {/* Carrusel de Cultivos */}
      <section className="plantios-section">
        <h2>Cultivos que Manejamos</h2>
        <div className="plantios-carousel">
          <div className="plantios-grid">
            {sampleCultivos.length === 0 ? (
              <p>No hay cultivos cargados</p>
            ) : (
              sampleCultivos.map((c) => (
                <div key={c.cultivationId || c.id} className="plantio-card">
                  <img src={c.plant?.imageUrl || 'https://via.placeholder.com/300'} alt={c.plant?.name || 'Cultivo'} />
                  <h3>{c.plant?.name || 'Sin nombre'}</h3>
                  <p>Temporada: {c.season || 'N/D'}</p>
                </div>
              ))
            )}
          </div>
        </div>
      </section>

      {/* Equipo */}
      <section className="equipo-section">
        <h2>Nuestro Equipo</h2>
        <p className="equipo-subtitle">Profesionales especializados comprometidos con la excelencia</p>

        <div className="equipo-carousel">
          <button className="carousel-btn prev" onClick={prevTeam}>❮</button>

          <div className="equipo-grid">
            {displayedTeam.map((miembro, idx) => (
              <div key={idx} className="equipo-card">
                <div className="equipo-image-wrapper">
                  <img src={miembro.imagen} alt={miembro.nombre} />
                </div>
                <div className="equipo-info">
                  <h3>{miembro.nombre}</h3>
                  <p className="cargo">{miembro.cargo}</p>
                  <p className="especialidad">Especialidad: {miembro.especialidad}</p>
                </div>
              </div>
            ))}
          </div>

          <button className="carousel-btn next" onClick={nextTeam}>❯</button>
        </div>

        <div className="carousel-indicators">
          {Array.from({ length: Math.ceil(equipo.length / 2) }).map((_, idx) => (
            <div
              key={idx}
              className={`indicator ${idx === currentTeamIndex ? 'active' : ''}`}
              onClick={() => setCurrentTeamIndex(idx)}
            />
          ))}
        </div>
      </section>

      {/* Estadísticas */}
      <section className="estadisticas-section">
        <h2>Nuestro Impacto</h2>
        <div className="stats-grid">
          <div className="stat-card">
            <h3>{stats.usuarios}</h3>
            <p>Usuarios</p>
          </div>
          <div className="stat-card">
            <h3>{stats.cultivos}</h3>
            <p>Cultivos</p>
          </div>
          <div className="stat-card">
            <h3>{stats.productos}</h3>
            <p>Productos</p>
          </div>
          <div className="stat-card">
            <h3>{stats.ventas}</h3>
            <p>Ventas</p>
          </div>
        </div>
      </section>

      {/* Testimonios */}
      <section className="testimonios-section">
        <h2>Lo que Dicen Nuestros Clientes</h2>
        <div className="testimonios-grid">
          <div className="testimonio-card">
            <p className="testimonio-text">
              "BioCampo transformó completamente la manera en que gestiono mis cultivos.
              Aumenté la productividad en un 40% y reduje costos significativamente."
            </p>
            <p className="testimonio-autor">- José García, Agricultor</p>
          </div>
          <div className="testimonio-card">
            <p className="testimonio-text">
              "La plataforma es intuitiva y las herramientas de análisis son muy potentes.
              Recomiendo BioCampo a todos mis colegas."
            </p>
            <p className="testimonio-autor">- María Fernández, Agrónoma</p>
          </div>
          <div className="testimonio-card">
            <p className="testimonio-text">
              "Excelente soporte técnico y actualizaciones constantes. Nos encanta
              trabajar con el equipo de BioCampo."
            </p>
            <p className="testimonio-autor">- Roberto López, Cooperativa</p>
          </div>
        </div>
      </section>
    </div>
  </Layout>
);
}
=======
      nombre: 'María González',
      cargo: 'Directora de Tecnología',
      especialidad: 'Sistemas IoT',
      imagen: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=300&h=300&fit=crop'
    },
    {
      nombre: 'Juan Martínez',
      cargo: 'Agrónomo Principal',
      especialidad: 'Sostenibilidad',
      imagen: 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=300&h=300&fit=crop'
    },
    {
      nombre: 'Ana López',
      cargo: 'Especialista en Datos',
      especialidad: 'Big Data',
      imagen: 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=300&h=300&fit=crop'
    }
  ];

  const [stats, setStats] = useState({cultivos:0, productos:0, usuarios:0, ventas:0});
  const [sampleCultivos, setSampleCultivos] = useState([]);

  useEffect(() => {
    Promise.allSettled([getCultivos(), getProductos(), getUsuarios(), getVentas()]).then(results => {
      const [rCult, rProd, rUser, rSale] = results;
      setStats({
        cultivos: rCult.status === 'fulfilled' ? (rCult.value?.length || 0) : 0,
        productos: rProd.status === 'fulfilled' ? (rProd.value?.length || 0) : 0,
        usuarios: rUser.status === 'fulfilled' ? (rUser.value?.length || 0) : 0,
        ventas: rSale.status === 'fulfilled' ? (rSale.value?.length || 0) : 0
      });
      if (rCult.status === 'fulfilled') setSampleCultivos(rCult.value.slice(0,3));
    }).catch(()=>{});
  }, []);

  const nextTeam = () => {
    setCurrentTeamIndex((prev) => (prev + 1) % Math.ceil(equipo.length / 2));
  };

  const prevTeam = () => {
    setCurrentTeamIndex((prev) => (prev - 1 + Math.ceil(equipo.length / 2)) % Math.ceil(equipo.length / 2));
  };

  const displayedTeam = equipo.slice(currentTeamIndex * 2, currentTeamIndex * 2 + 2);

  return (
    <Layout>
      <div className="nosotros-container">
        {/* Hero Banner */}
        <section className="hero-banner">
          <div className="hero-content">
            <h1>Sobre BioCampo</h1>
            <p>Innovación y tecnología al servicio de la agricultura sostenible</p>
          </div>
        </section>

        {/* Historia */}
        <section className="historia-section">
          <div className="section-content">
            <h2>Nuestra Historia</h2>
            <p>
              BioCampo nace en 2015 con la visión de transformar la agricultura tradicional 
              mediante la implementación de tecnologías inteligentes. Desde nuestros inicios, 
              hemos trabajado con agricultores, cooperativas y empresas para desarrollar soluciones 
              prácticas que aumentan la productividad y sostenibilidad.
            </p>
            <p>
              Hoy contamos con más de 10,000 usuarios activos y una red global de aliados 
              que confían en nuestras plataformas para optimizar sus operaciones agrícolas.
            </p>
          </div>
          <div className="historia-image">
            <img 
              src="https://images.unsplash.com/photo-1599599810694-b3b81c547dcd?w=500&h=400&fit=crop" 
              alt="Historia de BioCampo"
            />
          </div>
        </section>

        {/* Misión, Visión y Valores */}
        <section className="mvv-section">
          <div className="mvv-grid">
            {/* Misión */}
            <div className="mvv-card">
              <div className="mvv-icon">🎯</div>
              <h3>Misión</h3>
              <p>
                Proporcionar herramientas tecnológicas innovadoras que permitan a los 
                agricultores optimizar sus procesos, aumentar la rentabilidad y 
                contribuir a la seguridad alimentaria global.
              </p>
            </div>

            {/* Visión */}
            <div className="mvv-card">
              <div className="mvv-icon">🌟</div>
              <h3>Visión</h3>
              <p>
                Ser la plataforma líder de agricultura digital en América Latina, 
                generando un impacto positivo en la economía rural y la sostenibilidad 
                ambiental.
              </p>
            </div>

            {/* Valores */}
            <div className="mvv-card">
              <div className="mvv-icon">💎</div>
              <h3>Valores</h3>
              <ul className="valores-list">
                <li>Innovación constante</li>
                <li>Sostenibilidad</li>
                <li>Transparencia</li>
                <li>Excelencia</li>
                <li>Compromiso social</li>
              </ul>
            </div>
          </div>
        </section>

        {/* Carrusel de Plantios */}
        <section className="plantios-section">
          <h2>Cultivos que Manejamos</h2>
          <div className="plantios-carousel">
            <div className="plantios-grid">
              {sampleCultivos.length === 0 ? (
                <p>No hay cultivos cargados</p>
              ) : (
                sampleCultivos.map((c) => (
                  <div key={c.cultivationId || c.id} className="plantio-card">
                    <img src={c.plant?.imageUrl || 'https://via.placeholder.com/300'} alt={c.plant?.name || 'Cultivo'} />
                    <h3>{c.plant?.name || 'Sin nombre'}</h3>
                    <p>Temporada: {c.season || 'N/D'}</p>
                  </div>
                ))
              )}
            </div>
          </div>
        </section>

        {/* Cartas del Equipo */}
        <section className="equipo-section">
          <h2>Nuestro Equipo</h2>
          <p className="equipo-subtitle">Profesionales especializados comprometidos con la excelencia</p>
          
          <div className="equipo-carousel">
            <button className="carousel-btn prev" onClick={prevTeam}>❮</button>
            
            <div className="equipo-grid">
              {displayedTeam.map((miembro, idx) => (
                <div key={idx} className="equipo-card">
                  <div className="equipo-image-wrapper">
                    <img src={miembro.imagen} alt={miembro.nombre} />
                  </div>
                  <div className="equipo-info">
                    <h3>{miembro.nombre}</h3>
                    <p className="cargo">{miembro.cargo}</p>
                    <p className="especialidad">Especialidad: {miembro.especialidad}</p>
                  </div>
                </div>
              ))}
            </div>

            <button className="carousel-btn next" onClick={nextTeam}>❯</button>
          </div>

          {/* Indicadores de página */}
          <div className="carousel-indicators">
            {Array.from({ length: Math.ceil(equipo.length / 2) }).map((_, idx) => (
              <div
                key={idx}
                className={`indicator ${idx === currentTeamIndex ? 'active' : ''}`}
                onClick={() => setCurrentTeamIndex(idx)}
              />
            ))}
          </div>
        </section>

        {/* Estadísticas */}
        <section className="estadisticas-section">
          <h2>Nuestro Impacto</h2>
          <div className="stats-grid">
            <div className="stat-card">
              <h3>{stats.usuarios}</h3>
              <p>Usuarios</p>
            </div>
            <div className="stat-card">
              <h3>{stats.cultivos}</h3>
              <p>Cultivos</p>
            </div>
            <div className="stat-card">
              <h3>{stats.productos}</h3>
              <p>Productos</p>
            </div>
            <div className="stat-card">
              <h3>{stats.ventas}</h3>
              <p>Ventas</p>
            </div>
          </div>
        </section>

        {/* Testimonios */}
        <section className="testimonios-section">
          <h2>Lo que Dicen Nuestros Clientes</h2>
          <div className="testimonios-grid">
            <div className="testimonio-card">
              <p className="testimonio-text">
                "BioCampo transformó completamente la manera en que gestiono mis cultivos. 
                Aumenté la productividad en un 40% y reduje costos significativamente."
              </p>
              <p className="testimonio-autor">- José García, Agricultor</p>
            </div>
            <div className="testimonio-card">
              <p className="testimonio-text">
                "La plataforma es intuitiva y las herramientas de análisis son muy potentes. 
                Recomiendo BioCampo a todos mis colegas."
              </p>
              <p className="testimonio-autor">- María Fernández, Agrónoma</p>
            </div>
            <div className="testimonio-card">
              <p className="testimonio-text">
                "Excelente soporte técnico y actualizaciones constantes. Nos encanta 
                trabajar con el equipo de BioCampo."
              </p>
              <p className="testimonio-autor">- Roberto López, Cooperativa</p>
            </div>
          </div>
        </section>
      </div>
    </Layout>
  );
}
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
