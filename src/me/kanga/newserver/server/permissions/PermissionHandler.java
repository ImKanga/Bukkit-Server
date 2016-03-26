package me.kanga.newserver.server.permissions;

import me.kanga.newserver.EventHandlers.JoinGameHandler;
import me.kanga.newserver.Main;
import me.kanga.newserver.player.GamePlayer;
import me.kanga.newserver.storage.ConfigManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PermissionHandler {

    Main plugin;
    JoinGameHandler jgh;

    public PermissionHandler(Main plugin) {
        this.plugin = plugin;
        jgh = new JoinGameHandler(plugin);
    }

    private Player p;
    private Boolean hasPermission;
    private Boolean isPermissionActive;
    private ConfigManager cfg;
    // private Statement stmt;
    // private ResultSet rs;

    /* Used for SQL, this was the original idea but after reading through some forums
       I decided to go with flat files for permissions/groups.
    public void createPlayerPermissionsTable() {
        try {
            DatabaseMetaData meta = SQLManager.getConnection().getMetaData();
            ResultSet table = meta.getTables(null, null, "p_permissions", null);
            if (SQLManager.getConnected()) {
                if (!table.next()) {
                    stmt = SQLManager.getConnection().createStatement();
                    String sql;
                    sql = "CREATE TABLE p_permissions (uuid VARCHAR(255))";
                    rs = stmt.executeQuery(sql);
                    rs.close();
                    stmt.close();
                    Bukkit.getLogger().info("PlayerPermissions DB created successfully!");
                }
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }*/
}
