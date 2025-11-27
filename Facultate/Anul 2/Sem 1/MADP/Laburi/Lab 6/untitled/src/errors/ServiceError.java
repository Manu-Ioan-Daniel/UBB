package errors;

public class ServiceError extends RuntimeException {
    public ServiceError(String message) {
        super(message);
    }
}
