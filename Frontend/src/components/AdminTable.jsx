import { useState } from 'react';
import '../styles/adminTable.css';

export default function AdminTable({ data, columns, title, onAdd, onEdit, onDelete, actions = true }) {
  const [editingId, setEditingId] = useState(null);
  const [editData, setEditData] = useState({});
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({});

  const detectIdKey = (row) => {
    if (!row) return 'id';
    const possible = Object.keys(row).find(k => /id$/i.test(k) || k.toLowerCase() === 'id');
    return possible || Object.keys(row)[0];
  };

  const handleAddClick = () => {
    setShowForm(true);
    setFormData({});
    setEditingId(null);
  };

  const handleEditClick = (row) => {
    const idKey = detectIdKey(row);
    setShowForm(true);
    setEditingId(row[idKey]);
    setEditData({ ...row });
    setFormData({ ...row });
  };

  const handleDeleteClick = (id) => {
    if (window.confirm('¿Está seguro de que desea eliminar este registro?')) {
      onDelete(id);
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked, files } = e.target;
    let finalValue = value;
    if (type === 'checkbox') {
      finalValue = checked;
    } else if (type === 'file') {
      finalValue = files && files[0] ? files[0] : null;
    }
    setFormData(prev => ({
      ...prev,
      [name]: finalValue
    }));
  };

  const handleSave = async () => {
    try {
      if (editingId) {
        await onEdit(editingId, formData);
      } else {
        await onAdd(formData);
      }
      setShowForm(false);
      setFormData({});
      setEditingId(null);
    } catch (error) {
      alert('Error al guardar: ' + error.message);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setFormData({});
    setEditingId(null);
  };

  return (
    <div className="admin-table-container">
      <div className="table-header">
        <h2>{title}</h2>
        <button className="btn-add" onClick={handleAddClick}>+ Agregar</button>
      </div>

      {showForm && (
        <div className="form-overlay">
          <div className="form-container">
            <h3>{editingId ? 'Editar' : 'Crear nuevo'} {title}</h3>
            <form>
              {columns.map(col => (
                <div key={col.key} className="form-group">
                  <label>{col.label}</label>
                  {col.type === 'file' ? (
                    <input
                      type="file"
                      name={col.key}
                      onChange={handleInputChange}
                      accept="image/*"
                    />
                  ) : col.type === 'checkbox' ? (
                    <input
                      type="checkbox"
                      name={col.key}
                      checked={!!formData[col.key]}
                      onChange={handleInputChange}
                    />
                  ) : (
                    <input
                      type={col.type || 'text'}
                      name={col.key}
                      value={formData[col.key] || ''}
                      onChange={handleInputChange}
                      placeholder={col.label}
                    />
                  )}
                </div>
              ))}
              <div className="form-actions">
                <button type="button" className="btn-save" onClick={handleSave}>
                  Guardar
                </button>
                <button type="button" className="btn-cancel" onClick={handleCancel}>
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              {columns.map(col => (
                <th key={col.key}>{col.label}</th>
              ))}
              {actions && <th>Acciones</th>}
            </tr>
          </thead>
          <tbody>
            {data && data.length > 0 ? (
              data.map((row, idx) => {
                const idKey = detectIdKey(row);
                const rowId = row[idKey] || idx;
                return (
                  <tr key={rowId}>
                    {columns.map(col => (
                      <td key={`${rowId}-${col.key}`} className={`col-${col.key}`}>
                        {col.render ? col.render(row[col.key], row) : (typeof row[col.key] === 'object' ? JSON.stringify(row[col.key]) : row[col.key])}
                      </td>
                    ))}
                    {actions && (
                      <td className="actions">
                        <button
                          className="btn-edit"
                          onClick={() => handleEditClick(row)}
                          title="Editar"
                        >
                          ✏️
                        </button>
                        <button
                          className="btn-delete"
                          onClick={() => handleDeleteClick(rowId)}
                          title="Eliminar"
                        >
                          🗑️
                        </button>
                      </td>
                    )}
                  </tr>
                );
              })
            ) : (
              <tr>
                <td colSpan={columns.length + (actions ? 1 : 0)} className="no-data">
                  No hay datos disponibles
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
