package biocampo.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;


import biocampo.demo.Domain.Model.JobPosition;

public interface JobPositionRepository {
    List<JobPosition> getAll();
    Optional<JobPosition> getJobPosition(Long positionId);
    JobPosition save(JobPosition jobPosition);
    void delete(Long positionId);
}
