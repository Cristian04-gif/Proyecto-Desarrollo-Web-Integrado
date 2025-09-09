package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class DetalleVenta {

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
