package proxy;

import dtos.CancelReservationDTO;
import dtos.LoginDTO;
import dtos.MakeReservationDTO;
import dtos.RideDTO;
import exceptions.ServiceException;
import models.Reservation;
import models.Ride;
import network.Request;
import network.RequestType;
import network.Response;
import network.ResponseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.IAuthService;
import services.IMainService;
import utils.Observer;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesObjectProxy implements IAuthService, IMainService {
    private final String host;
    private final int port;

    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private List<Observer> observers = new CopyOnWriteArrayList<>();

    private final BlockingQueue<Response> qresponses = new LinkedBlockingQueue<>();
    private volatile boolean finished;
    private Thread readerThread;

    private static final Logger logger = LogManager.getLogger(ServicesObjectProxy.class);

    public ServicesObjectProxy(String host, int port) {

        this.host = host;
        this.port = port;
        try {
            initializeConnection();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeConnection() throws Exception {
        logger.info("Initializing connection to server at {}:{}", host, port);
        connection = new Socket(host, port);
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        finished = false;
        startReader();
        logger.info("Connection established");
    }

    private void startReader() {
        readerThread = new Thread(() -> {
            while (!finished) {
                try {
                    Object obj = input.readObject();
                    if (!(obj instanceof Response response)) {
                        logger.warn("Received unknown object from server: {}", obj);
                        continue;
                    }

                    logger.debug("Received response from server: {}", response.getType());
                    if (response.getType() == ResponseType.UPDATE_NOTIFICATION) {
                        notifyObservers(response.getData());
                        continue;
                    }
                    qresponses.put(response);
                } catch (EOFException ignored) {
                    logger.warn("Connection closed by server (EOF)");
                    finished = true;
                    signalErrorIfWaiting("Connection closed by server");
                } catch (Exception e) {
                    if (!finished) {
                        logger.error("Connection closed: {}", e.getMessage(), e);
                        signalErrorIfWaiting("Connection closed: " + e.getMessage());
                    }
                    finished = true;
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.setName("travel-proxy-reader");
        readerThread.start();
    }

    private void signalErrorIfWaiting(String message) {
        logger.error("Signaling error to waiting thread: {}", message);
        try {
            qresponses.put(Response.error(message));
        } catch (InterruptedException e) {
            logger.error("Error signaling error", e);
        }
    }

    private void closeConnection() {
        finished = true;
        logger.info("Closing connection to server");
        try {
            if (input != null) input.close();
        } catch (Exception e) {
            logger.warn("Error closing input stream: {}", e.getMessage());
        }
        try {
            if (output != null) output.close();
        } catch (Exception e) {
            logger.warn("Error closing output stream: {}", e.getMessage());
        }
        try {
            if (connection != null) connection.close();
        } catch (Exception e) {
            logger.warn("Error closing socket: {}", e.getMessage());
        }
    }

    private synchronized void sendRequest(Request request) throws Exception {
        logger.debug("Sending request to server: {}", request.getType());
        output.writeObject(request);
        output.flush();
    }

    private Response readResponse() throws ServiceException {
        try {
            Response response = qresponses.take();
            logger.debug("Read response from queue: {}", response.getType());
            if (response.getType() == ResponseType.ERROR) {
                logger.warn("Received error response: {}", response.getMessage());
                throw new ServiceException(response.getMessage());
            }
            return response;
        } catch (InterruptedException e) {
            logger.error("Interrupted while reading response", e);
            throw new ServiceException("Error reading response");
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        logger.info("Attempting login for user: {}", username);
        try {

            sendRequest(new Request(RequestType.LOGIN, new LoginDTO(username, password)));
            Response response = readResponse();

            if (response.getType() == ResponseType.AUTH_SUCCES) {
                logger.info("Login successful for user: {}", username);
                return true;
            }
            if (response.getType() == ResponseType.AUTH_FAILED) {
                logger.warn("Login failed for user: {}: {}", username, response.getMessage());
                closeConnection();
                return false;
            }
            logger.error("Unexpected response during login: {}", response.getType());
            closeConnection();
            return false;
        } catch (Exception e) {
            logger.error("Login error for user: {}: {}", username, e.getMessage(), e);
            closeConnection();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RideDTO> findRides(LocalDate rideDate, String departureHour, String destination) {
        logger.info("Searching rides for destination: {}, date: {}, hour: {}", destination, rideDate, departureHour);
        try {
            String[] params = {rideDate.toString(), departureHour, destination};
            sendRequest(new Request(RequestType.FIND_RIDES, params));
            Response response = readResponse();
            return (List<RideDTO>) response.getData();
        } catch (Exception e) {
            logger.error("Error searching rides: {}", e.getMessage(), e);
            throw new ServiceException("Error searching rides: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ride> getAllRides() {
        logger.info("Requesting all rides");
        try {
            sendRequest(new Request(RequestType.GET_ALL_RIDES, null));
            Response response = readResponse();
            return (List<Ride>) response.getData();
        } catch (Exception e) {
            logger.error("Error getting rides: {}", e.getMessage(), e);
            throw new ServiceException("Error getting rides: " + e.getMessage());
        }
    }

    @Override
    public void makeReservation(Long rideId, String clientName, int noSeats) {
        logger.info("Making reservation for ride {} for client {}", rideId, clientName);
        try {
            sendRequest(new Request(RequestType.MAKE_RESERVATION, new MakeReservationDTO(rideId, clientName, noSeats)));
            readResponse();
            logger.info("Reservation made successfully");
        } catch (Exception e) {
            logger.error("Error making reservation: {}", e.getMessage(), e);
            throw new ServiceException("Error making reservation: " + e.getMessage());
        }
    }

    @Override
    public void cancelReservation(Long reservationId) {
        logger.info("Cancelling reservation {}", reservationId);
        try {
            sendRequest(new Request(RequestType.CANCEL_RESERVATION, new CancelReservationDTO(reservationId)));
            readResponse();
            logger.info("Reservation cancelled successfully");
        } catch (Exception e) {
            logger.error("Error cancelling reservation: {}", e.getMessage(), e);
            throw new ServiceException("Error cancelling reservation: " + e.getMessage());
        }
    }

    @Override
    public Ride getRide(Long rideId) {
        logger.info("Getting ride {}", rideId);
        try {
            sendRequest(new Request(RequestType.GET_RIDE, rideId));
            Response response = readResponse();
            return (Ride) response.getData();
        } catch (Exception e) {
            logger.error("Error getting ride: {}", e.getMessage(), e);
            throw new ServiceException("Error getting ride: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Reservation> findReservations() {
        logger.info("Finding all reservations");
        try {
            sendRequest(new Request(RequestType.FIND_RESERVATIONS, null));
            Response response = readResponse();
            return (List<Reservation>) response.getData();
        } catch (Exception e) {
            logger.error("Error finding reservations: {}", e.getMessage(), e);
            throw new ServiceException("Error finding reservations: " + e.getMessage());
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void closeProxy() {
        closeConnection();
    }

    private void notifyObservers(Object data) {
        for (Observer o : observers) {
            o.update();
        }
    }
}
