const API = "http://localhost:8080/api/pago";
const token = localStorage.getItem("token");

export async function crearPago(detailsSale) {
  const user = JSON.parse(localStorage.getItem("user"));   
  const email = user.email;                                

  const detalles = JSON.parse(detailsSale);

  const carrito = detalles.map(item => ({
    product: { productId: item.productId },  
    quantity: item.qty,
    subTotal: item.price * item.qty
  }));

  console.log("Payload enviado:", { email, details: carrito });

  const res = await fetch(API, {
    method: 'POST',
    headers: { 
      "Content-Type": "application/json", 
      "Authorization": `Bearer ${token}` 
    },
    body: JSON.stringify({ email, details: carrito })
  });

  if (!res.ok) throw new Error("No se pudo hacer la conexión con el API de pago");
  return res.json();
}
