package rocketseat.gestao_vagas.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
    //sem o optional ficaria assim: CandidateEntity findByUs...resto do mmetodo.. ele retornaria ou o objeto ou null
    //com o optional, queremos realizar mais operações
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

    Optional<CandidateEntity> findByUsername(String username);
}
