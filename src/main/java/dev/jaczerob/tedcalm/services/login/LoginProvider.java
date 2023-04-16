package dev.jaczerob.tedcalm.services.login;

import dev.jaczerob.tedcalm.services.login.models.request.LoginRequest;
import dev.jaczerob.tedcalm.services.login.models.response.LoginResponse;

public interface LoginProvider {
    LoginResponse login(LoginRequest loginRequest) throws Exception;
}
