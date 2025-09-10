package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoCategoriaPlanta;
import biocampo.demo.Domain.Repository.RepoPlanta;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Entity.Planta;

@Service
public class ServicesPlanta {

    @Autowired
    private RepoPlanta repoPlanta;

    @Autowired
    private RepoCategoriaPlanta repoCategoriaPlanta;

    public List<Planta> listarTodo() {
        return repoPlanta.findAll();
    }

    public Optional<Planta> buscarPlanta(Long id) {
        return repoPlanta.findById(id);
    }

    public Planta registrar(String nombre, int stock, CategoriaPlanta categoria) {

        Planta nueva = new Planta();
        nueva.setNombre(nombre);
        nueva.setStock(stock);

        Optional<CategoriaPlanta> categoriaExiste = repoCategoriaPlanta.findById(categoria.getIdCateogriaPlanta());

        if (categoriaExiste.isPresent()) {
            nueva.setCategoria(categoria);
        }
        ;
        if (stock != 0) {
            nueva.setDisponible(true);
        } else {
            nueva.setDisponible(false);
        }
        return repoPlanta.save(nueva);
    }

    public Planta actualizarPlanta(Long id, String nombre, int stock, CategoriaPlanta categoria) {

        Optional<Planta> existe = repoPlanta.findById(id);

        if (existe.isPresent()) {
            Planta actualizar = existe.get();

            if (nombre != null)
                actualizar.setNombre(nombre);
            if (stock >= 0)
                actualizar.setStock(stock);
            if (categoria != null) {
                Optional<CategoriaPlanta> categoriaExiste = repoCategoriaPlanta
                        .findById(categoria.getIdCateogriaPlanta());

                if (categoriaExiste.isPresent()) {
                    actualizar.setCategoria(categoria);
                }
            }
            if (stock != 0) {
                actualizar.setDisponible(true);
            } else {
                actualizar.setDisponible(false);
            }

            return repoPlanta.save(actualizar);
        } else {
            return null;
        }
    }

    public void eliminar(Long id) {
        repoPlanta.deleteById(id);
    }
}