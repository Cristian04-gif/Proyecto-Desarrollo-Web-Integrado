package biocampo.demo.Domain.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoImagenes;
import biocampo.demo.Persistance.Entity.Imagen;
import biocampo.demo.Persistance.Entity.Imagen.TipoEntidad;

@Service
public class ServicesImagenes {

    @Autowired
    private RepoImagenes repoImagenes;

    public List<Imagen> listarTodo() {
        return repoImagenes.findAll();
    }

    /*public Imagen subirImagen(String url, TipoEntidad tipo, Long idReferencia) {
        
        Imagen nueva = Imagen.builder()
                .url(url)
                .tipoEntidad(tipo)
                .idReferencia(idReferencia)
                .build();
        return repoImagenes.save(nueva);
    }*/
    public Imagen subirImagen(Imagen imagen) {
        return repoImagenes.save(imagen);
    }

    public List<Imagen> obtenerImgRelacionado(TipoEntidad tipo, Long id) {
        return repoImagenes.findByTipoEntidadAndIdReferencia(tipo, id);
    }

    public void eliminar(Long id){
        repoImagenes.deleteById(id);
    }
}
