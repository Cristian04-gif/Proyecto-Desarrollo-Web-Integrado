const API = "http://localhost:8080/api/pago"
const email = localStorage.getItem("user")
const token = localStorage.getItem("token")
export async function crearPago(detailsSale) {
    const detalles = JSON.parse(detailsSale)
    const carrito = detalles.map(item => ({
        product: { "productId": item.productId },
        quantity: item.qty
    }))
    console.log(detalles)
    const res = await fetch(API, {
        method: 'POST',
        headers: { "Content-Type": "application/json", Authorization: `Bearer ${token}` },
        body: JSON.stringify({ email: email, details: carrito })
    })
    if (!res.ok) throw new Error("No se pudo hacer la coneccion con el API de pago")
    return res.json();
}
