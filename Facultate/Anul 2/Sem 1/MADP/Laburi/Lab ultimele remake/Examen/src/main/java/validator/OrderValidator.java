package validator;

import exceptions.ValidationException;
import models.Order;

public class OrderValidator implements Validator<Order> {
    @Override
    public void validate(Order entity) {
        StringBuilder sb = new StringBuilder();
        if(entity.getPickupAdress() == null || entity.getPickupAdress().isEmpty()){
            sb.append("Pickup address cannot be empty or null!\n");
        }
        if(entity.getDestinationAdress() == null || entity.getDestinationAdress().isEmpty()){
            sb.append("Destination address cannot be empty or null!\n");
        }
        if(entity.getClientName() == null || entity.getClientName().isEmpty()){
            sb.append("Client name cannot be empty or null!\n");
        }
        if(!sb.isEmpty()) {
            throw new ValidationException(sb.toString());
        }
    }
}
