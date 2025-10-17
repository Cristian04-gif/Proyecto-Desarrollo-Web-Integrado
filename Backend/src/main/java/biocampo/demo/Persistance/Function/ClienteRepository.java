package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Customer;
import biocampo.demo.Domain.Repository.CustomerRepository;
import biocampo.demo.Persistance.CRUD.RepoCliente;
import biocampo.demo.Persistance.Entity.Cliente;
import biocampo.demo.Persistance.Entity.Cliente.Tipo;
import biocampo.demo.Persistance.Mappings.CustomerMapper;

@Repository
public class ClienteRepository implements CustomerRepository{

    @Autowired
    private RepoCliente repoCliente;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> getAll() {
        List<Cliente> all = repoCliente.findAll();
        return customerMapper.toCustomers(all);
    }

    @Override
    public Optional<Customer> getByIdCustomer(Long id) {
        return repoCliente.findById(id).map(cliente -> customerMapper.toCustomer(cliente));
    }

    @Override
    public Customer save(Customer customer) {
        Cliente cliente = customerMapper.toCliente(customer);
        Cliente clienteGuardado = repoCliente.save(cliente);
        return customerMapper.toCustomer(clienteGuardado);
    }

    @Override
    public void deleteById(Long id) {
        repoCliente.deleteById(id);
    }

    @Override
    public List<Customer> findByType(String type) {
        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equalsIgnoreCase(type)) {
                List<Cliente> listaTipo = repoCliente.findByTipo(tipo);
                return customerMapper.toCustomers(listaTipo);
            }
        }
        return null;
        
    }

}
