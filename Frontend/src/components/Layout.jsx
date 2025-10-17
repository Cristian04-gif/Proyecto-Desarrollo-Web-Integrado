// src/components/Layout.jsx
import Navbar from './Navbar';
import Footer from './Footer';

export default function Layout({ children }) {
  return (
    <>
      <Navbar />
      <main className="layout-content">{children}</main>
      <Footer />
    </>
  );
}
