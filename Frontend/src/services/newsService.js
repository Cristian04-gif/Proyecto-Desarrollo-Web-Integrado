// src/services/newsService.js
const API_KEY = 'b2bc5b8fd4144b598f472e4e44657e16'; // Reemplaza con tu clave real
const BASE_URL = 'https://newsapi.org/v2/everything';

export async function fetchAgricultureNews() {
  const response = await fetch(`${BASE_URL}?q=agriculture&language=es&sortBy=publishedAt&apiKey=${API_KEY}`);
  if (!response.ok) throw new Error('Error al obtener noticias');
  const data = await response.json();
  return data.articles;
}

