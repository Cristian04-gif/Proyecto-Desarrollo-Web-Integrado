package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Domain.Repository.JobPositionRepository;
import biocampo.demo.Persistance.CRUD.RepoPuestoEmpleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import biocampo.demo.Persistance.Mappings.JobPositionMapper;

@Repository
public class PuestoEmpleadoRepositony implements JobPositionRepository{

    @Autowired
    private RepoPuestoEmpleado repoPuestoEmpleado;
    
    @Autowired
    private JobPositionMapper mapper;

    @Override
    public List<JobPosition> getAll() {
        List<PuestoEmpleado> puestosEmpleado = repoPuestoEmpleado.findAll();
        return mapper.toJobPositions(puestosEmpleado);
    }

    @Override
    public Optional<JobPosition> getJobPosition(Long positionId) {
        return repoPuestoEmpleado.findById(positionId).map(puestoEmpleado -> mapper.toJobPosition(puestoEmpleado));
    }

    @Override
    public JobPosition save(JobPosition jobPosition) {
        PuestoEmpleado puestoEmpleado = mapper.toPuestoEmpleado(jobPosition);
        PuestoEmpleado puestoEmpleadoGuardado = repoPuestoEmpleado.save(puestoEmpleado);
        return mapper.toJobPosition(puestoEmpleadoGuardado);
    }

    @Override
    public void delete(Long positionId) {
        repoPuestoEmpleado.deleteById(positionId);
    }

}
