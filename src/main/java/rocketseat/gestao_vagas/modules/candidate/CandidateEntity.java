package rocketseat.gestao_vagas.modules.candidate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;


//lombok cria todos os getters e setters
@Data
// ele automaticamente cria as colunas do banco de dados
//também não preciso dizer o nome da coluna pois é o mesmo nome dos atributos
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "jhonatan")
    private String name;

    //padrão para que não tenha espaços no username
    @NotBlank
    @Pattern(regexp = "\\S+", message = "O campo [username] NÃO deve conter espaços")
    @Schema(example = "jhon")
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido")
    @Schema(example = "teste@email.com")
    private String email;

    @Length(min = 8, max = 100, message = "A senha deve conter entre 8 e 100 caracteres!!!")
    @Schema(example = "pass123456", minLength = 8, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(example = "Java Developer")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
