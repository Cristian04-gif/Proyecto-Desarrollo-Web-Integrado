package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.CategoriaPlanta;

@Repository
public interface RepoCategoriaPlanta extends JpaRepository<CategoriaPlanta, Long>{

}
