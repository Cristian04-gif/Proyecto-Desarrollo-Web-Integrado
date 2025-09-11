package biocampo.demo.APIs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Services.ServicesImagenes;
import biocampo.demo.Persistance.Entity.Imagen;
import biocampo.demo.Persistance.Entity.Imagen.TipoEntidad;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/imagenes")
public class ControllerImgs {

    @Autowired
    private ServicesImagenes servicesImagenes;

    @PostMapping("/registrar")
    public ResponseEntity<Imagen> subirImg(@RequestParam("url") String url, @RequestParam("tipo") TipoEntidad tipo,
            @RequestParam("idReferncia") Long id) {
        try {
            Imagen imagen = servicesImagenes.subirImagen(url, tipo, id);
            return new ResponseEntity<>(imagen, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{tipo}/{idReferencia}")
    public ResponseEntity<List<Imagen>> buscarImgRelacionada(@PathVariable TipoEntidad tipo,
            @PathVariable Long idReferencia) {
        List<Imagen> lista = servicesImagenes.obtenerImgRelacionado(tipo, idReferencia);
        return ResponseEntity.ok(lista);
    }

}
