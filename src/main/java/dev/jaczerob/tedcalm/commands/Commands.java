package dev.jaczerob.tedcalm.commands;

import dev.jaczerob.tedcalm.services.LoginService;
import dev.jaczerob.tedcalm.services.login.models.request.AuthenticationRequest;
import dev.jaczerob.tedcalm.services.login.models.request.InitialLoginRequest;
import dev.jaczerob.tedcalm.services.password.PasswordProvider;
import io.quarkus.logging.Log;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.util.Optional;

@QuarkusMain
@Command(name = "tedcalm", mixinStandardHelpOptions = true, version = "0.1")
public class Commands implements QuarkusApplication {
    @Inject
    LoginService loginService;

    @Inject
    PasswordProvider passwordProvider;

    @Command(name = "login", description = "Logs you into Toontown", version = "0.1", mixinStandardHelpOptions = true)
    public void loginCommand(
            @Option(names = {"-u", "--username"}, description = "Username", required = true) final String username,
            @Option(names = {"-p", "--password"}, description = "Password") String password
    ) throws Exception {
        if (password == null || password.isEmpty()) {
            final Optional<String> optionalPassword = passwordProvider.getPassword(username);
            if (optionalPassword.isEmpty()) {
                Log.errorf("No password saved for user: %s", username);
                System.exit(1);
            }

            password = optionalPassword.orElseThrow();
        }

        final InitialLoginRequest initialLoginRequest = new InitialLoginRequest(username, password);
        loginService.login(initialLoginRequest);
    }

    @Command(name = "authenticate", description = "Authenticates with ToonGuard", version = "0.1", mixinStandardHelpOptions = true)
    public void authenticateCommand(
            @Option(names = {"-t", "--toonGuardToken"}, description = "ToonGuard Token", required = true) final String toonGuardToken,
            @Option(names = {"-r", "--responseToken"}, description = "Response Token", required = true) final String responseToken
    ) throws Exception {
        final AuthenticationRequest authenticationRequest = new AuthenticationRequest(toonGuardToken, responseToken);
        loginService.login(authenticationRequest);
    }

    @Command(name = "save", description = "Saves your password", version = "0.1", mixinStandardHelpOptions = true)
    public void saveCommand(
            @Option(names = {"-u", "--username"}, description = "Username", required = true) final String username,
            @Option(names = {"-p", "--password"}, description = "Password") final String password
    ) {
        passwordProvider.setPassword(username, password);
        Log.infof("Password saved for user: %s", username);
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
