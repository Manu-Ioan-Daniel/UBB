package validation;

import exceptions.ValidationException;

public interface Validator <T> {
    void validate(T entity) throws ValidationException;
}
