package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.DTO.Request.CultivationRequest;
import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Services.CultivationService;
import biocampo.demo.Domain.Services.InputCultivationService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/cultivation")
public class CultivationController {

    @Autowired
    private CultivationService cultivationService;

    @Autowired
    private InputCultivationService inputCultivationService;

    @GetMapping("/all")
    public ResponseEntity<List<Cultivation>> getAll() {
        List<Cultivation> cultivations = cultivationService.getAllCultivations();
        return new ResponseEntity<>(cultivations, HttpStatus.OK);
    }

    @GetMapping("/season")
    public ResponseEntity<List<Cultivation>> getCultivvationBySeason(@RequestParam("season") String season) {
        System.out.println("Entro al controlador");
        List<Cultivation> cultivations = cultivationService.getBySeason(season);
        return new ResponseEntity<>(cultivations, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Cultivation> getCultivation(@PathVariable Long id) {
        Optional<Cultivation> exist = cultivationService.getCultivationById(id);
        return exist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Cultivation> registerCultivation(@RequestBody CultivationRequest cultivationRequest) {
        try {
            Cultivation cultivation = cultivationService.registerCultivation(cultivationRequest.getCultivation(),
                    cultivationRequest.getInputCultivation(), cultivationRequest.getEmployees());
            return new ResponseEntity<>(cultivation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cultivation> updateCultivation(@PathVariable Long id, @RequestBody Cultivation cultivation){
        try {
            Cultivation cultivation2 = cultivationService.updateCultivation(id, cultivation);
            return new ResponseEntity<>(cultivation2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCultivation(@PathVariable Long id) {
        try {
            inputCultivationService.deleteByCultivationId(id);
            cultivationService.deleteCultivation(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
