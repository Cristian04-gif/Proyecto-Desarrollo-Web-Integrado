package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Repository.SaleDetailRepository;
import biocampo.demo.Persistance.CRUD.RepoDetalleVenta;
import biocampo.demo.Persistance.CRUD.RepoVenta;
import biocampo.demo.Persistance.Entity.DetalleVenta;
import biocampo.demo.Persistance.Entity.Venta;
import biocampo.demo.Persistance.Mappings.SaleDetailMapper;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class DetalleVentaRepository implements SaleDetailRepository {

    @Autowired
    private RepoDetalleVenta repoDetalleVenta;
    @Autowired
    private SaleDetailMapper detailMapper;
    @Autowired
    private RepoVenta repoVenta;

    @Override
    public List<SaleDetail> getAll() {
        List<DetalleVenta> detalleVentas = repoDetalleVenta.findAll();
        return detailMapper.toSaleDetails(detalleVentas);
    }

    @Override
    public Optional<SaleDetail> getById(Long id) {
        return repoDetalleVenta.findById(id).map(dv -> detailMapper.toSaleDetail(dv));
    }

    @Override
    public SaleDetail save(SaleDetail detail) {
        DetalleVenta detalleVenta = detailMapper.toDetalleVenta(detail);
        DetalleVenta detalleVenta2 = repoDetalleVenta.save(detalleVenta);
        return detailMapper.toSaleDetail(detalleVenta2);
    }

    @Override
    public void deleteById(Long id) {
        repoDetalleVenta.deleteById(id);
    }

    @Override
    public List<SaleDetail> findBySale(Sale sale) {
        if (sale.getSaleId() == null) {
            throw new IllegalArgumentException("error, no puede ser nulo");
        }

        Venta venta = repoVenta.findById(sale.getSaleId()).orElseThrow(() -> new EntityNotFoundException("Error, no se encontro la venta"));
        List<DetalleVenta> detalleVentas = repoDetalleVenta.findByVenta(venta);
        return detailMapper.toSaleDetails(detalleVentas);
    }

    @Override
    public List<SaleDetail> saveAll(List<SaleDetail> details) {
        List<DetalleVenta> detalleVentas = detailMapper.toDetalleVentas(details);
        List<DetalleVenta> detalleVentasGuardados = repoDetalleVenta.saveAll(detalleVentas);
        return detailMapper.toSaleDetails(detalleVentasGuardados);
    }

}
