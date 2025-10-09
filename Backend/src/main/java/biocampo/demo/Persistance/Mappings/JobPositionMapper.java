package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;

@Mapper(componentModel = "spring")
public interface JobPositionMapper {

    @Mappings({
            @Mapping(source = "idPuesto", target = "positionId"),
            @Mapping(source = "nombrePuesto", target = "positionName"),
            // @Mapping(source = "employee", target = "employee")
    })
    JobPosition toJobPosition(PuestoEmpleado puestoEmpleado);

    List<JobPosition> toJobPositions(List<PuestoEmpleado> puestosEmpleado);

    @InheritInverseConfiguration
    @Mapping(target = "empleado", ignore = true)
    PuestoEmpleado toPuestoEmpleado(JobPosition jobPosition);
}
