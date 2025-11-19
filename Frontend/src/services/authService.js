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
  return res.json();
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
