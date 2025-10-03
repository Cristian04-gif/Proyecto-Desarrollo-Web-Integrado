package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Persistance.Entity.Perdida;
import biocampo.demo.Domain.Repository.RepoPerdida;

@Service
public class ServicePerdida {

    @Autowired
    private RepoPerdida repoPerdida;

    // Listar todas las pérdidas
    public List<Perdida> listarPerdidas() {
        return repoPerdida.findAll();
    }

    // Buscar pérdida por ID
    public Optional<Perdida> buscarPerdidaPorId(Long idPerdida) {
        return repoPerdida.findById(idPerdida);
    }

    // Registrar nueva pérdida
    public Perdida registrarPerdida(Perdida perdida) {
        return repoPerdida.save(perdida);
    }

    // Actualizar pérdida existente
    public Optional<Perdida> actualizarPerdida(Long idPerdida, Perdida datosActualizados) {
        return repoPerdida.findById(idPerdida).map(perdida -> {
            perdida.setTipoPerdida(datosActualizados.getTipoPerdida());
            perdida.setAccion(datosActualizados.getAccion());
            perdida.setCultivo(datosActualizados.getCultivo());
            perdida.setCosecha(datosActualizados.getCosecha());
            return repoPerdida.save(perdida);
        });
    }

    // Eliminar pérdida
    public void eliminarPerdida(Long idPerdida) {
        repoPerdida.deleteById(idPerdida);
    }
}