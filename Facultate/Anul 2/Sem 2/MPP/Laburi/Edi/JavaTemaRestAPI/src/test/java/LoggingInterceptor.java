import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        System.out.println("➡ REQUEST: " + request.getMethod() + " " + request.getURI());

        ClientHttpResponse response = execution.execute(request, body);

        System.out.println("⬅ RESPONSE: " + response.getStatusCode());

        return response;
    }
}