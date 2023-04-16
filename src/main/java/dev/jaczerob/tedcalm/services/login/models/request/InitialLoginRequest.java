package dev.jaczerob.tedcalm.services.login.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public record InitialLoginRequest(String username, String password) implements LoginRequest {
}
