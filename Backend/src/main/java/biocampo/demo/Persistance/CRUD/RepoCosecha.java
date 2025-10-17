package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Cosecha;

@Repository
public interface RepoCosecha extends JpaRepository<Cosecha, Long>{

}
