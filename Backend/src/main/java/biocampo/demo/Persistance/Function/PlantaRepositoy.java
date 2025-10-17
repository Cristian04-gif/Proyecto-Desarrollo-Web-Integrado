package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Model.PlantCategory;
import biocampo.demo.Domain.Repository.PlantRepository;
import biocampo.demo.Persistance.CRUD.RepoCategoriaPlanta;
import biocampo.demo.Persistance.CRUD.RepoPlanta;
import biocampo.demo.Persistance.Entity.CategoriaPlanta;
import biocampo.demo.Persistance.Entity.Planta;
import biocampo.demo.Persistance.Mappings.PlantMapper;
import jakarta.persistence.EntityNotFoundException;

@Repository
public class PlantaRepositoy implements PlantRepository {

    @Autowired
    private RepoPlanta repoPlanta;
    @Autowired
    private PlantMapper plantMapper;

    @Autowired
    private RepoCategoriaPlanta repoCategoriaPlanta;

    @Override
    public List<Plant> getAll() {
        List<Planta> todos = repoPlanta.findAll();
        return plantMapper.toPlants(todos);
    }

    @Override
    public Optional<Plant> getById(Long id) {
        return repoPlanta.findById(id).map(planta -> plantMapper.toPlant(planta));
    }

    @Override
    public Plant save(Plant plant) {
        Planta planta = plantMapper.toPlanta(plant);
        Planta plantaGuardada = repoPlanta.save(planta);
        return plantMapper.toPlant(plantaGuardada);
    }

    @Override
    public void deleteById(Long id) {
        repoPlanta.deleteById(id);
    }

    @Override
    public List<Plant> getAvailable(boolean disponible) {
        List<Planta> listaDisponible = repoPlanta.findByDisponible(disponible);
        return plantMapper.toPlants(listaDisponible);
    }

    @Override
    public List<Plant> getByCategory(PlantCategory category) {
        if (category.getCategoryId() == null) {
            throw new IllegalArgumentException("error, no puede ser nulo");
        }

        CategoriaPlanta categoriaPlanta = repoCategoriaPlanta.findById(category.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("NO se encontro la categoria"));
        List<Planta> list = repoPlanta.findByCategoria(categoriaPlanta);
        return plantMapper.toPlants(list);
    }

}
