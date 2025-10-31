package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.Supplier;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.SupplierRepository;
import biocampo.demo.Persistance.Entity.Insumo.Tipo;

@Service
public class InputService {

    @Autowired
    private InputRepository inputRepository;

    

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Input> getAllInputs() {
        return inputRepository.getAll();
    }

    public Optional<Input> getInputById(Long id) {
        return inputRepository.getById(id);
    }

    //////////
    public Input inputRegister(Input input) {

        System.out.println("Se ingreso al registro");
        System.out.println("nombre: " + input.getName());

        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equalsIgnoreCase(input.getType())) {

                input.setType(tipo.name());
                break;
            }
        }
        System.out.println("tipo: " + input.getType());
        input.setStock(0.0);
        input.setPriceUnit(0.0);
        input.setTotalCost(0.0);
        Supplier supplier = supplierRepository.getById(input.getSupplier().getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe el proveedor"));
        input.setSupplier(supplier);
        return inputRepository.save(input);
    }

    public Input inputUpdate(Long id, Input input) {
        System.out.println("Actualizando insumo");
        Input update = inputRepository.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("El insumo no existe"));

        update.setName(input.getName());
        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equalsIgnoreCase(input.getType())) {
                input.setType(tipo.name());
                break;
            }
        }
        update.setUnitStatet(input.getUnitStatet());
        Supplier supplier = supplierRepository.getById(input.getSupplier().getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("NO existe el proveedor"));
        input.setSupplier(supplier);
        

        return inputRepository.save(update);
    }

    public void deleteInput(Long id) {
        inputRepository.deleteById(id);
    }
}
