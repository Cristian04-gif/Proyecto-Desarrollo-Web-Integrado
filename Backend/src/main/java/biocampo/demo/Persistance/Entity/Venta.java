package biocampo.demo.Persistance.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
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
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;
    @ManyToOne
    private Usuario cliente;
    private LocalDateTime fechaVenta;
    @ElementCollection
    @CollectionTable(name = "detalle_venta", joinColumns = @JoinColumn(name = "venta_id"))
    private List<DetalleVenta> detalle;
}
