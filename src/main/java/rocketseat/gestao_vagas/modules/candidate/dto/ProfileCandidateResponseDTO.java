package rocketseat.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
    private UUID id;
    @Schema(example = "jhon")
    private String username;
    @Schema(example = "teste@email.com")
    private String email;
    @Schema(example = "jhonatan")
    private String name;
    @Schema(example = "Java Developer")
    private String description;
}
