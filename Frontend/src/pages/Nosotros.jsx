// src/pages/Nosotros.jsx
import Layout from '../components/Layout';
import { Carousel } from 'react-bootstrap';
import '../styles/aboutSection.css';

export default function Nosotros() {
    return (
        <Layout>
            {/* Banner de presentación */}
            <section className="about-banner">
                <img
                    src="https://tyba.pe/wp-content/uploads/2023/11/invertir-en-el-sector-agropecuario.jpg"
                    alt="Banner institucional"
                    className="banner-image"
                />
                <div className="banner-overlay">
                    <h1 className="banner-title">Sobre Nosotros</h1>
                    <p className="banner-subtitle">Tecnología y compromiso para transformar el agro</p>
                </div>
            </section>

            {/* Historia */}
            <section className="about-section fade-in-up">
                <h2 className="section-heading">Nuestra Historia</h2>
                <div className="history-block">
                    <p>
                        <strong>BioCampo</strong> nace como una iniciativa para digitalizar el agro peruano. Desde nuestros inicios, hemos colaborado con productores, cooperativas y empresas para ofrecer soluciones tecnológicas que mejoren la <span className="highlight">productividad</span>, la <span className="highlight">sostenibilidad</span> y la <span className="highlight">trazabilidad</span> de los cultivos.
                    </p>
                    <p>
                        Nuestra trayectoria está marcada por la innovación, el compromiso con el campo y la búsqueda constante de herramientas que empoderen al agricultor moderno.
                    </p>
                </div>
            </section>

            {/* Misión, Visión y Valores */}
            <section className="about-section fade-in-up">
                <h2 className="section-heading">Misión, Visión y Valores</h2>
                <div className="values-grid">
                    <div className="value-card">
                        <h3>Misión</h3>
                        <p>Impulsar la eficiencia agrícola mediante herramientas digitales accesibles y sostenibles.</p>
                    </div>
                    <div className="value-card">
                        <h3>Visión</h3>
                        <p>Ser referentes en innovación agraria en Latinoamérica, conectando tecnología con tradición.</p>
                    </div>
                    <div className="value-card">
                        <h3>Valores</h3>
                        <ul>
                            <li>Transparencia</li>
                            <li>Sostenibilidad</li>
                            <li>Colaboración</li>
                            <li>Excelencia</li>
                        </ul>
                    </div>
                </div>
            </section>

            {/* Carrusel de plantíos */}
            <section className="about-section">
                <h2 className="section-heading">Nuestros Cultivos</h2>
                <Carousel className="team-carousel">
                    {[
                        {
                            img: 'https://cdn.wikifarmer.com/images/detailed/2024/06/Untitled-design-24.jpg',
                            caption: 'Cultivo de fresas en Huánuco'
                        },
                        {
                            img: 'https://themewagon.github.io/AgriCulture/assets/img/hero_2.jpg',
                            caption: 'Plantación de lechugas en Arequipa'
                        },
                        {
                            img: 'https://themewagon.github.io/AgriCulture/assets/img/hero_1.jpg',
                            caption: 'Monitoreo satelital en campos de papa'
                        }
                    ].map((item, i) => (
                        <Carousel.Item key={i}>
                            <img src={item.img} alt={item.caption} className="carousel-image" />
                            <Carousel.Caption>
                                <p>{item.caption}</p>
                            </Carousel.Caption>
                        </Carousel.Item>
                    ))}
                </Carousel>
            </section>

            {/* Cartas del equipo */}
            <section className="about-section fade-in-up">
                <h2 className="section-heading">Nuestro Equipo</h2>
                <div className="card-grid">
                    {[
                        {
                            img: 'https://media.istockphoto.com/id/861030754/es/foto/ingeniero-agr%C3%B3nomo-con-tableta-en-el-campo-de-soja.jpg?s=612x612&w=0&k=20&c=nCjxu6GLRKRqKw_X9iaUnNAMN_z5zfcSD8T6PsMF8iE=',
                            name: 'Ing. Mariana Torres',
                            role: 'Agrónoma Jefe',
                            desc: 'Especialista en cultivos tropicales y trazabilidad.'
                        },
                        {
                            img: 'https://blog.centrodeelearning.com/wp-content/uploads/2021/05/software-developer-working-with-computer-in-the-mo-XTJBZ62-scaled.jpg',
                            name: 'Carlos Rivas',
                            role: 'Desarrollador Fullstack',
                            desc: 'Encargado de la plataforma digital y experiencia del usuario.'
                        },
                        {
                            img: 'https://media.istockphoto.com/id/1464048910/es/foto/analista-de-datos-parado-en-una-oficina-y-mirando-a-la-c%C3%A1mara.jpg?s=612x612&w=0&k=20&c=mNYbG6k-323JJhdm02Bab1D9NFhxGSobujKo19qbOfg=',
                            name: 'Lucía Mendoza',
                            role: 'Analista de datos',
                            desc: 'Responsable de los modelos agronómicos y análisis predictivo.'
                        }
                    ].map((person, i) => (
                        <div key={i} className="team-card">
                            <img src={person.img} alt={person.name} className="team-photo" />
                            <h3 className="team-name">{person.name}</h3>
                            <p className="team-role">{person.role}</p>
                            <p className="team-desc">{person.desc}</p>
                        </div>
                    ))}
                </div>
            </section>

        </Layout>
    );
}
