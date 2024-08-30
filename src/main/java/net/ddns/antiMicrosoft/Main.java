package main.java.net.ddns.antiMicrosoft;

import arc.Events;
import arc.util.Http;
import arc.util.Log;
import mindustry.game.EventType;
import mindustry.mod.*;

public class Main extends Plugin {
    public final String url = "https://azure-ip-lookup.azurewebsites.net/api/ipAddress?ipOrDomain=104.45.231.79";

    @Override
    public void init() {
        Events.on(EventType.PlayerConnect.class, event -> {
            Log.info(1);
            Http.HttpRequest result = Http.get(url);
            Log.info(result.content);
        });
    }
}
