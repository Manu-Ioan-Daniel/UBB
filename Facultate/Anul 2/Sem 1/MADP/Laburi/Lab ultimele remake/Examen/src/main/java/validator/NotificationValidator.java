package validator;

import exceptions.ValidationException;
import models.Notification;

public class NotificationValidator implements Validator<Notification> {

    @Override
    public void validate(Notification entity) {
        StringBuilder sb = new StringBuilder();
        if(entity.getMessage() == null || entity.getMessage().isEmpty()) {
            sb.append("Message is empty\n");
        }
        if(entity.getToId() == null || entity.getToId() < 0) {
            sb.append("Invalid recipient ID\n");
        }
        if(!sb.isEmpty()) {
            throw new ValidationException(sb.toString());
        }
    }
}
