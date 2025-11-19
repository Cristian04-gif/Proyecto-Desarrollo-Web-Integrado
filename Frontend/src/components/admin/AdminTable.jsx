// src/components/admin/AdminTable.jsx
import React from 'react';

function getNestedValue(obj, path) {
  return path.split('.').reduce((acc, key) => acc?.[key], obj);
}

export default function AdminTable({ columns, data, onEdit, onDelete }) {
  return (
    <div className="table-wrapper">
      <table className="admin-table">
        <thead>
          <tr>
            {columns.map(col => <th key={col.key}>{col.label}</th>)}
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {data.length === 0 ? (
            <tr><td colSpan={columns.length + 1}>Sin registros</td></tr>
          ) : data.map(row => {
            const rowId = row.id || row.cultivationId || row.productId || row.saleId || row.employeeId || row.customerId || row.supplierId;
            return (
              <tr key={rowId}>
                {columns.map(col => {
                  const value = getNestedValue(row, col.key);
                  return (
                    <td key={`${rowId}-${col.key}`}>
                      {col.render ? col.render(value, row) : (value ?? '—')}
                    </td>
                  );
                })}

                <td className="actions">
                  <button className="btn-edit" onClick={() => onEdit(row)} aria-label="Editar">✏️</button>
                  <button className="btn-delete" onClick={() => onDelete(row)} aria-label="Eliminar">🗑️</button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
