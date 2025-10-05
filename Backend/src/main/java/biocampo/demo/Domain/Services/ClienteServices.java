package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCliente;
import biocampo.demo.Persistance.Entity.Cliente;
import jakarta.transaction.Transactional;

@Service
public class ClienteServices {

    @Autowired
    private RepoCliente repoCliente;

    public List<Cliente> listarTodo() {
        return repoCliente.findAll();
    }

    public Optional<Cliente> buscarCliente(Long id) {
        return repoCliente.findById(id);
    }

    public Cliente registrarCliente(Cliente cliente) {
        return repoCliente.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Optional<Cliente> existe = repoCliente.findById(id);
        if (existe.isPresent()) {
            Cliente actualizar = existe.get();
            actualizar.setEdad(cliente.getEdad());
            actualizar.setTelefono(cliente.getTelefono());
            actualizar.setDireccion(cliente.getDireccion());
            actualizar.setTipo(cliente.getTipo());
            return repoCliente.save(actualizar);
        } else {
            return null;
        }
    }

    @Transactional
    public void eliminarCliente(Long id) {
        if (repoCliente.existsById(id)) {
            repoCliente.deleteById(id);
        }
    }
}
