package dev.jaczerob.tedcalm.commands;

import dev.jaczerob.tedcalm.services.LoginService;
import dev.jaczerob.tedcalm.services.login.models.request.AuthenticationRequest;
import dev.jaczerob.tedcalm.services.login.models.request.InitialLoginRequest;
import io.quarkus.logging.Log;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;

import javax.inject.Inject;

@QuarkusMain
@CommandLine.Command(name = "tedcalm", mixinStandardHelpOptions = true, version = "0.1")
public class Commands implements QuarkusApplication {
    @Inject
    LoginService loginService;

    @CommandLine.Command(name = "login", description = "Logs you into Toontown", version = "0.1", mixinStandardHelpOptions = true)
    public void loginCommand(
            @CommandLine.Option(names = {"-u", "--username"}, description = "Username", required = true) final String username,
            @CommandLine.Option(names = {"-p", "--password"}, description = "Password", required = true) final String password
    ) throws Exception {
        final InitialLoginRequest initialLoginRequest = new InitialLoginRequest(username, password);
        loginService.login(initialLoginRequest);
    }

    @CommandLine.Command(name = "authenticate", description = "Authenticates with ToonGuard", version = "0.1", mixinStandardHelpOptions = true)
    public void authenticateCommand(
            @CommandLine.Option(names = {"-t", "--toonGuardToken"}, description = "ToonGuard Token", required = true) final String toonGuardToken,
            @CommandLine.Option(names = {"-r", "--responseToken"}, description = "Response Token", required = true) final String responseToken
    ) throws Exception {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest(toonGuardToken, responseToken);
        loginService.login(authenticationRequest);
    }

    @Override
    public int run(final String... args) {
        try {
            return new CommandLine(this).execute(args);
        } catch (final Exception exc) {
            Log.error(exc);
            return 1;
        }
    }
}
