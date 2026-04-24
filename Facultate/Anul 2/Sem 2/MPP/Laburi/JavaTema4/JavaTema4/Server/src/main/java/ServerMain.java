
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ObjectConcurrentServer;
import utils.Props;
import java.util.Properties;

public class ServerMain {
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) throws Exception {
        try {
            int port = 5555;
            Properties prop = Props.getProperties();
            System.out.println(prop);
            ObjectConcurrentServer server = new ObjectConcurrentServer(port);
            server.start();
        } catch (Exception e) {
            logger.error("Failed to start server", e);
            System.exit(1);
        }
    }
}
