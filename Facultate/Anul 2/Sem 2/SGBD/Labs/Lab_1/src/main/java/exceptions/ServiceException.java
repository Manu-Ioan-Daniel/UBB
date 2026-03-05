package exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String s) {
        super(s);
    }
}
