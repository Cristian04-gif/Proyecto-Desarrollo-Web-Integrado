package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Persistance.Entity.PostCosecha;
import biocampo.demo.Domain.Repository.RepoPostCosecha;

@Service
public class ServicePostCosecha {

    @Autowired
    private RepoPostCosecha repoPostCosecha;

    // Obtener todas las postcosechas
    public List<PostCosecha> getAllPostCosechas() {
        return repoPostCosecha.findAll();
    }

    // Obtener postcosecha por ID
    public Optional<PostCosecha> getPostCosechaById(Long idPostCosecha) {
        return repoPostCosecha.findById(idPostCosecha);
    }

    // Registrar nueva postcosecha
    public PostCosecha registerPostCosecha(PostCosecha postCosecha) {
        return repoPostCosecha.save(postCosecha);
    }

    // Actualizar postcosecha existente
    public PostCosecha updatePostCosecha(Long idPostCosecha, PostCosecha updatedData) {
        Optional<PostCosecha> existingPostCosecha = repoPostCosecha.findById(idPostCosecha);

        if (existingPostCosecha.isPresent()) {
            PostCosecha toUpdate = existingPostCosecha.get();

            if (updatedData.getPlantaCosechada() != null)
                toUpdate.setPlantaCosechada(updatedData.getPlantaCosechada());
            if (updatedData.getLimpieza() != null)
                toUpdate.setLimpieza(updatedData.getLimpieza());
            if (updatedData.getTratamiento() != null)
                toUpdate.setTratamiento(updatedData.getTratamiento());
            if (updatedData.getEmpaque() != null)
                toUpdate.setEmpaque(updatedData.getEmpaque());
            if (updatedData.getAlmacenamiento() != null)
                toUpdate.setAlmacenamiento(updatedData.getAlmacenamiento());

            return repoPostCosecha.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar postcosecha
    public void deletePostCosecha(Long idPostCosecha) {
        Optional<PostCosecha> existingPostCosecha = repoPostCosecha.findById(idPostCosecha);
        if (existingPostCosecha.isPresent()) {
            repoPostCosecha.deleteById(idPostCosecha);
        }
    }
}
