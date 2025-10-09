package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Repository.RepoVenta;

@Service
public class ServiceVenta {

    @Autowired
    private RepoVenta repoVenta;

    // Obtener todas las ventas
    public List<Sale> getAllSales() {
        return repoVenta.getAll();
    }

    // Obtener venta por ID
    public Optional<Sale> getSaleById(Long id) {
        return repoVenta.getById(id);
    }

    // Registrar nueva venta
    public Sale registerSale(Sale sale) {
        return repoVenta.save(sale);
    }

    // Actualizar venta existente
    public Sale updateSale(Long id, Sale updatedSale) {
        Optional<Sale> existingSale = repoVenta.getById(id);

        if (existingSale.isPresent()) {
            Sale toUpdate = existingSale.get();

            if (updatedSale.getCustomer() != null)
                toUpdate.setCustomer(updatedSale.getCustomer());
            if (updatedSale.getSaleDate() != null)
                toUpdate.setSaleDate(updatedSale.getSaleDate());
            if (updatedSale.getTotal() != null)
                toUpdate.setTotal(updatedSale.getTotal());

            return repoVenta.save(toUpdate);
        } else {
            // Si no existe, opcionalmente se puede crear una nueva venta
            return repoVenta.save(updatedSale);
        }
    }

    // Eliminar venta
    public void deleteSale(Long id) {
        Optional<Sale> existingSale = repoVenta.getById(id);
        if (existingSale.isPresent()) {
            repoVenta.deleteById(id);
        }
    }
}
