package biocampo.demo.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.Cultivo;

@Repository
public interface RepoCultivo extends JpaRepository<Cultivo, Long>{

}
