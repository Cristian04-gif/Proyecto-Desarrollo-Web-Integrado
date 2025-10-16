package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Persistance.CRUD.RepoCultivo;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.Cosecha.Temporada;
import biocampo.demo.Persistance.Mappings.CultivationMapper;

@Repository
public class CultivoRepository implements CultivationRepository {

    @Autowired
    private RepoCultivo repoCultivo;
    @Autowired
    private CultivationMapper cultivationMapper;

    @Override
    public List<Cultivation> getAll() {
        List<Cultivo> all = repoCultivo.findAll();
        return cultivationMapper.toCultivations(all);
    }

    @Override
    public Optional<Cultivation> getById(Long id) {
        return repoCultivo.findById(id).map(cultivo -> cultivationMapper.toCultivation(cultivo));
    }

    @Override
    public Cultivation save(Cultivation cultivation) {
        Cultivo cultivo = cultivationMapper.toCultivo(cultivation);
        Cultivo cultivoGuardado = repoCultivo.save(cultivo);
        return cultivationMapper.toCultivation(cultivoGuardado);
    }

    @Override
    public void deleteById(Long id) {
        repoCultivo.deleteById(id);
    }

    @Override
    public List<Cultivation> findBySeason(String season) {
        for (Temporada temp : Temporada.values()) {
            if (temp.toString().equalsIgnoreCase(season)) {
                List<Cultivo> listaTemp = repoCultivo.findByTemporada(temp);
                return cultivationMapper.toCultivations(listaTemp);
            }
        }
        return null;
    }

}
