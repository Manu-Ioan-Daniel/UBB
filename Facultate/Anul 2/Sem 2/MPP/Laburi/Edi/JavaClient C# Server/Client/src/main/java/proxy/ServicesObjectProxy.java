package proxy;

import com.google.gson.JsonElement;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.nio.charset.StandardCharsets;

public class ServicesObjectProxy implements IAuthService, IMainService {
    private final String host;
    private final int port;

    private Socket connection;
    private BufferedReader input;
    private PrintWriter output;
    private List<Observer> observers = new CopyOnWriteArrayList<>();

    private final BlockingQueue<Response> qresponses = new LinkedBlockingQueue<>();
    private volatile boolean finished;
    private Thread readerThread;
    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .registerTypeAdapter(LocalDate.class, new com.google.gson.JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) {
                    return LocalDate.parse(json.getAsString());
                }
            })
            .registerTypeAdapter(LocalDateTime.class, new com.google.gson.JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) {
                    return LocalDateTime.parse(json.getAsString());
                }
            })
            .registerTypeAdapter(LocalTime.class, new com.google.gson.JsonDeserializer<LocalTime>() {
                @Override
                public LocalTime deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) {
                    return LocalTime.parse(json.getAsString());
                }
            })
            .create();

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
        output = new PrintWriter(connection.getOutputStream(), true, StandardCharsets.UTF_8);
        input = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        finished = false;
        startReader();
        logger.info("Connection established");
    }

    private void startReader() {
        readerThread = new Thread(() -> {
            while (!finished) {
                try {
                    String json = input.readLine();
                    if (json == null) {
                        logger.warn("Connection closed by server (EOF)");
                        finished = true;
                        signalErrorIfWaiting("Connection closed by server");
                        break;
                    }

                    logger.debug("Received from server: {}", json);

                    JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();

                    ResponseType type = ResponseType.OK;

                    if (jsonObj.has("Type")) {
                        try {
                            if (jsonObj.get("Type").isJsonPrimitive()) {
                                com.google.gson.JsonPrimitive prim = jsonObj.get("Type").getAsJsonPrimitive();
                                if (prim.isNumber()) {
                                    int typeNum = prim.getAsInt();
                                    type = mapCSharpNumericToJavaResponseType(typeNum);
                                } else if (prim.isString()) {
                                    String typeStr = prim.getAsString();
                                    type = mapCSharpToJavaResponseType(typeStr);
                                }
                            }
                        } catch (Exception e) {
                            logger.warn("Error parsing Type field: {}", e.getMessage());
                        }
                    }

                    String message = null;
                    if (jsonObj.has("Message") && !jsonObj.get("Message").isJsonNull()) {
                        message = jsonObj.get("Message").getAsString();
                    }

                    Object data = null;
                    if (jsonObj.has("Data") && !jsonObj.get("Data").isJsonNull()) {
                        data = jsonObj.get("Data");
                    }

                    Response response = new Response(type, data, message);
                    logger.debug("Parsed response: {} with message: {}", type, message);

                    if (type == ResponseType.UPDATE_NOTIFICATION) {
                        notifyObservers(data);
                        continue;
                    }
                    qresponses.put(response);
                } catch (EOFException ignored) {
                    logger.warn("Connection closed by server (EOF)");
                    finished = true;
                    signalErrorIfWaiting("Connection closed by server");
                } catch (Exception e) {
                    if (!finished) {
                        logger.error("Error reading response: {}", e.getMessage(), e);
                        signalErrorIfWaiting("Error reading response: " + e.getMessage());
                    }
                    finished = true;
                }
            }
        });
        readerThread.setDaemon(true);
        readerThread.setName("travel-proxy-reader");
        readerThread.start();
    }

    private ResponseType mapCSharpNumericToJavaResponseType(int typeNum) {
        return switch (typeNum) {
            case 0 -> ResponseType.AUTH_SUCCES;
            case 1 -> ResponseType.AUTH_FAILED;
            case 2 -> ResponseType.ERROR;
            case 3 -> ResponseType.RIDES_FOUND;
            case 4 -> ResponseType.RIDE_FOUND;
            case 5 -> ResponseType.RESERVATION_MADE;
            case 6 -> ResponseType.RESERVATION_CANCELED;
            case 7 -> ResponseType.RESERVATIONS_FOUND;
            case 8 -> ResponseType.UPDATE_NOTIFICATION;
            default -> ResponseType.ERROR;
        };
    }

    private ResponseType mapCSharpToJavaResponseType(String csharpType) {
        return switch (csharpType) {
            case "AuthSuccess" -> ResponseType.AUTH_SUCCES;
            case "AuthFailed" -> ResponseType.AUTH_FAILED;
            case "Error" -> ResponseType.ERROR;
            case "RidesFound" -> ResponseType.RIDES_FOUND;
            case "RideFound" -> ResponseType.RIDE_FOUND;
            case "ReservationMade" -> ResponseType.RESERVATION_MADE;
            case "ReservationCanceled" -> ResponseType.RESERVATION_CANCELED;
            case "ReservationsFound" -> ResponseType.RESERVATIONS_FOUND;
            case "UpdateNotification" -> ResponseType.UPDATE_NOTIFICATION;
            default -> ResponseType.ERROR;
        };
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

        JsonObject json = new JsonObject();
        String csharpType = mapJavaToCSharpRequestType(request.getType());
        json.addProperty("type", csharpType);

        if (request.getData() != null) {
            String dataJsonStr = gson.toJson(request.getData());
            JsonElement dataJsonElem = JsonParser.parseString(dataJsonStr);
            json.add("data", dataJsonElem);
        }

        String jsonString = gson.toJson(json);
        logger.debug("Sending JSON: {}", jsonString);
        output.println(jsonString);
    }

    private String mapJavaToCSharpRequestType(RequestType javaType) {
        return switch (javaType) {
            case LOGIN -> "Login";
            case FIND_RIDES -> "FindRides";
            case GET_ALL_RIDES -> "GetAllRides";
            case MAKE_RESERVATION -> "MakeReservation";
            case CANCEL_RESERVATION -> "CancelReservation";
            case GET_RIDE -> "GetRide";
            case FIND_RESERVATIONS -> "FindReservations";
            default -> "Login";
        };
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

            if (response.getData() instanceof com.google.gson.JsonElement) {
                com.google.gson.JsonElement el = (com.google.gson.JsonElement) response.getData();
                logger.debug("Raw findRides JSON data: {}", el.toString());

                if (el.isJsonArray()) {
                    com.google.gson.JsonArray arr = el.getAsJsonArray();
                    boolean looksLikeSeatList = false;
                    if (arr.size() > 0 && arr.get(0).isJsonObject()) {
                        com.google.gson.JsonObject obj0 = arr.get(0).getAsJsonObject();
                        if (obj0.has("SeatNo") && obj0.has("ClientName")) looksLikeSeatList = true;
                    }

                    if (looksLikeSeatList) {
                        java.util.List<RideDTO> seats = new java.util.ArrayList<>();
                        for (com.google.gson.JsonElement item : arr) {
                            com.google.gson.JsonObject o = item.getAsJsonObject();
                            int seatNo = o.has("SeatNo") && !o.get("SeatNo").isJsonNull() ? o.get("SeatNo").getAsInt() : 0;
                            String client = o.has("ClientName") && !o.get("ClientName").isJsonNull() ? o.get("ClientName").getAsString() : "-";
                            seats.add(new RideDTO(seatNo, client));
                        }
                        return seats;
                    }

                    return gson.fromJson(arr, new TypeToken<List<RideDTO>>(){}.getType());
                }
            }

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

            if (response.getData() instanceof com.google.gson.JsonElement) {
                com.google.gson.JsonElement el = (com.google.gson.JsonElement) response.getData();
                logger.debug("Raw rides JSON data: {}", el.toString());

                if (el.isJsonPrimitive()) {
                    String raw = el.getAsString().trim();
                    logger.debug("Rides data is primitive string, parsing CSV-style rows");
                    java.util.ArrayList<Ride> parsed = new java.util.ArrayList<>();
                    if (!raw.isEmpty()) {
                        String[] lines = raw.split("\\r?\\n|\\s+(?=\\d+,)");
                        for (String line : lines) {
                            line = line.trim();
                            if (line.isEmpty()) continue;
                            String[] parts = line.split(",");
                            if (parts.length >= 5) {
                                try {
                                    String dest = parts[1].trim();
                                    LocalDate date = LocalDate.parse(parts[2].trim());
                                    LocalTime time = LocalTime.parse(parts[3].trim());
                                    int seats = Integer.parseInt(parts[4].trim());
                                    Ride r = new Ride(dest, date, time);
                                    r.setAvailableSeats(seats);
                                    parsed.add(r);
                                } catch (Exception ex) {
                                    logger.warn("Failed parsing CSV ride line: {} -> {}", line, ex.getMessage());
                                }
                            }
                        }
                    }
                    if (!parsed.isEmpty()) return parsed;
                }

                List<Ride> rides = gson.fromJson(el, new TypeToken<List<Ride>>(){}.getType());

                boolean fallbackNeeded = false;
                if (rides == null) fallbackNeeded = true;
                else if (rides.isEmpty()) fallbackNeeded = false;
                else {
                    String firstDest = rides.get(0).getDestination();
                    LocalDate firstDate = rides.get(0).getDate();
                    LocalTime firstTime = rides.get(0).getDepartureTime();
                    if (firstDest == null || firstDest.isBlank()) fallbackNeeded = true;
                    else {
                        for (Ride r : rides) {
                            if (!r.getDestination().equals(firstDest)) { fallbackNeeded = false; break; }
                        }
                        boolean allDatesSame = true;
                        for (Ride r : rides) { if (!r.getDate().equals(firstDate)) { allDatesSame = false; break; } }
                        if (allDatesSame) fallbackNeeded = true;
                    }
                }

                if (!fallbackNeeded) {
                    return rides;
                }

                logger.warn("Standard deserialization failed or produced suspicious values; attempting manual parsing");

                java.util.ArrayList<Ride> manual = new java.util.ArrayList<>();
                if (el.isJsonArray()) {
                    for (com.google.gson.JsonElement item : el.getAsJsonArray()) {
                        if (!item.isJsonObject()) continue;
                        com.google.gson.JsonObject obj = item.getAsJsonObject();

                        String dest = null;
                        String[] destKeys = {"destination","Destination","DestinationName","destinationName","clientName","ClientName"};
                        for (String k : destKeys) {
                            if (obj.has(k) && !obj.get(k).isJsonNull()) { dest = obj.get(k).getAsString(); break; }
                        }

                        LocalDate date = null;
                        String[] dateKeys = {"date","Date","RideDate","rideDate"};
                        for (String k : dateKeys) {
                            if (obj.has(k) && !obj.get(k).isJsonNull()) {
                                try { date = LocalDate.parse(obj.get(k).getAsString()); break; } catch (Exception ex) { }
                            }
                        }

                        LocalTime time = null;
                        String[] timeKeys = {"departureTime","DepartureTime","time","Time","DepartureHour","departureHour"};
                        for (String k : timeKeys) {
                            if (obj.has(k) && !obj.get(k).isJsonNull()) {
                                try { time = LocalTime.parse(obj.get(k).getAsString()); break; } catch (Exception ex) { }
                            }
                        }

                        Integer seats = null;
                        String[] seatsKeys = {"availableSeats","AvailableSeats","seats","Seats","SeatNo","seatNo"};
                        for (String k : seatsKeys) {
                            if (obj.has(k) && !obj.get(k).isJsonNull()) {
                                try { seats = obj.get(k).getAsInt(); break; } catch (Exception ex) { }
                            }
                        }

                        if (dest == null) dest = "";
                        if (date == null) date = LocalDate.now();
                        if (time == null) time = LocalTime.MIDNIGHT;
                        Ride r = new Ride(dest, date, time);
                        if (seats != null) r.setAvailableSeats(seats);
                        manual.add(r);
                    }
                }

                return manual;
            }
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

            if (response.getData() instanceof com.google.gson.JsonElement) {
                return gson.fromJson((com.google.gson.JsonElement) response.getData(), Ride.class);
            }
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

            if (response.getData() instanceof com.google.gson.JsonElement) {
                return gson.fromJson((com.google.gson.JsonElement) response.getData(), new TypeToken<List<Reservation>>(){}.getType());
            }
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
