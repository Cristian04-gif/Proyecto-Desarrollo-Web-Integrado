package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Function.ServicesCategoriaPlanta;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/categoriaPlanta")
public class ControllerCategoria {

    @Autowired
    private ServicesCategoriaPlanta categoriaPlanta;

    @GetMapping("/todos")
    public ResponseEntity<List<CategoriaPlanta>> listarTodo() {
        List<CategoriaPlanta> lista = categoriaPlanta.listarTodo();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaPlanta> buscarCategoria(@PathVariable Long id) {
        Optional<CategoriaPlanta> existe = categoriaPlanta.buscarCategoria(id);

        if (existe.isPresent()) {
            CategoriaPlanta categoriaPlanta = existe.get();
            return new ResponseEntity<>(categoriaPlanta, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<CategoriaPlanta> registrarCategoria(@RequestBody CategoriaPlanta categoria) {
        try {
            CategoriaPlanta nueva = categoriaPlanta.registrar(categoria.getNombre());
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CategoriaPlanta> actualizarCategoria(@PathVariable Long id,
            @RequestBody CategoriaPlanta categoria) {
        try {
            CategoriaPlanta actualizar = categoriaPlanta.actualizar(id, categoria.getNombre());
            return ResponseEntity.ok(actualizar);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id){
        try {
            categoriaPlanta.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
