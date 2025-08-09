package rocketseat.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocketseat.gestao_vagas.exeptions.JobNotFoundException;
import rocketseat.gestao_vagas.exeptions.UserNotFoundException;
import rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import rocketseat.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import rocketseat.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import rocketseat.gestao_vagas.modules.company.repositories.JobRepository;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        //validar se candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> new UserNotFoundException());
        //validar se vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() ->new JobNotFoundException());
        //canditato se inscrever na vaga
        var applyJob = ApplyJobEntity.builder()
                        .candidateId(idCandidate)
                                .jobId(idJob).build();
        //return = applyJob = applyJobRepository.save(applyJob);
        return applyJobRepository.save(applyJob);
    }

}
