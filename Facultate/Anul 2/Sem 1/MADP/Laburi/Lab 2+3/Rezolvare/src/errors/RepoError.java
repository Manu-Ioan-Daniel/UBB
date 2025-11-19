package errors;

public class RepoError extends RuntimeException {
    public RepoError(String message) {
        super(message);
    }
}
