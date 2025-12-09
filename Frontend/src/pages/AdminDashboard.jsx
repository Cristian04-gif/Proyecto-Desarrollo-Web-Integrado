import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCultivos, crearCultivo, eliminarCultivo } from '../services/cultivations';
import { getPlants, createPlant, deletePlant } from '../services/plants';
import { getCategories, createCategory, deleteCategory } from '../services/plantCategories';
import { getHarvests, createHarvest, deleteHarvest } from '../services/harvests';
import { getPostHarvests, createPostHarvest, deletePostHarvest } from '../services/postHarvests';
import '../styles/adminStyles.css';

// 1. Vista de Inicio
const DashboardHome = () => (
    <div className="admin-card">
        <h2>Panel General BioCampo</h2>
        <p>Selecciona un módulo del menú lateral para comenzar a gestionar.</p>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '20px', marginTop: '20px' }}>
            <div style={{ padding: '20px', background: '#E8F3EE', borderRadius: '8px', borderLeft: '4px solid #2F4842' }}>
                <h3>Pedidos Pendientes</h3>
                <p style={{ fontSize: '24px', fontWeight: 'bold' }}>--</p>
            </div>
            <div style={{ padding: '20px', background: '#E8F3EE', borderRadius: '8px', borderLeft: '4px solid #6B9080' }}>
                <h3>Cultivos Activos</h3>
                <p style={{ fontSize: '24px', fontWeight: 'bold' }}>--</p>
            </div>
            <div style={{ padding: '20px', background: '#E8F3EE', borderRadius: '8px', borderLeft: '4px solid #C5A880' }}>
                <h3>Alertas de Stock</h3>
                <p style={{ fontSize: '24px', fontWeight: 'bold' }}>--</p>
            </div>
        </div>
    </div>
);

// 2. Componentes Genéricos de Sección (Luego los llenaremos con lógica real)
const SectionHeader = ({ title, subtitle, action }) => (
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
            <h2 style={{ color: '#2F4842', margin: 0 }}>{title}</h2>
            <p style={{ color: '#666', margin: '5px 0 0 0' }}>{subtitle}</p>
        </div>
        <button className="btn-primary">{action}</button>
    </div>
);

// --- Módulos de Administración ---
const UsersView = () => (
    <div className="admin-card">
        <SectionHeader title="Usuarios del Sistema" subtitle="Gestiona accesos y roles (Admin/Cliente)" action="+ Nuevo Usuario" />
        <p>Conectará con: <code>users.js</code></p>
    </div>
);

// --- Módulos de Personas ---
const CustomersView = () => (
    <div className="admin-card">
        <SectionHeader title="Cartera de Clientes" subtitle="Directorio de clientes registrados" action="+ Registrar Cliente" />
        <p>Conectará con: <code>customers.js</code></p>
    </div>
);
const EmployeesView = () => (
    <div className="admin-card">
        <SectionHeader title="Personal y Empleados" subtitle="Gestión de RRHH y Cargos" action="+ Nuevo Empleado" />
        <p>Conectará con: <code>employees.js</code> y <code>jobPositions.js</code></p>
    </div>
);
const SuppliersView = () => (
    <div className="admin-card">
        <SectionHeader title="Proveedores" subtitle="Gestión de la cadena de suministro" action="+ Nuevo Proveedor" />
        <p>Conectará con: <code>suppliers.js</code></p>
    </div>
);

