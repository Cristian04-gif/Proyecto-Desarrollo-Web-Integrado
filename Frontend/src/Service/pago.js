const API = "http://localhost:8080/api/pago"
export async function crearPago(emailUser, detailsSale) {
    const res = await fetch(API, {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: emailUser, details: detailsSale })
    })
    if (!res.ok) throw new Error("No se pudo hacer la coneccion con el API de pago")
    return res.json();
}
