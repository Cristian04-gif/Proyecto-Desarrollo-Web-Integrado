import React, { useEffect, useState } from 'react';
import AdminTable from '../components/AdminTable';
import Layout from '../components/Layout';
import {
  getCultivos, crearCultivo, actualizarCultivo, eliminarCultivo,
  getClientes, crearCliente, actualizarCliente, eliminarCliente,
  getEmpleados, crearEmpleado, actualizarEmpleado, eliminarEmpleado,
  getCosechas, crearCosecha, actualizarCosecha, eliminarCosecha,
  getProveedores, crearProveedor, actualizarProveedor, eliminarProveedor,
  getInsumos, crearInsumo, actualizarInsumo, eliminarInsumo,
  getPerdidas, crearPerdida, actualizarPerdida, eliminarPerdida,
  getUsuarios, crearUsuario, actualizarUsuario, eliminarUsuario,
  getProductos, crearProducto, actualizarProducto, eliminarProducto,
  getVentas, subirImagen
} from '../services/authService';

export default function AdminPanel() {
  const [activeTab, setActiveTab] = useState('usuarios');
  const [usuarios, setUsuarios] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [empleados, setEmpleados] = useState([]);
  const [productos, setProductos] = useState([]);
  const [cultivos, setCultivos] = useState([]);
  const [cosechas, setCosechas] = useState([]);
  const [perdidas, setPerdidas] = useState([]);
  const [insumos, setInsumos] = useState([]);
  const [proveedores, setProveedores] = useState([]);
  const [ventas, setVentas] = useState([]);

  useEffect(() => {
    cargarTodos();
  }, []);

  const cargarTodos = async () => {
    try {
      const [usuariosRes, clientesRes, empleadosRes, productosRes, cultivosRes, cosechasRes, perdidasRes, insumosRes, proveedoresRes, ventasRes] = await Promise.all([
        getUsuarios(),
        getClientes(),
        getEmpleados(),
        getProductos(),
        getCultivos(),
        getCosechas(),
        getPerdidas(),
        getInsumos(),
        getProveedores(),
        getVentas()
      ]);
      setUsuarios(usuariosRes || []);
      setClientes(clientesRes || []);
      setEmpleados(empleadosRes || []);
      setProductos(productosRes || []);
      setCultivos(cultivosRes || []);
      setCosechas(cosechasRes || []);
      setPerdidas(perdidasRes || []);
      setInsumos(insumosRes || []);
      setProveedores(proveedoresRes || []);
      setVentas(ventasRes || []);
    } catch (error) {
      alert('Error cargando datos: ' + error.message);
    }
  };

  const leerFileComoBase64 = (file) => {
    return new Promise((resolve) => {
      if (!file) return resolve(null);
      const reader = new FileReader();
      reader.onload = () => resolve(reader.result.split(',')[1]);
      reader.readAsDataURL(file);
    });
  };

  // ==================== USUARIOS ====================
  const validarUsuario = (u) => {
    if (!u.firstName?.trim()) throw new Error('Nombre requerido');
    if (!u.lastName?.trim()) throw new Error('Apellido requerido');
    if (!u.email?.trim()) throw new Error('Email requerido');
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(u.email)) throw new Error('Email inválido');
    if (!u.password?.trim()) throw new Error('Contraseña requerida');
    if (u.password.length < 6) throw new Error('Contraseña mín. 6 caracteres');
  };

  const handleAddUsuario = async (data) => {
    try {
      validarUsuario(data);
      await crearUsuario(data);
      await cargarTodos();
      alert('Usuario creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditUsuario = async (id, data) => {
    try {
      validarUsuario(data);
      await actualizarUsuario(data.email, data);
      await cargarTodos();
      alert('Usuario actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteUsuario = async (id) => {
    try {
      if (window.confirm('¿Eliminar usuario?')) {
        await eliminarUsuario(id);
        await cargarTodos();
        alert('Usuario eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== CLIENTES ====================
  const validarCliente = (c) => {
    if (!c.userId) throw new Error('Usuario requerido');
    if (!c.phone?.trim()) throw new Error('Teléfono requerido');
    if (c.age && (isNaN(c.age) || c.age < 0 || c.age > 150)) throw new Error('Edad inválida');
  };

  const handleAddCliente = async (data) => {
    try {
      validarCliente(data);
      const payload = { ...data, user: { userId: data.userId } };
      await crearCliente(payload);
      await cargarTodos();
      alert('Cliente creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditCliente = async (id, data) => {
    try {
      validarCliente(data);
      const payload = { ...data, user: { userId: data.userId } };
      await actualizarCliente(id, payload);
      await cargarTodos();
      alert('Cliente actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteCliente = async (id) => {
    try {
      if (window.confirm('¿Eliminar cliente?')) {
        await eliminarCliente(id);
        await cargarTodos();
        alert('Cliente eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== EMPLEADOS ====================
  const validarEmpleado = (e) => {
    if (!e.firstName?.trim()) throw new Error('Nombre requerido');
    if (!e.lastName?.trim()) throw new Error('Apellido requerido');
    if (!e.workEmail?.trim()) throw new Error('Email laboral requerido');
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(e.workEmail)) throw new Error('Email inválido');
    if (e.age && (isNaN(e.age) || e.age < 18 || e.age > 80)) throw new Error('Edad inválida (18-80)');
    if (e.salary && (isNaN(e.salary) || e.salary <= 0)) throw new Error('Salario debe ser > 0');
  };

  const handleAddEmpleado = async (data) => {
    try {
      validarEmpleado(data);
      await crearEmpleado(data);
      await cargarTodos();
      alert('Empleado creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditEmpleado = async (id, data) => {
    try {
      validarEmpleado(data);
      await actualizarEmpleado(id, data);
      await cargarTodos();
      alert('Empleado actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteEmpleado = async (id) => {
    try {
      if (window.confirm('¿Eliminar empleado?')) {
        await eliminarEmpleado(id);
        await cargarTodos();
        alert('Empleado eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== PRODUCTOS ====================
  const validarProducto = (p) => {
    if (!p.name?.trim()) throw new Error('Nombre requerido');
    if (p.weight && (isNaN(p.weight) || p.weight <= 0)) throw new Error('Peso > 0');
    if (p.price && (isNaN(p.price) || p.price <= 0)) throw new Error('Precio > 0');
    if (p.stock && (isNaN(p.stock) || p.stock < 0)) throw new Error('Stock >= 0');
  };

  const handleAddProducto = async (data) => {
    try {
      validarProducto(data);
      const payload = {
        name: data.name,
        description: data.description || '',
        weight: data.weight ? parseFloat(data.weight) : 0,
        price: data.price ? parseFloat(data.price) : 0,
        stock: data.stock ? parseInt(data.stock) : 0,
        active: !!data.active,
        imageUrl: ''
      };
      const created = await crearProducto(payload);
      const productId = created?.productId || created?.id;

      if (data.imageFile && productId) {
        const base64 = await leerFileComoBase64(data.imageFile);
        const imgObj = { entityType: 'Product', referenceId: productId, fileName: data.imageFile.name, data: base64 };
        const uploaded = await subirImagen(imgObj);
        const imgUrl = uploaded?.url || uploaded?.imageUrl || '';
        if (imgUrl) {
          await actualizarProducto(productId, { ...payload, imageUrl: imgUrl });
        }
      }
      await cargarTodos();
      alert('Producto creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditProducto = async (id, data) => {
    try {
      validarProducto(data);
      const payload = {
        name: data.name,
        description: data.description || '',
        weight: data.weight ? parseFloat(data.weight) : 0,
        price: data.price ? parseFloat(data.price) : 0,
        stock: data.stock ? parseInt(data.stock) : 0,
        active: !!data.active,
        imageUrl: data.imageUrl || ''
      };
      await actualizarProducto(id, payload);

      if (data.imageFile) {
        const base64 = await leerFileComoBase64(data.imageFile);
        const imgObj = { entityType: 'Product', referenceId: id, fileName: data.imageFile.name, data: base64 };
        const uploaded = await subirImagen(imgObj);
        const imgUrl = uploaded?.url || uploaded?.imageUrl || '';
        if (imgUrl) {
          await actualizarProducto(id, { ...payload, imageUrl: imgUrl });
        }
      }
      await cargarTodos();
      alert('Producto actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteProducto = async (id) => {
    try {
      if (window.confirm('¿Eliminar producto?')) {
        await eliminarProducto(id);
        await cargarTodos();
        alert('Producto eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== CULTIVOS ====================
  const validarCultivo = (c) => {
    if (!c.plantId) throw new Error('Planta requerida');
    if (!c.hectares || isNaN(c.hectares) || c.hectares <= 0) throw new Error('Hectáreas > 0');
    if (!c.season?.trim()) throw new Error('Temporada requerida');
    if (!c.startDate) throw new Error('Fecha inicio requerida');
  };

  const handleAddCultivo = async (data) => {
    try {
      validarCultivo(data);
      const payload = {
        plant: { plantId: data.plantId },
        hectares: parseFloat(data.hectares),
        requiredPackages: data.requiredPackages ? parseFloat(data.requiredPackages) : 0,
        eachIrrigation: data.eachIrrigation ? parseInt(data.eachIrrigation) : 0,
        season: data.season,
        startDate: data.startDate,
        endDate: data.endDate || null
      };
      await crearCultivo(payload);
      await cargarTodos();
      alert('Cultivo creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditCultivo = async (id, data) => {
    try {
      validarCultivo(data);
      const payload = {
        plant: { plantId: data.plantId },
        hectares: parseFloat(data.hectares),
        requiredPackages: data.requiredPackages ? parseFloat(data.requiredPackages) : 0,
        eachIrrigation: data.eachIrrigation ? parseInt(data.eachIrrigation) : 0,
        season: data.season,
        startDate: data.startDate,
        endDate: data.endDate || null
      };
      await actualizarCultivo(id, payload);
      await cargarTodos();
      alert('Cultivo actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteCultivo = async (id) => {
    try {
      if (window.confirm('¿Eliminar cultivo?')) {
        await eliminarCultivo(id);
        await cargarTodos();
        alert('Cultivo eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== COSECHAS ====================
  const validarCosecha = (c) => {
    if (!c.cultivationId) throw new Error('Cultivo requerido');
    if (!c.season?.trim()) throw new Error('Temporada requerida');
    if (!c.collector?.trim()) throw new Error('Tipo de recolector requerido');
  };

  const handleAddCosecha = async (data) => {
    try {
      validarCosecha(data);
      const payload = {
        cultivation: { cultivationId: data.cultivationId },
        season: data.season,
        collector: data.collector,
        dateHarvested: data.dateHarvested || null
      };
      await crearCosecha(payload);
      await cargarTodos();
      alert('Cosecha creada');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditCosecha = async (id, data) => {
    try {
      validarCosecha(data);
      const payload = {
        cultivation: { cultivationId: data.cultivationId },
        season: data.season,
        collector: data.collector,
        dateHarvested: data.dateHarvested || null
      };
      await actualizarCosecha(id, payload);
      await cargarTodos();
      alert('Cosecha actualizada');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteCosecha = async (id) => {
    try {
      if (window.confirm('¿Eliminar cosecha?')) {
        await eliminarCosecha(id);
        await cargarTodos();
        alert('Cosecha eliminada');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== PÉRDIDAS ====================
  const validarPerdida = (p) => {
    if (!p.typeLoss?.trim()) throw new Error('Tipo de pérdida requerido');
    if (p.pocentageAffect && (isNaN(p.pocentageAffect) || p.pocentageAffect < 0 || p.pocentageAffect > 100)) {
      throw new Error('Porcentaje 0-100');
    }
  };

  const handleAddPerdida = async (data) => {
    try {
      validarPerdida(data);
      const payload = {
        typeLoss: data.typeLoss,
        description: data.description || '',
        pocentageAffect: data.pocentageAffect ? parseFloat(data.pocentageAffect) : 0,
        cultivation: data.cultivationId ? { cultivationId: data.cultivationId } : null,
        harvest: data.harvestId ? { harvestId: data.harvestId } : null
      };
      await crearPerdida(payload);
      await cargarTodos();
      alert('Pérdida registrada');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditPerdida = async (id, data) => {
    try {
      validarPerdida(data);
      const payload = {
        typeLoss: data.typeLoss,
        description: data.description || '',
        pocentageAffect: data.pocentageAffect ? parseFloat(data.pocentageAffect) : 0,
        cultivation: data.cultivationId ? { cultivationId: data.cultivationId } : null,
        harvest: data.harvestId ? { harvestId: data.harvestId } : null
      };
      await actualizarPerdida(id, payload);
      await cargarTodos();
      alert('Pérdida actualizada');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeletePerdida = async (id) => {
    try {
      if (window.confirm('¿Eliminar pérdida?')) {
        await eliminarPerdida(id);
        await cargarTodos();
        alert('Pérdida eliminada');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== INSUMOS ====================
  const validarInsumo = (i) => {
    if (!i.name?.trim()) throw new Error('Nombre requerido');
    if (!i.type?.trim()) throw new Error('Tipo requerido');
    if (!i.unit?.trim()) throw new Error('Unidad requerida');
  };

  const handleAddInsumo = async (data) => {
    try {
      validarInsumo(data);
      const payload = {
        name: data.name,
        type: data.type,
        description: data.description || '',
        unit: data.unit,
        plant: data.plantId ? { plantId: data.plantId } : null,
        cultivation: data.cultivationId ? { cultivationId: data.cultivationId } : null
      };
      await crearInsumo(payload);
      await cargarTodos();
      alert('Insumo creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditInsumo = async (id, data) => {
    try {
      validarInsumo(data);
      const payload = {
        name: data.name,
        type: data.type,
        description: data.description || '',
        unit: data.unit,
        plant: data.plantId ? { plantId: data.plantId } : null,
        cultivation: data.cultivationId ? { cultivationId: data.cultivationId } : null
      };
      await actualizarInsumo(id, payload);
      await cargarTodos();
      alert('Insumo actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteInsumo = async (id) => {
    try {
      if (window.confirm('¿Eliminar insumo?')) {
        await eliminarInsumo(id);
        await cargarTodos();
        alert('Insumo eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== PROVEEDORES ====================
  const validarProveedor = (p) => {
    if (!p.name?.trim()) throw new Error('Nombre requerido');
    if (!p.ruc?.trim()) throw new Error('RUC requerido');
    if (!p.email?.trim()) throw new Error('Email requerido');
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(p.email)) throw new Error('Email inválido');
    if (!p.phone?.trim()) throw new Error('Teléfono requerido');
  };

  const handleAddProveedor = async (data) => {
    try {
      validarProveedor(data);
      await crearProveedor(data);
      await cargarTodos();
      alert('Proveedor creado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleEditProveedor = async (id, data) => {
    try {
      validarProveedor(data);
      await actualizarProveedor(id, data);
      await cargarTodos();
      alert('Proveedor actualizado');
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  const handleDeleteProveedor = async (id) => {
    try {
      if (window.confirm('¿Eliminar proveedor?')) {
        await eliminarProveedor(id);
        await cargarTodos();
        alert('Proveedor eliminado');
      }
    } catch (error) {
      alert('Error: ' + error.message);
    }
  };

  // ==================== COLUMNAS ====================
  const usuariosColumns = [
    { key: 'userId', label: 'ID' },
    { key: 'firstName', label: 'Nombre', type: 'text' },
    { key: 'lastName', label: 'Apellido', type: 'text' },
    { key: 'email', label: 'Email', type: 'email' },
    { key: 'password', label: 'Contraseña', type: 'password' },
    { key: 'country', label: 'País', type: 'text' },
    { key: 'role', label: 'Rol', type: 'text' },
    { key: 'dateRegistered', label: 'Registrado', type: 'date' }
  ];

  const clientesColumns = [
    { key: 'customerId', label: 'ID' },
    { key: 'userId', label: 'Usuario ID', type: 'number' },
    { key: 'user', label: 'Usuario', render: (val) => (val ? `${val.firstName} ${val.lastName}` : 'N/A') },
    { key: 'phone', label: 'Teléfono', type: 'text' },
    { key: 'address', label: 'Dirección', type: 'text' },
    { key: 'age', label: 'Edad', type: 'number' },
    { key: 'type', label: 'Tipo', type: 'text' }
  ];

  const empleadosColumns = [
    { key: 'employeeId', label: 'ID' },
    { key: 'firstName', label: 'Nombre', type: 'text' },
    { key: 'lastName', label: 'Apellido', type: 'text' },
    { key: 'workEmail', label: 'Email Lab.', type: 'email' },
    { key: 'personalEmail', label: 'Email Pers.', type: 'email' },
    { key: 'phone', label: 'Teléfono', type: 'text' },
    { key: 'dni', label: 'DNI', type: 'text' },
    { key: 'age', label: 'Edad', type: 'number' },
    { key: 'salary', label: 'Salario', type: 'number' },
    { key: 'hireDate', label: 'Contratado', type: 'date' }
  ];

  const productosColumns = [
    { key: 'productId', label: 'ID' },
    { key: 'name', label: 'Nombre', type: 'text' },
    { key: 'description', label: 'Descripción', type: 'text' },
    { key: 'weight', label: 'Peso (kg)', type: 'number' },
    { key: 'price', label: 'Precio', type: 'number' },
    { key: 'stock', label: 'Stock', type: 'number' },
    { key: 'active', label: 'Activo', type: 'checkbox', render: (val) => (val ? 'Sí' : 'No') },
    { key: 'imageFile', label: 'Imagen', type: 'file' },
    { key: 'imageUrl', label: 'URL Imagen', render: (val) => val ? (<a href={val} target="_blank" rel="noreferrer" style={{color:'#007bff'}}>Ver</a>) : 'Sin imagen' }
  ];

  const cultivosColumns = [
    { key: 'cultivationId', label: 'ID' },
    { key: 'plantId', label: 'Planta ID', type: 'number' },
    { key: 'plant', label: 'Planta', render: (val) => (val ? val.name : 'N/A') },
    { key: 'hectares', label: 'Hectáreas', type: 'number' },
    { key: 'requiredPackages', label: 'Paquetes Req.', type: 'number' },
    { key: 'eachIrrigation', label: 'Riego c/x', type: 'number' },
    { key: 'season', label: 'Temporada', type: 'text' },
    { key: 'startDate', label: 'Inicio', type: 'date' },
    { key: 'endDate', label: 'Fin', type: 'date' }
  ];

  const cosechasColumns = [
    { key: 'harvestId', label: 'ID' },
    { key: 'cultivationId', label: 'Cultivo ID', type: 'number' },
    { key: 'cultivation', label: 'Cultivo', render: (val) => (val ? `Cultivo #${val.cultivationId}` : 'N/A') },
    { key: 'dateHarvested', label: 'Fecha Cosecha', type: 'date' },
    { key: 'season', label: 'Temporada', type: 'text' },
    { key: 'collector', label: 'Recolector (MANUAL/MACHINERY)', type: 'text' }
  ];

  const perdidasColumns = [
    { key: 'lossId', label: 'ID' },
    { key: 'typeLoss', label: 'Tipo Pérdida', type: 'text' },
    { key: 'description', label: 'Descripción', type: 'text' },
    { key: 'pocentageAffect', label: 'Porcentaje %', type: 'number' },
    { key: 'cultivationId', label: 'Cultivo ID', type: 'number' },
    { key: 'harvestId', label: 'Cosecha ID', type: 'number' }
  ];

  const insumosColumns = [
    { key: 'inputId', label: 'ID' },
    { key: 'name', label: 'Nombre', type: 'text' },
    { key: 'type', label: 'Tipo', type: 'text' },
    { key: 'description', label: 'Descripción', type: 'text' },
    { key: 'unit', label: 'Unidad', type: 'text' },
    { key: 'plantId', label: 'Planta ID', type: 'number' },
    { key: 'cultivationId', label: 'Cultivo ID', type: 'number' }
  ];

  const proveedoresColumns = [
    { key: 'supplierId', label: 'ID' },
    { key: 'name', label: 'Nombre', type: 'text' },
    { key: 'ruc', label: 'RUC', type: 'text' },
    { key: 'email', label: 'Email', type: 'email' },
    { key: 'phone', label: 'Teléfono', type: 'text' },
    { key: 'address', label: 'Dirección', type: 'text' }
  ];

  const ventasColumns = [
    { key: 'saleId', label: 'ID' },
    { key: 'customer', label: 'Cliente', render: (val) => (val?.user ? `${val.user.firstName} ${val.user.lastName}` : 'N/A') },
    { key: 'saleDate', label: 'Fecha Venta', type: 'date' },
    { key: 'total', label: 'Total', type: 'number' },
    { key: 'paymentMethod', label: 'Método Pago', type: 'text' }
  ];

  const tabs = [
    { id: 'usuarios', label: '👤 Usuarios', cols: usuariosColumns, data: usuarios, onAdd: handleAddUsuario, onEdit: handleEditUsuario, onDelete: handleDeleteUsuario },
    { id: 'clientes', label: '👥 Clientes', cols: clientesColumns, data: clientes, onAdd: handleAddCliente, onEdit: handleEditCliente, onDelete: handleDeleteCliente },
    { id: 'empleados', label: '👨‍💼 Empleados', cols: empleadosColumns, data: empleados, onAdd: handleAddEmpleado, onEdit: handleEditEmpleado, onDelete: handleDeleteEmpleado },
    { id: 'productos', label: '🛒 Productos', cols: productosColumns, data: productos, onAdd: handleAddProducto, onEdit: handleEditProducto, onDelete: handleDeleteProducto },
    { id: 'cultivos', label: '🌱 Cultivos', cols: cultivosColumns, data: cultivos, onAdd: handleAddCultivo, onEdit: handleEditCultivo, onDelete: handleDeleteCultivo },
    { id: 'cosechas', label: '🌾 Cosechas', cols: cosechasColumns, data: cosechas, onAdd: handleAddCosecha, onEdit: handleEditCosecha, onDelete: handleDeleteCosecha },
    { id: 'perdidas', label: '⚠️ Pérdidas', cols: perdidasColumns, data: perdidas, onAdd: handleAddPerdida, onEdit: handleEditPerdida, onDelete: handleDeletePerdida },
    { id: 'insumos', label: '📦 Insumos', cols: insumosColumns, data: insumos, onAdd: handleAddInsumo, onEdit: handleEditInsumo, onDelete: handleDeleteInsumo },
    { id: 'proveedores', label: '🏭 Proveedores', cols: proveedoresColumns, data: proveedores, onAdd: handleAddProveedor, onEdit: handleEditProveedor, onDelete: handleDeleteProveedor },
    { id: 'ventas', label: '💳 Ventas', cols: ventasColumns, data: ventas, onAdd: null, onEdit: null, onDelete: null }
  ];

  return (
    <Layout>
      <div style={{ padding: '20px', background: '#f5f5f5', minHeight: '100vh' }}>
        <h1 style={{ marginBottom: '20px', color: '#333' }}>🎯 Panel de Administración</h1>
        
        <div style={{ display: 'flex', gap: '8px', marginBottom: '20px', flexWrap: 'wrap' }}>
          {tabs.map(t => (
            <button
              key={t.id}
              onClick={() => setActiveTab(t.id)}
              style={{
                padding: '10px 16px',
                background: activeTab === t.id ? '#007bff' : '#fff',
                color: activeTab === t.id ? '#fff' : '#333',
                border: `2px solid ${activeTab === t.id ? '#007bff' : '#ddd'}`,
                borderRadius: '6px',
                cursor: 'pointer',
                fontWeight: activeTab === t.id ? 'bold' : 'normal',
                transition: 'all 0.3s'
              }}
            >
              {t.label}
            </button>
          ))}
          <button onClick={cargarTodos} style={{ padding: '10px 16px', background: '#28a745', color: '#fff', border: 'none', borderRadius: '6px', cursor: 'pointer' }}>
            🔄 Refrescar
          </button>
        </div>

        {tabs.find(t => t.id === activeTab) && (
          <AdminTable
            data={tabs.find(t => t.id === activeTab).data}
            columns={tabs.find(t => t.id === activeTab).cols}
            title={tabs.find(t => t.id === activeTab).label}
            onAdd={tabs.find(t => t.id === activeTab).onAdd}
            onEdit={tabs.find(t => t.id === activeTab).onEdit}
            onDelete={tabs.find(t => t.id === activeTab).onDelete}
            actions={activeTab !== 'ventas'}
          />
        )}
      </div>
    </Layout>
  );
}
