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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PostCosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPostCosecha;

    @OneToOne
    @JoinColumn(name = "idCosecha")
    private Cosecha cosecha;

    @CreationTimestamp
    private LocalDate fecha;

    private Double costoAlmacenamiento;
    private Double costoEmpleado;
    private Double precioUnidad; // precio por kilo: ((suma de costos de cultivo, cosecha, postcosecha, (precioUnidad*unidadPerdida))/cosecha.cantidadCosecha)
    private Double unidadPerdida; //kg de posible cantidad de cosecha perdida//calcular (precioUnidad*unidadPerdida) para calcular el precio unitario
    private Double ingresoTotal; // cosecha.cantidadCosecha *precioUnidad;
    private Double ganancia; // ingresototal-(costos de cultivo, cosecha y postcosecha)
    private String observaciones;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "postCosecha_empleado", joinColumns = @JoinColumn(name = "idPostCosecha"), inverseJoinColumns = @JoinColumn(name = "idEmpleado"))
    private List<Empleado> empleados;

}
