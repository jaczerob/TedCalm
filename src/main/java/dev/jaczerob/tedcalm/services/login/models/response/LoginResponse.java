package dev.jaczerob.tedcalm.services.login.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.jaczerob.tedcalm.services.login.models.LoginSuccess;
import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public record LoginResponse(LoginSuccess success, String banner, String responseToken, String error, int eta,
                            String position, String queueToken, String gameserver, String cookie) {
}
