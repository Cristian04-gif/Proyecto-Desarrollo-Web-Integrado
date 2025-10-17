package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.Harvest;
import biocampo.demo.Domain.Services.HarvestService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/harvest")
public class HarvestControler {

    @Autowired
    private HarvestService harvestService;

    @GetMapping("/all")
    public ResponseEntity<List<Harvest>> getAll() {
        List<Harvest> harvests = harvestService.getAll();
        return new ResponseEntity<>(harvests, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Harvest> getHarvest(@PathVariable Long id) {
        Optional<Harvest> optional = harvestService.getHarvest(id);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Harvest> registerHarvest(@RequestBody Harvest harvest) {
        try {
            Harvest harvest2 = harvestService.registerHarvest(harvest);
            return new ResponseEntity<>(harvest2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Harvest> updateHarvest(@PathVariable Long id, @RequestBody Harvest harvest) {
        try {
            Harvest harvest2 = harvestService.updateHarvest(id, harvest);
            return new ResponseEntity<>(harvest2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHarvest(@PathVariable Long id) {
        try {
            harvestService.deleteHarvest(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
