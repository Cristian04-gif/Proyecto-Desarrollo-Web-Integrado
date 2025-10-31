package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @CreationTimestamp
    private LocalDate fecha;
    private Double total;
    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.MERGE)
    private List<DetallePedido> detallePedidos;

    /*@ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "idInsumo")
    private Insumo insumo;
    private BigDecimal precio;*/

}
