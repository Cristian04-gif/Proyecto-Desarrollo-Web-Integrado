import React, { useEffect, useMemo, useState } from 'react';
import AdminLayout from '../components/admin/AdminLayout';
import Sidebar from '../components/admin/Sidebar';
import AdminTable from '../components/admin/AdminTable';
import CRUDModal from '../components/admin/CRUDModal';
import {
  userService,
  cultivationService,
  customerService,
  employeeService,
  harvestService,
  imageService,
  inputService,
  inputCultivationService,
  inputSupplierService,
  jobPositionService,
  lossService,
  plantCategoryService,
  plantService,
  postHarvestService,
  productService,
  saleService,
  saleDetailsService,
  supplierService
} from '../services/authService';

const ENTITY_MAP = {
  users: {
    service: userService,
    columns: [
      { key: 'userId', label: 'ID' },
      { key: 'firstName', label: 'Nombre' },
      { key: 'lastName', label: 'Apellido' },
      { key: 'email', label: 'Email' },
      { key: 'country', label: 'País' },
      { key: 'dateRegistered', label: 'Fecha de Registro' },
      { key: 'role', label: 'Rol' }
    ],
    schema: [
      { key: 'firstName', label: 'Nombre', type: 'text' },
      { key: 'lastName', label: 'Apellido', type: 'text' },
      { key: 'email', label: 'Email', type: 'email' },
      { key: 'password', label: 'Contraseña', type: 'password' },
      { key: 'country', label: 'País', type: 'text' },
      { key: 'dateRegistered', label: 'Fecha de Registro', type: 'date' },
      { key: 'role', label: 'Rol', type: 'text' }
    ]
  },

  cultivations: {
    service: cultivationService,
    columns: [
      { key: 'cultivationId', label: 'ID' },
      { key: 'season', label: 'Temporada' },
      { key: 'hectares', label: 'Hectáreas' },
      { key: 'startDate', label: 'Inicio' },
      { key: 'endDate', label: 'Fin' },
      { key: 'requiredPackages', label: 'Paquetes requeridos', type: 'number' },
      { key: 'eachIrrigation', label: 'Cada riego (días)', type: 'number' },
      { key: 'plantId', label: 'ID Planta', type: 'number' }
    ],
    schema: [
      { key: 'season', label: 'Temporada', type: 'text' },
      { key: 'hectares', label: 'Hectáreas', type: 'number' },
      { key: 'requiredPackages', label: 'Paquetes requeridos', type: 'number' },
      { key: 'eachIrrigation', label: 'Cada riego (días)', type: 'number' },
      { key: 'startDate', label: 'Fecha inicio', type: 'date' },
      { key: 'endDate', label: 'Fecha fin', type: 'date' },
      { key: 'plantId', label: 'ID Planta', type: 'number' }
    ]
  },

  customers: {
    service: customerService,
    columns: [
      { key: 'customerId', label: 'ID' },
      { key: 'userId', label: 'ID Usuario' },
      { key: 'age', label: 'Edad' },
      { key: 'phone', label: 'Teléfono' },
      { key: 'address', label: 'Dirección' },
      { key: 'type', label: 'Tipo' }
    ],
    schema: [
      { key: 'userId', label: 'ID Usuario', type: 'number' },
      { key: 'age', label: 'Edad', type: 'number' },
      { key: 'phone', label: 'Teléfono', type: 'text' },
      { key: 'address', label: 'Dirección', type: 'text' },
      { key: 'type', label: 'Tipo', type: 'text' }
    ]
  },


  employees: {
    service: employeeService,
    columns: [
      { key: 'employeeId', label: 'ID' },
      { key: 'firstName', label: 'Nombre' },
      { key: 'lastName', label: 'Apellido' },
      { key: 'workEmail', label: 'Email Empresarial' },
      { key: 'phone', label: 'Teléfono' },
      { key: 'age', label: 'Edad' },
      { key: 'personalEmail', label: 'Email Personal' },
      { key: 'dni', label: 'DNI' },
      { key: 'country', label: 'País' },
      { key: 'address', label: 'Dirección' },
      { key: 'hireDate', label: 'Fecha Contratación' },
      { key: 'jobPositionId', label: 'ID Puesto' },
      { key: 'salary', label: 'Salario' }
    ],
    schema: [
      { key: 'firstName', label: 'Nombre', type: 'text' },
      { key: 'lastName', label: 'Apellido', type: 'text' },
      { key: 'age', label: 'Edad', type: 'number' },
      { key: 'phone', label: 'Teléfono', type: 'text' },
      { key: 'personalEmail', label: 'Email Personal', type: 'email' },
      { key: 'workEmail', label: 'Email Empresarial', type: 'email' },
      { key: 'dni', label: 'DNI', type: 'text' },
      { key: 'country', label: 'País', type: 'text' },
      { key: 'address', label: 'Dirección', type: 'text' },
      { key: 'salary', label: 'Salario', type: 'number' },
      { key: 'hireDate', label: 'Fecha Contratación', type: 'date' },
      { key: 'jobPositionId', label: 'ID Puesto', type: 'number' }
    ]
  },

  harvests: {
    service: harvestService,
    columns: [
      { key: 'harvestId', label: 'ID' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo' },
      { key: 'dateHarvested', label: 'Fecha de Cosecha' },
      { key: 'season', label: 'Temporada' },
      { key: 'collector', label: 'Recolector' }
    ],
    schema: [
      { key: 'cultivation.cultivationId', label: 'ID Cultivo', type: 'number' },
      { key: 'dateHarvested', label: 'Fecha de Cosecha', type: 'date' },
      { key: 'season', label: 'Temporada', type: 'text' },
      { key: 'collector', label: 'Recolector', type: 'text' }
    ]
  },

  images: {
    service: imageService,
    columns: [
      { key: 'imageId', label: 'ID' },
      { key: 'url', label: 'URL' },
      { key: 'entityType', label: 'Tipo de Entidad' },
      { key: 'referenceId', label: 'ID Referencia' },
      { key: 'uploadDate', label: 'Fecha de Subida' }
    ],
    schema: [
      { key: 'url', label: 'URL', type: 'text' },
      { key: 'entityType', label: 'Tipo de Entidad', type: 'text' },
      { key: 'referenceId', label: 'ID Referencia', type: 'number' },
      { key: 'uploadDate', label: 'Fecha de Subida', type: 'date' }
    ]
  },

  inputs: {
    service: inputService,
    columns: [
      { key: 'inputId', label: 'ID' },
      { key: 'name', label: 'Nombre' },
      { key: 'type', label: 'Tipo' },
      { key: 'description', label: 'Descripción' },
      { key: 'unit', label: 'Unidad' },
      { key: 'plant.plantId', label: 'ID Planta' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo' }
    ],
    schema: [
      { key: 'name', label: 'Nombre', type: 'text' },
      { key: 'type', label: 'Tipo', type: 'text' },
      { key: 'description', label: 'Descripción', type: 'text' },
      { key: 'unit', label: 'Unidad', type: 'text' },
      { key: 'plant.plantId', label: 'ID Planta', type: 'number' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo', type: 'number' }
    ]
  },

  inputCultivations: {
    service: inputCultivationService,
    columns: [
      { key: 'inputCultivationId', label: 'ID' },
      { key: 'quantity', label: 'Cantidad' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo' },
      { key: 'input.inputId', label: 'ID Insumo' }
    ],
    schema: [
      { key: 'quantity', label: 'Cantidad', type: 'number' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo', type: 'number' },
      { key: 'input.inputId', label: 'ID Insumo', type: 'number' }
    ]
  },

  inputSuppliers: {
    service: inputSupplierService,
    columns: [
      { key: 'inputSupplierId', label: 'ID' },
      { key: 'supplier.supplierId', label: 'ID Proveedor' },
      { key: 'input.inputId', label: 'ID Insumo' },
      { key: 'price', label: 'Precio' }
    ],
    schema: [
      { key: 'supplier.supplierId', label: 'ID Proveedor', type: 'number' },
      { key: 'input.inputId', label: 'ID Insumo', type: 'number' },
      { key: 'price', label: 'Precio', type: 'number' }
    ]
  },

  jobPositions: {
    service: jobPositionService,
    columns: [
      { key: 'positionId', label: 'ID' },
      { key: 'positionName', label: 'Nombre del Puesto' }
    ],
    schema: [
      { key: 'positionName', label: 'Nombre del Puesto', type: 'text' }
    ]
  },

  losses: {
    service: lossService,
    columns: [
      { key: 'lossId', label: 'ID' },
      { key: 'typeLoss', label: 'Tipo de Pérdida' },
      { key: 'description', label: 'Descripción' },
      { key: 'pocentageAffect', label: 'Porcentaje de Afectación' },
      { key: 'lossDate', label: 'Fecha de Pérdida' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo' },
      { key: 'harvest.harvestId', label: 'ID Cosecha' }
    ],
    schema: [
      { key: 'typeLoss', label: 'Tipo de Pérdida', type: 'text' },
      { key: 'description', label: 'Descripción', type: 'text' },
      { key: 'pocentageAffect', label: 'Porcentaje de Afectación', type: 'number' },
      { key: 'lossDate', label: 'Fecha de Pérdida', type: 'date' },
      { key: 'cultivation.cultivationId', label: 'ID Cultivo', type: 'number' },
      { key: 'harvest.harvestId', label: 'ID Cosecha', type: 'number' }
    ]
  },

  plantCategories: {
    service: plantCategoryService,
    columns: [
      { key: 'categoryId', label: 'ID' },
      { key: 'categoryName', label: 'Nombre de Categoría' }
    ],
    schema: [
      { key: 'categoryName', label: 'Nombre de Categoría', type: 'text' }
    ]
  },

  plants: {
    service: plantService,
    columns: [
      { key: 'plantId', label: 'ID' },
      { key: 'name', label: 'Nombre' },
      { key: 'stock', label: 'Stock' },
      { key: 'seedingDensity', label: 'Densidad de Siembra' },
      { key: 'averageSeedWeight', label: 'Peso Promedio Semilla' },
      { key: 'weightPerPackage', label: 'Peso por Paquete' },
      { key: 'available', label: 'Disponible' },
      { key: 'category.categoryId', label: 'ID Categoría' }
    ],
    schema: [
      { key: 'name', label: 'Nombre', type: 'text' },
      { key: 'stock', label: 'Stock', type: 'number' },
      { key: 'seedingDensity', label: 'Densidad de Siembra', type: 'number' },
      { key: 'averageSeedWeight', label: 'Peso Promedio Semilla', type: 'number' },
      { key: 'weightPerPackage', label: 'Peso por Paquete', type: 'number' },
      { key: 'available', label: 'Disponible', type: 'checkbox' },
      { key: 'category.categoryId', label: 'ID Categoría', type: 'number' }
    ]
  },

  postHarvests: {
    service: postHarvestService,
    columns: [
      { key: 'postHarvestId', label: 'ID' },
      { key: 'harvest.harvestId', label: 'ID Cosecha' },
      { key: 'dateProcessed', label: 'Fecha de Procesado' },
      { key: 'cleaningMethod', label: 'Método de Limpieza' },
      { key: 'treatmentMethod', label: 'Método de Tratamiento' },
      { key: 'packing', label: 'Empaque' },
      { key: 'storage', label: 'Almacenamiento' },
      { key: 'stock', label: 'Stock' }
    ],
    schema: [
      { key: 'harvest.harvestId', label: 'ID Cosecha', type: 'number' },
      { key: 'dateProcessed', label: 'Fecha de Procesado', type: 'date' },
      { key: 'cleaningMethod', label: 'Método de Limpieza', type: 'text' },
      { key: 'treatmentMethod', label: 'Método de Tratamiento', type: 'text' },
      { key: 'packing', label: 'Empaque', type: 'text' },
      { key: 'storage', label: 'Almacenamiento', type: 'text' },
      { key: 'stock', label: 'Stock', type: 'number' }
    ]
  },

  products: {
    service: productService,
    columns: [
      { key: 'productId', label: 'ID' },
      { key: 'postHarvest.postHarvestId', label: 'ID PostCosecha' },
      { key: 'imageUrl', label: 'Imagen' },
      { key: 'name', label: 'Nombre' },
      { key: 'description', label: 'Descripción' },
      { key: 'weight', label: 'Peso' },
      { key: 'price', label: 'Precio' },
      { key: 'stock', label: 'Stock' },
      { key: 'active', label: 'Activo' },
      { key: 'plantCategory.categoryId', label: 'ID Categoría' }
    ],
    schema: [
      { key: 'postHarvest.postHarvestId', label: 'ID PostCosecha', type: 'number' },
      { key: 'imageUrl', label: 'Imagen', type: 'text' },
      { key: 'name', label: 'Nombre', type: 'text' },
      { key: 'description', label: 'Descripción', type: 'text' },
      { key: 'weight', label: 'Peso', type: 'number' },
      { key: 'price', label: 'Precio', type: 'number' },
      { key: 'stock', label: 'Stock', type: 'number' },
      { key: 'active', label: 'Activo', type: 'checkbox' },
      { key: 'plantCategory.categoryId', label: 'ID Categoría', type: 'number' }
    ]
  },

  sales: {
    service: saleService,
    columns: [
      { key: 'saleId', label: 'ID' },
      { key: 'customer.customerId', label: 'ID Cliente' },
      { key: 'saleDate', label: 'Fecha de Venta' },
      { key: 'total', label: 'Total' },
      { key: 'paymentMethod', label: 'Método de Pago' }
    ],
    schema: [
      { key: 'customer.customerId', label: 'ID Cliente', type: 'number' },
      { key: 'saleDate', label: 'Fecha de Venta', type: 'datetime' },
      { key: 'total', label: 'Total', type: 'number' },
      { key: 'paymentMethod', label: 'Método de Pago', type: 'text' }
    ]
  },


  saleDetails: {
    service: saleDetailsService,
    columns: [
      { key: 'saleDetailId', label: 'ID' },
      { key: 'sale.saleId', label: 'ID Venta' },
      { key: 'product.productId', label: 'ID Producto' },
      { key: 'quantity', label: 'Cantidad' },
      { key: 'subTotal', label: 'Subtotal' }
    ],
    schema: [
      { key: 'sale.saleId', label: 'ID Venta', type: 'number' },
      { key: 'product.productId', label: 'ID Producto', type: 'number' },
      { key: 'quantity', label: 'Cantidad', type: 'number' },
      { key: 'subTotal', label: 'Subtotal', type: 'number' }
    ]
  },

  suppliers: {
    service: supplierService,
    columns: [
      { key: 'supplierId', label: 'ID' },
      { key: 'name', label: 'Nombre' },
      { key: 'ruc', label: 'RUC' },
      { key: 'phone', label: 'Teléfono' },
      { key: 'email', label: 'Email' },
      { key: 'address', label: 'Dirección' }
    ],
    schema: [
      { key: 'name', label: 'Nombre', type: 'text' },
      { key: 'ruc', label: 'RUC', type: 'text' },
      { key: 'phone', label: 'Teléfono', type: 'text' },
      { key: 'email', label: 'Email', type: 'email' },
      { key: 'address', label: 'Dirección', type: 'text' }
    ]
  }

};

export default function AdminPanel() {
  const [entity, setEntity] = useState('users');
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [q, setQ] = useState('');
  const [error, setError] = useState('');
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState(null);

  const cfg = ENTITY_MAP[entity];

  async function load() {
    if (!cfg) return;
    setLoading(true); setError('');
    try {
      const list = await cfg.service.list();
      setData(Array.isArray(list) ? list : []);
    } catch (e) {
      setError('Error loading data'); setData([]);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => { load(); }, [entity]);

  const filtered = useMemo(() => {
    if (!q) return data;
    const qt = q.toLowerCase();
    return data.filter(row => JSON.stringify(row).toLowerCase().includes(qt));
  }, [q, data]);

  async function handleSave(form) {
    try {
      if (editing) {
        if (entity === 'users') {
          await cfg.service.update(editing.email, form); // 👈 aquí va el email
        } else {
          await cfg.service.update(
            editing.id ?? editing.cultivationId ?? editing.productId ?? editing.saleId,
            form
          );
        }
      } else {
        await cfg.service.create(form);
      }
      setModalOpen(false);
      setEditing(null);
      await load();
    } catch (e) {
      alert('Could not save.');
    }
  }



  async function handleDelete(row) {
    const id = row.id ?? row.cultivationId ?? row.productId ?? row.saleId;
    if (!id) return alert('Invalid ID');
    if (!confirm('Are you sure you want to delete this record?')) return;
    try {
      await cfg.service.remove(id);
      await load();
    } catch (e) {
      alert('Could not delete.');
    }
  }

  return (
    <AdminLayout>
      <Sidebar current={entity} onSelect={setEntity} />
      <main className="admin-main">
        <div className="admin-header">
          <h2>{entity.charAt(0).toUpperCase() + entity.slice(1)}</h2>
          <div className="admin-actions">
            <input
              type="search"
              placeholder="Search..."
              value={q}
              onChange={e => setQ(e.target.value)}
              aria-label="Search records"
            />
            <button className="btn-primary" onClick={() => { setEditing(null); setModalOpen(true); }}>
              Create
            </button>
          </div>
        </div>

        {error && <div className="alert-danger" aria-live="polite">{error}</div>}
        {loading ? (
          <p>Loading...</p>
        ) : cfg ? (
          <AdminTable
            columns={cfg.columns}
            data={filtered}
            onEdit={(row) => { setEditing(row); setModalOpen(true); }}
            onDelete={handleDelete}
          />
        ) : (
          <p>Invalid entity selected.</p>
        )}

        {cfg && (
          <CRUDModal
            open={modalOpen}
            title={editing ? 'Edit record' : 'Create record'}
            schema={cfg.schema}
            initial={editing || {}}
            onClose={() => { setModalOpen(false); setEditing(null); }}
            onSubmit={handleSave}
          />
        )}
      </main>
    </AdminLayout>
  );
}
