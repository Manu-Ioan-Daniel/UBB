
import org.example.dtos.RideRequest;
import org.example.dtos.RideResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class RideAPITest {

    @Test
    void testApi() {

        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8080/api/rides")
                .requestInterceptor(new LoggingInterceptor())
                .build();
        //post test
        RideRequest newRide = new RideRequest();
        newRide.setDestination("Bucuresti");
        newRide.setRideDate(LocalDate.now().plusDays(1));
        newRide.setDepartureTime(LocalTime.of(12, 0));
        newRide.setAvailableSeats(3);

        restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(newRide)
                .retrieve()
                .toEntity(Object.class);



        //get
        List<RideResponse> rides = restClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("destination", "Bucuresti").build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (rides != null) {
            rides.forEach(r -> System.out.println("Rides found: ID -  " + r.getId() + ", to - " + r.getDestination()));
        }


        //delete
        restClient.delete()
                .uri("/4")
                .retrieve()
                .toBodilessEntity();

    }
}