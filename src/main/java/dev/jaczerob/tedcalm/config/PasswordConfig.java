package dev.jaczerob.tedcalm.config;

import dev.jaczerob.tedcalm.services.password.PasswordProvider;
import dev.jaczerob.tedcalm.services.password.keyring.KeyringPasswordProvider;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class PasswordConfig {
    @Produces
    public PasswordProvider keyringPasswordProvider() {
        return new KeyringPasswordProvider();
    }
}
