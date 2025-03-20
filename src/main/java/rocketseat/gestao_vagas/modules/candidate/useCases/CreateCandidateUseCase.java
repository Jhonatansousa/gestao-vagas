package rocketseat.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rocketseat.gestao_vagas.exeptions.UsernameEmailAlreadyExistsException;
import rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import rocketseat.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository
                .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) ->{
                    throw new UsernameEmailAlreadyExistsException("Usuário ou E-mail já existe!");
                    //acima eu verifico se já tem  e caso tenha eu retorno com esse erro
                });

        //abaixo tenho só a parte da criptografia
        //criptografo a senha usando o metodo encode da classe passwordEncoder
        var password = passwordEncoder.encode(candidateEntity.getPassword());
        //sobreescrevo a senha criptografada
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
