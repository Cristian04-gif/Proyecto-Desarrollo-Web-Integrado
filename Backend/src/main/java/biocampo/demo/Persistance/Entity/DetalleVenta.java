package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Venta venta;
    @ManyToOne
    private Producto producto;
    private int cantidad;
    @ManyToOne
    private Usuario destino;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private Metodo pago;

    enum Metodo{
        PAYPAL, TARJETA
    }
}
