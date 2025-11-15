import Layout from '../components/Layout';
import { useState, useEffect } from 'react';
import { getCultivos } from '../services/authService';
import '../styles/cultivosSection.css';

export default function Cultivos() {
    const [selectedType, setSelectedType] = useState('todos');
    const [openCrop, setOpenCrop] = useState(null);
    const [cultivos, setCultivos] = useState([]);

    useEffect(() => {
        getCultivos().then(data => setCultivos(data)).catch(() => setCultivos([]));
    }, []);

    const filtered = selectedType === 'todos'
        ? cultivos
        : cultivos.filter(c => (c.plant?.plantCategory?.name || c.type || 'otros').toLowerCase() === selectedType);

    return (
        <Layout>
            <div className="cultivos-container">
                {/* Hero Banner */}
                <section className="cultivos-hero">
                    <h1>Información de Cultivos</h1>
                    <p>Catálogo completo con detalles agrícolas y de mercado</p>
                </section>

                {/* Sección de Cultivos */}
                <section className="cultivos-content">
                    {/* Filtros */}
                    <div className="filtros-container">
                        <h2>Filtrar por tipo</h2>
                        <div className="filter-buttons">
                            {[
                                { key: 'todos', label: '🌾 Todos', icon: '🌾' },
                                { key: 'verduras', label: '🥬 Verduras', icon: '🥬' },
                                { key: 'frutas', label: '🍎 Frutas', icon: '🍎' },
                                { key: 'cereales', label: '🌽 Cereales', icon: '🌽' },
                                { key: 'legumbres', label: '🫘 Legumbres', icon: '🫘' },
                                { key: 'raices', label: '🥕 Raíces', icon: '🥕' }
                            ].map(btn => (
                                <button
                                    key={btn.key}
                                    className={`filter-btn ${selectedType === btn.key ? 'active' : ''}`}
                                    onClick={() => setSelectedType(btn.key)}
                                >
                                    {btn.label}
                                </button>
                            ))}
                        </div>
                    </div>

                    {/* Grid de Cultivos */}
                    {filtered.length === 0 ? (
                        <div className="empty-state">
                            <p>No hay cultivos para esta categoría.</p>
                        </div>
                    ) : (
                        <div className="cultivos-grid">
                            {filtered.map(crop => (
                                <div key={crop.cultivationId || crop.id} className="cultivo-card">
                                    <div className="card-image">
                                        <img src={crop.plant?.imageUrl || crop.image || 'https://via.placeholder.com/400x300'} alt={crop.plant?.name || crop.title || 'Cultivo'} />
                                    </div>
                                    <div className="card-content">
                                        <h3>{crop.plant?.name || crop.title || 'Sin nombre'}</h3>
                                        <p className="card-desc">Temporada: {crop.season || 'N/D'}</p>
                                        <button
                                            className="btn-ver-mas"
                                            onClick={() => setOpenCrop(crop)}
                                        >
                                            Ver Detalles
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </section>

                {/* Modal */}
                {openCrop && (
                    <div className="modal-overlay" onClick={() => setOpenCrop(null)}>
                        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                            <button
                                className="modal-close"
                                onClick={() => setOpenCrop(null)}
                                aria-label="Cerrar modal"
                            >
                                ✕
                            </button>
                            <img src={openCrop.plant?.imageUrl || openCrop.image || 'https://via.placeholder.com/600x400'} alt={openCrop.plant?.name || openCrop.title} className="modal-image" />
                            <div className="modal-body">
                                <h2>{openCrop.plant?.name || openCrop.title}</h2>
                                <p className="modal-desc">Temporada: {openCrop.season || 'N/D'}</p>

                                <div className="info-grid">
                                    <div className="info-item">
                                        <strong>Hectáreas:</strong>
                                        <p>{openCrop.hectares || 'N/D'}</p>
                                    </div>
                                    <div className="info-item">
                                        <strong>Fecha inicio:</strong>
                                        <p>{openCrop.startDate || 'N/D'}</p>
                                    </div>
                                    <div className="info-item">
                                        <strong>Fecha fin:</strong>
                                        <p>{openCrop.endDate || 'N/D'}</p>
                                    </div>
                                    <div className="info-item">
                                        <strong>Requerimientos:</strong>
                                        <p>{openCrop.requiredPackages || 'N/D'}</p>
                                    </div>
                                </div>

                                <button
                                    className="modal-close-btn"
                                    onClick={() => setOpenCrop(null)}
                                >
                                    Cerrar
                                </button>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </Layout>
    );
}
