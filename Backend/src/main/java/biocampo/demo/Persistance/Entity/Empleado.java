package biocampo.demo.Persistance.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;
    private String nombres;
    private String apellidos;
    private int edad;
    private String telefono;
    private String emailPersonal;
    private String emailEmpresarial;
    private String dni;
    private String pais;
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idPuesto")
    @JsonManagedReference
    private PuestoEmpleado puesto;

    private BigDecimal salario;
    @CreationTimestamp
    private LocalDate fechaContratado;

    @ManyToMany
    @JoinTable(name = "cultivo_empleado", joinColumns = @JoinColumn(name = "idEmpleado"), inverseJoinColumns = @JoinColumn(name = "idCultivo"))
    private List<Cultivo> cultivo;

    @ManyToMany
    @JoinTable(name = "cosecha_empleado", joinColumns = @JoinColumn(name = "idEmpleado"), inverseJoinColumns = @JoinColumn(name = "idCosecha"))
    private List<Cosecha> cosecha;

    @ManyToMany
    @JoinTable(name = "postCosecha_empleado", joinColumns = @JoinColumn(name = "idEmpleado"), inverseJoinColumns = @JoinColumn(name = "idPostCosecha"))
    private List<PostCosecha> postCosecha;
}
