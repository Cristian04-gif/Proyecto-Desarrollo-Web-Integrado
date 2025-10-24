package biocampo.demo.Persistance.Mappings;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import biocampo.demo.Domain.Model.Employee;
import biocampo.demo.Persistance.Entity.Empleado;

@Mapper(componentModel = "spring", uses = {JobPositionMapper.class})
public interface EmployeeMapper {
    @Mappings({
            @Mapping(source = "idEmpleado", target = "employeeId"),
            @Mapping(source = "nombres", target = "firstName"),
            @Mapping(source = "apellidos", target = "lastName"),
            @Mapping(source = "edad", target = "age"),
            @Mapping(source = "telefono", target = "phone"),
            @Mapping(source = "emailPersonal", target = "personalEmail"),
            @Mapping(source = "emailEmpresarial", target = "workEmail"),
            @Mapping(source = "dni", target = "dni"),
            @Mapping(source = "pais", target = "country"),
            @Mapping(source = "direccion", target = "address"),
            @Mapping(source = "puesto", target = "jobPosition"),
            @Mapping(source = "salario", target = "salary"),
            @Mapping(source = "fechaContratado", target = "hireDate"),
            @Mapping(source = "disponible", target = "available")
    })

    Employee toEmployee(Empleado empleado);

    List<Employee> toEmployees(List<Empleado> empleados);

    @InheritInverseConfiguration
    /*@Mapping(target = "telefono", ignore = true)
    @Mapping(target = "emailPersonal", ignore = true)
    @Mapping(target ="dni", ignore = true)
    @Mapping(target = "direccion", ignore = true)
    @Mapping(target = "fechaContratado", ignore = true)*/
    @Mapping(target = "cultivo", ignore = true)
    @Mapping(target = "cosecha", ignore = true)
    @Mapping(target = "postCosecha", ignore = true)
    Empleado toEmpleado(Employee employee);
    List<Empleado> toEmpleados(List<Employee> employees);
}
