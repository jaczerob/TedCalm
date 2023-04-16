package dev.jaczerob.tedcalm.services;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;

@Singleton
public class LauncherService {
    @ConfigProperty(name = "toontown.launcher.path")
    String toontownPathString;

    public void launch(final String gameserver, final String cookie) {
        final Path toontownPath = Path.of(toontownPathString);
        if (!toontownPath.toFile().exists()) {
            Log.errorf("Toontown path does not exist: %s", toontownPathString);
            System.exit(1);
        }

        final ProcessBuilder processBuilder = new ProcessBuilder()
                .directory(toontownPath.getParent().toFile())
                .command(toontownPathString);

        processBuilder.environment().put("TTR_GAMESERVER", gameserver);
        processBuilder.environment().put("TTR_PLAYCOOKIE", cookie);

        Log.infof("Launching Toontown");
        Log.debugf("Directory: %s, gameserver: %s, cookie: %s", toontownPath.getParent(), gameserver, cookie);

        try {
            processBuilder.start();
            System.exit(0);
        } catch (final IOException exc) {
            Log.error("Failed to launch Toontown", exc);
            System.exit(1);
        } finally {
            Log.info("Goodbye");
        }
    }
}
