package rocketseat.gestao_vagas.modules.candidate.useCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rocketseat.gestao_vagas.exeptions.JobNotFoundException;
import rocketseat.gestao_vagas.exeptions.UserNotFoundException;
import rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import rocketseat.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import rocketseat.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import rocketseat.gestao_vagas.modules.company.repositories.JobRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // faz com que o JUnit habilite o Mockito nessa classe
public class ApplyJobCandidateUseCaseTest {

    //inject é usado na classe que quero testar, que no caso é essa abaixo
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    //mock é uma dependência dentro do injectMock
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;


    @Test
    @DisplayName("Shouldn't be able to apply job with candidate not found")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        //try {
          //  applyJobCandidateUseCase.execute(null, null);
       // } catch (Exception e) {
       //     assertThat(e).isInstanceOf(UserNotFoundException.class);
        //deixei comentado o código anterior, pesquisando encontrei essa maneira que pelo que parece é 1000x melhor
            assertThrows(UserNotFoundException.class, () -> applyJobCandidateUseCase.execute(null, null));
        //}
        /*o teste aqui foi validando se o job não existe, passando o param null, como o erro foi tratado no test
        * ele entende que o teste foi um sucesso*/
    }

    //==================reescrevendo o código abaixo ===============================

//    @Test
//    @DisplayName("Shouldn't be able to apply job with job not found")
//    public void shouldNotBeAbleToApplyJobWithJobNotFound() {
//        var idCandidate = UUID.randomUUID();
//        var candidate = new CandidateEntity();
//        candidate.setId(idCandidate);
//
//        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
//        try {
//            applyJobCandidateUseCase.execute(idCandidate, null);
//        } catch (Exception e) {
//            assertThat(e).isInstanceOf(JobNotFoundException.class);
//        }
//    }
//========== REESCREVI USANDO DOIS MÉTODOS DIFERENTES ===================
    @Test
    @DisplayName("Shouldn't be able to apply job with job is null")
    public void shouldNotBeAbleToApplyJobWithJobNotIsNull() {
        var candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        assertThrows(JobNotFoundException.class, () -> applyJobCandidateUseCase.execute(candidate.getId(), null));
    }

    @Test
    @DisplayName("Should throw Exception = JobNotFound When job doesn't exist in database")
    public void shouldThrowJobNotFoundWhenJobDoesNotExistInDatabase() {
        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(UUID.randomUUID());

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());
        assertThrows(JobNotFoundException.class, () -> applyJobCandidateUseCase.execute(candidateId, jobId));
    }



    // ==================================================================================

    //==========================REFATORADO CÓDIGO ABAIXO ===============================

//    @Test
//    public void shouldBeAbleToCreateANewApplyJob() {
//        var idCandidate = UUID.randomUUID();
//        var idJob = UUID.randomUUID();
//
//        var applyJob = ApplyJobEntity.builder()
//                        .candidateId(idCandidate)
//                        .jobId(idJob).build();
//
//        var applyJobWithID = ApplyJobEntity.builder()
//                .id(UUID.randomUUID())
//                .candidateId(applyJob.getCandidateId())
//                .jobId(applyJob.getJobId()).build();
//
//
//        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
//        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));
//        when(applyJobRepository.save(applyJob)).thenReturn(applyJobWithID);
//
//        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);
//        System.out.println(applyJobWithID.getId() + " | " + applyJobWithID.getJobId() +  " | " + applyJobWithID.getCandidateId());
//        assertThat(result).hasFieldOrProperty("id");
//        assertNotNull(result.getId());
//    }
    @Test
    public void shouldBeAbleToCreateANewApplyJob() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobEntity()));

        var applyJobWithId = ApplyJobEntity.builder()
                .id(UUID.randomUUID())
                .candidateId(idCandidate)
                .jobId(idJob)
                .id(UUID.randomUUID()).build();

        when(applyJobRepository.save(any(ApplyJobEntity.class))).thenReturn(applyJobWithId);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(applyJobWithId, result);
        verify(applyJobRepository, times(1)).save(any(ApplyJobEntity.class));
    }



}