package server;

import dtos.CancelReservationDTO;
import dtos.LoginDTO;
import dtos.MakeReservationDTO;
import network.Request;
import network.Response;
import network.ResponseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.IAuthService;
import services.IMainService;
import utils.ServiceFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientObjectWorker implements Runnable {

    private final IMainService mainService;
    private final IAuthService authService;
    private final Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private volatile boolean connected = true;

    private final Logger logger = LogManager.getLogger(ClientObjectWorker.class);

    private static final List<ClientObjectWorker> clients =
            new CopyOnWriteArrayList<>();

    public ClientObjectWorker(Socket socket) {
        this.mainService = ServiceFactory.getInstance().getMainService();
        this.authService = ServiceFactory.getInstance().getAuthService();
        this.socket = socket;
        clients.add(this);
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());

            while (connected) {
                Object obj = input.readObject();

                if (!(obj instanceof Request request)) {
                    sendResponse(Response.error("Invalid request"));
                    continue;
                }

                Response response = handleRequest(request);

                if (response != null) {
                    sendResponse(response);
                }
            }

        } catch (Exception e) {
            logger.error("Client error", e);
        } finally {
            close();
        }
    }

    private Response handleRequest(Request request) {
        try {
            return switch (request.getType()) {

                case LOGIN -> handleLogin((LoginDTO) request.getData());

                case GET_ALL_RIDES ->
                        new Response(ResponseType.RIDES_FOUND,
                                mainService.getAllRides(), null);

                case FIND_RIDES -> handleFindRides(request.getData());

                case MAKE_RESERVATION -> {
                    MakeReservationDTO dto = (MakeReservationDTO) request.getData();

                    mainService.makeReservation(
                            dto.getRideId(),
                            dto.getText(),
                            dto.getNoSeats()
                    );

                    notifyAllClients();

                    yield new Response(ResponseType.RESERVATION_MADE, null, null);
                }

                case CANCEL_RESERVATION -> {
                    CancelReservationDTO dto = (CancelReservationDTO) request.getData();

                    mainService.cancelReservation(dto.getReservationId());

                    notifyAllClients();

                    yield new Response(ResponseType.RESERVATION_CANCELED, null, null);
                }

                case GET_RIDE -> {
                    Long id = (Long) request.getData();
                    yield new Response(ResponseType.RIDE_FOUND,
                            mainService.getRide(id), null);
                }

                case FIND_RESERVATIONS ->
                        new Response(ResponseType.RESERVATIONS_FOUND,
                                mainService.findReservations(), null);
            };

        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    private Response handleLogin(LoginDTO dto) {
        boolean ok = authService.authenticate(dto.getUsername(), dto.getPassword());
        if (ok) return new Response(ResponseType.AUTH_SUCCES, null, null);
        return Response.error("Invalid credentials");
    }

    private Response handleFindRides(Object data) {
        try {
            String[] p = (String[]) data;

            return new Response(ResponseType.RIDES_FOUND,
                    mainService.findRides(
                            java.time.LocalDate.parse(p[0]),
                            p[1],
                            p[2]
                    ), null);

        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    private synchronized void sendResponse(Response response) throws IOException {
        output.writeObject(response);
        output.flush();
    }

    private void notifyAllClients() {
        try {
            for (ClientObjectWorker client : clients) {
                client.sendResponse(
                        new Response(ResponseType.UPDATE_NOTIFICATION, null, null)
                );
            }

        } catch (Exception ignored) {}
    }

    private void close() {
        connected = false;
        try { if (input != null) input.close(); } catch (Exception ignored) {}
        try { if (output != null) output.close(); } catch (Exception ignored) {}
        try { socket.close(); } catch (Exception ignored) {}
    }
}