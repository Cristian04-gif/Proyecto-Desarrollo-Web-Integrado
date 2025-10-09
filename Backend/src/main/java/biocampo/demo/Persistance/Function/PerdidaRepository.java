package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Loss;
import biocampo.demo.Domain.Repository.LossRepository;
import biocampo.demo.Persistance.CRUD.RepoPerdida;
import biocampo.demo.Persistance.Entity.Perdida;
import biocampo.demo.Persistance.Mappings.LossMapper;

@Repository
public class PerdidaRepository implements LossRepository{

    @Autowired
    private RepoPerdida repoPerdida;
    @Autowired
    private LossMapper lossMapper;

    @Override
    public List<Loss> getAll() {
        List<Perdida> list = repoPerdida.findAll();
        return lossMapper.toLosses(list);
    }

    @Override
    public Optional<Loss> getById(Long id) {
        return repoPerdida.findById(id).map(perdida -> lossMapper.toLoss(perdida));
    }

    @Override
    public Loss save(Loss loss) {
        Perdida perdida = lossMapper.toPerdida(loss);
        Perdida perdida2 = repoPerdida.save(perdida);
        return lossMapper.toLoss(perdida2);
    }

    @Override
    public void deleteById(Long id) {
        repoPerdida.deleteById(id);
    }

}
