package errors;

public class ValidationError extends RuntimeException {
    public ValidationError(String message) {
        super(message);
    }
}
