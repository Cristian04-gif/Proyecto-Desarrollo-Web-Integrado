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

    // Listar todas las postcosechas
    public List<PostCosecha> listarPostCosechas() {
        return repoPostCosecha.findAll();
    }

    // Buscar por ID
    public Optional<PostCosecha> buscarPostCosechaPorId(Long idPostCosecha) {
        return repoPostCosecha.findById(idPostCosecha);
    }

    // Registrar nueva postcosecha
    public PostCosecha registrarPostCosecha(PostCosecha postCosecha) {
        return repoPostCosecha.save(postCosecha);
    }

    // Actualizar postcosecha existente
    public Optional<PostCosecha> actualizarPostCosecha(Long idPostCosecha, PostCosecha datosActualizados) {
        return repoPostCosecha.findById(idPostCosecha).map(postCosecha -> {
            postCosecha.setPlantaCosechada(datosActualizados.getPlantaCosechada());
            postCosecha.setLimpieza(datosActualizados.getLimpieza());
            postCosecha.setTratamiento(datosActualizados.getTratamiento());
            postCosecha.setEmpaque(datosActualizados.getEmpaque());
            postCosecha.setAlmacenamiento(datosActualizados.getAlmacenamiento());
            return repoPostCosecha.save(postCosecha);
        });
    }

    // Eliminar postcosecha
    public void eliminarPostCosecha(Long idPostCosecha) {
        repoPostCosecha.deleteById(idPostCosecha);
    }
}