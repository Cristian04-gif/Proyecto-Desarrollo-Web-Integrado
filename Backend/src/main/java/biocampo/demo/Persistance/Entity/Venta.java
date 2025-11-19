package biocampo.demo.Persistance.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @CreationTimestamp
    private LocalDateTime fechaVenta;
    private Double subTotal;
    private Double impuestoTotal;
    private Double total;
    @Enumerated(EnumType.STRING)
    private Metodo pago;
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Metodo {
        PAYPAL, MERCADO_PAGO
    }
    public enum Estado{
        PENDIENTE, PAGADO
    }

    @OneToMany(mappedBy = "venta", cascade = CascadeType.MERGE)
    private List<DetalleVenta> detalle = new ArrayList<>();
}
