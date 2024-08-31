package main.java.net.ddns.antiMicrosoft;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.mod.*;
import mindustry.net.Administration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@SuppressWarnings("unused")
public class Main extends Plugin {
    // Kick duration in minutes
    public static Administration.Config kickDurationMinutes;

    @Override
    public void init() {
        kickDurationMinutes = new Administration.Config("antiAzureKickDuration", "The kick duration (in minutes) for a" +
                " player that is using an Azure IP.", "1440");

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
                event.player.kick("Using Azure servers to connect to this server is not permitted. If you believe" +
                        " that this was a mistake, then please contact a staff member of this server.",
                        (long) Integer.parseInt((String) kickDurationMinutes.get()) * 60 * 1000);

            } catch (IOException e) {
                Log.warn(String.format("An IOException has occurred: %s", e.getMessage()));
                Log.warn("Source: antiMicrosoft");
            }
        });
    }
}
