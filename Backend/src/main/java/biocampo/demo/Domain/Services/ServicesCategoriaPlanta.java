package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCategoriaPlanta;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;

@Service
public class ServicesCategoriaPlanta {

    @Autowired
    private RepoCategoriaPlanta repoCategoriaPlanta;

    public List<CategoriaPlanta> listarTodo() {
        return repoCategoriaPlanta.findAll();
    }

    public Optional<CategoriaPlanta> buscarCategoria(Long id) {
        return repoCategoriaPlanta.findById(id);
    }

    public CategoriaPlanta registrar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }
        CategoriaPlanta nueva = CategoriaPlanta.builder()
                .nombre(nombre)
                .build();
        return repoCategoriaPlanta.save(nueva);
    }

    public CategoriaPlanta actualizar(Long id, String nombre) {

        Optional<CategoriaPlanta> existe = repoCategoriaPlanta.findById(id);

        if (existe.isPresent()) {

            CategoriaPlanta actualizar = existe.get();
            if (nombre != null)
                actualizar.setNombre(nombre);
            return repoCategoriaPlanta.save(actualizar);

        } else {
            
            CategoriaPlanta nueva = new CategoriaPlanta();
            nueva.setNombre(nombre);
            return repoCategoriaPlanta.save(nueva);
        }
    }

    public void eliminar(Long id) {
        repoCategoriaPlanta.deleteById(id);
    }
}
