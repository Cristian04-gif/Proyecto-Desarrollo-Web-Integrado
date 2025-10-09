package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Repository.PlantCategoryRepository;
import biocampo.demo.Persistance.CRUD.RepoCategoriaPlanta;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Mappings.PlantCategoryMapper;

@Repository
public class CategoriaPlantaRepository implements PlantCategoryRepository {

    @Autowired
    private RepoCategoriaPlanta repoCategoriaPlanta;
    @Autowired
    private PlantCategoryMapper mapper;

    @Override
    public List<PlantCategory> getAll() {
        List<CategoriaPlanta> todos = repoCategoriaPlanta.findAll();
        return mapper.toPlantCategories(todos);
    }

    @Override
    public Optional<PlantCategory> getPlantCategory(Long id) {
        return repoCategoriaPlanta.findById(id).map(categoria -> mapper.toCategory(categoria));
    }

    @Override
    public PlantCategory save(PlantCategory category) {
        System.out.println("Entro al repositorio");
        CategoriaPlanta categoriaPlanta = mapper.toCategoriaPlanta(category);
        CategoriaPlanta categoriaPlantaGuardado = repoCategoriaPlanta.save(categoriaPlanta);
        System.out.println("se guardo la categoria");
        return mapper.toCategory(categoriaPlantaGuardado);
    }

    @Override
    public void delete(Long id) {
        repoCategoriaPlanta.deleteById(id);
    }

}
