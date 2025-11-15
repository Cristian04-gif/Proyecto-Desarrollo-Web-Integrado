// src/services/productService.js
import axios from "axios";

const API_BASE = "http://localhost:8080";

export function fetchProducts() {
  return axios.get(`${API_BASE}/api/product/all`)
    .then(res => res.data)
    .catch(error => {
      console.error("Error al obtener productos:", error);
      return [];
    });
}
