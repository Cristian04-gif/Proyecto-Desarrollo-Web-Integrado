const API = "https://servidor2-biocampo-latest.onrender.com/api/pago";
const token = localStorage.getItem("token");
const user = JSON.parse(localStorage.getItem("user"));
export async function crearPago(detailsSale) {

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


export async function comprobantePago() {
  const res = await fetch(`https://servidor2-biocampo-latest.onrender.com/sale/customer/${user.email}`, {
    method: 'GET',
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    }
  });

  if (!res.ok) throw new Error("No se descargar el comprobante de pago");
  return res.json();
}