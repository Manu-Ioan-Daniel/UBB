package validation;
import errors.ValidationError;
import event.RaceEvent;

public class RaceEventValidationStrategy implements ValidationStrategy<RaceEvent> {
    @Override
    public void validate(RaceEvent raceEvent) {
        StringBuilder dbErrors = new StringBuilder();
        if (raceEvent == null) {
            dbErrors.append("RaceEvent cannot be null.(it probably doesnt exist)\n ");
            throw new ValidationError("RaceEvent validation failed:\n" + dbErrors.toString());
        }
        if (raceEvent.getId() == null || raceEvent.getId() <= 0) {
            dbErrors.append("Invalid ID\n ");
        }
        if (raceEvent.getM() < 1) {
            dbErrors.append("M must be at least 1\n ");
        }
        if (!dbErrors.isEmpty()) {
            throw new ValidationError("RaceEvent validation failed:\n" + dbErrors.toString());
        }
    }
}
