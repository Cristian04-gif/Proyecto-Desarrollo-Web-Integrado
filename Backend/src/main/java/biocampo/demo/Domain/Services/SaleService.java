package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Repository.CustomerRepository;
import biocampo.demo.Domain.Repository.SaleRepository;
import biocampo.demo.Persistance.Entity.Venta.Metodo;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
        Customer customer = customerRepository.getByIdCustomer(sale.getCustomer().getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe el cliente asignado"));
        sale.setCustomer(customer);
        return saleRepository.save(sale);
    }

    // Actualizar venta existente
    public Sale updateSale(Long id, Sale updatedSale) {
        Optional<Sale> existingSale = saleRepository.getById(id);

        if (existingSale.isPresent()) {
            Sale toUpdate = existingSale.get();

            if (updatedSale.getCustomer() != null) {
                Optional<Customer> existe = customerRepository
                        .getByIdCustomer(updatedSale.getCustomer().getCustomerId());
                if (existe.isPresent()) {
                    toUpdate.setCustomer(existe.get());
                } else {
                    throw new IllegalArgumentException("Error! el cliente no existe");
                }
            }
            if (updatedSale.getTotal() != null)
                toUpdate.setTotal(updatedSale.getTotal());

            if (updatedSale.getPaymentMethod() != null) {
                boolean existPay = false;
                for (Metodo metodo : Metodo.values()) {
                    if (metodo.toString().equalsIgnoreCase(updatedSale.getPaymentMethod())) {
                        updatedSale.setPaymentMethod(metodo.name());
                        existPay = true;
                        break;
                    }
                }
                if (!existPay) {
                    throw new IllegalArgumentException("EL metodo de pago no existe");
                }
                toUpdate.setPaymentMethod(updatedSale.getPaymentMethod());
            }
            return saleRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar venta
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
