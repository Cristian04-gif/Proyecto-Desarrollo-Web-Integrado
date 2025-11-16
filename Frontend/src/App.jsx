import { Routes, Route } from 'react-router-dom';
import Layout from './components/Layout'; 
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import CatalogPage from './pages/CatalogPage';
import CartPage from './pages/CartPage';
import Mapeo from './pages/tecnologia/Mapeo';
import Seguimiento from './pages/tecnologia/Seguimiento';
import Defensa from './pages/tecnologia/Defensa';
import DSS from './pages/tecnologia/DSS';
import Exploracion from './pages/tecnologia/Exploracion';
import Sensores from './pages/tecnologia/Sensores';
import Precision from './pages/tecnologia/Precision';
import NewsPage from './pages/NewsPage';

import { useCart, CartProvider } from './services/cartContext';

function App() {
  return (
    <CartProvider>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/catalogo" element={<CatalogPage />} />
          <Route path="/carrito" element={<CartPage />} />
          <Route path="/tecnologia/mapeo" element={<Mapeo />} />
          <Route path="/tecnologia/seguimiento" element={<Seguimiento />} />
          <Route path="/tecnologia/defensa" element={<Defensa />} />
          <Route path="/tecnologia/dss" element={<DSS />} />
          <Route path="/tecnologia/exploracion" element={<Exploracion />} />
          <Route path="/tecnologia/sensores" element={<Sensores />} />
          <Route path="/tecnologia/precision" element={<Precision />} />
          <Route path="/noticias" element={<NewsPage />} />
        </Route>
      </Routes>
    </CartProvider>
  );
}

export default App;
