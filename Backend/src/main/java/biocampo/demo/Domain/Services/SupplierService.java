package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Supplier;
import biocampo.demo.Domain.Repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAll() {
        return supplierRepository.getAll();
    }

    public Optional<Supplier> getSupplier(Long id) {
        return supplierRepository.getById(id);
    }

    public Supplier registeSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier updateSupplier) {
        Optional<Supplier> existSupplier = supplierRepository.getById(id);
        if (existSupplier.isPresent()) {
            Supplier toUpdate = existSupplier.get();
            if (updateSupplier.getName() != null) {
                toUpdate.setName(updateSupplier.getName());
            }
            if (updateSupplier.getRuc() != null) {
                toUpdate.setRuc(updateSupplier.getRuc());
            }
            if (updateSupplier.getPhone() != null) {
                toUpdate.setPhone(updateSupplier.getPhone());
            }
            if (updateSupplier.getEmail() != null) {
                toUpdate.setEmail(updateSupplier.getEmail());
            }
            if (updateSupplier.getAddress() != null) {
                toUpdate.setAddress(updateSupplier.getAddress());
            }
            return supplierRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteSupplier(Long id){
        supplierRepository.deleteById(id);
    }
}
