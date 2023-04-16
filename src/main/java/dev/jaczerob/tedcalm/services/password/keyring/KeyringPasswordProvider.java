package dev.jaczerob.tedcalm.services.password.keyring;

import dev.jaczerob.tedcalm.services.password.PasswordProvider;
import org.netbeans.api.keyring.Keyring;

import java.util.Optional;

public class KeyringPasswordProvider implements PasswordProvider {
    private static final String SERVICE = "TedCalm";

    public Optional<String> getPassword(final String username) {
        final Optional<char[]> optionalPassword = Optional.ofNullable(Keyring.read(username));
        if (optionalPassword.isEmpty())
            return Optional.empty();

        final char[] password = optionalPassword.orElseThrow();
        return Optional.of(new String(password));
    }

    public void setPassword(final String username, final String password) {
        Keyring.save(username, password.toCharArray(), SERVICE);
    }
}
