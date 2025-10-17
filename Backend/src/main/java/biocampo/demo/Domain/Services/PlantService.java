package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Repository.PlantCategoryRepository;
import biocampo.demo.Domain.Repository.PlantRepository;

@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private PlantCategoryRepository categoryRepository;

    public List<Plant> getAll() {
        return plantRepository.getAll();
    }

    public Optional<Plant> getPlant(Long id) {
        return plantRepository.getById(id);
    }

    public Plant registerPlant(Plant plant) {

        if (plant.getStock() > 0) {
            plant.setAvailable(true);
            System.out.println("Se ingrese un stock positivo");
        } else {
            throw new IllegalArgumentException("NO se puede ingresar una numero menor a 0");
        }
        System.out.println("Commpureba si la categoria existe");
        Optional<PlantCategory> existCategory = categoryRepository
                .getPlantCategory(plant.getCategory().getCategoryId());

        if (existCategory.isPresent()) {
            // PlantCategory category = existCategory.get();
            System.out.println("categoria: " + existCategory.get().getCategoryId());
            plant.setCategory(existCategory.get());
        } else {
            throw new IllegalArgumentException("Error! la categoria no existe");
        }

        return plantRepository.save(plant);
    }

    public Plant updatePlant(Long id, Plant plant) {
        Optional<Plant> existPlant = plantRepository.getById(id);

        if (existPlant.isPresent()) {
            Plant toUpdate = existPlant.get();

            if (plant.getName() != null) {
                toUpdate.setName(plant.getName());
            }
            if (plant.getStock() > 0) {
                toUpdate.setStock(plant.getStock());
                toUpdate.setAvailable(true);
            }

            Optional<PlantCategory> existCategory = categoryRepository
                    .getPlantCategory(plant.getCategory().getCategoryId());
            if (existCategory.isPresent()) {
                toUpdate.setCategory(existCategory.get());
            } else {
                throw new IllegalArgumentException("Error! la categoria no existe");
            }
            System.out.println("Planta actualizada");
            System.out.println("nombre: " + toUpdate.getName());
            System.out.println("categoria: " + toUpdate.getCategory().getCategoryName());
            toUpdate.setSeedingDensity(plant.getSeedingDensity());
            toUpdate.setAverageSeedWeight(plant.getAverageSeedWeight());
            toUpdate.setWeightPerPackage(plant.getWeightPerPackage());
            return plantRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}
