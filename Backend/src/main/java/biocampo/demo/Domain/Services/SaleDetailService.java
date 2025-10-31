package biocampo.demo.Domain.Services;

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

    public List<SaleDetail> getAllSaleDetails() {
        return saleDetailRepository.getAll();
    }

    public Optional<SaleDetail> getSaleDetailById(Long id) {
        return saleDetailRepository.getById(id);
    }

    public List<SaleDetail> getSaleDetailBySale(Long idSale){
        return saleDetailRepository.findBySaleId(idSale);
    }


    public void deleteSaleDetail(Long id) {
        saleDetailRepository.deleteById(id);
    }
}
