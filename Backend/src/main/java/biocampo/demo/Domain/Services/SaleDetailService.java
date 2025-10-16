package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return saleDetailRepository.findBySale(sale);
    }

    public SaleDetail registerSaleDetail(SaleDetail saleDetail) {
        Optional<Sale> sale = saleRepository.getById(saleDetail.getSale().getSaleId());
        Optional<Product> product = productRepository.getById(saleDetail.getProduct().getProductId());

        if (sale.isPresent() && product.isPresent()) {
            saleDetail.setSale(sale.get());
            saleDetail.setProduct(product.get());
            return saleDetailRepository.save(saleDetail);
        } else {
            return null;
        }
    }

    public SaleDetail updateSaleDetail(Long id, SaleDetail saleDetail) {
        Optional<SaleDetail> existing = saleDetailRepository.getById(id);
        if (existing.isPresent()) {
            SaleDetail toUpdate = existing.get();
            toUpdate.setQuantity(saleDetail.getQuantity());
            toUpdate.setSubTotal(saleDetail.getSubTotal());
            toUpdate.setPaymentMethod(saleDetail.getPaymentMethod());

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
