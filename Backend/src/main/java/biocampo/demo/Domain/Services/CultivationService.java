package biocampo.demo.Domain.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Domain.Model.InputCultivation;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Persistance.CRUD.RepoCultivo;
import biocampo.demo.Persistance.CRUD.RepoCultivoInsumo;
import biocampo.demo.Persistance.CRUD.RepoEmpleado;
import biocampo.demo.Persistance.CRUD.RepoInsumo;
import biocampo.demo.Persistance.CRUD.RepoPlanta;
import biocampo.demo.Persistance.Entity.Cosecha.Temporada;
import biocampo.demo.Persistance.Entity.Cultivo;
import biocampo.demo.Persistance.Entity.CultivoInsumo;
import biocampo.demo.Persistance.Entity.CultivoInsumoId;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.Insumo;
import biocampo.demo.Persistance.Entity.Planta;
import biocampo.demo.Persistance.Mappings.CultivationMapper;
import biocampo.demo.Persistance.Mappings.EmployeeMapper;
import biocampo.demo.Persistance.Mappings.InputCultivationMapper;

@Service
public class CultivationService {

    @Autowired
    private CultivationRepository cultivationRepository;

    //
    @Autowired
    private RepoPlanta repoPlanta;
    @Autowired
    private CultivationMapper cultivationMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RepoCultivo repoCultivo;

    @Autowired
    private InputCultivationMapper inputCultivationMapper;

    @Autowired
    private RepoInsumo repoInsumo;

    @Autowired
    private RepoCultivoInsumo repoCultivoInsumo;

    @Autowired
    private RepoEmpleado repoEmpleado;

    //

    public List<Cultivation> getAllCultivations() {
        return cultivationRepository.getAll();
    }

    public Optional<Cultivation> getCultivationById(Long id) {
        return cultivationRepository.getById(id);
    }

    public List<Cultivation> getBySeason(String season) {
        return cultivationRepository.findBySeason(season);
    }

    public double calculo(Planta plant, Cultivo cultivation) {
        // calculo de paquete requeridos
        double hectareas = cultivation.getHectareas();
        double densidad = plant.getDensidadSiembra();
        double pesoSemillaGramos = plant.getPesoSemillaPromedio();
        double pesoPaquete = plant.getPesoPorPaquete();

        // calculo de semillas necesarias por kg
        double semillasXHectarea = densidad * 10000;
        double pesoTotalKg = (semillasXHectarea * pesoSemillaGramos) / 1000;
        double paquetesXHectareas = pesoTotalKg / pesoPaquete;
        return paquetesXHectareas * hectareas;
        // cultivation.setCost(0);

        // plantRepository.save(plant);
        // return cultivation;
    }

