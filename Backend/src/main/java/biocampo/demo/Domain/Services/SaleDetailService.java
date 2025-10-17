package biocampo.demo.Domain.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.SaleDetail;
import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.Product;
import biocampo.demo.Domain.Repository.SaleDetailRepository;
import biocampo.demo.Domain.Repository.SaleRepository;

import biocampo.demo.Domain.Repository.ProductRepository;

@Service
public class SaleDetailService {

    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.getAll();
    }

    public Optional<SaleDetail> getSaleDetailById(Long id) {
        return saleDetailRepository.getById(id);
    }

    public List<SaleDetail> getSaleDetailsBySale(Sale sale) {
        Sale optionalSale = saleRepository.getById(sale.getSaleId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe la venta"));
        return saleDetailRepository.findBySale(optionalSale);
    }

    public SaleDetail registerSaleDetail(SaleDetail saleDetail) {
        Optional<Sale> sale = saleRepository.getById(saleDetail.getSale().getSaleId());
        Optional<Product> product = productRepository.getById(saleDetail.getProduct().getProductId());

        if (sale.isPresent() && product.isPresent()) {
            saleDetail.setSale(sale.get());
            saleDetail.setProduct(product.get());
            // return saleDetailRepository.save(saleDetail);
        } else {
            throw new IllegalArgumentException("La venta o producto relacionado no existe");
        }

        return saleDetailRepository.save(saleDetail);
    }

    @Transactional
    public List<SaleDetail> registerAllDetails(List<SaleDetail> details) {
        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("NO se proporcionod etalles");
        }

        Long saleId = details.getFirst().getSale().getSaleId();
        Sale sale = saleRepository.getById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("la venta con id: " + saleId + " no existe"));

        BigDecimal total = BigDecimal.ZERO;
        for (SaleDetail saleDetail : details) {
            Product product = productRepository.getById(saleDetail.getProduct().getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Prodcuto no encontrado"));

            if (product.getStock() < saleDetail.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente del producto: " + product.getName());
            }

            saleDetail.setSale(sale);
            saleDetail.setProduct(product);
            total = total.add(saleDetail.getSubTotal());
        }

        sale.setTotal(total);
        saleRepository.save(sale);
        return saleDetailRepository.saveAll(details);
        
    }

    public SaleDetail updateSaleDetail(Long id, SaleDetail saleDetail) {
        Optional<SaleDetail> existing = saleDetailRepository.getById(id);
        if (existing.isPresent()) {
            SaleDetail toUpdate = existing.get();
            toUpdate.setQuantity(saleDetail.getQuantity());
            toUpdate.setSubTotal(saleDetail.getSubTotal());

            Optional<Sale> sale = saleRepository.getById(saleDetail.getSale().getSaleId());
            sale.ifPresent(toUpdate::setSale);

            Optional<Product> product = productRepository.getById(saleDetail.getProduct().getProductId());
            product.ifPresent(toUpdate::setProduct);

            return saleDetailRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteSaleDetail(Long id) {
        saleDetailRepository.deleteById(id);
    }
}
