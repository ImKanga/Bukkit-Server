package me.kanga.newserver.sql;

import me.kanga.newserver.Main;
import org.bukkit.Bukkit;

import java.sql.*;

/**
 * Created by Dylan on 16/03/2016.
 */
public class SQLManager {

    Main plugin;
    public SQLManager(Main plugin) {
        this.plugin = plugin;
    }

    Connection c;
    Boolean connected = false;
    Boolean doesPlayerExist;

    private String url = "jdbc:mysql://localhost:3306/javabase";
    private String user = "root";
    private String password = "";

    public void attemptConnection() {
        Bukkit.getLogger().info("Trying to connect to the database..");
        try (Connection c = DriverManager.getConnection(url, user, password)){
            Bukkit.getLogger().info("Connection Successful!");
            connected = true;
        } catch (SQLException e) {
            Bukkit.getLogger().severe("Cannot connect to the database.");
            throw new IllegalStateException("Cannot connect to the database.", e);
        }
    }

    public void executeQuery(String query) {
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Boolean playerExists(String uuid) {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String query = "SELECT * FROM players WHERE uuid = '" + uuid + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.first() && !rs.next()) {
                doesPlayerExist = false;
            } else {
                doesPlayerExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doesPlayerExist;
    }
    public Boolean isConnected() {
        return connected;
    }
}