// --- Módulos Comerciales ---
const ProductsView = () => (
    <div className="admin-card">
        <SectionHeader title="Catálogo de Productos" subtitle="Insumos y servicios disponibles para venta" action="+ Nuevo Producto" />
        <p>Conectará con: <code>products.js</code></p>
    </div>
);
const OrdersView = () => (
    <div className="admin-card">
        <SectionHeader title="Gestión de Pedidos" subtitle="Seguimiento de compras y estados" action="Ver Reportes" />
        <p>Conectará con: <code>orders.js</code> y <code>orderDetails.js</code></p>
    </div>
);
// --- COMPONENTE CRUD DE CATEGORÍAS (Modelo Exacto) ---
const CategoriesCrud = () => {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(false);
    const [showModal, setShowModal] = useState(false);

    // Estado solo con el campo que pide tu Java
    const [categoryName, setCategoryName] = useState('');

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        setLoading(true);
        try {
            const data = await getCategories();
            setCategories(data);
        } catch (e) {
            console.error(e);
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Tu modelo Java espera: { categoryName:String }
            const payload = {
                categoryName: categoryName
            };

            await createCategory(payload);

            setShowModal(false);
            setCategoryName(''); // Limpiar
            loadData(); // Recargar tabla
            alert("Categoría creada exitosamente.");
        } catch (e) {
            alert("Error: " + e.message);
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("¿Seguro que deseas eliminar esta categoría?")) return;
        try {
            await deleteCategory(id);
            // Filtramos usando categoryId que es como se llama en tu Java
            setCategories(prev => prev.filter(c => c.categoryId !== id));
            alert("Categoría eliminada.");
        } catch (e) {
            alert("Error: " + e.message);
        }
    };

    return (
        <div className="admin-card">
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
                <div>
                    <h2 style={{ color: '#2F4842', margin: 0 }}>Categorías de Plantas</h2>
                    <p style={{ color: '#666', margin: 0 }}>Clasificación para tus tipos de cultivos.</p>
                </div>
                <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nueva Categoría</button>
            </div>

            {loading ? <p>Cargando...</p> : (
                <div className="table-container">
                    <table className="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre Categoría</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {categories.length > 0 ? categories.map(c => (
                                <tr key={c.categoryId}>
                                    <td>{c.categoryId}</td>
                                    <td><strong>{c.categoryName}</strong></td>
                                    <td>
                                        <button className="btn-delete" onClick={() => handleDelete(c.categoryId)}>Eliminar</button>
                                    </td>
                                </tr>
                            )) : (
                                <tr><td colSpan="3" style={{ textAlign: 'center', padding: '20px' }}>No hay categorías registradas.</td></tr>
                            )}
                        </tbody>
                    </table>
                </div>
            )}

            {/* MODAL */}
            {showModal && (
                <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
                    <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '400px' }}>
                        <h3 style={{ color: '#2F4842' }}>Nueva Categoría</h3>
                        <form onSubmit={handleSubmit}>

                            <label style={{ display: 'block', fontWeight: 'bold', marginTop: '10px' }}>Nombre de la Categoría</label>
                            <input
                                className="admin-input"
                                required
                                placeholder="Ej. Hortalizas"
                                value={categoryName}
                                onChange={e => setCategoryName(e.target.value)}
                            />

                            <div style={{ textAlign: 'right', marginTop: '20px' }}>
                                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px', padding: '8px', background: 'transparent', border: '1px solid #ccc', borderRadius: '4px', cursor: 'pointer' }}>Cancelar</button>
                                <button type="submit" className="btn-primary">Guardar</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};
