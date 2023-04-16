package dev.jaczerob.tedcalm.services.login.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaczerob.tedcalm.services.login.LoginProvider;
import dev.jaczerob.tedcalm.services.login.models.request.LoginRequest;
import dev.jaczerob.tedcalm.services.login.models.response.LoginResponse;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public class LoginHttpClientProvider implements LoginProvider {
    @Inject
    ObjectMapper objectMapper;

    @Inject
    HttpClient httpClient;

    @ConfigProperty(name = "toontown.login.uri")
    String loginURI;

    public LoginResponse login(final LoginRequest loginRequest) throws IOException, InterruptedException {
        Log.infof("Logging in with request: %s", loginRequest);

        final HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(loginURI))
                .header("User-Agent", "jaczerob/tedcalm")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(loginRequest)))
                .build();

        final HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        final LoginResponse loginResponse = objectMapper.readValue(response.body(), LoginResponse.class);

        Log.infof("Got login response: %s", loginResponse);

        return loginResponse;
    }
}
