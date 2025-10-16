package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Domain.Services.JobPositionService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/jobPosition")
public class JopPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @GetMapping("/all")
    public ResponseEntity<List<JobPosition>> getAllJobPositions() {
        List<JobPosition> lista = jobPositionService.getAllJobPositions();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JobPosition> getJobPositionById(@PathVariable Long id) {
        Optional<JobPosition> existe = jobPositionService.getJobPositionById(id);

        return existe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<JobPosition> createJobPosition(@RequestBody JobPosition jobPosition) {
        try {
            JobPosition nuevo = jobPositionService.createJobPosition(jobPosition);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @PutMapping("update/{id}")
    public ResponseEntity<JobPosition> updateJobPosition(@PathVariable Long id, @RequestBody JobPosition jobPosition) {
        try {
            JobPosition actualizar = jobPositionService.updateJobPosition(id, jobPosition);
            return new ResponseEntity<>(actualizar, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJobPosition(@PathVariable Long id) {
        try {
            jobPositionService.deleteJobPosition(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
