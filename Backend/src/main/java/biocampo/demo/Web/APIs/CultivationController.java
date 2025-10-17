package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Cultivation;
import biocampo.demo.Domain.Services.CultivationService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/cultivation")
public class CultivationController {

    @Autowired
    private CultivationService cultivationService;

    @GetMapping("/all")
    public ResponseEntity<List<Cultivation>> getAll() {
        List<Cultivation> cultivations = cultivationService.getAllCultivations();
        return new ResponseEntity<>(cultivations, HttpStatus.OK);
    }

    @GetMapping("/season")
    public ResponseEntity<List<Cultivation>> getCultivvationBySeason(@RequestParam("season") String season) {
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
    public ResponseEntity<Cultivation> registerCultivation(@RequestBody Cultivation cultivation) {
        try {
            Cultivation cultivation2 = cultivationService.registerCultivation(cultivation);
            return new ResponseEntity<>(cultivation2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cultivation> updateCultivacion(@PathVariable Long id, @RequestBody Cultivation cultivation) {
        try {
            Cultivation cultivation2 = cultivationService.updateCultivation(id, cultivation);
            return new ResponseEntity<>(cultivation2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCultivation(@PathVariable Long id) {
        try {
            cultivationService.deleteCultivation(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
