package biocampo.demo.Domain.Model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Supplier {
    private Long supplierId;
    private String name;
    private String ruc;
    private String phone;
    private String email;
    private String address;

    private List<InputSupplier> suppliedInputs;
}
