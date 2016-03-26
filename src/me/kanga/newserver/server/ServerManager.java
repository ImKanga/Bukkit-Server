package me.kanga.newserver.server;

import me.kanga.newserver.Main;
import me.kanga.newserver.storage.SQLManager;
import org.bukkit.Bukkit;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dylan on 16/03/2016.
 */
public class ServerManager {

    Main plugin;
    public ServerManager(Main plugin) {
        this.plugin = plugin;
    }

    private static Statement stmt;
    private static ResultSet rs;

    private static int totalplayers;
    private static String servername;
    private static String servermotd;

    public static void createServerTable() {
        try {
            DatabaseMetaData meta = SQLManager.getConnection().getMetaData();
            ResultSet table = meta.getTables(null, null, "Server", null);
            if (SQLManager.getConnected()) {
                if (!table.next()) {
                    stmt = SQLManager.getConnection().createStatement();
                    String sql;
                    sql = "CREATE TABLE Server (ServerName VARCHAR(255), MOTD TEXT, TotalPlayers int)";
                    rs = stmt.executeQuery(sql);
                    rs.close();
                    stmt.close();
                    Bukkit.getLogger().info("Server DB created succesfully!");
                }
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public static int getTotalPlayers(String server) {
        try {
            if (SQLManager.getConnected()) {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT TotalPlayers FROM Server WHERE name = '" + server + "'";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    totalplayers = rs.getInt("TotalPlayers");
                }
                rs.close();
                stmt.close();
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalplayers;
    }

    public static String getServerName(String server) {
        try {
            if (SQLManager.getConnected()) {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT ServerName FROM Server WHERE ServerName = '" + server + "'";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    servername = rs.getString("ServerName");
                }
                rs.close();
                stmt.close();
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servername;
    }

    public static String getServerMOTD(String server) {
        try {
            if (SQLManager.getConnected()) {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT MOTD FROM Server WHERE ServerName = '" + server + "'";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    servermotd = rs.getString("MOTD");
                }
                rs.close();
                stmt.close();
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servermotd;
    }
}
