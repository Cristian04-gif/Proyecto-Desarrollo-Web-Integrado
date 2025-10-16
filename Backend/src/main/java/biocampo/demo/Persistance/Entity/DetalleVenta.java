package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    private Metodo pago;

    public enum Metodo {
        PAYPAL, TARJETA
    }
}
