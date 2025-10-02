package biocampo.demo.Persistance.Entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;
    private String nombre;
    private String ruc;
    private String telefono;
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<ProveedorInsumo> proveedorInsumos;
}
