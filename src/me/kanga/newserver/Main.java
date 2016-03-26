package me.kanga.newserver;

import me.kanga.newserver.storage.ConfigManager;
import me.kanga.newserver.storage.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;
import sun.security.krb5.Config;

/**
 * Created by Dylan on 16/03/2016.
 */
public class Main extends JavaPlugin {

    private long serverStarted;
    public void onEnable() {
        serverStarted = System.currentTimeMillis();
        SQLManager.attemptConnection();
        SQLManager.useDatabase("AusCo");

        try {
            ConfigManager.firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConfigManager.loadFiles();
        ConfigManager.loadYamls();

        ConfigManager.saveGroupsFile();
        ConfigManager.savePermissionsFile();
    }
    public void onDisable() {
        if (SQLManager.getConnected()) {
            SQLManager.closeConnection();
        }
    }

    public long getServerStartTime() {
        return serverStarted;
    }
}
