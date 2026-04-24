package server;

import utils.ServiceFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObjectConcurrentServer {
    private final int port;
    private volatile boolean running;
    private ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final Logger logger = LogManager.getLogger(ObjectConcurrentServer.class);

    public ObjectConcurrentServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        logger.info("Server started on port {}", port);

        while (running) {
            Socket client = serverSocket.accept();
            executor.execute(new ClientObjectWorker(client));
        }
    }

    public void stop() throws IOException {
        running = false;
        executor.shutdownNow();
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        logger.info("Server stopped");
    }
}



