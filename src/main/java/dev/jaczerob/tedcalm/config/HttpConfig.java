package dev.jaczerob.tedcalm.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import java.net.http.HttpClient;

@Dependent
public class HttpConfig {
    // workaround for image heap build issue
    @Produces
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Produces
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
