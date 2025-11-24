const API = "http://localhost:8080/api/pago"
const token = localStorage.getItem("token")
const emailUser = localStorage.getItem("user")
export async function crearPago(detailsSale) {
    const res = await fetch(API, {
        method: 'POST',
        headers: { "Authorization": `Bearer ${token}` },
        body: JSON.stringify({ email: emailUser, details: detailsSale })
    })
    if (!res.ok) throw new Error("No se pudo hacer la coneccion con el API de pago")
    return res.json();
}
