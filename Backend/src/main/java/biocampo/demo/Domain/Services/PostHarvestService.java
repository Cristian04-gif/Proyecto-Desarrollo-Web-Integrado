package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Repository.PostHarvestRepository;

@Service
public class PostHarvestService {

    @Autowired
    private PostHarvestRepository postHarvestRepository;

    // Obtener todas las postcosechas
    public List<PostHarvest> getAllPostHarvests() {
        return postHarvestRepository.getAll();
    }

    // Obtener postcosecha por ID
    public Optional<PostHarvest> getPostHarvestById(Long postHarvestId) {
        return postHarvestRepository.getById(postHarvestId);
    }

    // Registrar nueva postcosecha
    public PostHarvest registerPostHarvest(PostHarvest postHarvest) {
        return postHarvestRepository.save(postHarvest);
    }

    // Actualizar postcosecha existente
    public PostHarvest updatePostHarvest(Long postHarvestId, PostHarvest updatedData) {
        Optional<PostHarvest> existingPostHarvest = postHarvestRepository.getById(postHarvestId);

        if (existingPostHarvest.isPresent()) {
            PostHarvest toUpdate = existingPostHarvest.get();

            if (updatedData.getHarvest() != null)
                toUpdate.setHarvest(updatedData.getHarvest());
            if (updatedData.getDateProcessed() != null)
                toUpdate.setDateProcessed(updatedData.getDateProcessed());
            if (updatedData.getCleaningMethod() != null)
                toUpdate.setCleaningMethod(updatedData.getCleaningMethod());
            if (updatedData.getTreatmentMethod() != null)
                toUpdate.setTreatmentMethod(updatedData.getTreatmentMethod());
            if (updatedData.getPacking() != null)
                toUpdate.setPacking(updatedData.getPacking());
            if (updatedData.getStorage() != null)
                toUpdate.setStorage(updatedData.getStorage());

            return postHarvestRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar postcosecha
    public void deletePostHarvest(Long postHarvestId) {
        Optional<PostHarvest> existingPostHarvest = postHarvestRepository.getById(postHarvestId);
        if (existingPostHarvest.isPresent()) {
            postHarvestRepository.deleteById(postHarvestId);
        }
    }
}
