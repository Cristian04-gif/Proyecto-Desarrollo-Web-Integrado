package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoDetalleVenta;
import biocampo.demo.Domain.Repository.RepoProducto;
import biocampo.demo.Domain.Repository.RepoVenta;
import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Producto;
import biocampo.demo.Persistance.Entity.Venta;
import jakarta.transaction.Transactional;

@Service
public class DetalleVentaServices {

    @Autowired
    private RepoDetalleVenta repoDetalleVenta;

    @Autowired
    private RepoVenta repoVenta;

    @Autowired
    private RepoProducto repoProducto;

    public List<DetalleVenta> listarTodo() {
        return repoDetalleVenta.findAll();
    }

    public Optional<DetalleVenta> buscarDetalleVenta(Long id) {
        return repoDetalleVenta.findById(id);
    }

    public DetalleVenta registrarDetalleVenta(DetalleVenta detalleVenta) {
        Optional<Venta> venta = repoVenta.findById(detalleVenta.getVenta().getIdVenta());
        Optional<Producto> producto = repoProducto.findById(detalleVenta.getProducto().getIdProducto());

        if (venta.isPresent() && producto.isPresent()) {
            detalleVenta.setVenta(venta.get());
            detalleVenta.setProducto(producto.get());
            return repoDetalleVenta.save(detalleVenta);
        } else {
            return null;
        }
    }

    public DetalleVenta actualizarDetalleVenta(Long id, DetalleVenta detalleVenta) {
        Optional<DetalleVenta> existe = repoDetalleVenta.findById(id);
        if (existe.isPresent()) {
            DetalleVenta actualizar = existe.get();
            actualizar.setCantidad(detalleVenta.getCantidad());
            actualizar.setTotal(detalleVenta.getTotal());
            actualizar.setPago(detalleVenta.getPago());

            Optional<Venta> venta = repoVenta.findById(detalleVenta.getVenta().getIdVenta());
            venta.ifPresent(actualizar::setVenta);

            Optional<Producto> producto = repoProducto.findById(detalleVenta.getProducto().getIdProducto());
            producto.ifPresent(actualizar::setProducto);

            return repoDetalleVenta.save(actualizar);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarDetalleVenta(Long id) {
        if (repoDetalleVenta.existsById(id)) {
            repoDetalleVenta.deleteById(id);
        }
    }
}
