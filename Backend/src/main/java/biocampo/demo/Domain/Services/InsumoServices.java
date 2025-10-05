package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCultivo;
import biocampo.demo.Domain.Repository.RepoInsumo;
import biocampo.demo.Domain.Repository.RepoPlanta;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.Insumo;
import biocampo.demo.Persistance.Entity.Planta;
import jakarta.transaction.Transactional;

@Service
public class InsumoServices {

    @Autowired
    private RepoInsumo repoInsumo;

    @Autowired
    private RepoPlanta repoPlanta;

    @Autowired
    private RepoCultivo repoCultivo;

    public List<Insumo> listarTodo() {
        return repoInsumo.findAll();
    }

    public Optional<Insumo> buscarInsumo(Long id) {
        return repoInsumo.findById(id);
    }

    public Insumo registrarInsumo(Insumo insumo) {
        Optional<Planta> planta = repoPlanta.findById(insumo.getPlanta().getIdPlanta());
        Optional<Cultivo> cultivo = repoCultivo.findById(insumo.getCultivo().getIdCultivo());

        if (planta.isPresent() && cultivo.isPresent()) {
            insumo.setPlanta(planta.get());
            insumo.setCultivo(cultivo.get());
            return repoInsumo.save(insumo);
        } else {
            return null;
        }
    }

    public Insumo actualizarInsumo(Long id, Insumo insumo) {
        Optional<Insumo> existe = repoInsumo.findById(id);
        if (existe.isPresent()) {
            Insumo actualizar = existe.get();
            actualizar.setNombre(insumo.getNombre());
            actualizar.setTipo(insumo.getTipo());
            actualizar.setDescripcion(insumo.getDescripcion());
            actualizar.setUnidad(insumo.getUnidad());

            Optional<Planta> planta = repoPlanta.findById(insumo.getPlanta().getIdPlanta());
            planta.ifPresent(actualizar::setPlanta);

            Optional<Cultivo> cultivo = repoCultivo.findById(insumo.getCultivo().getIdCultivo());
            cultivo.ifPresent(actualizar::setCultivo);

            return repoInsumo.save(actualizar);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarInsumo(Long id) {
        if (repoInsumo.existsById(id)) {
            repoInsumo.deleteById(id);
        }
    }
}
