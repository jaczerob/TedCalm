package dev.jaczerob.tedcalm.services;

import dev.jaczerob.tedcalm.services.login.models.request.LoginRequest;
import dev.jaczerob.tedcalm.services.login.models.request.QueuedRequest;
import dev.jaczerob.tedcalm.services.login.models.response.LoginResponse;
import dev.jaczerob.tedcalm.services.login.providers.LoginHttpClientProvider;
import io.quarkus.logging.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginService {
    @Inject
    LoginHttpClientProvider loginProvider;

    @Inject
    LauncherService launcherService;

    private String gameserver = "";
    private String cookie = "";

    public void login(final LoginRequest loginRequest) throws Exception {
        boolean isLoggedIn = false;

        LoginResponse loginResponse = this.loginProvider.login(loginRequest);

        while (!isLoggedIn) {
            switch (loginResponse.success()) {
                case FALSE -> this.falseLogin(loginResponse);
                case PARTIAL -> this.partialLogin(loginResponse);
                case DELAYED -> loginResponse = this.requeue(loginResponse);
                case TRUE -> {
                    this.gameserver = loginResponse.gameserver();
                    this.cookie = loginResponse.cookie();
                    isLoggedIn = true;
                }
            }
        }

        Log.infof("Logging you into Toontown! Gameserver: %s, Cookie: %s", this.gameserver, this.cookie);
        launcherService.launch(this.gameserver, this.cookie);
    }

    private LoginResponse requeue(final LoginResponse loginResponse) throws Exception {
        Log.infof("In queue for login, please wait %d seconds...", loginResponse.eta());

        try {
            Thread.sleep(loginResponse.eta() * 1000L);
        } catch (final InterruptedException exc) {
            Log.error("You couldn't wait...", exc);
            System.exit(1);
        }

        return this.loginProvider.login(new QueuedRequest(loginResponse.queueToken()));
    }

    private void partialLogin(final LoginResponse loginResponse) {
        Log.info(loginResponse.banner());
        Log.info("Please use the authenticate command with your ToonGuard code and the response token: " + loginResponse.responseToken());

        System.exit(1);
    }

    private void falseLogin(final LoginResponse loginResponse) {
        Log.info(loginResponse.banner());

        System.exit(1);
    }
}
