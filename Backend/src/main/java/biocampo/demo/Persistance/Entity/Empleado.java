package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    @JsonBackReference
    private PuestoEmpleado puesto;

    private Double salario;
    @CreationTimestamp
    private LocalDate fechaContratado;
    private boolean disponible;
    @ManyToMany(mappedBy = "empleados")
    @JsonBackReference
    private List<Cultivo> cultivo;// = new ArrayList<>();

    @ManyToMany(mappedBy = "empleados")
    private List<Cosecha> cosecha;

    @ManyToMany(mappedBy = "empleados")
    private List<PostCosecha> postCosecha;
}
