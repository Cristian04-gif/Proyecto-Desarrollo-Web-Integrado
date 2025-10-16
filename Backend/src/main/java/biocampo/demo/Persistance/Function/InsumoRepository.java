package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Persistance.CRUD.RepoInsumo;
import biocampo.demo.Persistance.Entity.Insumo;
import biocampo.demo.Persistance.Mappings.InputMapper;

@Repository
public class InsumoRepository implements InputRepository{

    @Autowired
    private RepoInsumo repoInsumo;

    @Autowired
    private InputMapper inputMapper;

    @Override
    public List<Input> getAll() {
        List<Insumo> all = repoInsumo.findAll();
        return inputMapper.toInputs(all);
    }

    @Override
    public Optional<Input> getById(Long id) {
        return repoInsumo.findById(id).map(insumo -> inputMapper.toInput(insumo));
    }

    @Override
    public Input save(Input input) {
        Insumo insumo = inputMapper.toInsumo(input);
        Insumo insumoGuardado = repoInsumo.save(insumo);
        return inputMapper.toInput(insumoGuardado);
    }

    @Override
    public void deleteById(Long id) {
        repoInsumo.deleteById(id);
    }

}
