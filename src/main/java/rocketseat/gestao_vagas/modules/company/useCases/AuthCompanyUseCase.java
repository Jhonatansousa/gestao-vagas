package rocketseat.gestao_vagas.modules.company.useCases;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rocketseat.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import rocketseat.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        var company = companyRepository.findByUsername(authCompanyDTO.username()).orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/Password Incorrect");
                });

        // verificar se a senha são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());

        //se não for igual -> Erro
        if (!passwordMatches) {
            throw new AuthenticationException("Username/Password Incorrect");
        }
        //se for igual -> Gerar o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();


        return authCompanyResponseDTO;
    }

}
