package rocketseat.gestao_vagas.exeptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User Not Found");
    }
}
