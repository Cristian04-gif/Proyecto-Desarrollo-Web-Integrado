package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCultivo;
import biocampo.demo.Domain.Repository.RepoCultivoInsumo;
import biocampo.demo.Domain.Repository.RepoInsumo;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.CultivoInsumo;
import biocampo.demo.Persistance.Entity.Insumo;
import jakarta.transaction.Transactional;

@Service
public class CultivoInsumoServices {

    @Autowired
    private RepoCultivoInsumo repoCultivoInsumo;

    @Autowired
    private RepoCultivo repoCultivo;

    @Autowired
    private RepoInsumo repoInsumo;

    public List<CultivoInsumo> listarTodo() {
        return repoCultivoInsumo.findAll();
    }

    public Optional<CultivoInsumo> buscarCultivoInsumo(Long id) {
        return repoCultivoInsumo.findById(id);
    }

    public CultivoInsumo registrarCultivoInsumo(CultivoInsumo cultivoInsumo) {
        Optional<Cultivo> cultivo = repoCultivo.findById(cultivoInsumo.getCultivo().getIdCultivo());
        Optional<Insumo> insumo = repoInsumo.findById(cultivoInsumo.getInsumo().getIdInsumo());

        if (cultivo.isPresent() && insumo.isPresent()) {
            cultivoInsumo.setCultivo(cultivo.get());
            cultivoInsumo.setInsumo(insumo.get());
            return repoCultivoInsumo.save(cultivoInsumo);
        } else {
            return null;
        }
    }

    public CultivoInsumo actualizarCultivoInsumo(Long id, CultivoInsumo cultivoInsumo) {
        Optional<CultivoInsumo> existe = repoCultivoInsumo.findById(id);
        if (existe.isPresent()) {
            CultivoInsumo actualizar = existe.get();
            actualizar.setCantidad(cultivoInsumo.getCantidad());

            Optional<Cultivo> cultivo = repoCultivo.findById(cultivoInsumo.getCultivo().getIdCultivo());
            cultivo.ifPresent(actualizar::setCultivo);

            Optional<Insumo> insumo = repoInsumo.findById(cultivoInsumo.getInsumo().getIdInsumo());
            insumo.ifPresent(actualizar::setInsumo);

            return repoCultivoInsumo.save(actualizar);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCultivoInsumo(Long id) {
        if (repoCultivoInsumo.existsById(id)) {
            repoCultivoInsumo.deleteById(id);
        }
    }
}
