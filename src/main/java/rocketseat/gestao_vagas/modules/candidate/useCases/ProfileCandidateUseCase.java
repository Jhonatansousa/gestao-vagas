package rocketseat.gestao_vagas.modules.candidate.useCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocketseat.gestao_vagas.exeptions.UserNotFoundException;
import rocketseat.gestao_vagas.modules.candidate.CandidateRepository;
import rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
                .orElseThrow(UserNotFoundException::new);

        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .username(candidate.getUsername())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .description(candidate.getDescription()).build();

        return candidateDTO;
    }
}
