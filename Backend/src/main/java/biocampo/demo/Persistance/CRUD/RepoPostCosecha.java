package biocampo.demo.Persistance.CRUD;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biocampo.demo.Persistance.Entity.PostCosecha;

@Repository
public interface RepoPostCosecha extends JpaRepository<PostCosecha, Long>{

}
