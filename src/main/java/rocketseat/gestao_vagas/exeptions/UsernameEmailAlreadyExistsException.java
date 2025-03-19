package rocketseat.gestao_vagas.exeptions;

public class UsernameEmailAlreadyExistsException extends  RuntimeException{
    public UsernameEmailAlreadyExistsException(String message) {
        super(message);
    }
}
