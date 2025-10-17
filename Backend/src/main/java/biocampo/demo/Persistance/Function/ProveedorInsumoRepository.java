package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.InputSupplier;
import biocampo.demo.Domain.Repository.InputSupplierRepository;
import biocampo.demo.Persistance.CRUD.RepoProveedorInsumo;
import biocampo.demo.Persistance.Entity.ProveedorInsumo;
import biocampo.demo.Persistance.Mappings.InputSupplierMapper;

@Repository
public class ProveedorInsumoRepository implements InputSupplierRepository {

    @Autowired
    private RepoProveedorInsumo repoProveedorInsumo;

    @Autowired
    private InputSupplierMapper inputSupplierMapper;

    @Override
    public List<InputSupplier> getAll() {
        List<ProveedorInsumo> all = repoProveedorInsumo.findAll();
        return inputSupplierMapper.toInputSuppliers(all);
    }

    @Override
    public Optional<InputSupplier> getById(Long id) {
        return repoProveedorInsumo.findById(id).map(pi -> inputSupplierMapper.toInputSupplier(pi));
    }

    @Override
    public InputSupplier save(InputSupplier inputSupplier) {
        ProveedorInsumo proveedorInsumo = inputSupplierMapper.toProveedorInsumo(inputSupplier);
        ProveedorInsumo proveedorInsumo2 = repoProveedorInsumo.save(proveedorInsumo);
        return inputSupplierMapper.toInputSupplier(proveedorInsumo2);
    }

    @Override
    public void deleteById(Long id) {
        repoProveedorInsumo.deleteById(id);
    }

}
