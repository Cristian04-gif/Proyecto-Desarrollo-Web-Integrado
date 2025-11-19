// src/App.jsx
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Mapeo from './pages/tecnologia/Mapeo';
import Seguimiento from './pages/tecnologia/Seguimiento';
import Defensa from './pages/tecnologia/Defensa';
import DSS from './pages/tecnologia/DSS';
import Exploracion from './pages/tecnologia/Exploracion';
import Sensores from './pages/tecnologia/Sensores';
import Precision from './pages/tecnologia/Precision';
import Nosotros from './pages/Nosotros';
import Servicios from './pages/Servicios';
import Cultivos from './pages/Cultivos';
import AdminPanel from './pages/AdminPanel';
import ProtectedAdmin from './components/ProtectedAdmin';

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/nosotros" element={<Nosotros />} />
      <Route path="/servicios" element={<Servicios />} />
      <Route path="/cultivos" element={<Cultivos />} />
      <Route path="/tecnologia/mapeo" element={<Mapeo />} />
      <Route path="/tecnologia/seguimiento" element={<Seguimiento />} />
      <Route path="/tecnologia/defensa" element={<Defensa />} />
      <Route path="/tecnologia/dss" element={<DSS />} />
      <Route path="/tecnologia/exploracion" element={<Exploracion />} />
      <Route path="/tecnologia/sensores" element={<Sensores />} />
      <Route path="/tecnologia/precision" element={<Precision />} />
      <Route path="/admin"element={<ProtectedAdmin><AdminPanel /></ProtectedAdmin>}/>
    </Routes>
  );
}

export default App;