// --- COMPONENTE CRUD DE CULTIVOS (CON COSTO AGREGADO) ---
// --- COMPONENTE CRUD DE CULTIVOS (COMPLETO Y CORREGIDO) ---
const CultivationsCrud = () => {
  const [listaCultivos, setListaCultivos] = useState([]);
  const [listaPlantas, setListaPlantas] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showModal, setShowModal] = useState(false);

  // Formulario con todos los campos necesarios
  const [formData, setFormData] = useState({
    name: '',       
    season: '',     
    area: '',       
    plantId: '',
    eachIrrigation: '',    
    startDate: '',         
    endDate: '',
    cost: ''               
  });

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    setLoading(true);
    try {
      const [cultivosData, plantasData] = await Promise.all([
        getCultivos(),
        getPlants()
      ]);
      setListaCultivos(cultivosData);
      setListaPlantas(plantasData); 
    } catch (err) {
      setError('Error al cargar datos. Verifica el backend.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Estás seguro de eliminar este cultivo?")) return;
    try {
      await eliminarCultivo(id);
      setListaCultivos(prev => prev.filter(c => c.cultivationId !== id));
      alert("Cultivo eliminado.");
    } catch (err) {
      alert("Error al eliminar: " + err.message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.plantId) { alert("Selecciona una planta."); return; }
    if (!formData.season) { alert("Selecciona una temporada."); return; }

    try {
      // Construcción del objeto exacto para Java
      const payload = {
        cultivation: {
          plotName: formData.name,
          season: formData.season,
          hectares: Number(formData.area) || 0.0,
          plant: { plantId: parseInt(formData.plantId) }, 
          
          eachIrrigation: parseInt(formData.eachIrrigation) || 0,
          startDate: formData.startDate || new Date().toISOString().split('T')[0],
          endDate: formData.endDate || null,
          cost: parseFloat(formData.cost) || 0.0,
          
          requiredPackages: 0.0, 
          employees: [],
          insumos: [] 
        },
        inputCultivation: [], 
        employees: []
      };

      console.log("Enviando Payload:", payload);

      await crearCultivo(payload);
      
      setShowModal(false);
      // Limpiar formulario
      setFormData({ 
        name: '', season: '', area: '', plantId: '', 
        eachIrrigation: '', startDate: '', endDate: '', cost: '' 
      });
      cargarDatos(); 
      alert("¡Cultivo registrado correctamente!");
    } catch (err) {
      console.error(err);
      alert("Error del Servidor. Revisa la consola de Java.");
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{color: '#2F4842', margin: 0}}>Gestión de Cultivos</h2>
          <p style={{color: '#666', margin: 0}}>Administra tus parcelas, siembras y costos.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Cultivo</button>
      </div>

      {error && <p style={{color: 'red'}}>{error}</p>}

      {loading ? <p>Cargando datos...</p> : (
        <div className="table-container">
          {/* ✅ AQUÍ ESTÁ LA CORRECCIÓN DE LA TABLA (Sin espacios extraños) */}
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Parcela / Planta</th>
                <th>Temp.</th>
                <th>Hectáreas</th>
                <th>Costo Ini.</th>
                <th>Cosecha Est.</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {listaCultivos.map(c => (
                <tr key={c.cultivationId}>
                  <td>{c.cultivationId}</td>
                  <td>
                    <strong>{c.plotName}</strong><br/>
                    <small style={{color:'#555'}}>{c.plant ? c.plant.name : 'Sin Planta'}</small>
                  </td>
                  <td>{c.season}</td>
                  <td style={{textAlign:'center'}}>{c.hectares}</td>
                  <td style={{color:'#2F4842', fontWeight:'bold'}}>${c.costo || c.cost || 0}</td>
                  <td>{c.endDate || c.fechaEstimadaCosecha || '-'}</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(c.cultivationId)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL */}
      {showModal && (
        <div style={{
          position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
          backgroundColor: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000
        }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '600px', maxHeight:'90vh', overflowY:'auto' }}>
            <h3 style={{color: '#2F4842', marginTop:0}}>Nuevo Cultivo</h3>
            <form onSubmit={handleSubmit} style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'15px'}}>
              
              <div style={{gridColumn: 'span 2'}}>
                <label style={{fontWeight:'bold'}}>Nombre Parcela</label>
                <input className="admin-input" required placeholder="Ej. Sector Norte" style={{marginTop:'5px', marginBottom:0}}
                  value={formData.name} onChange={e => setFormData({...formData, name: e.target.value})} />
              </div>
              
              <div>
                <label style={{fontWeight:'bold'}}>Temporada</label>
                <select className="admin-input" required style={{marginTop:'5px', marginBottom:0}}
                  value={formData.season} onChange={e => setFormData({...formData, season: e.target.value})}>
                  <option value="">-- Selecciona --</option>
                  <option value="PRIMAVERA">Primavera</option>
                  <option value="VERANO">Verano</option>
                  <option value="OTONO">Otoño</option>
                  <option value="INVIERNO">Invierno</option>
                </select>
              </div>

              <div>
                <label style={{fontWeight:'bold'}}>Planta</label>
                <select className="admin-input" required style={{marginTop:'5px', marginBottom:0}}
                  value={formData.plantId} onChange={e => setFormData({...formData, plantId: e.target.value})}>
                  <option value="">-- Selecciona --</option>
                  {listaPlantas.map(planta => (
                    <option key={planta.plantId} value={planta.plantId}>{planta.name}</option>
                  ))}
                </select>
              </div>

              <div>
                <label style={{fontWeight:'bold'}}>Hectáreas</label>
                <input type="number" step="0.01" className="admin-input" required placeholder="0.0" style={{marginTop:'5px', marginBottom:0}}
                  value={formData.area} onChange={e => setFormData({...formData, area: e.target.value})} />
              </div>

              <div>
                <label style={{fontWeight:'bold'}}>Costo Inicial ($)</label>
                <input type="number" step="0.01" className="admin-input" required placeholder="0.00" style={{marginTop:'5px', marginBottom:0}}
                  value={formData.cost} onChange={e => setFormData({...formData, cost: e.target.value})} />
              </div>

              <div>
                <label style={{fontWeight:'bold'}}>Frecuencia Riego (Días)</label>
                <input type="number" className="admin-input" required placeholder="Ej. 7" style={{marginTop:'5px', marginBottom:0}}
                  value={formData.eachIrrigation} onChange={e => setFormData({...formData, eachIrrigation: e.target.value})} />
              </div>

              <div>
                <label style={{fontWeight:'bold'}}>Fecha Inicio</label>
                <input type="date" className="admin-input" required style={{marginTop:'5px', marginBottom:0}}
                  value={formData.startDate} onChange={e => setFormData({...formData, startDate: e.target.value})} />
              </div>

              <div style={{gridColumn: 'span 2'}}>
                <label style={{fontWeight:'bold'}}>Fecha Estimada Cosecha</label>
                <input type="date" className="admin-input" style={{marginTop:'5px', marginBottom:0}}
                  value={formData.endDate} onChange={e => setFormData({...formData, endDate: e.target.value})} />
              </div>

              <div style={{gridColumn: 'span 2', marginTop: '20px', textAlign: 'right', borderTop:'1px solid #eee', paddingTop:'15px'}}>
                <button type="button" onClick={() => setShowModal(false)} style={{marginRight: '10px', background:'none', border:'1px solid #ccc', padding:'8px 15px', borderRadius:'4px', cursor:'pointer'}}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar Cultivo</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE PLANTAS ---
const PlantsCrud = () => {
  const [plants, setPlants] = useState([]);
  const [categories, setCategories] = useState([]); // Lista para el dropdown
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const [formData, setFormData] = useState({
    name: '',
    description: '',
    seedingDensity: '',
    averageSeedWeight: '',
    weightPerPackage: '',
    harvestDays: '',
    categoryId: '' // Aquí guardaremos el ID seleccionado
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      // Cargamos Plantas y Categorías a la vez
      const [plantsData, catsData] = await Promise.all([
        getPlants(),
        getCategories()
      ]);
      setPlants(plantsData);
      setCategories(catsData);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Eliminar esta planta?")) return;
    try {
      await deletePlant(id);
      setPlants(prev => prev.filter(p => p.plantId !== id));
      alert("Planta eliminada.");
    } catch (err) {
      alert("Error: " + err.message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.categoryId) {
      alert("Por favor selecciona una categoría.");
      return;
    }

    try {
      const payload = {
        name: formData.name,
        description: formData.description,
        seedingDensity: parseFloat(formData.seedingDensity),
        averageSeedWeight: parseFloat(formData.averageSeedWeight),
        weightPerPackage: parseFloat(formData.weightPerPackage),
        harvestDays: parseInt(formData.harvestDays),
        available: true,
        
        // Enviamos el objeto categoría con el ID seleccionado
        category: { categoryId: parseInt(formData.categoryId) } 
      };

      await createPlant(payload);
      
      setShowModal(false);
      setFormData({
        name: '', description: '', seedingDensity: '', averageSeedWeight: '', 
        weightPerPackage: '', harvestDays: '', categoryId: ''
      });
      loadData();
      alert("¡Planta registrada exitosamente!");
    } catch (err) {
      alert("Error al guardar (Backend devolvió 404/Error): " + err.message);
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{color: '#2F4842', margin: 0}}>Catálogo de Plantas</h2>
          <p style={{color: '#666', margin: 0}}>Gestión de especies.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nueva Planta</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Categoría</th>
                <th>Días Cosecha</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {plants.length > 0 ? plants.map(p => (
                <tr key={p.plantId}>
                  <td>{p.plantId}</td>
                  <td>
                    <strong>{p.name}</strong><br/>
                    <small>{p.description}</small>
                  </td>
                  {/* Mostramos el nombre de la categoría si existe */}
                  <td>
                    {p.category ? p.category.categoryName : <span style={{color:'red'}}>Sin Cat.</span>}
                  </td>
                  <td>{p.harvestDays} días</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(p.plantId)}>Eliminar</button>
                  </td>
                </tr>
              )) : (
                <tr><td colSpan="5" style={{textAlign:'center', padding:'30px'}}>No hay plantas.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <div style={{
          position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
          backgroundColor: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000
        }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '600px', maxHeight: '90vh', overflowY: 'auto' }}>
            <h3 style={{color: '#2F4842', marginTop: 0}}>Nueva Planta</h3>
            <form onSubmit={handleSubmit} style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'15px'}}>
              
              <div style={{gridColumn: 'span 2'}}>
                <label style={{fontWeight:'600', fontSize:'13px'}}>Nombre</label>
                <input className="admin-input" required 
                  value={formData.name} onChange={e => setFormData({...formData, name: e.target.value})} />
              </div>

              <div style={{gridColumn: 'span 2'}}>
                <label style={{fontWeight:'600', fontSize:'13px'}}>Descripción</label>
                <input className="admin-input" required 
                  value={formData.description} onChange={e => setFormData({...formData, description: e.target.value})} />
              </div>

              <div>
                <label style={{fontSize:'13px'}}>Densidad Siembra</label>
                <input type="number" step="0.01" className="admin-input" required 
                  value={formData.seedingDensity} onChange={e => setFormData({...formData, seedingDensity: e.target.value})} />
              </div>

              <div>
                <label style={{fontSize:'13px'}}>Peso Prom. Semilla</label>
                <input type="number" step="0.01" className="admin-input" required 
                  value={formData.averageSeedWeight} onChange={e => setFormData({...formData, averageSeedWeight: e.target.value})} />
              </div>

              <div>
                <label style={{fontSize:'13px'}}>Peso Paquete</label>
                <input type="number" step="0.01" className="admin-input" required 
                  value={formData.weightPerPackage} onChange={e => setFormData({...formData, weightPerPackage: e.target.value})} />
              </div>

              <div>
                <label style={{fontSize:'13px'}}>Días Cosecha</label>
                <input type="number" className="admin-input" required 
                  value={formData.harvestDays} onChange={e => setFormData({...formData, harvestDays: e.target.value})} />
              </div>

              {/* SELECTOR DE CATEGORÍA OBLIGATORIO */}
              <div style={{gridColumn: 'span 2'}}>
                <label style={{fontWeight:'600', fontSize:'13px', color:'#2F4842'}}>Categoría (Requerido)</label>
                <select 
                  className="admin-input" 
                  required 
                  value={formData.categoryId} 
                  onChange={e => setFormData({...formData, categoryId: e.target.value})}
                  style={{cursor:'pointer', backgroundColor:'#f9f9f9'}}
                >
                  <option value="">-- Selecciona una categoría --</option>
                  {categories.map(c => (
                    // Asegúrate de usar el ID correcto segun tu BD (id o categoryId)
                    <option key={c.categoryId} value={c.categoryId}>
                      {c.categoryName}
                    </option>
                  ))}
                </select>
                {categories.length === 0 && (
                   <small style={{color:'red'}}>
                     ⚠️ Primero crea una categoría en la pestaña "Categorías".
                   </small>
                )}
              </div>

              <div style={{gridColumn: 'span 2', textAlign: 'right', marginTop:'10px'}}>
                <button type="button" onClick={() => setShowModal(false)} style={{marginRight: '10px'}}>Cancelar</button>
                <button type="submit" className="btn-primary" disabled={categories.length === 0}>Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE COSECHAS Y POST-COSECHAS (FINAL) ---
const HarvestsCrud = () => {
  const [activeSubTab, setActiveSubTab] = useState('harvest'); 
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  
  const [harvests, setHarvests] = useState([]);
  const [postHarvests, setPostHarvests] = useState([]);
  const [cultivations, setCultivations] = useState([]); 

  const [formData, setFormData] = useState({});

  useEffect(() => {
    loadAllData();
  }, []);

  const loadAllData = async () => {
    setLoading(true);
    try {
      const [hData, phData, cData] = await Promise.all([
        getHarvests(),
        getPostHarvests(),
        getCultivos()
      ]);
      setHarvests(hData);
      setPostHarvests(phData);
      setCultivations(cData);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteHarvest = async (id) => {
    if(!window.confirm("¿Eliminar esta cosecha?")) return;
    try {
      await deleteHarvest(id);
      setHarvests(prev => prev.filter(h => h.harvestId !== id));
    } catch(e) { alert("Error: " + e.message); }
  };

  const handleDeletePostHarvest = async (id) => {
    if(!window.confirm("¿Eliminar registro?")) return;
    try {
      await deletePostHarvest(id);
      setPostHarvests(prev => prev.filter(ph => ph.postHarvestId !== id));
    } catch(e) { alert("Error: " + e.message); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      if (activeSubTab === 'harvest') {
        // --- CREAR COSECHA ---
        if (!formData.cultivationId) { alert("Selecciona un cultivo"); return; }
        
        const payload = {
          harvest: {
            cultivation: { cultivationId: parseInt(formData.cultivationId) }, 
            harvestQuantity: parseFloat(formData.harvestQuantity) || 0.0,
            unitMeasure: formData.unitMeasure || 'Kg',
            cost: parseFloat(formData.cost) || 0.0,
            yeilfHectare: 0.0, 
            dateHarvested: new Date().toISOString().split('T')[0], 
            employees: []
          },
          employees: [] 
        };
        await createHarvest(payload);
        alert("Cosecha registrada!");

      } else {
        // --- CREAR POST-COSECHA ---
        if (!formData.harvestId) { alert("Selecciona una cosecha"); return; }
        if (!formData.status) { alert("Selecciona un estado"); return; } 

        const payload = {
          postHarvest: {
            harvest: { harvestId: parseInt(formData.harvestId) }, 
            storageCost: parseFloat(formData.storageCost) || 0.0,
            costEmployee: parseFloat(formData.costEmployee) || 0.0,
            kgComerciables: parseFloat(formData.kgComerciables) || 0.0,
            priceKg: parseFloat(formData.priceKg) || 0.0,
            lossKg: parseFloat(formData.lossKg) || 0.0,
            
            totalReveneu: 0.0, 
            profit: 0.0,
            
            // ⚠️ AQUÍ USAMOS EL VALOR DEL SELECT (Coincide con tu Java)
            status: formData.status, 
            
            observations: formData.observations || 'Sin observaciones',
            dateProcessed: new Date().toISOString().split('T')[0], 
            conversionDate: null 
          },
          employees: []
        };

        console.log("Payload PostHarvest:", payload);
        await createPostHarvest(payload);
        alert("Post-Cosecha registrada!");
      }

      setShowModal(false);
      setFormData({});
      loadAllData(); 
    } catch (err) {
      console.error(err);
      alert("Error 500. Revisa la consola.");
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{color: '#2F4842', margin: 0}}>Cosechas y Procesamiento</h2>
          <p style={{color: '#666', margin: 0}}>Gestión de recolección.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>
          + Registrar {activeSubTab === 'harvest' ? 'Cosecha' : 'Post-Cosecha'}
        </button>
      </div>

      <div style={{display:'flex', gap:'10px', marginBottom:'20px', borderBottom:'1px solid #eee', paddingBottom:'10px'}}>
        <button onClick={() => setActiveSubTab('harvest')} style={{padding:'8px 16px', borderRadius:'20px', border:'none', cursor:'pointer', fontWeight:'bold', background: activeSubTab === 'harvest' ? '#6B9080' : '#ddd', color: activeSubTab === 'harvest' ? 'white' : '#666'}}>Recolección</button>
        <button onClick={() => setActiveSubTab('postharvest')} style={{padding:'8px 16px', borderRadius:'20px', border:'none', cursor:'pointer', fontWeight:'bold', background: activeSubTab === 'postharvest' ? '#6B9080' : '#ddd', color: activeSubTab === 'postharvest' ? 'white' : '#666'}}>Procesamiento</button>
      </div>

      {loading ? <p>Cargando datos...</p> : (
        <div className="table-container">
          {activeSubTab === 'harvest' ? (
            <table className="admin-table">
              <thead>
                <tr><th>ID</th><th>Cultivo</th><th>Fecha</th><th>Cantidad</th><th>Acciones</th></tr>
              </thead>
              <tbody>
                {harvests.map(h => (
                  <tr key={h.harvestId}>
                    <td>{h.harvestId}</td>
                    <td>{h.cultivation ? h.cultivation.plotName : 'N/A'}</td>
                    <td>{h.dateHarvested}</td>
                    <td>{h.harvestQuantity} {h.unitMeasure}</td>
                    <td><button className="btn-delete" onClick={() => handleDeleteHarvest(h.harvestId)}>Eliminar</button></td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <table className="admin-table">
              <thead>
                <tr><th>ID</th><th>Cosecha ID</th><th>Estado</th><th>Kg Comerciables</th><th>Acciones</th></tr>
              </thead>
              <tbody>
                {postHarvests.map(ph => (
                  <tr key={ph.postHarvestId}>
                    <td>{ph.postHarvestId}</td>
                    <td>{ph.harvest ? ph.harvest.harvestId : 'N/A'}</td>
                    <td>{ph.status}</td>
                    <td>{ph.kgComerciables} kg</td>
                    <td><button className="btn-delete" onClick={() => handleDeletePostHarvest(ph.postHarvestId)}>Eliminar</button></td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}

      {showModal && (
        <div style={{position:'fixed', top:0, left:0, right:0, bottom:0, background:'rgba(0,0,0,0.6)', display:'flex', justifyContent:'center', alignItems:'center', zIndex:1000}}>
          <div style={{background:'white', padding:'30px', borderRadius:'12px', width:'500px', maxHeight:'90vh', overflowY:'auto'}}>
            <h3 style={{color:'#2F4842'}}>{activeSubTab === 'harvest' ? 'Nueva Cosecha' : 'Nuevo Procesamiento'}</h3>
            
            <form onSubmit={handleSubmit} style={{display:'grid', gap:'15px'}}>
              
              {activeSubTab === 'harvest' && (
                <>
                  <label>Seleccionar Cultivo</label>
                  <select className="admin-input" required value={formData.cultivationId || ''} onChange={e => setFormData({...formData, cultivationId: e.target.value})}>
                    <option value="">-- Elige un cultivo --</option>
                    {cultivations.map(c => <option key={c.cultivationId} value={c.cultivationId}>{c.plotName}</option>)}
                  </select>
                  <label>Cantidad</label>
                  <input type="number" className="admin-input" required value={formData.harvestQuantity || ''} onChange={e => setFormData({...formData, harvestQuantity: e.target.value})} />
                  <label>Unidad</label>
                  <select className="admin-input" value={formData.unitMeasure || 'Kg'} onChange={e => setFormData({...formData, unitMeasure: e.target.value})}>
                    <option value="Kg">Kilogramos</option><option value="Ton">Toneladas</option>
                  </select>
                  <label>Costo</label>
                  <input type="number" className="admin-input" value={formData.cost || ''} onChange={e => setFormData({...formData, cost: e.target.value})} />
                </>
              )}

              {activeSubTab === 'postharvest' && (
                <>
                  <label>Seleccionar Cosecha</label>
                  <select className="admin-input" required value={formData.harvestId || ''} onChange={e => setFormData({...formData, harvestId: e.target.value})}>
                    <option value="">-- Elige una cosecha --</option>
                    {harvests.map(h => <option key={h.harvestId} value={h.harvestId}>ID: {h.harvestId} - {h.harvestQuantity} {h.unitMeasure}</option>)}
                  </select>

                  {/* ⚠️ SELECTOR DE ESTADO ACTUALIZADO */}
                  <label style={{color:'#2F4842', fontWeight:'bold'}}>Estado del Proceso</label>
                  <select 
                    className="admin-input" 
                    required 
                    value={formData.status || ''} 
                    onChange={e => setFormData({...formData, status: e.target.value})}
                  >
                    <option value="">-- Selecciona Estado --</option>
                    
                    {/* 👇 VALORES EXACTOS DE TU ENUM JAVA 👇 */}
                    <option value="EN_ALMACENAMIENTO">En Almacenamiento</option>
                    <option value="PROCESADA">Procesada</option>
                    <option value="CONVERTIDA_EN_PRODUCTO">Convertida en Producto</option>
                    
                  </select>

                  <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'10px'}}>
                    <div><label>Kg Comerciables</label><input type="number" className="admin-input" required value={formData.kgComerciables || ''} onChange={e => setFormData({...formData, kgComerciables: e.target.value})} /></div>
                    <div><label>Kg Pérdida</label><input type="number" className="admin-input" value={formData.lossKg || ''} onChange={e => setFormData({...formData, lossKg: e.target.value})} /></div>
                  </div>
                  <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'10px'}}>
                     <div><label>Precio/Kg</label><input type="number" className="admin-input" value={formData.priceKg || ''} onChange={e => setFormData({...formData, priceKg: e.target.value})} /></div>
                     <div><label>Costo Almacén</label><input type="number" className="admin-input" value={formData.storageCost || ''} onChange={e => setFormData({...formData, storageCost: e.target.value})} /></div>
                  </div>
                  <label>Observaciones</label>
                  <textarea className="admin-input" value={formData.observations || ''} onChange={e => setFormData({...formData, observations: e.target.value})} />
                </>
              )}

              <div style={{marginTop:'20px', textAlign:'right'}}>
                <button type="button" onClick={() => setShowModal(false)} style={{marginRight:'10px'}}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
export default function AdminDashboard() {
    // Estado para controlar qué pestaña se muestra
    const [activeTab, setActiveTab] = useState('home');
    const [adminName, setAdminName] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        // 🔒 Seguridad básica
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
        }
        setAdminName(localStorage.getItem('userName') || 'Admin');
    }, [navigate]);

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login');
    };

    // Lógica para renderizar el contenido correcto
    const renderContent = () => {
        switch (activeTab) {
            case 'home': return <DashboardHome />;

            // Admin
            case 'users': return <UsersView />;

            // Personas
            case 'customers': return <CustomersView />;
            case 'employees': return <EmployeesView />;
            case 'suppliers': return <SuppliersView />;
            // Comercial
            case 'products': return <ProductsView />;
            case 'orders': return <OrdersView />;

            // Agrícola
            
            case 'categories': return <CategoriesCrud />;
            case 'cultivations': return <CultivationsCrud />;
            case 'plants': return <PlantsCrud />;
            case 'harvests': return <HarvestsCrud />;

            default: return <DashboardHome />;
        }
    };

    return (
        <div className="admin-container">
            {/* SIDEBAR */}
            <aside className="admin-sidebar">
                <div className="sidebar-header">
                    <h2 className="brand-logo">
                        <span className="brand-bio">BIO</span><span className="brand-campo">CAMPO</span>
                    </h2>
                </div>

                <nav className="sidebar-nav">
                    <button
                        className={activeTab === 'home' ? 'active' : ''}
                        onClick={() => setActiveTab('home')}
                    >
                        📊 Panel General
                    </button>

                    {/* Separador de Sección */}
                    <div style={{ padding: '15px 20px 5px', fontSize: '12px', color: '#8aa', textTransform: 'uppercase', letterSpacing: '1px' }}>
                        Administración
                    </div>
                    <button className={activeTab === 'users' ? 'active' : ''} onClick={() => setActiveTab('users')}>
                        🔑 Usuarios & Accesos
                    </button>

                    <div style={{ padding: '15px 20px 5px', fontSize: '12px', color: '#8aa', textTransform: 'uppercase', letterSpacing: '1px' }}>
                        Personas
                    </div>
                    <button className={activeTab === 'customers' ? 'active' : ''} onClick={() => setActiveTab('customers')}>
                        👥 Clientes
                    </button>
                    <button className={activeTab === 'employees' ? 'active' : ''} onClick={() => setActiveTab('employees')}>
                        👷 Empleados
                    </button>
                    <button className={activeTab === 'suppliers' ? 'active' : ''} onClick={() => setActiveTab('suppliers')}>
                        🚚 Proveedores
                    </button>

                    <div style={{ padding: '15px 20px 5px', fontSize: '12px', color: '#8aa', textTransform: 'uppercase', letterSpacing: '1px' }}>
                        Comercial
                    </div>
                    <button className={activeTab === 'products' ? 'active' : ''} onClick={() => setActiveTab('products')}>
                        📦 Productos
                    </button>
                    <button className={activeTab === 'orders' ? 'active' : ''} onClick={() => setActiveTab('orders')}>
                        🛒 Pedidos / Ventas
                    </button>

                    <div style={{ padding: '15px 20px 5px', fontSize: '12px', color: '#8aa', textTransform: 'uppercase', letterSpacing: '1px' }}>
                        Gestión Agrícola
                    </div>
                    <button className={activeTab === 'categories' ? 'active' : ''} onClick={() => setActiveTab('categories')}>
                        🏷️ Categorías
                    </button>
                    <button className={activeTab === 'cultivations' ? 'active' : ''} onClick={() => setActiveTab('cultivations')}>
                        🌱 Cultivos
                    </button>
                    <button className={activeTab === 'plants' ? 'active' : ''} onClick={() => setActiveTab('plants')}>
                        🌿 Catálogo Plantas
                    </button>
                    <button className={activeTab === 'harvests' ? 'active' : ''} onClick={() => setActiveTab('harvests')}>
                        🚜 Cosechas
                    </button>
                </nav>

                <div className="sidebar-footer">
                    <button onClick={handleLogout} className="logout-btn">
                        Cerrar Sesión
                    </button>
                </div>
            </aside>

            {/* MAIN CONTENT */}
            <main className="admin-main">
                <header className="admin-header">
                    <span className="header-title">
                        {/* Título dinámico según la tab activa */}
                        {activeTab.charAt(0).toUpperCase() + activeTab.slice(1)}
                    </span>
                    <div className="user-profile">
                        <span>Hola, {adminName}</span>
                        <div className="user-avatar">
                            {adminName.charAt(0).toUpperCase()}
                        </div>
                    </div>
                </header>

                <div className="admin-content-area">
                    {renderContent()}
                </div>
            </main>
        </div>
    );
}