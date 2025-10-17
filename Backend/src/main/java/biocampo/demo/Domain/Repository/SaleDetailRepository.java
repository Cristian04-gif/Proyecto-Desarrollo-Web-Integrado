package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;

public interface SaleDetailRepository {

    List<SaleDetail> getAll();
    Optional<SaleDetail> getById(Long id);
    SaleDetail save(SaleDetail detail);
    void deleteById(Long id);
    List<SaleDetail> findBySale(Sale sale);
    List<SaleDetail> saveAll(List<SaleDetail> details);
}
