<<<<<<< HEAD
const API_AUTH = 'http://localhost:8080/auth';
const API_BASE = 'http://localhost:8080/api';

// ========= AUTH =========
export async function login(email, password) {
  const res = await fetch(`${API_AUTH}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  if (!res.ok) throw new Error('Credenciales inválidas');
  const data = await res.json();

  // Guardar token en localStorage
  if (data.token) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('userName', data.userName || email);
=======
// src/services/authService.js
const API_URL = 'http://localhost:8080/auth';
const API_BASE = 'http://localhost:8080';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  return {
    'Content-Type': 'application/json',
    ...(token && { 'Authorization': `Bearer ${token}` })
  };
};

// Helper para manejar respuestas y mostrar mensaje de error detallado
async function handleResponse(res, defaultMsg) {
  if (res.ok) {
    // Some endpoints return empty body on DELETE
    const text = await res.text();
    try { return text ? JSON.parse(text) : null; } catch(e) { return text; }
  }
  let body = null;
  try { body = await res.text(); } catch (e) { /* ignore */ }
  let detail = body;
  if (body) {
    try { const j = JSON.parse(body); detail = j.message || j.error || body; } catch (e) { /* keep body */ }
  }
  throw new Error(`${defaultMsg} (status ${res.status})${detail ? ': ' + detail : ''}`);
}

// ==================== AUTH ====================
export async function login(correo, contraseña) {
  const res = await fetch(`${API_URL}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email: correo, password: contraseña })
  });
  if (!res.ok) throw new Error('Credenciales inválidas');
  const data = await res.json();
  if (data.token) {
    localStorage.setItem('token', data.token);
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
  }
  return data;
}

export async function register(formData) {
  const res = await fetch(`${API_AUTH}/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(formData)
  });
  if (!res.ok) throw new Error('Error al registrar usuario');
<<<<<<< HEAD
  return res.json();
=======
  const data = await res.json();
  return data;
}

// ==================== USUARIOS ====================
export async function getUsuarios() {
  const res = await fetch(`${API_BASE}/api/users/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener usuarios');
  return await res.json();
}

export async function getUsuarioById(id) {
  const res = await fetch(`${API_BASE}/api/users/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener usuario');
  return await res.json();
}

export async function actualizarUsuario(email, datos) {
  const res = await fetch(`${API_BASE}/api/users/update/${email}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(datos)
  });
  if (!res.ok) throw new Error('Error al actualizar usuario');
  return await res.json();
}

