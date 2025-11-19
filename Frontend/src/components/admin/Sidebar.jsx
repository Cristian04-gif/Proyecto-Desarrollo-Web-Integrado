// src/components/admin/Sidebar.jsx
import React from 'react';

export default function Sidebar({ current, onSelect }) {
  const items = [
    { key: 'users', label: 'Usuarios', icon: '👤' },
    { key: 'cultivations', label: 'Cultivos', icon: '🌾' },
    { key: 'plants', label: 'Plantas', icon: '🪴' },
    { key: 'products', label: 'Productos', icon: '📦' },
    { key: 'sales', label: 'Ventas', icon: '💸' },
    { key: 'saleDetails', label: 'Detalle de Venta', icon: '🧾' },
    { key: 'customers', label: 'Clientes', icon: '🧍' },
    { key: 'employees', label: 'Empleados', icon: '🧑‍🌾' },
    { key: 'suppliers', label: 'Proveedores', icon: '🚚' },
    { key: 'harvests', label: 'Cosechas', icon: '🌽' },
    { key: 'losses', label: 'Pérdidas', icon: '⚠️' },
    { key: 'postHarvests', label: 'Postcosechas', icon: '📊' },
    { key: 'images', label: 'Imágenes', icon: '🖼️' },
    { key: 'inputs', label: 'Insumos', icon: '🧪' },
    { key: 'inputCultivations', label: 'Insumos por Cultivo', icon: '🔗' },
    { key: 'inputSuppliers', label: 'Insumos por Proveedor', icon: '🔗' },
    { key: 'jobPositions', label: 'Puestos', icon: '🏷️' },
    { key: 'plantCategories', label: 'Categorías de Planta', icon: '🌱' }
  ];

  return (
    <aside className="admin-sidebar">
      <nav>
        {items.map(i => (
          <button
            key={i.key}
            className={`sidebar-item ${current === i.key ? 'active' : ''}`}
            onClick={() => onSelect(i.key)}
            aria-label={`Ir a ${i.label}`}
          >
            <span className="sidebar-icon">{i.icon}</span> {i.label}
          </button>
        ))}
      </nav>
    </aside>
  );
}
