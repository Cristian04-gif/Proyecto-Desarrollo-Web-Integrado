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
        boolean disponible = false;
        if (plant.getStock() != 0) {
            disponible = true;
        }

        Optional<PlantCategory> existCategory = categoryRepository
                .getPlantCategory(plant.getCategory().getCategoryId());

        if (existCategory.isPresent()) {
            PlantCategory category = existCategory.get();
            System.out.println("categoria: " + category.getCategoryId());
            plant.setCategory(category);
        }

        Plant registerPlant = Plant.builder()
                .name(plant.getName())
                .stock(plant.getStock())
                .available(disponible)
                .category(plant.getCategory())
                .build();
        System.out.println("Objeto creado");
        System.out.println("nombre: "+registerPlant.getName());
        System.out.println("disponible: "+registerPlant.isAvailable());
        System.out.println("CategoriaPlanta: "+registerPlant.getCategory().getCategoryName());
        return plantRepository.save(registerPlant);
    }

    public Plant updatePlant(Long id, Plant plant) {
        Optional<Plant> existPlant = plantRepository.getById(id);

        if (existPlant.isPresent()) {
            Plant toUpdate = existPlant.get();

            if (plant.getName() != null) {
                toUpdate.setName(plant.getName());
            }
            if (plant.getStock() != 0) {
                toUpdate.setStock(plant.getStock());
                toUpdate.setAvailable(true);
            }

            Optional<PlantCategory> existCategory = categoryRepository.getPlantCategory(plant.getCategory().getCategoryId());
            if (existCategory.isPresent()) {
                toUpdate.setCategory(existCategory.get());
            } else{
                throw new IllegalArgumentException("Error! la categoria no existe");
            }
            System.out.println("Planta actualizada");
            System.out.println("nombre: "+toUpdate.getName());
            System.out.println("categoria: "+toUpdate.getCategory().getCategoryName());
            return plantRepository.save(toUpdate);
        } else{
            return null;
        }
    }

    public void deletePlant(Long id){
        plantRepository.deleteById(id);
    }
}
