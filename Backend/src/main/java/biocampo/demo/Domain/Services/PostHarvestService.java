package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Repository.HarvestRepository;
import biocampo.demo.Domain.Repository.PostHarvestRepository;
import biocampo.demo.Persistance.Entity.PostCosecha.Almacenamiento;
import biocampo.demo.Persistance.Entity.PostCosecha.Empaque;

@Service
public class PostHarvestService {

    @Autowired
    private PostHarvestRepository postHarvestRepository;

    @Autowired
    private HarvestRepository harvestRepository;

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
        Optional<Harvest> exiiste = harvestRepository.getByIdHarvest(postHarvest.getHarvest().getHarvestId());
        if (exiiste.isPresent()) {
            postHarvest.setHarvest(exiiste.get());
        }

        boolean packingExit = false;
        for (Empaque empaque : Empaque.values()) {

            if (empaque.toString().equalsIgnoreCase(postHarvest.getPacking())) {
                postHarvest.setPacking(empaque.name());
                packingExit = true;
                break;
            }
        }
        if (!packingExit) {
            throw new IllegalArgumentException("Error! el empaque no existe");
        }

        boolean storageExist = false;
        for (Almacenamiento almacenamiento : Almacenamiento.values()) {
            if (almacenamiento.toString().equalsIgnoreCase(postHarvest.getStorage())) {
                postHarvest.setStorage(almacenamiento.name());
                storageExist = true;
                break;
            }
        }
        if (!storageExist) {
            throw new IllegalArgumentException("Error! no existe el tipo de almacenamiento");
        }

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

            if (updatedData.getPacking() != null) {
                boolean packingExit = false;
                for (Empaque empaque : Empaque.values()) {
                    if (empaque.toString().equalsIgnoreCase(updatedData.getPacking())) {
                        updatedData.setPacking(empaque.name());
                        packingExit = true;
                        break;
                    }
                }
                if (!packingExit) {
                    throw new IllegalArgumentException("Error! el empaque no existe");
                }
            }
            if (updatedData.getStorage() != null) {
                boolean storageExist = false;
                for (Almacenamiento almacenamiento : Almacenamiento.values()) {
                    if (almacenamiento.toString().equalsIgnoreCase(updatedData.getStorage())) {
                        updatedData.setStorage(almacenamiento.name());
                        storageExist = true;
                        break;
                    }
                }
                if (!storageExist) {
                    throw new IllegalArgumentException("Error! no existe el tipo de almacenamiento");
                }
            }
            if (updatedData.getStock() > 0) {
                toUpdate.setStock(updatedData.getStock());
            }

            return postHarvestRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar postcosecha
    public void deletePostHarvest(Long postHarvestId) {
        postHarvestRepository.deleteById(postHarvestId);

    }
}
