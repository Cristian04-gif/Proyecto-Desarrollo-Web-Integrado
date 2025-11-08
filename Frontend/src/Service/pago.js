const API = "http://localhost:8080/api/pago"
export async function crearPago(sale, details) {
    const res = await fetch(API, {
        method: 'POST',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ sale, details })
    })
    if (!res.ok) throw new Error("No se pudo hacer la coneccion con el API de pago")
    return res.json();
}
