import Layout from '../components/Layout';
import { useState } from 'react';
import '../styles/cultivosSection.css';

export default function Cultivos() {
    const [selectedType, setSelectedType] = useState('todos');
    const [openCrop, setOpenCrop] = useState(null);

    const cultivos = [
        {
            id: 'fresa',
            type: 'frutas',
            title: 'Fresa',
            img: 'https://cdn.wikifarmer.com/images/detailed/2024/06/Untitled-design-24.jpg',
            desc: 'Cultivo delicado, ideal para climas templados.',
            extra: 'Siembra en bandejas o camas; requiere riego frecuente y buen drenaje.'
        },
        {
            id: 'lechuga',
            type: 'verduras',
            title: 'Lechuga',
            img: 'https://www.almanac.com/sites/default/files/styles/or/public/image_nodes/lettuce-growing-guide.jpg',
            desc: 'Ciclo corto, alta demanda comercial.',
            extra: 'Se adapta bien a cultivo protegido; rotación y control de plagas básicos.'
        },
        {
            id: 'frijol',
            type: 'legumbres',
            title: 'Frijol',
            img: 'https://www.wikihow.com/images/thumb/4/4b/Grow-Beans-Step-1-Version-2.jpg/v4-460px-Grow-Beans-Step-1-Version-2.jpg',
            desc: 'Resistente, requiere control de humedad.',
            extra: 'Buena opción para rotación; evita suelos compactados.'
        },

        /* Nuevos productos */
        {
            id: 'mango',
            type: 'frutas',
            title: 'Mango',
            img: 'https://images.unsplash.com/photo-1592928309163-7b3b5f3d6a5f',
            desc: 'Fruta tropical de alta demanda.',
            extra: 'Requiere climas cálidos; poda y control de plagas en etapas tempranas.'
        },
        {
            id: 'tomate',
            type: 'verduras',
            title: 'Tomate',
            img: 'https://images.unsplash.com/photo-1582281298059-2d5f6d1b5b6b',
            desc: 'Cultivo versátil para mercado fresco y procesado.',
            extra: 'Sensible a riego irregular; buen manejo de fertilización y soporte.'
        },
        {
            id: 'papa',
            type: 'verduras',
            title: 'Papa',
            img: 'https://images.unsplash.com/photo-1506806732259-39c2d0268443',
            desc: 'Tubérculo de ciclo medio, alto rendimiento.',
            extra: 'Necesita suelos sueltos y rotación para evitar nematodos y enfermedades.'
        },
        {
            id: 'cacao',
            type: 'frutas',
            title: 'Cacao',
            img: 'https://images.unsplash.com/photo-1582719478140-5f7d1d52c1b2',
            desc: 'Cultivo de sombra, rentable en zonas tropicales.',
            extra: 'Requiere manejo de sombra y control de enfermedades fúngicas.'
        },
        {
            id: 'cebolla',
            type: 'verduras',
            title: 'Cebolla',
            img: 'https://images.unsplash.com/photo-1588167106194-2c1d9a0b4f1a',
            desc: 'Ciclo variable, demanda estable en el mercado.',
            extra: 'Control de riego y cosecha en el punto óptimo para almacenaje.'
        },
        {
            id: 'zanahoria',
            type: 'verduras',
            title: 'Zanahoria',
            img: 'https://images.unsplash.com/photo-1535920527006-5c2f7d8427d8',
            desc: 'Hortaliza de raíz, exige suelos profundos y sueltos.',
            extra: 'Evitar suelos compactados; thinning para rendimiento uniforme.'
        },
        {
            id: 'pepino',
            type: 'verduras',
            title: 'Pepino',
            img: 'https://images.unsplash.com/photo-1601004890684-d8cbf643f5f2',
            desc: 'Rápido ciclo, buena rotación con otros cultivos.',
            extra: 'Soporte y manejo de humedad; evitar exceso de nitrógeno.'
        },
        {
            id: 'pimiento',
            type: 'verduras',
            title: 'Pimiento',
            img: 'https://images.unsplash.com/photo-1571689937011-0b8a1f8d4b7f',
            desc: 'Variedades para mercado fresco e industrial.',
            extra: 'Requiere clima templado a cálido y manejo de polinización.'
        }
    ];


    const filtered = selectedType === 'todos'
        ? cultivos
        : cultivos.filter(c => c.type === selectedType);

    return (
        <Layout>
            <section className="cultivos-banner">
                <div className="banner-text">
                    <h1>Información de Cultivos</h1>
                    <p>Variedades y recomendaciones básicas — presentación limpia y directa</p>
                </div>
            </section>

            <section className="cultivos-section">
                <div className="controls">
                    <div className="filter-buttons" role="tablist" aria-label="Filtrar por tipo">
                        {[
                            { key: 'todos', label: 'Todos' },
                            { key: 'frutas', label: 'Frutas' },
                            { key: 'verduras', label: 'Verduras' },
                            { key: 'legumbres', label: 'Legumbres' }
                        ].map(btn => (
                            <button
                                key={btn.key}
                                className={`filter-btn ${selectedType === btn.key ? 'active' : ''}`}
                                onClick={() => setSelectedType(btn.key)}
                                aria-pressed={selectedType === btn.key}
                            >
                                {btn.label}
                            </button>
                        ))}
                    </div>
                </div>

                {filtered.length === 0 ? (
                    <div className="empty-state">No hay cultivos para esta categoría.</div>
                ) : (
                    <div className="card-grid">
                        {filtered.map(crop => (
                            <article
                                key={crop.id}
                                className="crop-card fade-in-up"
                                tabIndex={0}
                                aria-labelledby={`crop-title-${crop.id}`}
                            >
                                <img src={crop.img} alt={crop.title} className="crop-image" />
                                <div className="card-body">
                                    <h3 id={`crop-title-${crop.id}`}>{crop.title}</h3>
                                    <p className="crop-desc">{crop.desc}</p>
                                </div>

                                <div className="card-actions">
                                    <button
                                        className="btn btn-primary"
                                        onClick={() => setOpenCrop(crop)}
                                        aria-label={`Ver más sobre ${crop.title}`}
                                    >
                                        Ver más
                                    </button>
                                </div>
                            </article>
                        ))}
                    </div>
                )}

                {openCrop && (
                    <div className="modal-backdrop" role="dialog" aria-modal="true">
                        <div className="modal-card">
                            <button className="modal-close" onClick={() => setOpenCrop(null)} aria-label="Cerrar">✕</button>
                            <img src={openCrop.img} alt={openCrop.title} className="modal-image" />
                            <h3>{openCrop.title}</h3>
                            <p className="modal-desc">{openCrop.extra}</p>
                            <div className="modal-actions">
                                <button className="btn btn-secondary" onClick={() => setOpenCrop(null)}>Cerrar</button>
                            </div>
                        </div>
                    </div>
                )}
            </section>
        </Layout>
    );
}
