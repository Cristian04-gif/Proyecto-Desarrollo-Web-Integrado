package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Repository.SaleRepository;
import biocampo.demo.Persistance.CRUD.RepoVenta;
import biocampo.demo.Persistance.Entity.Venta;
import biocampo.demo.Persistance.Mappings.SaleMapper;

@Repository
public class VentaRepository implements SaleRepository{

    @Autowired
    private RepoVenta repoVenta;
    @Autowired
    private SaleMapper saleMapper;

    @Override
    public List<Sale> getAll() {
        List<Venta> list = repoVenta.findAll();
        return saleMapper.toSales(list);
    }

    @Override
    public Optional<Sale> getbyId(Long id) {
        return repoVenta.findById(id).map(venta -> saleMapper.toSale(venta));
    }

    @Override
    public Sale save(Sale sale) {
        Venta venta = saleMapper.toVenta(sale);
        Venta venta2 = repoVenta.save(venta);
        return saleMapper.toSale(venta2);
    }

    @Override
    public void deleteById(Long id) {
        repoVenta.deleteById(id);
    }

}
