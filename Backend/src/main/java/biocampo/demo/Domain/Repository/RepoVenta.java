package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Sale;

public interface RepoVenta {

    // Obtener todas las ventas
    List<Sale> getAll();

    // Obtener venta por ID
    Optional<Sale> getById(Long id);

    // Guardar o actualizar venta
    Sale save(Sale sale);

    // Eliminar venta por ID
    void deleteById(Long id);
}
