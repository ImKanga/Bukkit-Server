package me.kanga.newserver.player;

import me.kanga.newserver.Main;
import me.kanga.newserver.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;

/**
 * Created by Dylan on 16/03/2016.
 */
public class GamePlayer {

    Main plugin;
    SQLManager sql;
    FirstJoinManager join;
    LeaveGameManager leave;
    public GamePlayer(Main plugin) {
        this.plugin = plugin;
        sql = new SQLManager(plugin);
        join = new FirstJoinManager(plugin);
        leave = new LeaveGameManager(plugin);
    }

    long totalGameTime = 0;
    Boolean doesPlayerExist;
    Statement stmt;

    public void setTotalGameTime(Player p) {
        if (playerExists(p.getUniqueId().toString())) {
            if (sql.isConnected()) {
                String query = "INSERT INTO players(playtime) VALUES(" + getTotalGameTime(p) + (leave.getTimeDisconnect() - join.getTimeJoined()) + ")";
                sql.executeQuery(query);
            }
        } else {
            createPlayer(p);
        }
    }
    public long getTotalGameTime(Player p) {
        if (sql.isConnected()) {
            if (!playerExists(p.getUniqueId().toString())) {
                String query = "SELECT playtime FROM players WHERE uuid = '" + p.getUniqueId() + "'";
                sql.executeQuery(query);
                try {
                    if (!sql.getResultSet().next()) {
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return sql.getResultSet().getLong("playtime");
    }
    public Boolean playerExists(String uuid) {
        try {
            stmt = sql.getConnection().createStatement();
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

    public void createPlayer(Player p) {
        if (sql.isConnected()) {
            if (!playerExists(p.getUniqueId().toString())) {
                String query = "INSERT INTO players(uuid, username, playtime) VALUES(" + p.getUniqueId() + ", " + p.getName() + ", " + System.currentTimeMillis() + ")";
                sql.executeQuery(query);
            }
        }
    }
}
