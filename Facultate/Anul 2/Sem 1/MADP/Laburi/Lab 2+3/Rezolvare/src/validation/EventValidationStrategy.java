package validation;
import errors.ValidationError;
import events.Event;

public class EventValidationStrategy implements ValidationStrategy<Event> {
    @Override
    public void validate(Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new ValidationError("Event name cannot be null or empty");
        }

    }
}
