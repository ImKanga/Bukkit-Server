package me.kanga.newserver;

import me.kanga.newserver.sql.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Dylan on 16/03/2016.
 */
public class Main extends JavaPlugin {



    long serverStarted;
    public void onEnable() {
        serverStarted = System.currentTimeMillis();
        SQLManager.attemptConnection();
    }
    public void onDisable() {

    }

    public long getServerStartTime() {
        return serverStarted;
    }
}
