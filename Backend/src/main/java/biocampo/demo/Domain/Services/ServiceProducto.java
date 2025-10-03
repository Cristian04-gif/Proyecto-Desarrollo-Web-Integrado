package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Domain.Repository.RepoProducto;

@Service
public class ServiceProducto {

    @Autowired
    private RepoProducto repoProducto;

    // Listar todos los productos
    public List<Producto> listarProductos() {
        return repoProducto.findAll();
    }

    // Buscar producto por ID
    public Optional<Producto> buscarProductoPorId(Long idProducto) {
        return repoProducto.findById(idProducto);
    }

    // Registrar nuevo producto
    public Producto registrarProducto(Producto producto) {
        return repoProducto.save(producto);
    }

    // Actualizar producto existente
    public Optional<Producto> actualizarProducto(Long idProducto, Producto datosActualizados) {
        return repoProducto.findById(idProducto).map(producto -> {
            producto.setPantaPostCosecha(datosActualizados.getPantaPostCosecha());
            producto.setImgProducto(datosActualizados.getImgProducto());
            producto.setEtiqueta(datosActualizados.getEtiqueta());
            producto.setPeso(datosActualizados.getPeso());
            producto.setPrecio(datosActualizados.getPrecio());
            producto.setCantidad(datosActualizados.getCantidad());
            producto.setDisponible(datosActualizados.isDisponible());
            return repoProducto.save(producto);
        });
    }

    // Eliminar producto
    public void eliminarProducto(Long idProducto) {
        repoProducto.deleteById(idProducto);
    }
}