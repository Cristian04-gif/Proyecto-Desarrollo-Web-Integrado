package biocampo.demo.Persistance.Function;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import biocampo.demo.Domain.Model.InputCultivation;
import biocampo.demo.Domain.Repository.INputCultivationRepository;
import biocampo.demo.Persistance.CRUD.RepoCultivoInsumo;
import biocampo.demo.Persistance.Entity.CultivoInsumo;
import biocampo.demo.Persistance.Entity.CultivoInsumoId;
import biocampo.demo.Persistance.Mappings.InputCultivationMapper;

@Repository
public class CultivoInsumoRepository implements INputCultivationRepository{

    @Autowired
    private RepoCultivoInsumo repoCultivoInsumo;
    @Autowired
    private InputCultivationMapper cultivationMapper;

    @Override
    public List<InputCultivation> getAll() {
        List<CultivoInsumo> all = repoCultivoInsumo.findAll();
        return cultivationMapper.toInputCultivations(all);
    }

    

    @Override
    public InputCultivation save(InputCultivation inputCultivation) {
        CultivoInsumo cultivoInsumo = cultivationMapper.toCultivoInsumo(inputCultivation);
        CultivoInsumo cultivoInsumoGuardado = repoCultivoInsumo.save(cultivoInsumo);
        return cultivationMapper.toInputCultivation(cultivoInsumoGuardado);
    }



    @Override
    public List<InputCultivation> findByCultivoIdCultivo(Long idCultivo) {
        List<CultivoInsumo> cultivoInsumos = repoCultivoInsumo.findByCultivoIdCultivo(idCultivo);
        return cultivationMapper.toInputCultivations(cultivoInsumos);
    }



    @Override
    public List<InputCultivation> findByInsumoIdInsumo(Long idInsumo) {
        List<CultivoInsumo> cultivoInsumos = repoCultivoInsumo.findByInsumoIdInsumo(idInsumo);
        return cultivationMapper.toInputCultivations(cultivoInsumos);
    }

    @Override
    public Optional<InputCultivation> getById(Long idCultivo, Long idInsumo) {
        CultivoInsumoId id = new CultivoInsumoId(idCultivo, idInsumo);
        CultivoInsumo cultivoInsumo = repoCultivoInsumo.getReferenceById(id);
        InputCultivation cultivation = cultivationMapper.toInputCultivation(cultivoInsumo);
        return Optional.ofNullable(cultivation);
    }



    @Override
    public void deleteById(Long idCultivo, Long idInsumo) {
        CultivoInsumoId id = new CultivoInsumoId(idCultivo, idInsumo);
        repoCultivoInsumo.deleteById(id);
    }

    

}
