package biocampo.demo.Persistance.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import biocampo.demo.Persistance.Entity.Cosecha.Temporada;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCultivo;

    private String nombreParcela;
    @ManyToOne
    @JoinColumn(name = "idPlanta")
    private Planta planta;
    private Double hectareas;
    private Double paquetesRequeridos;
    private Double costo;
    @CreationTimestamp
    private LocalDate fechaCultivo;
    private int cadaRiego;

    @Enumerated(EnumType.STRING)
    private Temporada temporada;
    private LocalDate fechaEstimadaCosecha;

    @OneToMany(mappedBy = "cultivo")
    private List<Perdida> perdidas;

    @OneToMany(mappedBy = "cultivo", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<CultivoInsumo> insumos;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "cultivo_empleado", joinColumns = @JoinColumn(name = "idCultivo"), inverseJoinColumns = @JoinColumn(name = "idEmpleado"))
    @JsonManagedReference
    private List<Empleado> empleados = new ArrayList<>();
}
