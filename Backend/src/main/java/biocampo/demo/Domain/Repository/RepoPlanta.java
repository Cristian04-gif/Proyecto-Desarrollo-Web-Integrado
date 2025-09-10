package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Planta;

@Repository
public interface RepoPlanta extends JpaRepository<Planta, Long>{

}
