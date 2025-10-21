package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Domain.Repository.PlantRepository;
import biocampo.demo.Persistance.Entity.Cosecha.Temporada;

@Service
public class CultivationService {

    @Autowired
    private CultivationRepository cultivationRepository;

    @Autowired
    private PlantRepository plantRepository;

    public List<Cultivation> getAllCultivations() {
        return cultivationRepository.getAll();
    }

    public Optional<Cultivation> getCultivationById(Long id) {
        return cultivationRepository.getById(id);
    }

    public Cultivation calculo(Cultivation cultivation) {
        Plant plant = plantRepository.getById(cultivation.getPlant().getPlantId())
                .orElseThrow(() -> new IllegalArgumentException("La planta relacionada no existe"));

        // calculo de paquete requeridos
        double hectareas = cultivation.getHectares();
        double densidad = plant.getSeedingDensity();
        double pesoSemillaGramos = plant.getAverageSeedWeight();
        double pesoPaquete = plant.getWeightPerPackage();

        // calculo de semillas necesarias por kg
        double semillasXHectarea = densidad * 10000;
        double pesoTotalKg = (semillasXHectarea * pesoSemillaGramos) / 1000;
        double paquetesXHectareas = pesoTotalKg / pesoPaquete;

        double paquetesTotales = paquetesXHectareas * hectareas;

        cultivation.setRequiredPackages(paquetesTotales);
        cultivation.setPlant(plant);
        //cultivation.setCost(0);

        
        plantRepository.save(plant);
        return cultivation;
    }

    public Cultivation registerCultivation(Cultivation cultivation) {
        calculo(cultivation);
        cultivation.setCost(0);
        boolean existTemp = false;
        for (Temporada temp : Temporada.values()) {
            if (temp.toString().equalsIgnoreCase(cultivation.getSeason())) {
                cultivation.setSeason(temp.name());
                existTemp = true;
                break;
            }
        }

        if (!existTemp) {
            throw new IllegalArgumentException("La temporada no existe");
        }
        return cultivationRepository.save(cultivation);
    }

    public Cultivation updateCultivation(Long id, Cultivation cultivation) {
        Cultivation toUpdate = cultivationRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("EL cultivo no existte"));

        // Cultivation toUpdate = existing;
        //toUpdate.setHectares(cultivation.getHectares());
        // toUpdate.setCost(cultivation.getCost());
        // toUpdate.setRequiredPackages(cultivation.getRequiredPackages());
        // toUpdate.setStartDate(cultivation.getStartDate());
        toUpdate.setEachIrrigation(cultivation.getEachIrrigation());
        toUpdate.setEndDate(cultivation.getEndDate());

        // temporada
        boolean existTemp = false;
        for (Temporada temp : Temporada.values()) {
            if (temp.toString().equalsIgnoreCase(cultivation.getSeason())) {
                cultivation.setSeason(temp.name());
                existTemp = true;
                break;
            }
        }

        if (!existTemp) {
            throw new IllegalArgumentException("La temporada no existe");
        }
        toUpdate.setSeason(cultivation.getSeason());
        //

        Plant plant = plantRepository.getById(cultivation.getPlant().getPlantId())
                .orElseThrow(() -> new IllegalArgumentException("La planta ingresada no existe"));
        toUpdate.setPlant(plant);

        //
        //plant.setStock(plant.getStock() + (int) toUpdate.getRequiredPackages());
        
        toUpdate.setHectares(cultivation.getHectares());
        
        plantRepository.save(plant);
        Cultivation cultivation2= calculo(cultivation);
        toUpdate.setRequiredPackages(cultivation2.getRequiredPackages());
        //plant.setStock(plant.getStock() - (int) cultivation.getRequiredPackages());
        
        //
        return cultivationRepository.save(toUpdate);

    }

    public void deleteCultivation(Long id) {
        cultivationRepository.deleteById(id);
    }

    public List<Cultivation> getBySeason(String season) {
        return cultivationRepository.findBySeason(season);
    }
}
