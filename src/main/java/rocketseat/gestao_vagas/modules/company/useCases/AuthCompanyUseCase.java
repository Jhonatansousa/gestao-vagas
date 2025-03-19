package rocketseat.gestao_vagas.modules.company.useCases;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;

import javax.naming.AuthenticationException;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        var company = companyRepository.findByUsername(authCompanyDTO.username()).orElseThrow(() -> {
                    throw new UsernameNotFoundException("Company not found");
                });

        // verificar se a senha são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());

        //se não for igual -> Erro
        if (!passwordMatches) {
            throw new AuthenticationException();
        }
        //se for igual -> Gerar o token
    }

}
