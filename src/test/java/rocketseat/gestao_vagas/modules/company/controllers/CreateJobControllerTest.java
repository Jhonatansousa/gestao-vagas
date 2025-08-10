package rocketseat.gestao_vagas.modules.company.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rocketseat.gestao_vagas.exeptions.CompanyNotFoundException;
import rocketseat.gestao_vagas.modules.company.dto.CreateJobDTO;
import rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import rocketseat.gestao_vagas.utils.TestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static rocketseat.gestao_vagas.utils.TestUtils.objectToJson;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    //simula um servidor
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CompanyRepository companyRepository;

    //com esse before, antes do teste ele cria esse "servidor" e aplica a configuração para o teste funcionar
    @Before
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var company = CompanyEntity.builder()
                .name("COMPANY_NAME")
                .username("COMPANY_USERNAME")
                .password("COMPANY_PASSWORD")
                .email("COMPANY_EMAIL@EMAIL.COM")
                .description("COMPANY_DESCRIPTION")
                .build();

        //saveAndFlush salva imediatamente de forma síncrona, seguindo o código após a conclusão
        company = companyRepository.saveAndFlush(company);


        var createJobDTO = CreateJobDTO.builder()
                        .benefits("BENEFITS_TEST")
                        .description("DESCRIPTION_TEST")
                        .level("LEVEL_TEST")
                        .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createJobDTO))
                .header("Authorization", TestUtils.generateToken(company.getId(), "JAVAGAS_@123#"))
        ).andExpect(status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
        var createJobDTO = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST")
                .description("DESCRIPTION_TEST")
                .level("LEVEL_TEST")
                .build();


        mockMvc.perform(MockMvcRequestBuilders.post("/company/job/")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(TestUtils.objectToJson(createJobDTO))
                       .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#"))
               ).andExpect(MockMvcResultMatchers.status().isBadRequest());
               //.andExpect(status().isNotFound());
               //.andReturn();


    }



}
