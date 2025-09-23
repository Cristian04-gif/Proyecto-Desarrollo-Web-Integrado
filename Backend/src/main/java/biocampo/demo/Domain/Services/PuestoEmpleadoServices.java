package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Repository.RepoPuestoEmpleado;
import biocampo.demo.Persistance.Entity.PuestoEmpleado;

@Service
public class PuestoEmpleadoServices {

    @Autowired
    private RepoPuestoEmpleado puestoEmpleado;

    public List<PuestoEmpleado> listarTodo() {
        return puestoEmpleado.findAll();
    }

    public Optional<PuestoEmpleado> buscar(Long id) {
        return puestoEmpleado.findById(id);
    }

    /*public PuestoEmpleado registrar(String nombre) {
        PuestoEmpleado puesto = PuestoEmpleado.builder()
                .nombrePuesto(nombre).build();
        return puestoEmpleado.save(puesto);
    }*/
    public PuestoEmpleado registrar(PuestoEmpleado puesto) {
        return puestoEmpleado.save(puesto);
    }

    public PuestoEmpleado actualizar(Long id, PuestoEmpleado puesto) {

        Optional<PuestoEmpleado> existe = puestoEmpleado.findById(id);

        if (existe.isPresent()) {
            PuestoEmpleado actualizar = existe.get();
            actualizar.setNombrePuesto(puesto.getNombrePuesto());
            return puestoEmpleado.save(actualizar);
        } else {
            return null;
        }
    }
    /*public PuestoEmpleado actualizar(Long id, String nombre) {

        Optional<PuestoEmpleado> puesto = puestoEmpleado.findById(id);
        
        if (puesto.isPresent()) {
            PuestoEmpleado actualizar = puesto.get();
            actualizar.setNombrePuesto(nombre);
            return puestoEmpleado.save(actualizar);
        } else {
            return null;
        }
    }*/

    public void eliminar(Long id) {
        puestoEmpleado.deleteById(id);
    }
}
