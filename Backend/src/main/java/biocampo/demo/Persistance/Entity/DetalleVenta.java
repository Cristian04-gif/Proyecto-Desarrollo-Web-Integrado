package biocampo.demo.Persistance.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "idVenta")
    private Venta venta;
    
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;
    
    private int cantidad;
    private Double subtotal;
    private Double impuestos;
    private Double porcentajeImpuestos;
    

    public String getEtiquetaProducto() {
        return producto != null ? producto.getEtiqueta() : null;
    }

    public Double getPrecioUnitario(){
        return producto != null ? producto.getPrecio() : null;
    }

    public String getPesoUnidadMedida(){
        return producto != null ? producto.getPeso() + " " + producto.getUnidadMedida() : null;
    }
}
