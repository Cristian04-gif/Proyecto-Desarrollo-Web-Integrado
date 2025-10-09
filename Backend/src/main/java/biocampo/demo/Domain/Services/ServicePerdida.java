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
    public List<Perdida> getAllPerdidas() {
        return repoPerdida.findAll();
    }

    // Obtener pérdida por ID
    public Optional<Perdida> getPerdidaById(Long idPerdida) {
        return repoPerdida.findById(idPerdida);
    }

    // Registrar nueva pérdida
    public Perdida registerPerdida(Perdida perdida) {
        return repoPerdida.save(perdida);
    }

    // Actualizar pérdida existente
    public Perdida updatePerdida(Long idPerdida, Perdida updatedData) {
        Optional<Perdida> existingPerdida = repoPerdida.findById(idPerdida);

        if (existingPerdida.isPresent()) {
            Perdida toUpdate = existingPerdida.get();

            if (updatedData.getTipoPerdida() != null)
                toUpdate.setTipoPerdida(updatedData.getTipoPerdida());
            if (updatedData.getAccion() != null)
                toUpdate.setAccion(updatedData.getAccion());
            if (updatedData.getCultivo() != null)
                toUpdate.setCultivo(updatedData.getCultivo());
            if (updatedData.getCosecha() != null)
                toUpdate.setCosecha(updatedData.getCosecha());

            return repoPerdida.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar pérdida
    public void deletePerdida(Long idPerdida) {
        Optional<Perdida> existingPerdida = repoPerdida.findById(idPerdida);
        if (existingPerdida.isPresent()) {
            repoPerdida.deleteById(idPerdida);
        }
    }
}
