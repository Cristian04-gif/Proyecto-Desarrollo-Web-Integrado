package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Model.Loss;
import biocampo.demo.Domain.Repository.CultivationRepository;
import biocampo.demo.Domain.Repository.HarvestRepository;
import biocampo.demo.Domain.Repository.LossRepository;

@Service
public class LossService {

    @Autowired
    private LossRepository lossRepository;

    @Autowired
    private CultivationRepository cultivationRepository;

    @Autowired
    private HarvestRepository harvestRepository;

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
        Optional<Cultivation> existeCult = cultivationRepository.getById(loss.getCultivation().getCultivationId());
        Optional<Harvest> existeHarvest = harvestRepository.getByIdHarvest(loss.getHarvest().getHarvestId());
        if (existeCult.isPresent() && existeHarvest.isEmpty()) {
            loss.setCultivation(existeCult.get());
            loss.setHarvest(null);
        } else if (existeCult.isEmpty() && existeHarvest.isPresent()) {
            loss.setCultivation(null);
            loss.setHarvest(existeHarvest.get());
        } else {
            throw new IllegalArgumentException("el cultivo o cosecha no existe");
        }
        return lossRepository.save(loss);
    }

    // Actualizar pérdida existente
    public Loss updateLoss(Long lossId, Loss updatedData) {
        Optional<Loss> existingLoss = lossRepository.getById(lossId);

        if (existingLoss.isPresent()) {
            Loss toUpdate = existingLoss.get();

            if (updatedData.getTypeLoss() != null)
                toUpdate.setTypeLoss(updatedData.getTypeLoss());
            if (updatedData.getDescription() != null)
                toUpdate.setDescription(updatedData.getDescription());
            if (updatedData.getPocentageAffect() != 0)
                toUpdate.setPocentageAffect(updatedData.getPocentageAffect());
            if (updatedData.getCultivation() != null) {
                Optional<Cultivation> existe = cultivationRepository
                        .getById(updatedData.getCultivation().getCultivationId());
                if (existe.isPresent()) {
                    toUpdate.setCultivation(existe.get());
                } else {
                    throw new IllegalArgumentException("Error! no existe el cultivo");
                }
            }
            if (updatedData.getHarvest() != null) {
                Optional<Harvest> existe = harvestRepository.getByIdHarvest(updatedData.getHarvest().getHarvestId());
                if (existe.isPresent()) {
                    toUpdate.setHarvest(existe.get());
                }
            }

            return lossRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    // Eliminar pérdida
    public void deleteLoss(Long lossId) {
        lossRepository.deleteById(lossId);
    }
}
