package rocketseat.gestao_vagas.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private String benefits;

    @NotBlank(message = "Esse campo é obrigatório")
    private String level;

    @ManyToOne()
    @JoinColumn(name = "company_id", insertable=false, updatable=false)
    private CompanyEntity company;

    @Column(name = "company_id")
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}


/*id_company -> chave estrangeira -> chave primaria de outra tabela (uuid id)
este é o modo de fazer a conexão de uma tabela à outra, com o tipo de relacionamento
* insetrable e updatable, essencial para não dar erro, pois o objetivo é apenas referenciar, e não inserir ou atualizar
 */