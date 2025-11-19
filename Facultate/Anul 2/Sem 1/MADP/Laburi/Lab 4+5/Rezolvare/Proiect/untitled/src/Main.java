import service.ServiceUser;
import service.ServiceFlock;
import repo.DatabaseUserRepository;
import repo.DatabaseFlockRepository;
import Ui.Ui;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";
        DatabaseUserRepository userRepo = new DatabaseUserRepository(url);
        DatabaseFlockRepository flockRepo = new DatabaseFlockRepository(url);
        ServiceUser serviceUser = new ServiceUser(userRepo);
        ServiceFlock serviceFlock = new ServiceFlock(flockRepo);
        Ui ui = new Ui(serviceUser, serviceFlock);
        ui.run();
    }
}