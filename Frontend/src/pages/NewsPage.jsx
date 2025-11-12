// src/pages/NewsPage.jsx
import { useEffect, useState } from 'react';
import { fetchAgricultureNews } from '../services/newsService';
import '../styles/newsStyles.css';


export default function NewsPage() {
  const [news, setNews] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAgricultureNews()
      .then(setNews)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  return (
    <section className="news-page">
      <h2>Noticias sobre Agricultura</h2>
      {loading ? (
        <p>Cargando noticias...</p>
      ) : (
        <div className="news-list">
          {news.map((article, index) => (
            <div key={index} className="news-card">
              <h3>{article.title}</h3>
              <p>{article.description}</p>
              <a href={article.url} target="_blank" rel="noopener noreferrer">Leer más</a>
            </div>
          ))}
        </div>
      )}
    </section>
  );
}
