package biocampo.demo.Web.APIs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biocampo.demo.Domain.Model.PostHarvest;
import biocampo.demo.Domain.Services.PostHarvestService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/postHarvest")
public class PostHarvestController {

    @Autowired
    private PostHarvestService postHarvestService;

    @GetMapping("/all")
    public ResponseEntity<List<PostHarvest>> getAll() {
        List<PostHarvest> all = postHarvestService.getAllPostHarvests();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostHarvest> getPostHarvest(@PathVariable Long id) {
        Optional<PostHarvest> postharvest = postHarvestService.getPostHarvestById(id);
        return postharvest.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<PostHarvest> register(@RequestBody PostHarvest postHarvest) {
        try {
            PostHarvest postHarvest2 = postHarvestService.registerPostHarvest(postHarvest);
            return new ResponseEntity<>(postHarvest2, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostHarvest> update(@PathVariable Long id, @RequestBody PostHarvest postHarvest) {
        try {
            PostHarvest postHarvest2 = postHarvestService.updatePostHarvest(id, postHarvest);
            return new ResponseEntity<>(postHarvest2, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            postHarvestService.deletePostHarvest(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
