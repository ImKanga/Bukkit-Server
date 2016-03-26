package me.kanga.newserver.player;

import me.kanga.newserver.EventHandlers.JoinGameHandler;
import me.kanga.newserver.EventHandlers.LeaveGameHandler;
import me.kanga.newserver.Main;
import me.kanga.newserver.storage.ConfigManager;
import me.kanga.newserver.storage.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Dylan on 16/03/2016.
 */
public class GamePlayer {

    private Main plugin;
    private static LeaveGameHandler leave;
    private static JoinGameHandler join;

    public GamePlayer(Main plugin) {
        this.plugin = plugin;
        leave = new LeaveGameHandler(plugin);
        join = new JoinGameHandler(plugin);
    }

    private static Player p;
    private static Boolean p_exists;
    private static String group;
    private static Boolean hasPermission;

    private static long currenttime;
    private static long timeplayed;
    private static long lastplayed;
    private static long firstplayed;
    private static Statement stmt;
    private static ResultSet rs;

    // Sets GamePlayer equal to the Bukkit, "Player" class.
    public GamePlayer(Player p) {
        GamePlayer.p = p;
    }

    // Creates a 'players' DB.
    public static void createPlayerTable() {
        try {
            DatabaseMetaData meta = SQLManager.getConnection().getMetaData();
            ResultSet table = meta.getTables(null, null, "Players", null);
            if (SQLManager.getConnected()) {
                if (!table.next()) {
                    stmt = SQLManager.getConnection().createStatement();
                    String sql;
                    sql = "CREATE TABLE Players (uuid VARCHAR(255), user_name VARCHAR(255), display_name VARCHAR(255)" +
                            ", description VARCHAR(255), time_played BIGINT, last_played BIGINT, first_played BIGINT)";
                    rs = stmt.executeQuery(sql);
                    rs.close();
                    stmt.close();
                    Bukkit.getLogger().info("PlayersDB created successfully!");
                }
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void createPlayer(Player p) {
        try {
            if (SQLManager.getConnected()) {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "UPDATE Players uuid='" + p.getUniqueId() + "' " +
                        "user_name='" + p.getName() + "' " +
                        "display_name='" + p.getName() + "' " +
                        "description='Default Description.' " +
                        "time_played='0' " +
                        "last_played='" + 0 + "' " +
                        "first_played='" + System.currentTimeMillis() + "'";
                rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
                Bukkit.getLogger().info("Created player: '" + p.getName() + "' with UUID: '" + p.getUniqueId() + "'!");
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static Boolean doesPlayerExist(Player p) {
        try {
            if (SQLManager.getConnected()) {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT uuid FROM Players WHERE uuid='" + p.getUniqueId().toString() + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    p_exists = true;
                } else {
                    p_exists = false;
                }
                rs.close();
                stmt.close();
            } else {
                SQLManager.attemptConnection();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return p_exists;
    }

    // Set's the players 'displayname'
    public static void setDisplayName(String dname, String uuid) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement..");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "UPDATE players display_name='" + dname + "' WHERE UUID='" + uuid + "'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException se2) {
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
    }

    //PLAYTIME
    //PLAYTIME
    //PLAYTIME
    public static void setTotalPlayTime(Player p) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement...");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "UPDATE players time_played='" + (getTotalPlayTime(p) + (System.currentTimeMillis() - getTotalPlayTime(p))) + "' WHERE UUID='" + p.getUniqueId().toString() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
    }

    public static long getTotalPlayTime(Player p) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement...");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT time_played FROM players WHERE uuid = '" + p.getUniqueId().toString() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    timeplayed = rs.getLong("time_played");
                }
                stmt.close();
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
        return timeplayed;
    }

    public static long getFirstJoinedMillis(Player p) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement...");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT first_join FROM players WHERE uuid = '" + p.getUniqueId().toString() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    timeplayed = rs.getLong("first_join");
                }
                stmt.close();
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
        return firstplayed;
    }

    public static long getLastTimePlayedMillis(Player p) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement...");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "SELECT last_played FROM players WHERE uuid = '" + p.getUniqueId().toString() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    timeplayed = rs.getLong("last_played");
                }
                stmt.close();
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
        return lastplayed;
    }

    public static void setFirstTimePlayedMillis(Player p) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement...");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "UPDATE players last_played='" + leave.getTimeDisconnect().get(p) + "' WHERE uuid = '" + p.getUniqueId().toString() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                stmt.close();
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
    }

    public static void setLastTimePlayedMillis(Player p) {
        if (SQLManager.getConnected()) {
            Bukkit.getLogger().info("Creating statement...");
            try {
                stmt = SQLManager.getConnection().createStatement();
                String sql;
                sql = "UPDATE players first_played='" + join.getTimeJoined().get(p) + "' WHERE uuid = '" + p.getUniqueId().toString() + "'";
                ResultSet rs = stmt.executeQuery(sql);
                stmt.close();
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    se2.printStackTrace();
                }
            }
        } else {
            Bukkit.getLogger().info("DB not connected!");
            SQLManager.attemptConnection();
        }
    }

    // Get the players current group.
    public static String getGroup(Player p) {
        for (String groups : ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false)) {
            for (String users : ConfigManager.getGroups().getConfigurationSection("groups." + groups).getKeys(false)) {
                if (users.equals(p.getUniqueId().toString())) {
                    group = groups;
                }
            }
        }
        return group;
    }

    // Does the user have the specified permission
    public static Boolean hasPermission(Player p, String permission) {
        if (ConfigManager.getPermissions().getConfigurationSection("users." + p.getUniqueId().toString()).getKeys(false).contains(permission)) {
            if (ConfigManager.getGroups().getConfigurationSection("groups." + GamePlayer.getGroup(p) + ".permissions").getKeys(false).contains(permission)) {
                hasPermission = true;
            }
        } else {
            hasPermission = false;
        }
        return hasPermission;
    }

    // Adds a user to a group
    public void addUserToGroup(Player p, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            if (!ConfigManager.getGroups().getConfigurationSection("groups." + group).getKeys(false).contains(p.getUniqueId().toString())) {
                ArrayList<String> users = new ArrayList<String>();
                for (String oldUsers : ConfigManager.getGroups().getStringList("groups." + group + ".users")) {
                    users.add(oldUsers);
                    users.add(p.getUniqueId().toString());
                }
                ConfigManager.getGroups().set("groups." + group + ".users", users);
                users.clear();
                ConfigManager.saveGroupsFile();
            }
        }
    }

    // Removes a user from a group
    public void removeUserFromGroup(Player p, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            if (ConfigManager.getGroups().getConfigurationSection("groups." + group).getKeys(false).contains(p.getUniqueId().toString())) {
                ArrayList<String> users = new ArrayList<String>();
                for (String oldUsers : ConfigManager.getGroups().getStringList("groups." + group + ".users")) {
                    users.add(oldUsers);
                    users.remove(p.getUniqueId().toString());
                }
                ConfigManager.getGroups().set("groups." + group + ".users", users);
                users.clear();
                ConfigManager.saveGroupsFile();
            }
        }
    }

    // Give Permission to player.
    public void givePermission(Player p, String permission) {
        if (!ConfigManager.getPermissions().getConfigurationSection("users." + p.getUniqueId().toString()).contains(permission)) {
            ConfigManager.getPermissions().set("users." + p.getUniqueId(), permission);
            ConfigManager.savePermissionsFile();
        }
    }

    // Remove permission from player.
    public void removePermission(Player p, String permission) {
        if (ConfigManager.getPermissions().getConfigurationSection("users." + p.getUniqueId().toString()).getKeys(false).contains(permission)) {
            ArrayList<String> permissions = new ArrayList<String>();
            for (String perms : ConfigManager.getPermissions().getStringList("users." + p.getUniqueId())) {
                permissions.add(perms);
                permissions.remove(permission);
                ConfigManager.getPermissions().set("users." + p.getUniqueId(), permissions);
                permissions.clear();
            }
            ConfigManager.savePermissionsFile();
        }
    }
}