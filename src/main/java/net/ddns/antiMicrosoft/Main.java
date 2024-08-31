package main.java.net.ddns.antiMicrosoft;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.mod.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main extends Plugin {
    // Kick duration in minutes
    Integer kickDurationMinutes = 120;

    @Override
    public void init() {
        Events.on(EventType.PlayerConnect.class, event -> {
            try {
                URL url = new URL(String.format("https://www.azurespeed.com/api/ipAddress?ipOrDomain=%s",
                        event.player.ip()));
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                if (bufferedReader.readLine().length() <= 2) {
                    return;
                }
                event.player.kick("Using Azure servers to connect to the mindustry.ddns.net network is not" +
                        " permitted. If you believe that this was a mistake, then please contact [#ff5a00]bndlett[] or" +
                        " a similar staff member (i.e. [#ff3f00]xasmedy[] or [#d7342a]chefe[]) via our Discord server.");

            } catch (IOException e) {
                Log.warn(String.format("An IOException has occurred: %s", e.getMessage()));
                Log.warn("Source: antiMicrosoft");
            }
        });
    }
}
