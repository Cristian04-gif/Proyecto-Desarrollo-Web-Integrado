package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Supplier;
import biocampo.demo.Domain.Repository.SupplierRepository;
import biocampo.demo.Persistance.CRUD.RepoProveedor;
import biocampo.demo.Persistance.Entity.Proveedor;
import biocampo.demo.Persistance.Mappings.SupplierMapper;

@Repository
public class ProveedorRepository implements SupplierRepository{

    @Autowired
    private RepoProveedor repoProveedor;
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<Supplier> getAll() {
        List<Proveedor> list = repoProveedor.findAll();
        return supplierMapper.toSuppliers(list);
    }

    @Override
    public Optional<Supplier> getById(Long id) {
        return repoProveedor.findById(id).map(provee -> supplierMapper.toSupplier(provee));
    }

    @Override
    public Supplier save(Supplier supplier) {
        Proveedor proveedor = supplierMapper.toProveedor(supplier);
        Proveedor proveedor2 = repoProveedor.save(proveedor);
        return supplierMapper.toSupplier(proveedor2);
    }

    @Override
    public void deleteById(Long id) {
        repoProveedor.deleteById(id);
    }

}