export async function eliminarUsuario(id) {
  const res = await fetch(`${API_BASE}/api/users/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar usuario');
  return await res.json();
}

// ==================== PRODUCTOS ====================
export async function getProductos() {
  const res = await fetch(`${API_BASE}/api/product/all`, { headers: getAuthHeaders() });
  return await handleResponse(res, 'Error al obtener productos');
}

export async function getProductoById(id) {
  const res = await fetch(`${API_BASE}/api/product/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener producto');
  return await res.json();
}

export async function crearProducto(producto) {
  const headers = getAuthHeaders();
  console.debug('crearProducto headers:', headers);
  const res = await fetch(`${API_BASE}/api/product/register`, {
    method: 'POST',
    headers,
    body: JSON.stringify(producto)
  });
  return await handleResponse(res, 'Error al crear producto');
}

export async function actualizarProducto(id, producto) {
  const res = await fetch(`${API_BASE}/api/product/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(producto)
  });
  if (!res.ok) throw new Error('Error al actualizar producto');
  return await res.json();
}

export async function eliminarProducto(id) {
  const res = await fetch(`${API_BASE}/api/product/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar producto');
  return await res.json();
}

// ==================== VENTAS ====================
export async function getVentas() {
  const res = await fetch(`${API_BASE}/api/sale/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener ventas');
  return await res.json();
}

export async function getVentaById(id) {
  const res = await fetch(`${API_BASE}/api/sale/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener venta');
  return await res.json();
}

export async function crearVenta(venta) {
  const res = await fetch(`${API_BASE}/api/sale/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(venta)
  });
  if (!res.ok) throw new Error('Error al crear venta');
  return await res.json();
}

export async function actualizarVenta(id, venta) {
  const res = await fetch(`${API_BASE}/api/sale/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(venta)
  });
  if (!res.ok) throw new Error('Error al actualizar venta');
  return await res.json();
}

export async function eliminarVenta(id) {
  const res = await fetch(`${API_BASE}/api/sale/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar venta');
  return await res.json();
}

// ==================== CULTIVOS ====================
export async function getCultivos() {
  const res = await fetch(`${API_BASE}/api/cultivation/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cultivos');
  return await res.json();
}

export async function getCultivoById(id) {
  const res = await fetch(`${API_BASE}/api/cultivation/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cultivo');
  return await res.json();
}

export async function crearCultivo(cultivo) {
  const res = await fetch(`${API_BASE}/api/cultivation/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cultivo)
  });
  if (!res.ok) throw new Error('Error al crear cultivo');
  return await res.json();
}

export async function actualizarCultivo(id, cultivo) {
  const res = await fetch(`${API_BASE}/api/cultivation/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cultivo)
  });
  if (!res.ok) throw new Error('Error al actualizar cultivo');
  return await res.json();
}

export async function eliminarCultivo(id) {
  const res = await fetch(`${API_BASE}/api/cultivation/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cultivo');
  return await res.json();
}

// ==================== CLIENTES ====================
export async function getClientes() {
  const res = await fetch(`${API_BASE}/api/customer/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener clientes');
  return await res.json();
}

export async function getClienteById(id) {
  const res = await fetch(`${API_BASE}/api/customer/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cliente');
  return await res.json();
}

export async function crearCliente(cliente) {
  const res = await fetch(`${API_BASE}/api/customer/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cliente)
  });
  if (!res.ok) throw new Error('Error al crear cliente');
  return await res.json();
}

export async function actualizarCliente(id, cliente) {
  const res = await fetch(`${API_BASE}/api/customer/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cliente)
  });
  if (!res.ok) throw new Error('Error al actualizar cliente');
  return await res.json();
}

export async function eliminarCliente(id) {
  const res = await fetch(`${API_BASE}/api/customer/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cliente');
  return await res.json();
}

// ==================== EMPLEADOS ====================
export async function getEmpleados() {
  const res = await fetch(`${API_BASE}/api/employee/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener empleados');
  return await res.json();
}

export async function getEmpleadoById(id) {
  const res = await fetch(`${API_BASE}/api/employee/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener empleado');
  return await res.json();
}

export async function crearEmpleado(empleado) {
  const res = await fetch(`${API_BASE}/api/employee/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(empleado)
  });
  if (!res.ok) throw new Error('Error al crear empleado');
  return await res.json();
}

export async function actualizarEmpleado(id, empleado) {
  const res = await fetch(`${API_BASE}/api/employee/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(empleado)
  });
  if (!res.ok) throw new Error('Error al actualizar empleado');
  return await res.json();
}

export async function eliminarEmpleado(id) {
  const res = await fetch(`${API_BASE}/api/employee/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar empleado');
  return await res.json();
}

// ==================== COSECHAS ====================
export async function getCosechas() {
  const res = await fetch(`${API_BASE}/api/harvest/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cosechas');
  return await res.json();
}

export async function getCosechaById(id) {
  const res = await fetch(`${API_BASE}/api/harvest/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener cosecha');
  return await res.json();
}

export async function crearCosecha(cosecha) {
  const res = await fetch(`${API_BASE}/api/harvest/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(cosecha)
  });
  if (!res.ok) throw new Error('Error al crear cosecha');
  return await res.json();
}

export async function actualizarCosecha(id, cosecha) {
  const res = await fetch(`${API_BASE}/api/harvest/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(cosecha)
  });
  if (!res.ok) throw new Error('Error al actualizar cosecha');
  return await res.json();
}

export async function eliminarCosecha(id) {
  const res = await fetch(`${API_BASE}/api/harvest/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar cosecha');
  return await res.json();
}

// ==================== PROVEEDORES ====================
export async function getProveedores() {
  const res = await fetch(`${API_BASE}/api/supplier/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener proveedores');
  return await res.json();
}

export async function getProveedorById(id) {
  const res = await fetch(`${API_BASE}/api/supplier/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener proveedor');
  return await res.json();
}

export async function crearProveedor(proveedor) {
  const res = await fetch(`${API_BASE}/api/supplier/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(proveedor)
  });
  if (!res.ok) throw new Error('Error al crear proveedor');
  return await res.json();
}

export async function actualizarProveedor(id, proveedor) {
  const res = await fetch(`${API_BASE}/api/supplier/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(proveedor)
  });
  if (!res.ok) throw new Error('Error al actualizar proveedor');
  return await res.json();
}

export async function eliminarProveedor(id) {
  const res = await fetch(`${API_BASE}/api/supplier/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar proveedor');
  return await res.json();
}

// ==================== INSUMOS ====================
export async function getInsumos() {
  const res = await fetch(`${API_BASE}/api/input/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener insumos');
  return await res.json();
}

export async function getInsumoById(id) {
  const res = await fetch(`${API_BASE}/api/input/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener insumo');
  return await res.json();
}

export async function crearInsumo(insumo) {
  const res = await fetch(`${API_BASE}/api/input/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(insumo)
  });
  if (!res.ok) throw new Error('Error al crear insumo');
  return await res.json();
}

export async function actualizarInsumo(id, insumo) {
  const res = await fetch(`${API_BASE}/api/input/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(insumo)
  });
  if (!res.ok) throw new Error('Error al actualizar insumo');
  return await res.json();
}

export async function eliminarInsumo(id) {
  const res = await fetch(`${API_BASE}/api/input/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar insumo');
  return await res.json();
}

// ==================== PÉRDIDAS ====================
export async function getPerdidas() {
  const res = await fetch(`${API_BASE}/api/loss/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener pérdidas');
  return await res.json();
}

export async function getPerdidaById(id) {
  const res = await fetch(`${API_BASE}/api/loss/id/${id}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener pérdida');
  return await res.json();
}

export async function crearPerdida(perdida) {
  const res = await fetch(`${API_BASE}/api/loss/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(perdida)
  });
  if (!res.ok) throw new Error('Error al crear pérdida');
  return await res.json();
}

export async function actualizarPerdida(id, perdida) {
  const res = await fetch(`${API_BASE}/api/loss/update/${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(perdida)
  });
  if (!res.ok) throw new Error('Error al actualizar pérdida');
  return await res.json();
}

export async function eliminarPerdida(id) {
  const res = await fetch(`${API_BASE}/api/loss/delete/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al eliminar pérdida');
  return await res.json();
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
}

export function logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('userName');
}

// ==================== HELPERS ====================
function getAuthHeaders() {
  const token = localStorage.getItem('token');
  const headers = { 'Content-Type': 'application/json' };
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  return headers;
}

async function handleResponse(res, path, method = 'GET') {
  if (res.status === 401 || res.status === 403) {
    throw new Error(`Acceso denegado (${method} ${path}). Verifica tu sesión o token.`);
  }
  if (!res.ok) {
    throw new Error(`Error ${method} ${path}`);
  }
  return res.json();
}

async function apiGet(path) {
  const res = await fetch(`${API_BASE}${path}`, { headers: getAuthHeaders() });
  return handleResponse(res, path, 'GET');
}

async function apiPost(path, body) {
  const res = await fetch(`${API_BASE}${path}`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(body)
  });
  return handleResponse(res, path, 'POST');
}

async function apiPut(path, body) {
  const res = await fetch(`${API_BASE}${path}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(body)
  });
  return handleResponse(res, path, 'PUT');
}

async function apiDelete(path) {
  const res = await fetch(`${API_BASE}${path}`, {
    method: 'DELETE',
    headers: getAuthHeaders()
  });
  if (res.status === 401 || res.status === 403) {
    throw new Error(`Acceso denegado (DELETE ${path}). Verifica tu sesión o token.`);
  }
  if (!res.ok) throw new Error(`Error DELETE ${path}`);
  return res.ok;
}

// ==================== SERVICIOS ====================
export const cultivationService = {
  list: () => apiGet('/cultivation/all'),                
  getBySeason: season => apiGet(`/cultivation/season?season=${season}`), 
  get: id => apiGet(`/cultivation/id/${id}`),            
  create: body => apiPost('/cultivation/register', body), 
  update: (id, body) => apiPut(`/cultivation/update/${id}`, body), 
  remove: id => apiDelete(`/cultivation/delete/${id}`)    
};


<<<<<<< HEAD
export const customerService = {
  list: () => apiGet('/customer/all'),                
  getByType: type => apiGet(`/customer/type?type=${type}`), 
  get: id => apiGet(`/customer/id/${id}`),            
  create: body => apiPost('/customer/register', body), 
  update: (id, body) => apiPut(`/customer/update/${id}`, body), 
  remove: id => apiDelete(`/customer/delete/${id}`)    
};


export const employeeService = {
  list: () => apiGet('/employee/all'),                
  get: id => apiGet(`/employee/id/${id}`),             
  getByEmail: email => apiGet(`/employee/email/${email}`), 
  create: body => apiPost('/employee/register', body), 
  update: (id, body) => apiPut(`/employee/update/${id}`, body), 
  remove: id => apiDelete(`/employee/delete/${id}`)    
};


export const harvestService = {
  list: () => apiGet('/harvest/all'),                 
  get: id => apiGet(`/harvest/id/${id}`),             
  create: body => apiPost('/harvest/register', body), 
  update: (id, body) => apiPut(`/harvest/update/${id}`, body), 
  remove: id => apiDelete(`/harvest/delete/${id}`)    
};


export const imageService = {
  list: () => apiGet('/images/all'),                    
  upload: body => apiPost('/images/upload', body),       
  searchRelated: (tipo, idReferencia) => 
    apiGet(`/images/${tipo}/idReferencia?idReferencia=${idReferencia}`) 
};

export const inputService = {
  list: () => apiGet('/input/all'),                 
  get: id => apiGet(`/input/id/${id}`),           
  create: body => apiPost('/input/register', body), 
  update: (id, body) => apiPut(`/input/update/${id}`, body),
  remove: id => apiDelete(`/input/delete/${id}`)    
};


export const inputCultivationService = {
  list: () => apiGet('/inputCultivation/all'),              
  get: id => apiGet(`/inputCultivation/id/${id}`),           
  create: body => apiPost('/inputCultivation/register', body), 
  update: (id, body) => apiPut(`/inputCultivation/update/${id}`, body), 
  remove: id => apiDelete(`/inputCultivation/delete/${id}`)    
};


export const inputSupplierService = {
  list: () => apiGet('/inputSuplier/all'),               
  get: id => apiGet(`/inputSuplier/id/${id}`),            
  create: body => apiPost('/inputSuplier/register', body), 
  update: (id, body) => apiPut(`/inputSuplier/update/${id}`, body), 
  remove: id => apiDelete(`/inputSuplier/delete/${id}`)    
};

export const jobPositionService = {
  list: () => apiGet('/jobPosition/all'),
  get: id => apiGet(`/jobPosition/id/${id}`),
  create: body => apiPost('/jobPosition/register', body),
  update: (id, body) => apiPut(`/jobPosition/update/${id}`, body),
  remove: id => apiDelete(`/jobPosition/delete/${id}`)
};

export const lossService = {
  list: () => apiGet('/loss/all'),             
  get: id => apiGet(`/loss/id/${id}`),          
  create: body => apiPost('/loss/register', body), 
  update: (id, body) => apiPut(`/loss/update/${id}`, body), 
  remove: id => apiDelete(`/loss/delete/${id}`) 
};


export const plantCategoryService = {
  list: () => apiGet('/plantCategory/all'),
  get: id => apiGet(`/plantCategory/id/${id}`),
  create: body => apiPost('/plantCategory/register', body),
  update: (id, body) => apiPut(`/plantCategory/update/${id}`, body),
  remove: id => apiDelete(`/plantCategory/delete/${id}`)
};


export const plantService = {
  list: () => apiGet('/plant/all'),             
  get: id => apiGet(`/plant/id/${id}`),         
  create: body => apiPost('/plant/register', body), 
  update: (id, body) => apiPut(`/plant/update/${id}`, body), 
  remove: id => apiDelete(`/plant/delete/${id}`) 
};


export const postHarvestService = {
  list: () => apiGet('/postHarvest/all'),          
  get: id => apiGet(`/postHarvest/id/${id}`),          
  create: body => apiPost('/postHarvest/register', body), 
  update: (id, body) => apiPut(`/postHarvest/update/${id}`, body), 
  remove: id => apiDelete(`/postHarvest/delete/${id}`) 
};


export const productService = {
  list: () => apiGet('/product/all'),                  
  get: id => apiGet(`/product/id/${id}`),              
  getPriceLess: price => apiGet(`/product/priceLess?price=${price}`), 
  getActive: active => apiGet(`/product/active?active=${active}`),    
  create: body => apiPost('/product/register', body), 
  update: (id, body) => apiPut(`/product/update/${id}`, body), 
  remove: id => apiDelete(`/product/delete/${id}`)    
};


export const saleService = {
  list: () => apiGet('/sale/all'),          
  get: id => apiGet(`/sale/id/${id}`),         
  create: body => apiPost('/sale/register', body),
  update: (id, body) => apiPut(`/sale/update/${id}`, body), 
  remove: id => apiDelete(`/sale/delete/${id}`) 
};


export const saleDetailsService = {
  list: () => apiGet('/saleDetails/all'),                
  get: id => apiGet(`/saleDetails/id/${id}`),            
  getBySale: sale => apiPost('/saleDetails/sale', sale),  
  createAll: details => apiPost('/saleDetails/registerAll', details) 
};


export const supplierService = {
  list: () => apiGet('/supplier/all'),             
  get: id => apiGet(`/supplier/id/${id}`),         
  create: body => apiPost('/supplier/register', body), 
  update: (id, body) => apiPut(`/supplier/update/${id}`, body), 
  remove: id => apiDelete(`/supplier/delete/${id}`) 
};


export const userService = {
  list: () => apiGet('/users/all'),
  get: id => apiGet(`/users/id/${id}`),
  create: body => apiPost('/users', body),
  update: (email, body) => apiPut(`/users/update/${email}`, body),
  remove: id => apiDelete(`/users/delete/${id}`)
};
=======
// ==================== CREAR USUARIO (ALIAS PARA REGISTRO) ====================
export async function crearUsuario(usuario) {
  const res = await fetch(`${API_URL}/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(usuario)
  });
  if (!res.ok) throw new Error('Error al crear usuario');
  return await res.json();
}

// ==================== VENTAS (SOLO LECTURA) ====================
// Ya existen getVentas y getVentaById. Para crear/editar/eliminar, NO permitir desde panel.

// ==================== IMÁGENES ====================
export async function getImagenes() {
  const res = await fetch(`${API_BASE}/api/images/all`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener imágenes');
  return await res.json();
}

export async function subirImagen(imagen) {
  const res = await fetch(`${API_BASE}/api/images/upload`, {
    method: 'POST',
    headers: getAuthHeaders(),
    body: JSON.stringify(imagen)
  });
  if (!res.ok) throw new Error('Error al subir imagen');
  return await res.json();
}

export async function getImagenesPorEntidad(entityType, referenceId) {
  const res = await fetch(`${API_BASE}/api/images/${entityType}/${referenceId}`, {
    headers: getAuthHeaders()
  });
  if (!res.ok) throw new Error('Error al obtener imágenes por entidad');
  return await res.json();
}
>>>>>>> 820fd712dfbb147e9f07f536cad78d77574f028b
