package validation;

import domain.User;
import errors.ValidationError;

public class UserValidationStrategy implements ValidationStrategy<User> {
    public void validate(User user) {
        String errors="";
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errors+="User name cannot be null or empty\n";
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            errors+="Invalid email address\n";
        }
        if (user.getPassword() ==null || user.getPassword().length() < 6) {
            errors+="Password must be at least 6 characters long\n";
        }
        if(!errors.isEmpty()){
            throw new ValidationError(errors);
        }
    }
}
