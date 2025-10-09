package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Repository.PlantCategoryRepository;

@Service
public class PlantCategoryService {

    @Autowired
    private PlantCategoryRepository categoryRepository;

    public List<PlantCategory> getAll(){
        return categoryRepository.getAll();
    }

    public Optional<PlantCategory> getPlantCategory(Long id){
        return categoryRepository.getPlantCategory(id);
    }

    public PlantCategory registerCategory(PlantCategory category){
        return categoryRepository.save(category);
    }

    public PlantCategory updateCategory(Long id, PlantCategory category){
        Optional<PlantCategory> exist = categoryRepository.getPlantCategory(id);

        if (exist.isPresent()) {
            System.out.println("LA CATEGORIA EXISTE");
            PlantCategory toUpdate = exist.get();
            System.out.println("NOMBRE: "+toUpdate.getCategoryName());
            toUpdate.setCategoryName(category.getCategoryName());
            System.out.println("sE ACTUALIZO LA CATEGORIA");
            return categoryRepository.save(toUpdate);
        } else{
            return null;
        }
    }

    public void deleteCategory(Long id){
        categoryRepository.delete(id);
    }
}
