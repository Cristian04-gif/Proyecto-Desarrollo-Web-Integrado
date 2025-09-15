package biocampo.demo.Domain.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoEmpleado;
import biocampo.demo.Domain.Repository.RepoPuestoEmpleado;
import biocampo.demo.Persistance.Entity.Empleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;
import biocampo.demo.Persistance.Entity.Usuario.Rol;

@Service
public class EmpleadoServices {

    @Autowired
    private RepoEmpleado repoEmpleado;

    @Autowired
    private RepoPuestoEmpleado repoPuestoEmpleado;

    @Autowired
    private ServicesUsuario servicesUsuario;

    public List<Empleado> listarTodo() {
        return repoEmpleado.findAll();
    }

    public Optional<Empleado> buscarEmpleado(Long id) {
        return repoEmpleado.findById(id);
    }

    public Empleado registrarEmpleado(String nombres, String apellidos, int edad, String telefono,
            String emailPersonal, String dni, String pais, String direccion, PuestoEmpleado puesto,
            BigDecimal salario) {

        String correoEmpresarial = "E" + dni + "@utp.edu.pe";

        Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(puesto.getIdPuesto());
        if (existePuesto.isPresent()) {
            Empleado nuevo = Empleado.builder()
                    .nombres(nombres)
                    .apellidos(apellidos)
                    .edad(edad)
                    .telefono(telefono)
                    .emailPersonal(emailPersonal)
                    .emailEmpresarial(correoEmpresarial)
                    .dni(dni)
                    .pais(pais)
                    .direccion(direccion)
                    .puesto(puesto)
                    .salario(salario)
                    .build();

            repoEmpleado.save(nuevo);
            System.out.println("cargo: " + nuevo.getPuesto().getNombrePuesto());
            String cargo = nuevo.getPuesto().getNombrePuesto().toUpperCase();
            for (Rol rol : Rol.values()) {

                if (rol.toString().equalsIgnoreCase(cargo)) {
                    System.out.println("rol: " + rol.toString());
                    servicesUsuario.registrarUsuario(nombres, apellidos, correoEmpresarial, "123", pais);
                    break;
                }
            }
            return nuevo;

        } else {
            return null;
        }

    }

    public Empleado actualizarEmpleado(Long id, String nombres, String apellidos, int edad, String telefono,
            String emailPersonal, String dni, String pais, String direccion, PuestoEmpleado puesto,
            BigDecimal salario) {

        Optional<Empleado> existe = repoEmpleado.findById(id);

        if (existe.isPresent()) {
            Empleado actualizar = existe.get();
            actualizar.setNombres(nombres);
            actualizar.setApellidos(apellidos);
            actualizar.setEdad(edad);
            actualizar.setTelefono(telefono);
            actualizar.setEmailPersonal(emailPersonal);
            actualizar.setDni(dni);
            actualizar.setPais(pais);
            actualizar.setDireccion(direccion);

            Optional<PuestoEmpleado> existePuesto = repoPuestoEmpleado.findById(puesto.getIdPuesto());
            if (existePuesto.isPresent()) {
                actualizar.setPuesto(puesto);
                String cargo = puesto.getNombrePuesto().toUpperCase();
                System.out.println("cargo: " + cargo);
                System.out.println("CORREO EMPRESARIAL: "+actualizar.getEmailEmpresarial());
                for (Rol rol : Rol.values()) {
                    System.out.println("ROL: " + rol);
                    if (rol.toString().equalsIgnoreCase(cargo)) {
                        //servicesUsuario.actualizar(id, nombres, apellidos, actualizar.getEmailEmpresarial(), cargo, pais);
                        servicesUsuario.registrarUsuario(nombres, apellidos, actualizar.getEmailEmpresarial(), cargo, pais);
                        break;
                    } else {

                        servicesUsuario.eliminar(actualizar.getIdEmpleado());
                    } 
                }
            }
            actualizar.setSalario(salario);
            return repoEmpleado.save(actualizar);
        } else {
            return null;
        }
    }

    public void eliminarEmpleado(Long id) {
        repoEmpleado.deleteById(id);
    }
}
