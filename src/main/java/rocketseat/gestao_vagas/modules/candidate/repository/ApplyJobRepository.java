package rocketseat.gestao_vagas.modules.candidate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocketseat.gestao_vagas.modules.candidate.entity.ApplyJobEntity;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
