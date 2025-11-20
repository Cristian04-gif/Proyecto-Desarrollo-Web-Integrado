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
  //const params = new URLSearchParams({ email, contraseña });
  const res = await fetch(`${API_URL}/login`, {
    method: 'POST', headers: {
      'Content-Type': 'application/json'
    }, body: JSON.stringify({ email: correo, password: contraseña })
  });
  if (!res.ok) throw new Error('Credenciales inválidas');
  return await res.json().then(data => {
    if (data.token && data.email) {
      localStorage.setItem('token', data.token);
      localStorage.setItem("user", data.email)
      window.location.href = 'http://localhost:5173/';
    }
  });
}

export async function register(formData) {
  const res = await fetch(`${API_URL}/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  });
  if (!res.ok) throw new Error('Error al registrar usuario');
  return await res.json().then(window.location.href = 'http://localhost:5173/login');
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
}



// ==================== CREAR USUARIO (ALIAS PARA REGISTRO) ====================
export async function crearUsuario(usuario) {
  const res = await fetch(`${API_URL}/register`, {
    method: 'POST',
    headers: getAuthHeaders(),
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
// ==================== HELPERS ====================

// Recuperar token actual
export function getToken() {
  return localStorage.getItem('token');
}

// Saber si el usuario está logeado
export function isLoggedIn() {
  return !!getToken();
}

// Cerrar sesión
export function logout() {
  localStorage.removeItem('token');
}