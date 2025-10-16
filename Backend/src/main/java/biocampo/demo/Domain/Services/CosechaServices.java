package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Persistance.CRUD.RepoCosecha;
import biocampo.demo.Persistance.CRUD.RepoCultivo;
import biocampo.demo.Persistance.Entity.Cosecha;
import biocampo.demo.Persistance.Entity.Cultivo;
import jakarta.transaction.Transactional;

@Service
public class CosechaServices {

    @Autowired
    private RepoCosecha repoCosecha;

    @Autowired
    private RepoCultivo repoCultivo;

    public List<Cosecha> listarTodo() {
        return repoCosecha.findAll();
    }

    public Optional<Cosecha> buscarCosecha(Long id) {
        return repoCosecha.findById(id);
    }

    public Cosecha registrarCosecha(Cosecha cosecha) {
        Optional<Cultivo> cultivo = repoCultivo.findById(cosecha.getCultivo().getIdCultivo());
        if (cultivo.isPresent()) {
            cosecha.setCultivo(cultivo.get());
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

            Optional<Cultivo> cultivo = repoCultivo.findById(cosecha.getCultivo().getIdCultivo());
            cultivo.ifPresent(actualizar::setCultivo);

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
