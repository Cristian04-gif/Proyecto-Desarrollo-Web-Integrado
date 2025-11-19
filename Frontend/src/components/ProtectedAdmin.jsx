import React from 'react';
import { Navigate } from 'react-router-dom';

function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
}

export default function ProtectedAdmin({ children }) {
  const token = localStorage.getItem('token');
  if (!token) return <Navigate to="/login" replace />;
  const payload = parseJwt(token);
  const email = payload?.sub || payload?.username || payload?.email;
  if (!email) return <Navigate to="/" replace />;
  const allowed = email.toLowerCase().endsWith('@utp.edu.pe') || email.toLowerCase().includes('@biocampo');
  if (!allowed) return <Navigate to="/" replace />;
  return children;
}
