import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCultivos, crearCultivo, eliminarCultivo } from '../services/cultivations';
import { getPlants, createPlant, deletePlant } from '../services/plants';
import { getCategories, createCategory, deleteCategory } from '../services/plantCategories';
import { getHarvests, createHarvest, deleteHarvest } from '../services/harvests';
import { getPostHarvests, createPostHarvest, deletePostHarvest } from '../services/postHarvests';
import { getInsumos, crearInsumo, eliminarInsumo } from '../services/inputs';
import { getSuppliers, createSupplier, deleteSupplier } from '../services/suppliers';
import { getCustomers, createCustomer, deleteCustomer } from '../services/customers';
import { fetchProducts, createProduct, deleteProduct } from '../services/productService';
import { getOrders, createOrder, deleteOrder, getOrderDetails } from '../services/orders';
import { getUsuarios, actualizarUsuario, eliminarUsuario, crearUsuario } from '../services/users';
import { getEmployees, createEmployee, deleteEmployee } from '../services/employees';
import { getJobPositions, createJobPosition, deleteJobPosition } from '../services/jobPositions';
import '../styles/adminStyles.css';

// 1. Vista de Inicio
const DashboardHome = () => (
  <div className="admin-card">
    <h2>Panel General BioCampo</h2>
    <p>Selecciona un módulo del menú lateral para comenzar a gestionar.</p>
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
// --- COMPONENTE CRUD DE CARGOS (JOB POSITIONS) ---
const JobPositionsCrud = () => {
  const [positions, setPositions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [positionName, setPositionName] = useState('');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const data = await getJobPositions();
      setPositions(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Eliminar este cargo?")) return;
    try {
      await deleteJobPosition(id);
      setPositions(prev => prev.filter(p => p.positionId !== id));
      alert("Cargo eliminado.");
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = { positionName: positionName };
      await createJobPosition(payload);

      alert("Cargo creado exitosamente.");
      setShowModal(false);
      setPositionName('');
      loadData();
    } catch (err) {
      alert("Error al guardar: " + err.message);
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{ color: '#2F4842', margin: 0 }}>Gestión de Cargos</h2>
          <p style={{ color: '#666', margin: 0 }}>Puestos laborales disponibles (Ej. Gerente, Obrero).</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Cargo</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre del Cargo</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {positions.length > 0 ? positions.map(p => (
                <tr key={p.positionId}>
                  <td>{p.positionId}</td>
                  <td><strong>{p.positionName}</strong></td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(p.positionId)}>Eliminar</button>
                  </td>
                </tr>
              )) : (
                <tr><td colSpan="3" style={{ textAlign: 'center', padding: '20px' }}>No hay cargos registrados.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL */}
      {showModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '400px' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Nuevo Cargo</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '15px' }}>
              <div>
                <label style={{ fontWeight: 'bold' }}>Nombre del Cargo</label>
                <input className="admin-input" required placeholder="Ej. Ingeniero Agrónomo"
                  value={positionName} onChange={e => setPositionName(e.target.value)} />
              </div>

              <div style={{ marginTop: '20px', textAlign: 'right' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px', background: 'none', border: '1px solid #ccc', padding: '8px 16px', cursor: 'pointer' }}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE GESTIÓN DE USUARIOS (ROL AUTOMÁTICO) ---
const UsersView = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);

  const [formData, setFormData] = useState({
    id: '',
    nombre: '',
    apellido: '',
    email: '',
    pais: '',
    contraseña: ''
  });

  const [originalEmail, setOriginalEmail] = useState('');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const data = await getUsuarios();
      setUsers(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const safeUser = (u) => {
    const id = u.idUsuario || u.userId || u.id;
    const nombre = u.nombre || u.firstName || u.firstname || 'Sin Nombre';
    const apellido = u.apellido || u.lastName || u.lastname || '';
    
    let rawRole = u.rol || u.role || 'CLIENTE';
    if (typeof rawRole === 'object') {
        rawRole = rawRole.authority || rawRole.name || 'CLIENTE';
    }
    if (u.authorities && u.authorities.length > 0) {
        rawRole = u.authorities[0].authority;
    }
    const cleanRole = String(rawRole).replace('ROLE_', '').toUpperCase();

    return {
        id,
        nombre,
        apellido,
        email: u.email || 'Sin Email',
        pais: u.pais || u.country || 'Sin País',
        rol: cleanRole
    };
  };

  const handleDelete = async (rawUser) => {
    const user = safeUser(rawUser);
    if (!window.confirm(`¿Eliminar al usuario ${user.nombre}?`)) return;
    try {
      await eliminarUsuario(user.id);
      setUsers(prev => prev.filter(item => safeUser(item).id !== user.id));
      alert("Usuario eliminado.");
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleCreate = () => {
    setIsEditing(false);
    // Ya no inicializamos rol, porque no se usa en el form
    setFormData({ id: '', nombre: '', apellido: '', email: '', pais: '', contraseña: '' });
    setShowModal(true);
  };

  const handleEdit = (rawUser) => {
    const user = safeUser(rawUser);
    setIsEditing(true);
    setFormData({
      id: user.id,
      nombre: user.nombre,
      apellido: user.apellido,
      email: user.email,
      pais: user.pais,
      contraseña: '' 
    });
    setOriginalEmail(user.email);
    setShowModal(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        nombre: formData.nombre,
        apellido: formData.apellido,
        email: formData.email,
        pais: formData.pais,
        contraseña: formData.contraseña,
        // Mandamos datos extra para compatibilidad, pero SIN rol
        firstname: formData.nombre,
        lastname: formData.apellido,
        country: formData.pais
      };

      if (isEditing) {
        await actualizarUsuario(originalEmail, payload);
        alert("Usuario actualizado correctamente.");
      } else {
        if(!payload.contraseña) { alert("Contraseña obligatoria"); return; }
        await crearUsuario(payload);
        alert("Usuario creado (Rol: Cliente por defecto).");
      }
      setShowModal(false);
      loadData();
    } catch (err) {
      console.error(err);
      alert("Error en la operación.");
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{ color: '#2F4842', margin: 0 }}>Gestión de Usuarios</h2>
          <p style={{ color: '#666', margin: 0 }}>Administración de cuentas.</p>
        </div>
        <button className="btn-primary" onClick={handleCreate}>+ Nuevo Usuario</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr><th>ID</th><th>Nombre</th><th>Email</th><th>Rol</th><th>País</th><th>Acciones</th></tr>
            </thead>
            <tbody>
              {users.map((rawUser, index) => {
                const u = safeUser(rawUser);
                return (
                  <tr key={u.id || index}>
                    <td>{u.id}</td>
                    <td><strong>{u.nombre} {u.apellido}</strong></td>
                    <td>{u.email}</td>
                    <td>
                      {/* Mostramos el Rol, aunque no se pueda editar desde aquí */}
                      <span style={{
                        background: u.rol === 'ADMIN' ? '#fce4ec' : '#e3f2fd',
                        color: u.rol === 'ADMIN' ? '#c2185b' : '#1565c0',
                        padding: '4px 8px', borderRadius: '4px', fontSize: '12px', fontWeight: 'bold'
                      }}>
                        {u.rol}
                      </span>
                    </td>
                    <td>{u.pais}</td>
                    <td>
                      <button className="btn-edit" onClick={() => handleEdit(rawUser)} style={{ marginRight: '5px' }}>Editar</button>
                      <button className="btn-delete" onClick={() => handleDelete(rawUser)}>Eliminar</button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL SIN SELECTOR DE ROL */}
      {showModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '500px' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>{isEditing ? 'Editar Usuario' : 'Nuevo Usuario'}</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '15px' }}>
              
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                <div><label>Nombre</label><input className="admin-input" required value={formData.nombre} onChange={e => setFormData({ ...formData, nombre: e.target.value })} /></div>
                <div><label>Apellido</label><input className="admin-input" required value={formData.apellido} onChange={e => setFormData({ ...formData, apellido: e.target.value })} /></div>
              </div>

              <div><label>Email</label><input type="email" className="admin-input" required value={formData.email} onChange={e => setFormData({ ...formData, email: e.target.value })} /></div>

              {/* País ocupa todo el ancho ahora */}
              <div><label>País</label><input className="admin-input" value={formData.pais} onChange={e => setFormData({ ...formData, pais: e.target.value })} /></div>

              {/* MENSAJE INFORMATIVO EN LUGAR DE SELECTOR */}
              <div style={{background:'#f8f9fa', padding:'10px', borderRadius:'6px', fontSize:'13px', color:'#666', border:'1px solid #eee'}}>
                ℹ️ <strong>Rol del Usuario:</strong> Se asigna automáticamente como <strong>CLIENTE</strong>. <br/>
                
              </div>

              <div><label>Contraseña</label><input type="password" className="admin-input" placeholder={isEditing ? "Opcional" : "Obligatoria"} value={formData.contraseña} onChange={e => setFormData({ ...formData, contraseña: e.target.value })} /></div>

              <div style={{ marginTop: '20px', textAlign: 'right' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px', background: 'none', border: '1px solid #ccc', padding: '8px 16px', cursor: 'pointer' }}>Cancelar</button>
                <button type="submit" className="btn-primary">{isEditing ? 'Actualizar' : 'Crear'}</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE EMPLEADOS (VERSION FINAL ESTRICTA) ---
const EmployeesView = () => {
  const [employees, setEmployees] = useState([]);
  const [jobPositions, setJobPositions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  // Formulario exacto al modelo Java
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    dni: '',
    age: '',
    phone: '',
    personalEmail: '',
    country: 'Peru', // Valor por defecto
    address: '',
    salary: '',
    jobPositionId: ''
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [empData, jobsData] = await Promise.all([
        getEmployees(),
        getJobPositions()
      ]);
      setEmployees(empData);
      setJobPositions(jobsData || []);
    } catch (err) {
      console.error("Error cargando datos:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Dar de baja a este empleado?")) return;
    try {
      await deleteEmployee(id);
      setEmployees(prev => prev.filter(e => e.employeeId !== id));
      alert("Empleado eliminado correctamente.");
    } catch (e) { 
      alert("Error al eliminar: " + e.message); 
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validaciones básicas
    if (!formData.jobPositionId) { alert("Error: Selecciona un Cargo/Puesto."); return; }
    if (!formData.dni) { alert("Error: El DNI es obligatorio."); return; }

    try {
      // CONSTRUCCIÓN DEL PAYLOAD EXACTO PARA JAVA
      const payload = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        dni: formData.dni,
        age: parseInt(formData.age, 10) || 18, // Convertimos a int (Java: int age)
        phone: formData.phone,
        personalEmail: formData.personalEmail,
        // workEmail: null, // No lo enviamos, el backend lo genera con el DNI
        country: formData.country,
        address: formData.address,
        salary: parseFloat(formData.salary) || 0.0, // Convertimos a double (Java: Double salary)
        available: true, // (Java: boolean available)
        
        // Objeto anidado para la relación JobPosition
        jobPosition: {
          positionId: parseInt(formData.jobPositionId, 10)
        }
      };

      console.log("Enviando Payload a Java:", payload); // Revisa la consola si falla
      
      await createEmployee(payload);
      
      alert("¡Empleado registrado exitosamente!");
      setShowModal(false);
      
      // Limpiar formulario
      setFormData({ 
        firstName: '', lastName: '', dni: '', age: '', phone: '', 
        personalEmail: '', country: 'Peru', address: '', salary: '', jobPositionId: '' 
      });
      loadData();
    } catch (err) {
      console.error(err);
      alert("Error (500). Si el error persiste, verifica que el DNI no esté duplicado o que el cargo exista.");
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{color: '#2F4842', margin: 0}}>Gestión de Empleados</h2>
          <p style={{color: '#666', margin: 0}}>Planilla y Recursos Humanos.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Contratar Empleado</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre Completo</th>
                <th>Cargo</th>
                <th>DNI / Contacto</th>
                <th>Email Corp.</th>
                <th>Salario</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {employees.length > 0 ? employees.map(emp => (
                <tr key={emp.employeeId}>
                  <td>{emp.employeeId}</td>
                  <td>
                    <strong>{emp.firstName} {emp.lastName}</strong><br/>
                    <small style={{color:'#666'}}>{emp.age} años</small>
                  </td>
                  <td>
                    {emp.jobPosition ? (
                      <span style={{background:'#e3f2fd', color:'#1565c0', padding:'2px 6px', borderRadius:'4px', fontSize:'11px', fontWeight:'bold'}}>
                        {emp.jobPosition.positionName}
                      </span>
                    ) : <span style={{color:'red'}}>Sin Cargo</span>}
                  </td>
                  <td>
                    <strong>{emp.dni}</strong><br/>
                    <small>{emp.phone}</small>
                  </td>
                  <td>{emp.workEmail || '-'}</td>
                  <td style={{fontWeight:'bold'}}>${emp.salary}</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(emp.employeeId)}>Baja</button>
                  </td>
                </tr>
              )) : (
                <tr><td colSpan="7" style={{textAlign:'center', padding:'20px'}}>No hay empleados registrados.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL */}
      {showModal && (
        <div style={{position:'fixed', top:0, left:0, right:0, bottom:0, background:'rgba(0,0,0,0.6)', display:'flex', justifyContent:'center', alignItems:'center', zIndex:1000}}>
          <div style={{background:'white', padding:'30px', borderRadius:'12px', width:'700px', maxHeight:'90vh', overflowY:'auto'}}>
            <h3 style={{color:'#2F4842', marginTop:0}}>Nuevo Contrato</h3>
            
            {/* Aviso si no hay cargos */}
            {jobPositions.length === 0 && (
                <div style={{background:'#ffebee', color:'#c62828', padding:'10px', borderRadius:'4px', marginBottom:'15px', border:'1px solid #ef9a9a'}}>
                    ⚠️ <strong>ATENCIÓN:</strong> No hay cargos creados. Debes crear al menos un cargo en la pestaña "Cargos" antes de registrar empleados.
                </div>
            )}

            <form onSubmit={handleSubmit} style={{display:'grid', gap:'15px'}}>
              
              <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'10px'}}>
                <div>
                  <label style={{fontWeight:'bold'}}>Nombres</label>
                  <input className="admin-input" required value={formData.firstName} onChange={e => setFormData({...formData, firstName: e.target.value})} />
                </div>
                <div>
                  <label style={{fontWeight:'bold'}}>Apellidos</label>
                  <input className="admin-input" required value={formData.lastName} onChange={e => setFormData({...formData, lastName: e.target.value})} />
                </div>
              </div>

              <div style={{display:'grid', gridTemplateColumns:'1fr 1fr 1fr', gap:'10px'}}>
                <div>
                  <label style={{fontWeight:'bold'}}>DNI (Obligatorio)</label>
                  <input className="admin-input" required maxLength="8" placeholder="12345678"
                    value={formData.dni} onChange={e => setFormData({...formData, dni: e.target.value})} />
                </div>
                <div>
                  <label>Edad</label>
                  <input type="number" className="admin-input" placeholder="18"
                    value={formData.age} onChange={e => setFormData({...formData, age: e.target.value})} />
                </div>
                <div>
                  <label>País</label>
                  <input className="admin-input" value={formData.country} onChange={e => setFormData({...formData, country: e.target.value})} />
                </div>
              </div>

              <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'10px'}}>
                <div>
                  <label>Teléfono</label>
                  <input className="admin-input" value={formData.phone} onChange={e => setFormData({...formData, phone: e.target.value})} />
                </div>
                <div>
                  <label>Email Personal</label>
                  <input type="email" className="admin-input" placeholder="personal@gmail.com"
                    value={formData.personalEmail} onChange={e => setFormData({...formData, personalEmail: e.target.value})} />
                </div>
              </div>
              
              <div>
                <label>Dirección</label>
                <input className="admin-input" value={formData.address} onChange={e => setFormData({...formData, address: e.target.value})} />
              </div>

              <hr style={{width:'100%', border:'0', borderTop:'1px solid #eee', margin:'10px 0'}}/>

              <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'10px'}}>
                 <div>
                   <label style={{fontWeight:'bold', color:'#2F4842'}}>Cargo / Puesto *</label>
                   <select 
                        className="admin-input" 
                        required 
                        value={formData.jobPositionId} 
                        onChange={e => setFormData({...formData, jobPositionId: e.target.value})}
                   >
                     <option value="">-- Selecciona Cargo --</option>
                     {jobPositions.map(job => (
                       <option key={job.positionId} value={job.positionId}>{job.positionName}</option>
                     ))}
                   </select>
                 </div>
                 <div>
                   <label style={{fontWeight:'bold', color:'#2F4842'}}>Salario</label>
                   <input type="number" step="0.01" className="admin-input" required placeholder="0.00"
                     value={formData.salary} onChange={e => setFormData({...formData, salary: e.target.value})} />
                 </div>
              </div>

              <div style={{marginTop:'20px', textAlign:'right'}}>
                <button type="button" onClick={() => setShowModal(false)} style={{marginRight:'10px', background:'none', border:'1px solid #ccc', padding:'8px 16px', cursor:'pointer'}}>Cancelar</button>
                <button type="submit" className="btn-primary" disabled={jobPositions.length === 0}>Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE PRODUCTOS (COMPLETO CON TODOS LOS CAMPOS) ---
const ProductsView = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [postHarvests, setPostHarvests] = useState([]); // Lista de procesos de post-cosecha
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  // Formulario con TODOS los campos de tu Product.java
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    imageUrl: '',       
    price: '',
    stock: '',
    quantity: '',       
    weight: '',
    unitMeasure: '',
    lotCode: '',
    categoryId: '',    
    postHarvestId: ''   
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      // Cargamos Productos, Categorías y Post-Cosechas
      const [prodData, catData, postData] = await Promise.all([
        fetchProducts(),
        getCategories(),
        getPostHarvests()
      ]);
      setProducts(prodData);
      setCategories(catData);
      setPostHarvests(postData || []);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Eliminar este producto?")) return;
    try {
      await deleteProduct(id);
      setProducts(prev => prev.filter(p => p.productId !== id));
      alert("Producto eliminado.");
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.categoryId) { alert("La categoría es obligatoria"); return; }

    try {
      const payload = {
        name: formData.name,
        description: formData.description,
        imageUrl: formData.imageUrl,

        // Convertimos a números para evitar errores en Java
        price: parseFloat(formData.price) || 0.0,
        stock: parseInt(formData.stock) || 0,
        quantity: parseInt(formData.quantity) || 0,
        weight: parseFloat(formData.weight) || 0.0,

        unitMeasure: formData.unitMeasure || 'UNIDAD',
        lotCode: formData.lotCode || ('LOTE-' + Date.now()),
        active: true, // Por defecto activo

        // Relación con PlantCategory (Obligatoria)
        plantCategory: {
          categoryId: parseInt(formData.categoryId)
        },

        // Relación con PostHarvest (Opcional)
        // Si seleccionó uno, enviamos el objeto. Si no, enviamos null.
        postHarvest: formData.postHarvestId ? { postHarvestId: parseInt(formData.postHarvestId) } : null
      };

      console.log("Enviando Producto Completo:", payload);
      await createProduct(payload);

      alert("¡Producto registrado exitosamente!");
      setShowModal(false);

      // Limpiamos el formulario
      setFormData({
        name: '', description: '', imageUrl: '', price: '', stock: '',
        quantity: '', weight: '', unitMeasure: '', lotCode: '',
        categoryId: '', postHarvestId: ''
      });
      loadData();
    } catch (err) {
      console.error(err);
      alert("Error al guardar producto.");
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{ color: '#2F4842', margin: 0 }}>Catálogo de Productos</h2>
          <p style={{ color: '#666', margin: 0 }}>Gestión completa de productos finales.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Producto</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Producto</th>
                <th>Categoría</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Lote</th>
                <th>Origen (Post-C)</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {products.map(p => (
                <tr key={p.productId}>
                  <td>{p.productId}</td>
                  <td>
                    <strong>{p.name}</strong><br />
                    <small style={{ color: '#666' }}>{p.unitMeasure}</small>
                  </td>
                  <td>
                    {p.plantCategory ? (
                      <span style={{ background: '#e8f5e9', color: '#2e7d32', padding: '2px 6px', borderRadius: '4px', fontSize: '12px' }}>
                        {p.plantCategory.categoryName}
                      </span>
                    ) : '-'}
                  </td>
                  <td style={{ fontWeight: 'bold' }}>${p.price}</td>
                  <td style={{ color: p.stock < 10 ? 'red' : 'inherit' }}>{p.stock}</td>
                  <td>{p.lotCode}</td>
                  <td>{p.postHarvest ? `ID: ${p.postHarvest.postHarvestId}` : '-'}</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(p.productId)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL DE REGISTRO */}
      {showModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '700px', maxHeight: '90vh', overflowY: 'auto' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Nuevo Producto</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '15px' }}>

              {/* FILA 1: Nombre y Categoría */}
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Nombre</label>
                  <input className="admin-input" required placeholder="Ej. Papa Amarilla"
                    value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} />
                </div>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Categoría (Obligatorio)</label>
                  <select className="admin-input" required value={formData.categoryId} onChange={e => setFormData({ ...formData, categoryId: e.target.value })}>
                    <option value="">-- Selecciona --</option>
                    {categories.map(c => (
                      <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>
                    ))}
                  </select>
                </div>
              </div>

              {/* FILA 2: Descripción y Post-Cosecha */}
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                <div>
                  <label>Descripción</label>
                  <input className="admin-input" placeholder="Detalles..."
                    value={formData.description} onChange={e => setFormData({ ...formData, description: e.target.value })} />
                </div>
                <div>
                  <label>Origen (Post-Cosecha)</label>
                  <select className="admin-input" value={formData.postHarvestId} onChange={e => setFormData({ ...formData, postHarvestId: e.target.value })}>
                    <option value="">-- Ninguno / Externo --</option>
                    {postHarvests.map(ph => (
                      <option key={ph.postHarvestId || ph.id} value={ph.postHarvestId || ph.id}>
                        Proc. ID {ph.postHarvestId || ph.id} - {ph.date ? ph.date : ''}
                      </option>
                    ))}
                  </select>
                </div>
              </div>

              {/* FILA 3: Precios y Stock */}
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr', gap: '10px' }}>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Precio</label>
                  <input type="number" step="0.01" className="admin-input" required placeholder="0.00"
                    value={formData.price} onChange={e => setFormData({ ...formData, price: e.target.value })} />
                </div>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Stock</label>
                  <input type="number" className="admin-input" required placeholder="0"
                    value={formData.stock} onChange={e => setFormData({ ...formData, stock: e.target.value })} />
                </div>
                <div>
                  <label>Cantidad</label>
                  <input type="number" className="admin-input" placeholder="0"
                    value={formData.quantity} onChange={e => setFormData({ ...formData, quantity: e.target.value })} />
                </div>
                <div>
                  <label>Peso (kg)</label>
                  <input type="number" step="0.01" className="admin-input" placeholder="0.0"
                    value={formData.weight} onChange={e => setFormData({ ...formData, weight: e.target.value })} />
                </div>
              </div>

              {/* FILA 4: Detalles Técnicos */}
              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: '10px' }}>
                <div>
                  <label>Unidad Medida</label>
                  <select className="admin-input" value={formData.unitMeasure} onChange={e => setFormData({ ...formData, unitMeasure: e.target.value })}>
                    <option value="UNIDAD">Unidad</option>
                    <option value="KG">Kilogramo</option>
                    <option value="SACO">Saco</option>
                    <option value="CAJA">Caja</option>
                    <option value="TONELADA">Tonelada</option>
                  </select>
                </div>
                <div>
                  <label>Código Lote</label>
                  <input className="admin-input" placeholder="Auto si vacío"
                    value={formData.lotCode} onChange={e => setFormData({ ...formData, lotCode: e.target.value })} />
                </div>
                <div>
                  <label>URL Imagen</label>
                  <input className="admin-input" placeholder="http://..."
                    value={formData.imageUrl} onChange={e => setFormData({ ...formData, imageUrl: e.target.value })} />
                </div>
              </div>

              <div style={{ marginTop: '20px', textAlign: 'right' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px', background: 'none', border: '1px solid #ccc', padding: '8px 16px', cursor: 'pointer' }}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar Producto</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE INSUMOS (FRONTEND ROBUSTO) ---
const InputsCrud = () => {
  const [inputs, setInputs] = useState([]);
  const [suppliers, setSuppliers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const [formData, setFormData] = useState({
    name: '',
    type: '',
    unitStatet: '', // Coincide con tu Java
    stock: '',
    priceUnit: '',
    supplierId: ''
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [inputsData, suppliersData] = await Promise.all([
        getInsumos(),
        getSuppliers()
      ]);
      setInputs(inputsData);
      setSuppliers(suppliersData);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Eliminar este insumo?")) return;
    try {
      await eliminarInsumo(id);
      setInputs(prev => prev.filter(i => i.inputId !== id));
      alert("Insumo eliminado.");
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Validación crítica para evitar error 500 en Java
    if (!formData.supplierId) { alert("Selecciona un proveedor obligatorio"); return; }
    if (!formData.type) { alert("Selecciona un tipo"); return; }

    try {
      // Preparamos los números para que Java no reciba Strings
      const stockVal = parseFloat(formData.stock) || 0.0;
      const priceVal = parseFloat(formData.priceUnit) || 0.0;

      const payload = {
        name: formData.name,
        type: formData.type,
        unitStatet: formData.unitStatet,
        stock: stockVal,     // Enviamos número
        priceUnit: priceVal, // Enviamos número
        totalCost: stockVal * priceVal,

        // ESTRUCTURA EXACTA PARA EVITAR 500
        supplier: {
          supplierId: parseInt(formData.supplierId)
        }
      };

      console.log("Payload enviado:", payload); // Para depurar en consola F12
      await crearInsumo(payload);

      alert("¡Insumo registrado!");
      setShowModal(false);
      setFormData({ name: '', type: '', unitStatet: '', stock: '', priceUnit: '', supplierId: '' });
      loadData();
    } catch (err) {
      console.error(err);
      alert("Error al guardar. Revisa que el ID del proveedor exista.");
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{ color: '#2F4842', margin: 0 }}>Gestión de Insumos</h2>
          <p style={{ color: '#666', margin: 0 }}>Inventario de materiales.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Insumo</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr><th>ID</th><th>Insumo</th><th>Tipo</th><th>Unidad</th><th>Stock</th><th>Precio</th><th>Prov.</th><th>Acciones</th></tr>
            </thead>
            <tbody>
              {inputs.map(i => (
                <tr key={i.inputId}>
                  <td>{i.inputId}</td>
                  <td><strong>{i.name}</strong></td>
                  <td><span style={{ fontSize: '12px', background: '#eee', padding: '2px 5px' }}>{i.type}</span></td>
                  <td>{i.unitStatet}</td>
                  {/* Si el backend lo resetea a 0, aquí se verá 0 */}
                  <td style={{ fontWeight: 'bold' }}>{i.stock}</td>
                  <td>${i.priceUnit}</td>
                  <td>{i.supplier ? (i.supplier.name || i.supplier.nombre || 'ID ' + i.supplier.supplierId) : '-'}</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(i.inputId)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL */}
      {showModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '600px' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Nuevo Insumo</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '15px' }}>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Nombre</label>
                  <input className="admin-input" required value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} />
                </div>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Tipo</label>
                  <select className="admin-input" required value={formData.type} onChange={e => setFormData({ ...formData, type: e.target.value })}>
                    <option value="">-- Selecciona --</option>
                    {/* Asegúrate que estos valores coinciden con tu ENUM Java */}
                    <option value="FERTILIZANTE">FERTILIZANTE</option>
                    <option value="PESTICIDA">PESTICIDA</option>
                    <option value="SEMILLA">SEMILLA</option>
                    <option value="HERRAMIENTA">HERRAMIENTA</option>
                    <option value="OTRO">OTRO</option>
                  </select>
                </div>
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Proveedor (Obligatorio)</label>
                <select className="admin-input" required value={formData.supplierId} onChange={e => setFormData({ ...formData, supplierId: e.target.value })}>
                  <option value="">-- Selecciona Proveedor --</option>
                  {suppliers.map(s => (
                    <option key={s.supplierId || s.id} value={s.supplierId || s.id}>
                      {s.name || s.nombre || s.nombreEmpresa} (ID: {s.supplierId || s.id})
                    </option>
                  ))}
                </select>
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: '10px' }}>
                <div>
                  <label>Unidad</label>
                  <input className="admin-input" required placeholder="kg, lt..." value={formData.unitStatet} onChange={e => setFormData({ ...formData, unitStatet: e.target.value })} />
                </div>
                <div>
                  <label>Stock</label>
                  <input type="number" step="0.01" className="admin-input" required placeholder="0.0" value={formData.stock} onChange={e => setFormData({ ...formData, stock: e.target.value })} />
                </div>
                <div>
                  <label>Precio</label>
                  <input type="number" step="0.01" className="admin-input" required placeholder="0.0" value={formData.priceUnit} onChange={e => setFormData({ ...formData, priceUnit: e.target.value })} />
                </div>
              </div>

              <div style={{ marginTop: '20px', textAlign: 'right' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px' }}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
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
// --- COMPONENTE CRUD DE CLIENTES (FINAL: CON EMAIL) ---
const CustomersCrud = () => {
  const [customers, setCustomers] = useState([]);
  const [users, setUsers] = useState([]); 
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const [formData, setFormData] = useState({
    idUsuarioSeleccionado: '',   
    age: '',
    phone: '',
    address: '',
    type: 'MINORISTA'
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [customersData, usersData] = await Promise.all([
        getCustomers(),
        getUsuarios() 
      ]);
      setCustomers(customersData);
      setUsers(usersData || []);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const getSafeId = (u) => {
    if (!u) return null;
    return u.idUsuario || u.id || u.userId;
  };

  const getSafeName = (u) => {
    if (!u) return 'Desconocido';
    const nombre = u.nombre || u.firstName || '';
    const apellido = u.apellido || u.lastName || '';
    return `${nombre} ${apellido}`;
  };

  const getAvailableUsers = () => {
    return users.filter(u => {
      const uid = getSafeId(u);
      const isAlreadyCustomer = customers.some(c => {
        const cUid = getSafeId(c.user);
        return String(cUid) === String(uid);
      });
      return !isAlreadyCustomer;
    });
  };

  const handleDelete = async (id) => {
    if(!window.confirm("¿Eliminar este perfil de cliente?")) return;
    try {
      await deleteCustomer(id);
      setCustomers(prev => prev.filter(c => c.customerId !== id));
      alert("Cliente eliminado.");
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.idUsuarioSeleccionado) { alert("Debes seleccionar un usuario"); return; }

    // 1. BUSCAR EL USUARIO COMPLETO PARA OBTENER SU EMAIL
    const selectedUser = users.find(u => String(getSafeId(u)) === String(formData.idUsuarioSeleccionado));
    
    if (!selectedUser) {
        alert("Error: No se pudieron recuperar los datos del usuario seleccionado.");
        return;
    }

    try {
      // 2. ENVIAR EL OBJETO USUARIO COMPLETO (ID + EMAIL + DATOS)
      // Esto evita que el backend busque 'where email is null'
      const payload = {
        age: parseInt(formData.age) || 18,
        phone: formData.phone,
        address: formData.address,
        type: formData.type,
        
        user: { 
          idUsuario: parseInt(formData.idUsuarioSeleccionado),
          email: selectedUser.email, // ✅ CLAVE: Enviamos el email
          nombre: selectedUser.nombre || selectedUser.firstName, // Enviamos nombre por si acaso
          apellido: selectedUser.apellido || selectedUser.lastName,
          pais: selectedUser.pais || selectedUser.country,
          rol: selectedUser.rol || selectedUser.role || 'CLIENTE'
        }
      };

      console.log("Enviando Cliente con Email:", payload);
      await createCustomer(payload);
      
      alert("¡Cliente registrado correctamente!");
      setShowModal(false);
      setFormData({ idUsuarioSeleccionado: '', age: '', phone: '', address: '', type: 'MINORISTA' });
      loadData();
    } catch (err) {
      console.error(err);
      alert("Error al registrar. Revisa la consola.");
    }
  };

  const availableUsers = getAvailableUsers();

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{color: '#2F4842', margin: 0}}>Gestión de Clientes</h2>
          <p style={{color: '#666', margin: 0}}>Habilitar perfil de venta a usuarios.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Vincular Cliente</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr><th>ID</th><th>Usuario</th><th>Email</th><th>Teléfono</th><th>Tipo</th><th>Acciones</th></tr>
            </thead>
            <tbody>
              {customers.map(c => {
                const uId = getSafeId(c.user) || Math.random();
                return (
                  <tr key={c.customerId || uId}>
                    <td>{c.customerId}</td>
                    <td><strong>{getSafeName(c.user)}</strong></td>
                    <td>{c.user ? c.user.email : '-'}</td>
                    <td>{c.phone || '-'}</td>
                    <td><span style={{fontSize:'12px', background:'#eee', padding:'2px 6px', borderRadius:'4px'}}>{c.type}</span></td>
                    <td>
                      <button className="btn-delete" onClick={() => handleDelete(c.customerId)}>Eliminar</button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <div style={{position:'fixed', top:0, left:0, right:0, bottom:0, background:'rgba(0,0,0,0.6)', display:'flex', justifyContent:'center', alignItems:'center', zIndex:1000}}>
          <div style={{background:'white', padding:'30px', borderRadius:'12px', width:'500px'}}>
            <h3 style={{color:'#2F4842', marginTop:0}}>Habilitar Cliente</h3>
            <form onSubmit={handleSubmit} style={{display:'grid', gap:'15px'}}>
              <div>
                <label style={{fontWeight:'bold', color:'#2F4842'}}>Usuario Disponible</label>
                <select 
                  className="admin-input" required 
                  value={formData.idUsuarioSeleccionado} 
                  onChange={e => setFormData({...formData, idUsuarioSeleccionado: e.target.value})}
                >
                  <option value="">-- Selecciona Usuario --</option>
                  {availableUsers.map(u => {
                    const uid = getSafeId(u);
                    return <option key={uid} value={uid}>{getSafeName(u)} ({u.email})</option>;
                  })}
                </select>
                {availableUsers.length === 0 && <small style={{color:'#d32f2f', fontWeight:'bold'}}>⚠️ Todos los usuarios ya son clientes.</small>}
              </div>
              <div style={{display:'grid', gridTemplateColumns:'1fr 1fr', gap:'10px'}}>
                <div><label>Teléfono</label><input className="admin-input" value={formData.phone} onChange={e => setFormData({...formData, phone: e.target.value})} /></div>
                <div><label>Edad</label><input type="number" className="admin-input" value={formData.age} onChange={e => setFormData({...formData, age: e.target.value})} /></div>
              </div>
              <div><label>Tipo</label>
                <select className="admin-input" value={formData.type} onChange={e => setFormData({...formData, type: e.target.value})}>
                  <option value="MINORISTA">Minorista</option><option value="MAYORISTA">Mayorista</option>
                </select>
              </div>
              <div><label>Dirección</label><input className="admin-input" required value={formData.address} onChange={e => setFormData({...formData, address: e.target.value})} /></div>
              <div style={{marginTop:'20px', textAlign:'right'}}>
                <button type="button" onClick={() => setShowModal(false)} style={{marginRight:'10px'}}>Cancelar</button>
                <button type="submit" className="btn-primary" disabled={availableUsers.length === 0}>Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
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
          <h2 style={{ color: '#2F4842', margin: 0 }}>Gestión de Cultivos</h2>
          <p style={{ color: '#666', margin: 0 }}>Administra tus parcelas, siembras y costos.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Cultivo</button>
      </div>

      {error && <p style={{ color: 'red' }}>{error}</p>}

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
                    <strong>{c.plotName}</strong><br />
                    <small style={{ color: '#555' }}>{c.plant ? c.plant.name : 'Sin Planta'}</small>
                  </td>
                  <td>{c.season}</td>
                  <td style={{ textAlign: 'center' }}>{c.hectares}</td>
                  <td style={{ color: '#2F4842', fontWeight: 'bold' }}>${c.costo || c.cost || 0}</td>
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
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '600px', maxHeight: '90vh', overflowY: 'auto' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Nuevo Cultivo</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>

              <div style={{ gridColumn: 'span 2' }}>
                <label style={{ fontWeight: 'bold' }}>Nombre Parcela</label>
                <input className="admin-input" required placeholder="Ej. Sector Norte" style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} />
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Temporada</label>
                <select className="admin-input" required style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.season} onChange={e => setFormData({ ...formData, season: e.target.value })}>
                  <option value="">-- Selecciona --</option>
                  <option value="PRIMAVERA">Primavera</option>
                  <option value="VERANO">Verano</option>
                  <option value="OTONO">Otoño</option>
                  <option value="INVIERNO">Invierno</option>
                </select>
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Planta</label>
                <select className="admin-input" required style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.plantId} onChange={e => setFormData({ ...formData, plantId: e.target.value })}>
                  <option value="">-- Selecciona --</option>
                  {listaPlantas.map(planta => (
                    <option key={planta.plantId} value={planta.plantId}>{planta.name}</option>
                  ))}
                </select>
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Hectáreas</label>
                <input type="number" step="0.01" className="admin-input" required placeholder="0.0" style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.area} onChange={e => setFormData({ ...formData, area: e.target.value })} />
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Costo Inicial ($)</label>
                <input type="number" step="0.01" className="admin-input" required placeholder="0.00" style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.cost} onChange={e => setFormData({ ...formData, cost: e.target.value })} />
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Frecuencia Riego (Días)</label>
                <input type="number" className="admin-input" required placeholder="Ej. 7" style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.eachIrrigation} onChange={e => setFormData({ ...formData, eachIrrigation: e.target.value })} />
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Fecha Inicio</label>
                <input type="date" className="admin-input" required style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.startDate} onChange={e => setFormData({ ...formData, startDate: e.target.value })} />
              </div>

              <div style={{ gridColumn: 'span 2' }}>
                <label style={{ fontWeight: 'bold' }}>Fecha Estimada Cosecha</label>
                <input type="date" className="admin-input" style={{ marginTop: '5px', marginBottom: 0 }}
                  value={formData.endDate} onChange={e => setFormData({ ...formData, endDate: e.target.value })} />
              </div>

              <div style={{ gridColumn: 'span 2', marginTop: '20px', textAlign: 'right', borderTop: '1px solid #eee', paddingTop: '15px' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px', background: 'none', border: '1px solid #ccc', padding: '8px 15px', borderRadius: '4px', cursor: 'pointer' }}>Cancelar</button>
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
          <h2 style={{ color: '#2F4842', margin: 0 }}>Catálogo de Plantas</h2>
          <p style={{ color: '#666', margin: 0 }}>Gestión de especies.</p>
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
                    <strong>{p.name}</strong><br />
                    <small>{p.description}</small>
                  </td>
                  {/* Mostramos el nombre de la categoría si existe */}
                  <td>
                    {p.category ? p.category.categoryName : <span style={{ color: 'red' }}>Sin Cat.</span>}
                  </td>
                  <td>{p.harvestDays} días</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(p.plantId)}>Eliminar</button>
                  </td>
                </tr>
              )) : (
                <tr><td colSpan="5" style={{ textAlign: 'center', padding: '30px' }}>No hay plantas.</td></tr>
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
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Nueva Planta</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px' }}>

              <div style={{ gridColumn: 'span 2' }}>
                <label style={{ fontWeight: '600', fontSize: '13px' }}>Nombre</label>
                <input className="admin-input" required
                  value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} />
              </div>

              <div style={{ gridColumn: 'span 2' }}>
                <label style={{ fontWeight: '600', fontSize: '13px' }}>Descripción</label>
                <input className="admin-input" required
                  value={formData.description} onChange={e => setFormData({ ...formData, description: e.target.value })} />
              </div>

              <div>
                <label style={{ fontSize: '13px' }}>Densidad Siembra</label>
                <input type="number" step="0.01" className="admin-input" required
                  value={formData.seedingDensity} onChange={e => setFormData({ ...formData, seedingDensity: e.target.value })} />
              </div>

              <div>
                <label style={{ fontSize: '13px' }}>Peso Prom. Semilla</label>
                <input type="number" step="0.01" className="admin-input" required
                  value={formData.averageSeedWeight} onChange={e => setFormData({ ...formData, averageSeedWeight: e.target.value })} />
              </div>

              <div>
                <label style={{ fontSize: '13px' }}>Peso Paquete</label>
                <input type="number" step="0.01" className="admin-input" required
                  value={formData.weightPerPackage} onChange={e => setFormData({ ...formData, weightPerPackage: e.target.value })} />
              </div>

              <div>
                <label style={{ fontSize: '13px' }}>Días Cosecha</label>
                <input type="number" className="admin-input" required
                  value={formData.harvestDays} onChange={e => setFormData({ ...formData, harvestDays: e.target.value })} />
              </div>

              {/* SELECTOR DE CATEGORÍA OBLIGATORIO */}
              <div style={{ gridColumn: 'span 2' }}>
                <label style={{ fontWeight: '600', fontSize: '13px', color: '#2F4842' }}>Categoría (Requerido)</label>
                <select
                  className="admin-input"
                  required
                  value={formData.categoryId}
                  onChange={e => setFormData({ ...formData, categoryId: e.target.value })}
                  style={{ cursor: 'pointer', backgroundColor: '#f9f9f9' }}
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
                  <small style={{ color: 'red' }}>
                    ⚠️ Primero crea una categoría en la pestaña "Categorías".
                  </small>
                )}
              </div>

              <div style={{ gridColumn: 'span 2', textAlign: 'right', marginTop: '10px' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px' }}>Cancelar</button>
                <button type="submit" className="btn-primary" disabled={categories.length === 0}>Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE PROVEEDORES (FINAL CON RUC) ---
const SuppliersCrud = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  // Formulario exacto según tu Supplier.java
  const [formData, setFormData] = useState({
    name: '',
    ruc: '',     // ✅ Nuevo campo
    email: '',
    phone: '',
    address: ''
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const data = await getSuppliers();
      setSuppliers(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Eliminar este proveedor?")) return;
    try {
      await deleteSupplier(id);
      setSuppliers(prev => prev.filter(s => s.supplierId !== id));
      alert("Proveedor eliminado.");
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Payload exacto para tu Backend
      const payload = {
        name: formData.name,
        ruc: formData.ruc,
        email: formData.email,
        phone: formData.phone,
        address: formData.address
      };

      await createSupplier(payload);

      alert("¡Proveedor registrado correctamente!");
      setShowModal(false);
      setFormData({ name: '', ruc: '', email: '', phone: '', address: '' });
      loadData();
    } catch (err) {
      alert("Error al guardar: " + err.message);
    }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{ color: '#2F4842', margin: 0 }}>Gestión de Proveedores</h2>
          <p style={{ color: '#666', margin: 0 }}>Empresas para abastecimiento.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Proveedor</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              {/* ✅ Agregamos RUC a la tabla */}
              <tr><th>ID</th><th>Empresa</th><th>RUC</th><th>Email</th><th>Teléfono</th><th>Dirección</th><th>Acciones</th></tr>
            </thead>
            <tbody>
              {suppliers.length > 0 ? suppliers.map(s => (
                <tr key={s.supplierId}>
                  <td>{s.supplierId}</td>
                  <td><strong>{s.name}</strong></td>
                  <td>{s.ruc || '-'}</td>
                  <td>{s.email}</td>
                  <td>{s.phone}</td>
                  <td>{s.address}</td>
                  <td>
                    <button className="btn-delete" onClick={() => handleDelete(s.supplierId)}>Eliminar</button>
                  </td>
                </tr>
              )) : (
                <tr><td colSpan="7" style={{ textAlign: 'center', padding: '20px' }}>No hay proveedores registrados.</td></tr>
              )}
            </tbody>
          </table>
        </div>
      )}

      {/* MODAL */}
      {showModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '500px' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Nuevo Proveedor</h3>
            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '15px' }}>

              <div>
                <label style={{ fontWeight: 'bold' }}>Nombre Empresa</label>
                <input className="admin-input" required placeholder="Ej. AgroQuímicos S.A."
                  value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} />
              </div>

              {/* ✅ CAMPO RUC */}
              <div>
                <label style={{ fontWeight: 'bold' }}>RUC</label>
                <input className="admin-input" required placeholder="Ej. 20123456789"
                  value={formData.ruc} onChange={e => setFormData({ ...formData, ruc: e.target.value })} />
              </div>

              <div>
                <label style={{ fontWeight: 'bold' }}>Email de Contacto</label>
                <input type="email" className="admin-input" required placeholder="contacto@empresa.com"
                  value={formData.email} onChange={e => setFormData({ ...formData, email: e.target.value })} />
              </div>

              <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Teléfono</label>
                  <input className="admin-input" placeholder="999..."
                    value={formData.phone} onChange={e => setFormData({ ...formData, phone: e.target.value })} />
                </div>
                <div>
                  <label style={{ fontWeight: 'bold' }}>Dirección</label>
                  <input className="admin-input" placeholder="Av. Principal..."
                    value={formData.address} onChange={e => setFormData({ ...formData, address: e.target.value })} />
                </div>
              </div>

              <div style={{ marginTop: '20px', textAlign: 'right' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px', background: 'none', border: '1px solid #ccc', padding: '8px 16px', cursor: 'pointer' }}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar</button>
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
    if (!window.confirm("¿Eliminar esta cosecha?")) return;
    try {
      await deleteHarvest(id);
      setHarvests(prev => prev.filter(h => h.harvestId !== id));
    } catch (e) { alert("Error: " + e.message); }
  };

  const handleDeletePostHarvest = async (id) => {
    if (!window.confirm("¿Eliminar registro?")) return;
    try {
      await deletePostHarvest(id);
      setPostHarvests(prev => prev.filter(ph => ph.postHarvestId !== id));
    } catch (e) { alert("Error: " + e.message); }
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
          <h2 style={{ color: '#2F4842', margin: 0 }}>Cosechas y Procesamiento</h2>
          <p style={{ color: '#666', margin: 0 }}>Gestión de recolección.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>
          + Registrar {activeSubTab === 'harvest' ? 'Cosecha' : 'Post-Cosecha'}
        </button>
      </div>

      <div style={{ display: 'flex', gap: '10px', marginBottom: '20px', borderBottom: '1px solid #eee', paddingBottom: '10px' }}>
        <button onClick={() => setActiveSubTab('harvest')} style={{ padding: '8px 16px', borderRadius: '20px', border: 'none', cursor: 'pointer', fontWeight: 'bold', background: activeSubTab === 'harvest' ? '#6B9080' : '#ddd', color: activeSubTab === 'harvest' ? 'white' : '#666' }}>Recolección</button>
        <button onClick={() => setActiveSubTab('postharvest')} style={{ padding: '8px 16px', borderRadius: '20px', border: 'none', cursor: 'pointer', fontWeight: 'bold', background: activeSubTab === 'postharvest' ? '#6B9080' : '#ddd', color: activeSubTab === 'postharvest' ? 'white' : '#666' }}>Procesamiento</button>
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
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '500px', maxHeight: '90vh', overflowY: 'auto' }}>
            <h3 style={{ color: '#2F4842' }}>{activeSubTab === 'harvest' ? 'Nueva Cosecha' : 'Nuevo Procesamiento'}</h3>

            <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '15px' }}>

              {activeSubTab === 'harvest' && (
                <>
                  <label>Seleccionar Cultivo</label>
                  <select className="admin-input" required value={formData.cultivationId || ''} onChange={e => setFormData({ ...formData, cultivationId: e.target.value })}>
                    <option value="">-- Elige un cultivo --</option>
                    {cultivations.map(c => <option key={c.cultivationId} value={c.cultivationId}>{c.plotName}</option>)}
                  </select>
                  <label>Cantidad</label>
                  <input type="number" className="admin-input" required value={formData.harvestQuantity || ''} onChange={e => setFormData({ ...formData, harvestQuantity: e.target.value })} />
                  <label>Unidad</label>
                  <select className="admin-input" value={formData.unitMeasure || 'Kg'} onChange={e => setFormData({ ...formData, unitMeasure: e.target.value })}>
                    <option value="Kg">Kilogramos</option><option value="Ton">Toneladas</option>
                  </select>
                  <label>Costo</label>
                  <input type="number" className="admin-input" value={formData.cost || ''} onChange={e => setFormData({ ...formData, cost: e.target.value })} />
                </>
              )}

              {activeSubTab === 'postharvest' && (
                <>
                  <label>Seleccionar Cosecha</label>
                  <select className="admin-input" required value={formData.harvestId || ''} onChange={e => setFormData({ ...formData, harvestId: e.target.value })}>
                    <option value="">-- Elige una cosecha --</option>
                    {harvests.map(h => <option key={h.harvestId} value={h.harvestId}>ID: {h.harvestId} - {h.harvestQuantity} {h.unitMeasure}</option>)}
                  </select>

                  {/* ⚠️ SELECTOR DE ESTADO ACTUALIZADO */}
                  <label style={{ color: '#2F4842', fontWeight: 'bold' }}>Estado del Proceso</label>
                  <select
                    className="admin-input"
                    required
                    value={formData.status || ''}
                    onChange={e => setFormData({ ...formData, status: e.target.value })}
                  >
                    <option value="">-- Selecciona Estado --</option>

                    {/* 👇 VALORES EXACTOS DE TU ENUM JAVA 👇 */}
                    <option value="EN_ALMACENAMIENTO">En Almacenamiento</option>
                    <option value="PROCESADA">Procesada</option>
                    <option value="CONVERTIDA_EN_PRODUCTO">Convertida en Producto</option>

                  </select>

                  <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div><label>Kg Comerciables</label><input type="number" className="admin-input" required value={formData.kgComerciables || ''} onChange={e => setFormData({ ...formData, kgComerciables: e.target.value })} /></div>
                    <div><label>Kg Pérdida</label><input type="number" className="admin-input" value={formData.lossKg || ''} onChange={e => setFormData({ ...formData, lossKg: e.target.value })} /></div>
                  </div>
                  <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                    <div><label>Precio/Kg</label><input type="number" className="admin-input" value={formData.priceKg || ''} onChange={e => setFormData({ ...formData, priceKg: e.target.value })} /></div>
                    <div><label>Costo Almacén</label><input type="number" className="admin-input" value={formData.storageCost || ''} onChange={e => setFormData({ ...formData, storageCost: e.target.value })} /></div>
                  </div>
                  <label>Observaciones</label>
                  <textarea className="admin-input" value={formData.observations || ''} onChange={e => setFormData({ ...formData, observations: e.target.value })} />
                </>
              )}

              <div style={{ marginTop: '20px', textAlign: 'right' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ marginRight: '10px' }}>Cancelar</button>
                <button type="submit" className="btn-primary">Guardar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
// --- COMPONENTE CRUD DE PEDIDOS (COMPRAS A PROVEEDORES) ---
const OrdersCrud = () => {
  const [orders, setOrders] = useState([]);
  const [suppliers, setSuppliers] = useState([]);
  const [inputs, setInputs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [selectedOrderDetails, setSelectedOrderDetails] = useState([]);

  // Formulario de Pedido
  const [supplierId, setSupplierId] = useState('');

  // Carrito de compras interno
  const [cartItems, setCartItems] = useState([]);
  const [currentItem, setCurrentItem] = useState({ inputId: '', amount: '', priceUnit: '' });

  useEffect(() => {
    loadInitialData();
  }, []);

  const loadInitialData = async () => {
    setLoading(true);
    try {
      const [ordersData, suppliersData, inputsData] = await Promise.all([
        getOrders(),
        getSuppliers(),
        getInsumos()
      ]);
      setOrders(ordersData);
      setSuppliers(suppliersData);
      setInputs(inputsData);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // --- CARRITO ---
  const addItemToCart = () => {
    if (!currentItem.inputId || !currentItem.amount || !currentItem.priceUnit) {
      alert("Completa los datos del ítem");
      return;
    }
    // Buscamos el insumo en la lista cargada para obtener su nombre
    // Ojo: tu servicio puede devolver 'idInsumo' o 'id'. Ajusta si es necesario.
    const inputObj = inputs.find(i => (i.inputId || i.id || i.idInsumo) === parseInt(currentItem.inputId));

    const newItem = {
      ...currentItem,
      inputName: inputObj ? (inputObj.nombre || inputObj.name) : 'Item',
      totalLine: parseInt(currentItem.amount) * parseFloat(currentItem.priceUnit)
    };

    setCartItems([...cartItems, newItem]);
    setCurrentItem({ inputId: '', amount: '', priceUnit: '' });
  };

  const removeItemFromCart = (index) => {
    const newCart = [...cartItems];
    newCart.splice(index, 1);
    setCartItems(newCart);
  };

  const calculateTotalOrder = () => {
    return cartItems.reduce((acc, item) => acc + item.totalLine, 0);
  };

  // --- GUARDAR PEDIDO ---
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!supplierId) { alert("Selecciona un proveedor"); return; }
    if (cartItems.length === 0) { alert("El pedido está vacío"); return; }

    try {
      const payload = {
        order: {
          supplier: { supplierId: parseInt(supplierId) }, // Tu Java Order espera un objeto Supplier
          total: calculateTotalOrder(),
          date: new Date().toISOString().split('T')[0]
        },
        details: cartItems.map(item => ({
          input: { inputId: parseInt(item.inputId) }, // Tu Java OrderDetail espera objeto Input
          amount: parseInt(item.amount),
          priceUnit: parseFloat(item.priceUnit)
        }))
      };

      await createOrder(payload);
      alert("Pedido registrado correctamente!");

      setShowModal(false);
      setSupplierId('');
      setCartItems([]);
      loadInitialData();
    } catch (err) {
      console.error(err);
      alert("Error al guardar pedido. Revisa la consola.");
    }
  };

  // --- VER DETALLES ---
  const handleViewDetails = async (orderId) => {
    try {
      const details = await getOrderDetails(orderId);
      setSelectedOrderDetails(details);
      setShowDetailModal(true);
    } catch (err) {
      alert("Error cargando detalles");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Eliminar pedido?")) return;
    try {
      await deleteOrder(id);
      setOrders(prev => prev.filter(o => o.orderId !== id));
    } catch (e) { alert(e.message); }
  };

  return (
    <div className="admin-card">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '20px' }}>
        <div>
          <h2 style={{ color: '#2F4842', margin: 0 }}>Gestión de Pedidos</h2>
          <p style={{ color: '#666', margin: 0 }}>Compras a Proveedores.</p>
        </div>
        <button className="btn-primary" onClick={() => setShowModal(true)}>+ Nuevo Pedido</button>
      </div>

      {loading ? <p>Cargando...</p> : (
        <div className="table-container">
          <table className="admin-table">
            <thead>
              <tr><th>ID</th><th>Fecha</th><th>Proveedor</th><th>Total</th><th>Acciones</th></tr>
            </thead>
            <tbody>
              {orders.map(o => (
                <tr key={o.orderId}>
                  <td>{o.orderId}</td>
                  <td>{o.date}</td>
                  {/* Intentamos mostrar el nombre del proveedor con varios fallbacks */}
                  <td>{o.supplier ? (o.supplier.name || o.supplier.nombre || o.supplier.nombreEmpresa || 'Prov. ID ' + o.supplier.supplierId) : 'Sin Prov.'}</td>
                  <td style={{ fontWeight: 'bold' }}>${o.total}</td>
                  <td>
                    <button className="btn-edit" onClick={() => handleViewDetails(o.orderId)} style={{ marginRight: '5px' }}>Ver Items</button>
                    <button className="btn-delete" onClick={() => handleDelete(o.orderId)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* --- MODAL DE REGISTRO --- */}
      {showModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '700px', maxHeight: '90vh', overflowY: 'auto' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Registrar Pedido</h3>

            <div style={{ marginBottom: '20px', paddingBottom: '20px', borderBottom: '1px solid #eee' }}>
              <label style={{ fontWeight: 'bold' }}>Proveedor</label>
              <select className="admin-input" value={supplierId} onChange={e => setSupplierId(e.target.value)}>
                <option value="">-- Selecciona Proveedor --</option>
                {suppliers.map(s => (
                  // Usamos fallbacks para ID y Nombre por si acaso
                  <option key={s.supplierId || s.id || s.idProveedor} value={s.supplierId || s.id || s.idProveedor}>
                    {s.name || s.nombre || s.nombreEmpresa}
                  </option>
                ))}
              </select>
            </div>

            <h4 style={{ margin: '0 0 10px 0' }}>Agregar Insumos al Pedido</h4>
            <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr 1fr 0.5fr', gap: '10px', alignItems: 'end' }}>
              <div>
                <label style={{ fontSize: '12px' }}>Insumo</label>
                <select className="admin-input" style={{ margin: 0 }}
                  value={currentItem.inputId} onChange={e => setCurrentItem({ ...currentItem, inputId: e.target.value })}>
                  <option value="">- Elegir -</option>
                  {inputs.map(i => (
                    <option key={i.inputId || i.id || i.idInsumo} value={i.inputId || i.id || i.idInsumo}>
                      {i.nombre || i.name}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label style={{ fontSize: '12px' }}>Cantidad</label>
                <input type="number" className="admin-input" style={{ margin: 0 }} placeholder="0"
                  value={currentItem.amount} onChange={e => setCurrentItem({ ...currentItem, amount: e.target.value })} />
              </div>
              <div>
                <label style={{ fontSize: '12px' }}>Precio U.</label>
                <input type="number" className="admin-input" style={{ margin: 0 }} placeholder="$0.00"
                  value={currentItem.priceUnit} onChange={e => setCurrentItem({ ...currentItem, priceUnit: e.target.value })} />
              </div>
              <button type="button" onClick={addItemToCart} style={{ background: '#2F4842', color: 'white', border: 'none', height: '42px', borderRadius: '4px', cursor: 'pointer' }}>+</button>
            </div>

            <div style={{ marginTop: '20px', background: '#f9f9f9', padding: '10px', borderRadius: '4px' }}>
              <table style={{ width: '100%', fontSize: '14px' }}>
                <thead>
                  <tr style={{ textAlign: 'left' }}><th>Insumo</th><th>Cant.</th><th>Precio</th><th>Subtotal</th><th></th></tr>
                </thead>
                <tbody>
                  {cartItems.map((item, index) => (
                    <tr key={index}>
                      <td>{item.inputName}</td>
                      <td>{item.amount}</td>
                      <td>${item.priceUnit}</td>
                      <td>${item.totalLine}</td>
                      <td><button onClick={() => removeItemFromCart(index)} style={{ color: 'red', border: 'none', background: 'none', cursor: 'pointer' }}>X</button></td>
                    </tr>
                  ))}
                </tbody>
              </table>
              <div style={{ textAlign: 'right', marginTop: '10px', fontSize: '18px', fontWeight: 'bold', color: '#2F4842' }}>
                Total: ${calculateTotalOrder()}
              </div>
            </div>

            <div style={{ marginTop: '20px', textAlign: 'right' }}>
              <button onClick={() => setShowModal(false)} style={{ marginRight: '10px', padding: '10px 20px', background: 'none', border: '1px solid #ccc', borderRadius: '4px', cursor: 'pointer' }}>Cancelar</button>
              <button onClick={handleSubmit} className="btn-primary">Guardar Pedido</button>
            </div>
          </div>
        </div>
      )}

      {/* --- MODAL DETALLES --- */}
      {showDetailModal && (
        <div style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, background: 'rgba(0,0,0,0.6)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div style={{ background: 'white', padding: '30px', borderRadius: '12px', width: '500px', maxHeight: '80vh', overflowY: 'auto' }}>
            <h3 style={{ color: '#2F4842', marginTop: 0 }}>Detalles del Pedido</h3>
            <table className="admin-table">
              <thead><tr><th>Insumo</th><th>Cant.</th><th>Precio U.</th></tr></thead>
              <tbody>
                {selectedOrderDetails.map(d => (
                  <tr key={d.detailId}>
                    <td>{d.input ? (d.input.nombre || d.input.name) : 'Item'}</td>
                    <td>{d.amount}</td>
                    <td>${d.priceUnit}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div style={{ textAlign: 'right', marginTop: '20px' }}>
              <button onClick={() => setShowDetailModal(false)} className="btn-primary">Cerrar</button>
            </div>
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
      // --- DASHBOARD ---
      case 'home': return <DashboardHome />;

      // --- ADMINISTRACIÓN ---
      case 'users': return <UsersView />;
      // --- PERSONAS ---
      case 'customers': return <CustomersCrud />;
      case 'jobPositions': return <JobPositionsCrud />;
      case 'employees': return <EmployeesView />;
      case 'suppliers': return <SuppliersCrud />;
      // --- COMERCIAL ---
      case 'products': return <ProductsView />;
      case 'orders': return <OrdersCrud />;
      case 'inputs': return <InputsCrud />;
      // --- GESTIÓN AGRÍCOLA ---
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
          <button
            className={activeTab === 'jobPositions' ? 'active' : ''}
            onClick={() => setActiveTab('jobPositions')}
            style={{ fontSize: '13px', marginLeft: '15px' }}
          >
            💼 Cargos / Puestos
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
          <button
            className={activeTab === 'inputs' ? 'active' : ''}
            onClick={() => setActiveTab('inputs')}
          >
            🧪 Insumos
          </button>
          <button className={activeTab === 'orders' ? 'active' : ''} onClick={() => setActiveTab('orders')}>
            🛒 Pedidos
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