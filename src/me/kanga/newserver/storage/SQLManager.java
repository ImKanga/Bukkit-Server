package me.kanga.newserver.storage;

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

    private static Connection c;
    private static Boolean connected = false;

    private static final String url = "jdbc:mysql://localhost/phpmyadmin";
    private static final String user = "root";
    private static final String password = "";

    public static void attemptConnection() {
        if (!getConnected()) {
            Bukkit.getLogger().info("Trying to connect to the database..");
            try {
                c = DriverManager.getConnection(url, user, password);
                Bukkit.getLogger().info("Connection Successful!");
                setConnected(true);
            } catch (SQLException e) {
                Bukkit.getLogger().severe("Cannot connect to the database.");
                throw new IllegalStateException("Cannot connect to the database.", e);
            }
        } else {
            Bukkit.getLogger().info("Already connected to the database!");
        }
    }

    public static void useDatabase(String database) {
        Statement stmt;
        ResultSet rs;
        if (!getConnected()) {
            try {
                stmt = c.createStatement();
                String sql;
                sql = "USE " + database;
                rs = stmt.executeQuery(sql);
                rs.close();
                stmt.close();
                Bukkit.getLogger().info("Now using the " + database + " database!");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else {
            attemptConnection();
        }
    }

    public static void closeConnection() {
        if (getConnected()) {
            Bukkit.getLogger().info("Closing connection..");
            try {
                /* Statement & ResultSet are closed after each method:
                e.g. getPlayerName(), getPlayerUUID */
                c.close();
                setConnected(false);
                Bukkit.getLogger().info("Closed connection!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        return c;
    }

    public static Boolean getConnected() {
        return connected;
    }

    public static void setConnected(Boolean connected) {
        SQLManager.connected = connected;
    }

    /* EXAMPLE
       EXAMPLE
       EXAMPLE
    public static void execute() {
        Connection conn = null;
        Statement stmt = null;
        String DB_URL = "jdbc:mysql://localhost/phpmyadmin";
        String USER = "root";
        String PASS = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Bukkit.getLogger().info("Connecting to database..");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement..");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, first, last, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
            stmt.close();
            conn.close();
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
    }*/
}
