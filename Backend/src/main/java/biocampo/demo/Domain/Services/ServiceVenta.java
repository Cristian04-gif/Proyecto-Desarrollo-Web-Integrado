package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import biocampo.demo.Persistance.Entity.Venta;
import biocampo.demo.Domain.Repository.RepoVenta;

@Service
public class ServiceVenta {

    @Autowired
    private RepoVenta repoVenta;

    // Obtener todas las ventas
    public List<Venta> listarVentas() {
        return repoVenta.findAll();
    }

    // Buscar venta por ID
    public Optional<Venta> buscarVentaPorId(Long idVenta) {
        return repoVenta.findById(idVenta);
    }

    // Registrar nueva venta
    public Venta registrarVenta(Venta venta) {
        return repoVenta.save(venta);
    }

    // Actualizar venta existente
    public Optional<Venta> actualizarVenta(Long idVenta, Venta datosActualizados) {
        return repoVenta.findById(idVenta).map(venta -> {
            venta.setCliente(datosActualizados.getCliente());
            venta.setDetalle(datosActualizados.getDetalle());
            return repoVenta.save(venta);
        });
    }

    // Eliminar venta
    public void eliminarVenta(Long idVenta) {
        repoVenta.deleteById(idVenta);
    }
}
