package rocketseat.gestao_vagas.modules.candidate.useCases;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import rocketseat.gestao_vagas.modules.company.repositories.JobRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ListAllJobsByFilterUseCase {

    private final JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);

    }
}
