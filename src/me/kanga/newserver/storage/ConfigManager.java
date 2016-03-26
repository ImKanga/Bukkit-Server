package me.kanga.newserver.storage;

import me.kanga.newserver.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import sun.security.krb5.Config;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static Main plugin;
    public ConfigManager(Main plugin) {
        ConfigManager.plugin = plugin;
    }

    // Variables
    private static File permissionsFolder;
    private static File permissionsFile;
    private static File groupsFile;
    private static YamlConfiguration groups;
    private static YamlConfiguration permissions;

    /* Might be used later on.
    public static void createPermissionsFolder() {
        try {
            permissionsFolder = new File(plugin.getDataFolder() + File.separator + "permissions");
            if (!permissionsFolder.exists()) {
                permissionsFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createGroupsFile() {
        try {
            groupsFile = new File(plugin.getDataFolder() + File.separator + "groups.yml");
            if (!groupsFile.exists()) {
                groupsFile.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createPermissionsFile() {
        try {
            permissionsFile = new File(plugin.getDataFolder() + File.separator + "permissions.yml");
            if (!permissionsFile.exists()) {
                permissionsFile.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    // Check if all files exist, if not, create them :)
    public static void firstRun() {
        if (!permissionsFolder.exists()) {
            permissionsFolder.mkdirs();
        }
        if (!permissionsFile.exists()) {
            permissionsFile.mkdirs();
        }
        if (!groupsFile.exists()) {
            groupsFile.mkdirs();
        }
    }

    // Load all the files in to the YamlConfiguration ready for bukkit use.
    public static void loadYamls() {
        groups = YamlConfiguration.loadConfiguration(groupsFile);
        permissions = YamlConfiguration.loadConfiguration(permissionsFile);
    }

    // Load all files in to the server ready to be transformed in to YamlConfigurations.
    public static void loadFiles() {
        permissionsFolder = new File(plugin.getDataFolder() + File.separator + "permissions");
        permissionsFile = new File(plugin.getDataFolder() + File.separator + "permissions.yml");
        groupsFile = new File(plugin.getDataFolder() + File.separator + "groups.yml");
    }

    // Saves the permissions.yml file.
    public static void savePermissionsFile() {
        try {
            permissions.save(permissionsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saves the groups.yml file.
    public static void saveGroupsFile() {
        try {
            groups.save(groupsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Returns groups.yml
    public static YamlConfiguration getGroups() {
        return groups;
    }
    // Returns permissions.yml
    public static YamlConfiguration getPermissions() {
        return permissions;
    }
    // Returns the permissions file
    public static File getPermissionsFile() {
        return permissionsFile;
    }
    // Returns the groups file
    public static File getGroupsFile() {
        return groupsFile;
    }
}
