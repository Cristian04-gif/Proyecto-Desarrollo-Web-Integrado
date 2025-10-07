package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Persistance.Entity.Cliente;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CustomerMapper {

    @Mappings({
            @Mapping(source = "idCliente", target = "customerId"),
            @Mapping(source = "usuario", target = "user"),
            @Mapping(source = "edad", target = "age"),
            @Mapping(source = "telefono", target = "phone"),
            @Mapping(source = "direccion", target = "address"),
            @Mapping(source = "tipo", target = "type"),
    })

    Customer toCustomer(Cliente cliente);

    List<Customer> toCustomers(List<Cliente> clientes);

    @InheritInverseConfiguration
    @Mapping(target = "compras", ignore = true)
    Cliente toCliente(Customer customer);
}
