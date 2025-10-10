import domain.Vehicle;
import repository.VehicleRepository;
import utils.MileageUnit;

public class Application {

	public static void main(String[] args) {
		Vehicle vehicle;
        //TODO-instantiate a new vehicle
		vehicle=new Vehicle("BV25DAN",10.5,2020, MileageUnit.KM);
		VehicleRepository repository = new VehicleRepository();
		repository.addVehicle(vehicle);
	
		for (int i = 0; i < repository.getNumberOfVehicles(); i++) {
			Vehicle retrievedVehicle = repository.getVehicleAtPosition(i);
			retrievedVehicle.printVehicleDetails();
		}
	}

}
