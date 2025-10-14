package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Repository.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    // Obtener todas las ventas
    public List<Sale> getAllSales() {
        return saleRepository.getAll();
    }

    // Obtener venta por ID
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.getById(id);
    }

    // Registrar nueva venta
    public Sale registerSale(Sale sale) {
        return saleRepository.save(sale);
    }

    // Actualizar venta existente
    public Sale updateSale(Long id, Sale updatedSale) {
        Optional<Sale> existingSale = saleRepository.getById(id);

        if (existingSale.isPresent()) {
            Sale toUpdate = existingSale.get();

            if (updatedSale.getCustomer() != null)
                toUpdate.setCustomer(updatedSale.getCustomer());
            if (updatedSale.getSaleDate() != null)
                toUpdate.setSaleDate(updatedSale.getSaleDate());
            if (updatedSale.getTotal() != null)
                toUpdate.setTotal(updatedSale.getTotal());

            return saleRepository.save(toUpdate);
        } else {
            // También podrías lanzar una excepción si no existe
            return saleRepository.save(updatedSale);
        }
    }

    // Eliminar venta
    public void deleteSale(Long id) {
        Optional<Sale> existingSale = saleRepository.getById(id);
        existingSale.ifPresent(s -> saleRepository.deleteById(id));
    }
}
