// src/services/productService.js
import axios from "axios";

export function fetchProducts() {
  return axios.get("/api/product/all")
    .then(res => res.data)
    .catch(error => {
      console.error("Error al obtener productos:", error);
      return [];
    });
}
