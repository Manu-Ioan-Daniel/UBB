
import javafx.application.Application;
import repo.DatabaseEventRepository;
import service.ServiceEvent;
import service.ServiceUser;
import service.ServiceFlock;
import repo.DatabaseUserRepository;
import repo.DatabaseFlockRepository;
import Ui.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";
        DatabaseUserRepository userRepo = new DatabaseUserRepository(url);
        DatabaseFlockRepository flockRepo = new DatabaseFlockRepository(url);
        DatabaseEventRepository eventRepo = new DatabaseEventRepository(url, userRepo);
        ServiceEvent serviceEvent = new ServiceEvent(eventRepo);
        ServiceUser serviceUser = new ServiceUser(userRepo);
        ServiceFlock serviceFlock = new ServiceFlock(flockRepo);
        Ui ui = new Ui(serviceUser, serviceFlock, serviceEvent);
        ui.run();

    }
}
