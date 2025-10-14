package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Loss;
import biocampo.demo.Domain.Repository.LossRepository;

@Service
public class LossService {

    @Autowired
    private LossRepository lossRepository;

    // Listar todas las pérdidas
    public List<Loss> getAllLosses() {
        return lossRepository.getAll();
    }

    // Obtener pérdida por ID
    public Optional<Loss> getLossById(Long lossId) {
        return lossRepository.getById(lossId);
    }

    // Registrar nueva pérdida
    public Loss registerLoss(Loss loss) {
        return lossRepository.save(loss);
    }

    // Actualizar pérdida existente
    public Loss updateLoss(Long lossId, Loss updatedData) {
        Optional<Loss> existingLoss = lossRepository.getById(lossId);

        if (existingLoss.isPresent()) {
            Loss toUpdate = existingLoss.get();

            if (updatedData.getTypeLoss() != null)
                toUpdate.setTypeLoss(updatedData.getTypeLoss());
            if (updatedData.getAction() != null)
                toUpdate.setAction(updatedData.getAction());
            if (updatedData.getCultivation() != null)
                toUpdate.setCultivation(updatedData.getCultivation());
            if (updatedData.getHarvest() != null)
                toUpdate.setHarvest(updatedData.getHarvest());

            return lossRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar pérdida
    public void deleteLoss(Long lossId) {
        Optional<Loss> existingLoss = lossRepository.getById(lossId);
        if (existingLoss.isPresent()) {
            lossRepository.deleteById(lossId);
        }
    }
}
