package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Input;
import biocampo.demo.Domain.Model.InputSupplier;
import biocampo.demo.Domain.Model.Supplier;
import biocampo.demo.Domain.Repository.InputRepository;
import biocampo.demo.Domain.Repository.InputSupplierRepository;
import biocampo.demo.Domain.Repository.SupplierRepository;

@Service
public class InputSupplierService {

    @Autowired
    private InputSupplierRepository inputSupplierRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private InputRepository inputRepository;

    public List<InputSupplier> getAll() {
        return inputSupplierRepository.getAll();
    }

    public Optional<InputSupplier> getInputSupplier(long id) {
        return inputSupplierRepository.getById(id);
    }

    public InputSupplier registerInputSupplier(InputSupplier inputSupplier) {
        Optional<Supplier> existSupplier = supplierRepository.getById(inputSupplier.getSupplier().getSupplierId());
        Optional<Input> existInput = inputRepository.getById(inputSupplier.getInput().getInputId());

        if (existSupplier.isPresent() && existInput.isPresent()) {
            inputSupplier.setSupplier(existSupplier.get());
            inputSupplier.setInput(existInput.get());
        } else {
            throw new IllegalArgumentException("El proveedor o insumo no existe");
        }
        return inputSupplierRepository.save(inputSupplier);
    }

    public InputSupplier updataInputSupplier(Long id, InputSupplier inputSupplier) {
        Optional<InputSupplier> exist = inputSupplierRepository.getById(id);
        if (exist.isPresent()) {
            InputSupplier update = exist.get();

            if (inputSupplier.getSupplier() != null) {
                Optional<Supplier> existSupplier = supplierRepository
                        .getById(inputSupplier.getSupplier().getSupplierId());
                if (existSupplier.isPresent()) {
                    update.setSupplier(existSupplier.get());
                }
            }
            if (inputSupplier.getInput() != null) {
                Optional<Input> existInput = inputRepository.getById(inputSupplier.getInput().getInputId());
                if (existInput.isPresent()) {
                    update.setInput(existInput.get());
                }
            }
            if (inputSupplier.getPrice() != null) {
                update.setPrice(inputSupplier.getPrice());
            }

            return inputSupplierRepository.save(update);
        }else{
            return null;
        }
    }

    public void delete(Long id){
        inputSupplierRepository.deleteById(id);
    }
}
