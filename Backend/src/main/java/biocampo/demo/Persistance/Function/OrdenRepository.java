package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Order;
import biocampo.demo.Domain.Repository.OrderRepository;
import biocampo.demo.Persistance.CRUD.RepoOrden;
import biocampo.demo.Persistance.Entity.Pedido;
import biocampo.demo.Persistance.Mappings.OrderMapper;

@Repository
public class OrdenRepository implements OrderRepository {

    @Autowired
    private RepoOrden repoProveedorInsumo;

    @Autowired
    private OrderMapper inputSupplierMapper;

    @Override
    public List<Order> getAll() {
        List<Pedido> all = repoProveedorInsumo.findAll();
        return inputSupplierMapper.toInputSuppliers(all);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return repoProveedorInsumo.findById(id).map(pi -> inputSupplierMapper.toInputSupplier(pi));
    }

    @Override
    public Order save(Order inputSupplier) {
        Pedido proveedorInsumo = inputSupplierMapper.toProveedorInsumo(inputSupplier);
        Pedido proveedorInsumo2 = repoProveedorInsumo.save(proveedorInsumo);
        return inputSupplierMapper.toInputSupplier(proveedorInsumo2);
    }

    @Override
    public void deleteById(Long id) {
        repoProveedorInsumo.deleteById(id);
    }

}
