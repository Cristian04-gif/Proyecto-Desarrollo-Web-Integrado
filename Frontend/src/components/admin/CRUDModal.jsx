// src/components/admin/CRUDModal.jsx
import React, { useEffect, useState } from 'react';

export default function CRUDModal({ open, title, schema, initial, onClose, onSubmit }) {
  const [form, setForm] = useState(initial || {});
  const [errors, setErrors] = useState({});

  useEffect(() => {
    setForm(initial || {});
    setErrors({});
  }, [initial]);

  if (!open) return null;

  const handleChange = (key, value) => {
    setForm(prev => ({ ...prev, [key]: value }));
    setErrors(prev => ({ ...prev, [key]: null }));
  };

  const validate = () => {
    const newErrors = {};
    schema.forEach(field => {
      if (field.required && !form[field.key]) {
        newErrors[field.key] = 'Este campo es obligatorio';
      }
    });
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = e => {
    e.preventDefault();
    if (!validate()) return;
    onSubmit(form);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" role="dialog" aria-modal="true" onClick={e => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose} aria-label="Cerrar">✕</button>
        <h2>{title}</h2>
        <form onSubmit={handleSubmit} className="crud-form">
          {schema.map(field => (
            <div key={field.key} className="form-item">
              <label htmlFor={field.key}>{field.label}</label>
              {field.type === 'select' ? (
                <select
                  id={field.key}
                  value={form[field.key] ?? ''}
                  onChange={e => handleChange(field.key, e.target.value)}
                  disabled={field.disabled}
                >
                  <option value="">Seleccione</option>
                  {field.options?.map(opt => (
                    <option key={opt.value} value={opt.value}>{opt.label}</option>
                  ))}
                </select>
              ) : field.type === 'textarea' ? (
                <textarea
                  id={field.key}
                  value={form[field.key] ?? ''}
                  onChange={e => handleChange(field.key, e.target.value)}
                  rows={field.rows || 3}
                  disabled={field.disabled}
                  readOnly={field.readOnly}
                />
              ) : (
                <input
                  id={field.key}
                  type={field.type || 'text'}
                  value={form[field.key] ?? ''}
                  onChange={e => handleChange(field.key, e.target.value)}
                  disabled={field.disabled}
                  readOnly={field.readOnly}
                />
              )}
              {errors[field.key] && <div className="form-error">{errors[field.key]}</div>}
            </div>
          ))}
          <button type="submit" className="btn-primary">Guardar</button>
        </form>
      </div>
    </div>
  );
}
