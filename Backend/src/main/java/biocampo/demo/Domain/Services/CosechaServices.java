package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCosecha;
import biocampo.demo.Domain.Repository.RepoPlanta;
import biocampo.demo.Persistance.Entity.Cosecha;
import biocampo.demo.Persistance.Entity.Planta;
import jakarta.transaction.Transactional;

@Service
public class CosechaServices {

    @Autowired
    private RepoCosecha repoCosecha;

    @Autowired
    private RepoPlanta repoPlanta;

    public List<Cosecha> listarTodo() {
        return repoCosecha.findAll();
    }

    public Optional<Cosecha> buscarCosecha(Long id) {
        return repoCosecha.findById(id);
    }

    public Cosecha registrarCosecha(Cosecha cosecha) {
        Optional<Planta> planta = repoPlanta.findById(cosecha.getPlanta().getIdPlanta());
        if (planta.isPresent()) {
            cosecha.setPlanta(planta.get());
            return repoCosecha.save(cosecha);
        } else {
            return null;
        }
    }

    public Cosecha actualizarCosecha(Long id, Cosecha cosecha) {
        Optional<Cosecha> existe = repoCosecha.findById(id);
        if (existe.isPresent()) {
            Cosecha actualizar = existe.get();
            actualizar.setFechaCosecha(cosecha.getFechaCosecha());
            actualizar.setTemporada(cosecha.getTemporada());
            actualizar.setRecolector(cosecha.getRecolector());

            Optional<Planta> planta = repoPlanta.findById(cosecha.getPlanta().getIdPlanta());
            planta.ifPresent(actualizar::setPlanta);

            return repoCosecha.save(actualizar);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCosecha(Long id) {
        if (repoCosecha.existsById(id)) {
            repoCosecha.deleteById(id);
        }
    }
}
