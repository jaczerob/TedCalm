package dev.jaczerob.tedcalm.services.password;

import java.util.Optional;

public interface PasswordProvider {
        Optional<String> getPassword(String username);
        void setPassword(String username, String password);
}
