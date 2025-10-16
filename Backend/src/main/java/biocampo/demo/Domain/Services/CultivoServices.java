package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCultivo;
import biocampo.demo.Domain.Repository.RepoPlanta;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.Planta;
import jakarta.transaction.Transactional;

@Service
public class CultivoServices {

    @Autowired
    private RepoCultivo repoCultivo;

    @Autowired
    private RepoPlanta repoPlanta;

    public List<Cultivo> listarTodo() {
        return repoCultivo.findAll();
    }

    public Optional<Cultivo> buscarCultivo(Long id) {
        return repoCultivo.findById(id);
    }

    public Cultivo registrarCultivo(Cultivo cultivo) {
        Optional<Planta> planta = repoPlanta.findById(cultivo.getPlanta().getIdPlanta());
        if (planta.isPresent()) {
            cultivo.setPlanta(planta.get());
            return repoCultivo.save(cultivo);
        } else {
            return null;
        }
    }

    public Cultivo actualizarCultivo(Long id, Cultivo cultivo) {
        Optional<Cultivo> existe = repoCultivo.findById(id);
        if (existe.isPresent()) {
            Cultivo actualizar = existe.get();
            actualizar.setHectareas(cultivo.getHectareas());
            actualizar.setCosto(cultivo.getCosto());
            actualizar.setCadaRiego(cultivo.getCadaRiego());
            actualizar.setTemporada(cultivo.getTemporada());
            actualizar.setFechaEstimadaCosecha(cultivo.getFechaEstimadaCosecha());

            Optional<Planta> planta = repoPlanta.findById(cultivo.getPlanta().getIdPlanta());
            planta.ifPresent(actualizar::setPlanta);

            return repoCultivo.save(actualizar);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCultivo(Long id) {
        if (repoCultivo.existsById(id)) {
            repoCultivo.deleteById(id);
        }
    }
}
