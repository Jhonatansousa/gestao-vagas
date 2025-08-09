package rocketseat.gestao_vagas.exeptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Job Not Found");
    }
}
