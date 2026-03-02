package validator;

import exceptions.ValidationException;

public interface Validator<Entity> {
    void validate(Entity professor) throws ValidationException;
}
