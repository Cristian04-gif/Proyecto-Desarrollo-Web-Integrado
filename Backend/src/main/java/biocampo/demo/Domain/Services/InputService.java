package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.Plant;
import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.PlantRepository;
import biocampo.demo.Persistance.Entity.Insumo.Tipo;
import biocampo.demo.Domain.Repository.CultivationRepository;

@Service
public class InputService {

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private CultivationRepository cultivationRepository;

    public List<Input> getAllInputs() {
        return inputRepository.getAll();
    }

    public Optional<Input> getInputById(Long id) {
        return inputRepository.getById(id);
    }

    /*
     * public Input registerInput(Input input) {
     * Optional<Plant> plant =
     * plantRepository.getById(input.getPlant().getPlantId());
     * 
     * if (plant.isPresent()) {
     * boolean existType = false;
     * for (Tipo tipo : Tipo.values()) {
     * if (tipo.toString().equalsIgnoreCase(input.getType())) {
     * input.setType(tipo.name());
     * existType = true;
     * break;
     * }
     * }
     * if (!existType) {
     * throw new IllegalArgumentException("el tipo de invalido");
     * }
     * 
     * String[] unidadSeparada = input.getUnit().split(" ");
     * int convetir = Integer.parseInt(unidadSeparada[0]);
     * 
     * Plant actualizar = plant.get();
     * actualizar.setStock(actualizar.getStock() + convetir);
     * plantRepository.save(actualizar);
     * 
     * input.setPlant(actualizar);
     * } else {
     * input.setPlant(null);
     * }
     * 
     * Optional<Cultivation> cultivation =
     * cultivationRepository.getById(input.getCultivation().getCultivationId());
     * if (cultivation.isPresent()) {
     * input.setCultivation(cultivation.get());
     * } else {
     * input.setCultivation(null);
     * }
     * 
     * return inputRepository.save(input);
     * }
     */

    //////////
    public Input inputRegister(Input input) {

        System.out.println("Se ingreso al registro");
        System.out.println("nombre: " + input.getName());
        //System.out.println("planta: " + input.getPlant().getPlantId());
        if (input.getPlant() != null && input.getPlant().getPlantId() != null) {
            System.out.println("No se ingreso un cultivo, el insumo es para las plantas");
            Plant plant = plantRepository.getById(input.getPlant().getPlantId())
                    .orElseThrow(() -> new IllegalArgumentException("La planta no existe"));
            for (Tipo type : Tipo.values()) {
                if (input.getType().equalsIgnoreCase("Semilla") && type.toString().equalsIgnoreCase(input.getType())) {

                    plant.setStock(plant.getStock() + input.getUnit());
                    plantRepository.save(plant);

                    input.setType(type.name());
                    // input.setCultivation(null);
                    break;
                }
            }
            input.setPlant(plant);
        } else {
            System.out.println("No se ingreso una planta");
            input.setPlant(null);
        }
        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equalsIgnoreCase(input.getType())) {

                input.setType(tipo.name());
                break;
            }
        }
        System.out.println("tipo: " + input.getType());

        /*
         * if (input.getCultivation() != null &&
         * input.getCultivation().getCultivationId() != null) {
         * System.out.println("No no ingreso una planta, insumo para el cultivo");
         * Cultivation cultivation =
         * cultivationRepository.getById(input.getCultivation().getCultivationId())
         * .orElseThrow(() -> new IllegalArgumentException("EL cultivo no existe"));
         * for (Tipo tipo : Tipo.values()) {
         * if (tipo.toString().equalsIgnoreCase(input.getType())) {
         * input.setType(tipo.name());
         * break;
         * }
         * }
         * input.setCultivation(cultivation);
         * } else {
         * System.out.println("No se ingreso un cultivo");
         * 
         * }
         */
        return inputRepository.save(input);
    }

    ///

    /*
     * public Input updateInput(Long id, Input input) {
     * Input toUpdate = inputRepository.getById(id)
     * .orElseThrow(() -> new IllegalArgumentException("El insumo no existe"));
     * // Input toUpdate = existing.get();
     * toUpdate.setName(input.getName());
     * 
     * // type
     * boolean existType = false;
     * for (Tipo tipo : Tipo.values()) {
     * if (tipo.toString().equalsIgnoreCase(input.getType())) {
     * input.setType(tipo.name());
     * existType = true;
     * break;
     * }
     * }
     * if (!existType) {
     * throw new IllegalArgumentException("el tipo de invalido");
     * }
     * toUpdate.setType(input.getType());
     * //
     * 
     * toUpdate.setDescription(input.getDescription());
     * // toUpdate.setUnit(input.getUnit());
     * 
     * Optional<Plant> plant =
     * plantRepository.getById(input.getPlant().getPlantId());
     * if (plant.isPresent()) {
     * String[] seccionUnidad = input.getUnit().split(" ");
     * int cant = Integer.parseInt(seccionUnidad[0]);
     * Plant planta = plant.get();
     * planta.setStock(cant);
     * plantRepository.save(planta);
     * toUpdate.setPlant(planta);
     * } else {
     * toUpdate.setPlant(null);
     * }
     * 
     * Optional<Cultivation> cultivation = cultivationRepository
     * .getById(input.getCultivation().getCultivationId());
     * 
     * if (cultivation.isPresent()) {
     * toUpdate.setCultivation(cultivation.get());
     * } else {
     * toUpdate.setCultivation(null);
     * }
     * return inputRepository.save(toUpdate);
     * 
     * }
     */

    public Input inputUpdate(Long id, Input input) {
        Input update = inputRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("El insumo no existe"));

        update.setName(input.getName());
        update.setDescription(input.getDescription());

        update.setPrice(input.getPrice());
        System.out.println("Actualizando insumo");
        if (input.getPlant() != null && input.getPlant().getPlantId() != null) {
            Plant plant = plantRepository.getById(input.getPlant().getPlantId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe la planta relacionada"));
            for (Tipo tipo : Tipo.values()) {
                if (tipo.toString().equalsIgnoreCase("Semilla") && tipo.toString().equalsIgnoreCase(null)) {

                    update.setType(tipo.name());

                    break;
                }
            }
            // update.setCultivation(null);
            System.out.println("Stock de planta antes de actualizar: " + plant.getStock());
            plant.setStock(plant.getStock() - update.getUnit());
            update.setUnit(input.getUnit());
            plant.setStock(plant.getStock() + update.getUnit());
            System.out.println("Stock de planta despues de actualizar: " + plant.getStock());
            plantRepository.save(plant);
        } else {
            System.out.println("no se ingrese una planta en la actualizacion");
        }

        /*
         * if (input.getCultivation() != null &&
         * input.getCultivation().getCultivationId() != null) {
         * Cultivation cultivation =
         * cultivationRepository.getById(input.getCultivation().getCultivationId())
         * .orElseThrow(() -> new
         * IllegalArgumentException("EL cultivo relacionado no existe"));
         * for (Tipo tipo : Tipo.values()) {
         * if (tipo.toString().equalsIgnoreCase(input.getType())) {
         * update.setType(tipo.name());
         * break;
         * }
         * }
         * update.setCultivation(cultivation);
         * } else {
         * System.out.println("No se ingreso un cultivo");
         * }
         */
        return inputRepository.save(update);
    }

    public void deleteInput(Long id) {
        inputRepository.deleteById(id);
    }
}
