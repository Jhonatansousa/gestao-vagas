package rocketseat.gestao_vagas.modules.company.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocketseat.gestao_vagas.modules.company.dto.CreateJobDTO;
import rocketseat.gestao_vagas.modules.company.entities.JobEntity;
import rocketseat.gestao_vagas.modules.company.useCases.CreateJobUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    //companyId recebe o objeto do request e eu transformo em uma String, e depois transformo em um UUID a partir de uma string
    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        //jobEntity.setCompanyId(UUID.fromString(companyId.toString()));


        //aqui, eu uso a anotação no model (builde e o args const..)
        // ele faz com que eu não precise instanciar novamenet e fazer o setters
        var jobEntity = JobEntity.builder()
                .benefits(createJobDTO.benefits())
                .companyId(UUID.fromString(companyId.toString()))
                .description(createJobDTO.description())
                .level(createJobDTO.level()).build();
        return this.createJobUseCase.execute(jobEntity);
    }
}
