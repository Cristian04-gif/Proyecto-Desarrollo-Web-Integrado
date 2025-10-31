package biocampo.demo.Domain.DTO.Request;

import java.util.List;

import biocampo.demo.Domain.Model.Sale;
import biocampo.demo.Domain.Model.SaleDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaleRequest {
    private Sale sale;
    private List<SaleDetail> details;
}
