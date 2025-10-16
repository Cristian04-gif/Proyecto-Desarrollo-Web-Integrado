package biocampo.demo.Domain.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biocampo.demo.Domain.Model.JobPosition;
import biocampo.demo.Domain.Repository.JobPositionRepository;

@Service
public class JobPositionService {

    @Autowired
    private JobPositionRepository jobPositionRepository;

    public List<JobPosition> getAllJobPositions() {
        return jobPositionRepository.getAll();
    }

    public Optional<JobPosition> getJobPositionById(Long positionId) {
        return jobPositionRepository.getJobPosition(positionId);
    }

    public JobPosition createJobPosition(JobPosition jobPosition) {
        return jobPositionRepository.save(jobPosition);
    }

    public JobPosition updateJobPosition(Long id, JobPosition jobPosition) {
        Optional<JobPosition> existingPosition = jobPositionRepository.getJobPosition(id);
        if (existingPosition.isPresent()) {
            JobPosition toUpdate = existingPosition.get();
            if (jobPosition.getPositionName() != null)
                toUpdate.setPositionName(jobPosition.getPositionName());
            return jobPositionRepository.save(toUpdate);
        } else {
            return null;
        }
    }

    public void deleteJobPosition(Long positionId) {
        jobPositionRepository.delete(positionId);
    }
}
