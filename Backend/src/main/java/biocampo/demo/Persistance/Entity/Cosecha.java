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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cosecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCosecha;

    @OneToOne
    @JoinColumn(name = "idCultivo")
    private Cultivo cultivo;

    @CreationTimestamp
    private LocalDate fechaCosecha;

    private Double cantidadCosechada; //kilos, toneladas
    private String unidadMedida; //kg, ton
    private Double rendimietoXHectarea; //kg/ha cantidadCosechada/cultivo.heactareas
    private Double costo;

    
    @OneToMany(mappedBy = "cosecha")
    private List<Perdida> idPerdida;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "cosecha_empleado", joinColumns = @JoinColumn(name = "idCosecha"), inverseJoinColumns = @JoinColumn(name = "idEmpleado"))
    private List<Empleado> empleados;



    
}
