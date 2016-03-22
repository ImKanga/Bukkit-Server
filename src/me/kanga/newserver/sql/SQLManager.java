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

    private static Connection c;
    private static Boolean connected = false;
    private static ResultSet rs;
    private static Statement stmt;

    private static String url = "jdbc:mysql://localhost/phpmyadmin";
    private static String user = "root";
    private static String password = "";

    public static void attemptConnection() {
        if (!isConnected()) {
            Bukkit.getLogger().info("Trying to connect to the database..");
            try (Connection c = DriverManager.getConnection(url, user, password)) {
                Bukkit.getLogger().info("Connection Successful!");
                connected = true;
            } catch (SQLException e) {
                Bukkit.getLogger().severe("Cannot connect to the database.");
                throw new IllegalStateException("Cannot connect to the database.", e);
            }
        }
    }

    public static void closeConnection() {
        if (isConnected()) {
            Bukkit.getLogger().info("Closing connection..");
            try {
                rs.close();
                stmt.close();
                c.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeQuery(String query) {
        try {
            Statement stmt = c.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Boolean isConnected() {
        return connected;
    }

    public static Connection getConnection() {
        return c;
    }

    public static ResultSet getResultSet() {
        return rs;
    }

    public static void execute() {
        Connection conn = null;
        Statement stmt = null;
        String DB_URL = "jdbc:mysql://localhost/EMP";
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
    }
}
