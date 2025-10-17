package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Perdida;

@Repository
public interface RepoPerdida extends JpaRepository<Perdida, Long>{

}