    @Transactional
    public Cultivation registerCultivation(Cultivation cultivation, List<InputCultivation> inputCultivations,
            List<Employee> employees) {
        System.out.println("Entro al registro");
        System.out.println("NOmbre cultivo: " + cultivation.getPlotName());
        System.out.println("ID planta: " + cultivation.getPlant().getPlantId());
        boolean exis = repoPlanta.existsById(cultivation.getPlant().getPlantId());
        System.out.println("Existe: " + exis);

        Long idPlanta = cultivation.getPlant() != null ? cultivation.getPlant().getPlantId() : null;
        if (idPlanta == null) {
            throw new IllegalArgumentException("El ID de planta es nulo o inválido");
        }
        Planta p = repoPlanta.findById(1L).orElse(null);
        System.out.println("Planta encontrada: " + (p != null ? p.getNombre() : "NO se encontro"));

        Planta plantaEntity = repoPlanta.findById(cultivation.getPlant().getPlantId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe la planta"));

        Cultivo cultivoEntity = cultivationMapper.toCultivo(cultivation);
        cultivoEntity.setPlanta(plantaEntity);
        cultivoEntity.setCosto(0.0);

        try {
            cultivoEntity.setTemporada(Temporada.valueOf(cultivoEntity.getTemporada().toString().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Temporada no válida");
        }

        double paquetesTotales = calculo(plantaEntity, cultivoEntity);
        cultivoEntity.setPaquetesRequeridos(paquetesTotales);
        Cultivo cultivoguardado = repoCultivo.saveAndFlush(cultivoEntity);
        System.out.println("✅ ID del cultivo generado: " + cultivoguardado.getIdCultivo());

        if (inputCultivations == null || inputCultivations.isEmpty()) {
            throw new IllegalArgumentException("Los insumos no pueden ser nulos");
        }
        System.out.println("Insumos llenos");
        double costo = 0.0;

        int i = 0;
        for (InputCultivation ci : inputCultivations) {
            System.out.println("Vuelta Insumo: " + (i + 1));
            CultivoInsumo cultivoInsumoEntity = inputCultivationMapper.toCultivoInsumo(ci);
            System.out.println("Id insumo: " + ci.getInput().getInputId());
            Insumo insumoEntity = repoInsumo.findById(ci.getInput().getInputId()).orElseThrow();
            System.out.println("nombre: " + insumoEntity.getNombre());
            cultivoInsumoEntity.setCultivo(cultivoguardado);
            cultivoInsumoEntity.setInsumo(insumoEntity);

            System.out.println("Cultivo e insumos setteados");
            if (ci.getQuantity() > 0.0) {
                System.out.println("Sumando costo");
                if (insumoEntity.getStock() >= ci.getQuantity()) {
                    costo += ci.getQuantity() * insumoEntity.getPrecioUnitario();
                    insumoEntity.setStock(insumoEntity.getStock() - ci.getQuantity());
                    repoInsumo.save(insumoEntity);
                } else {
                    System.out.println("insumos insuficientes");
                    throw new IllegalArgumentException("Error! no hay suficiente stock del insumo");
                }
            } else {
                System.out.println("No se sumo ni mrd");
                throw new IllegalArgumentException("Error! la cantidad no puede ser nula o menor que cero");
            }
            System.out.println("idCultivo: " + cultivoInsumoEntity.getCultivo().getIdCultivo());
            System.out.println("idInsumo: " + cultivoInsumoEntity.getInsumo().getIdInsumo());

            CultivoInsumoId id = new CultivoInsumoId(cultivoInsumoEntity.getCultivo().getIdCultivo(),
                    cultivoInsumoEntity.getInsumo().getIdInsumo());
            cultivoInsumoEntity.setId(id);
            System.out.println("CultivoInsumo → cultivo ID: " + cultivoInsumoEntity.getCultivo().getIdCultivo());
            System.out.println("CultivoInsumo → insumo ID: " + cultivoInsumoEntity.getInsumo().getIdInsumo());
            System.out.println("CultivoInsumo → id compuesto: " + cultivoInsumoEntity.getId());

            repoCultivoInsumo.save(cultivoInsumoEntity);
            System.out.println("Insumo " + (i + 1) + " guardado");
            i++;
        }
        System.out.println("Insumos agregados");

        int j = 0;
        for (Employee empleado : employees) {
            try {
                System.out.println("Vuelta de empleados: " + (j + 1));
                Empleado empleadoEntity = employeeMapper.toEmpleado(empleado);
                System.out.println("id empleado: " + empleadoEntity.getIdEmpleado());
                Empleado empleadoExiste = repoEmpleado.findById(empleadoEntity.getIdEmpleado()).orElseThrow();
                System.out.println("Nombre: " + empleadoExiste.getNombres());
                if (empleadoExiste.isDisponible() == true) {
                    System.out.println("empleado disponible");
                    costo += empleadoExiste.getSalario();
                    empleadoExiste.setDisponible(false);
                    System.out.println("Empleado ya no esta disponible");
                    //

                    System.out.println("EMpleado amadido al cultivo");
                    if (empleadoExiste.getCultivo() == null) {
                        empleadoExiste.setCultivo(new ArrayList<>());
                    }

                    // y añade también al lado dueño (si no lo hiciste)
                    if (cultivoguardado.getEmpleados() == null) {
                        cultivoguardado.setEmpleados(new ArrayList<>());
                    }
                    empleadoExiste.getCultivo().add(cultivoguardado);
                    cultivoguardado.getEmpleados().add(empleadoExiste);

                    System.out.println("EMpleado añadido al cultivo y veceversa");
                    j++;
                } else {
                    System.out.println("empleado no disponible");
                }
            } catch (Exception e) {
                System.out.println("Error registrando cultivo" + e);
                throw e;
            }

        }
        cultivoguardado.setCosto(costo);
        Cultivo cultivoFinal = repoCultivo.save(cultivoguardado);
        return cultivationMapper.toCultivation(cultivoFinal);
    }

    @Transactional
    public Cultivation updateCultivation(Long id, Cultivation cultivation) {
        Cultivation existCultivation = cultivationRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Error! no existe el cultivo"));
        System.out.println("El cultivo existe");
        Cultivo cultivoEntity = cultivationMapper.toCultivo(existCultivation);
        System.out.println("mapeado al entity");
        Planta plantaEntity = repoPlanta.findById(cultivoEntity.getPlanta().getIdPlanta()).orElseThrow();
        System.out.println("planta existe");
        if (cultivation.getPlotName() != null) {
            cultivoEntity.setNombreParcela(cultivation.getPlotName());
        }
        if (cultivation.getPlant() != null && cultivation.getPlant().getPlantId() != null) {
            cultivoEntity.setPlanta(plantaEntity);
        }
        if (cultivation.getHectares() != null) {
            System.out.println("paquetes anteriores: " + cultivoEntity.getPaquetesRequeridos());
            cultivoEntity.setHectareas(cultivation.getHectares());
            double paquetesRequeridos = calculo(plantaEntity, cultivoEntity);
            cultivoEntity.setPaquetesRequeridos(paquetesRequeridos);
            System.out.println("nueva nactidad de paquetes: " + cultivoEntity.getPaquetesRequeridos());
        }
        if (cultivation.getEachIrrigation() > 0) {
            cultivoEntity.setCadaRiego(cultivation.getEachIrrigation());
        }
        System.out.println("datos antes de temporada actualizado");
        try {
            cultivoEntity.setTemporada(Temporada.valueOf(cultivoEntity.getTemporada().toString().toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.out.println("temporada invalida");
            throw new IllegalArgumentException("Temporada no válida: ", e);
        }
        if (cultivation.getEndDate() != null) {
            cultivoEntity.setFechaEstimadaCosecha(cultivation.getEndDate());
        }
        repoCultivo.save(cultivoEntity);
        return cultivationMapper.toCultivation(cultivoEntity);
    }

    public void deleteCultivation(Long id) {
        cultivationRepository.deleteById(id);
    }

}
