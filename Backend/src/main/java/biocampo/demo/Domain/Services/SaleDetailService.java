package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.SaleDetail;

import biocampo.demo.Domain.Repository.SaleDetailRepository;


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


}
