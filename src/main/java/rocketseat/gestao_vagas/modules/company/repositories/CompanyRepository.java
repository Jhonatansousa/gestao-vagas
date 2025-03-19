package rocketseat.gestao_vagas.modules.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {

    Optional<CompanyEntity> findByUsernameOrEmail(String username, String email);

    Optional<CompanyEntity> findByUsername(String username);
}
