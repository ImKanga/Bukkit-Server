package me.kanga.newserver.server.permissions;

import me.kanga.newserver.Main;
import me.kanga.newserver.storage.ConfigManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GroupsHandler {

    Main plugin;

    public void GroupHandler(Main plugin) {
        this.plugin = plugin;
    }

    private Boolean groupHasPerm;
    private Boolean doesGroupExist;
    private String prefix;
    private String suffix;
    private Boolean defaultGroup;


    // Creates a group
    public void createGroup(String group, String prefix, String suffix, Boolean defaultGroup) {
        if (!ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            ArrayList<String> permissions = new ArrayList<String>();
            ArrayList<String> users = new ArrayList<String>();
            ConfigManager.getGroups().set("groups", group);
            ConfigManager.getGroups().set("groups." + group + ".permissions", permissions);
            ConfigManager.getGroups().set("groups." + group + ".users", users);
            ConfigManager.getGroups().set("groups." + group + ".prefix", prefix);
            ConfigManager.getGroups().set("groups." + group + ".suffix", suffix);
            ConfigManager.getGroups().set("groups." + group + ".default", defaultGroup);
            ConfigManager.saveGroupsFile();
        }
    }

    // Adds a permission to a group
    public void addPermissionToGroup(String permission, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            if (!ConfigManager.getGroups().getConfigurationSection("groups." + group + ".permissions").getKeys(false).contains(permission)) {
                ArrayList<String> permissions = new ArrayList<String>();
                for (String oldPerms : ConfigManager.getGroups().getStringList("groups." + group + ".permissions")) {
                    permissions.add(oldPerms);
                    permissions.add(permission);
                }
                ConfigManager.getGroups().set("groups." + group + ".permissions", permissions);
                permissions.clear();
                ConfigManager.saveGroupsFile();
            }
        }
    }

    // Removes a permission from a group
    public void removePermissionFromGroup(String permission, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            if (ConfigManager.getGroups().getConfigurationSection("groups." + group + ".permissions").getKeys(false).contains(permission)) {
                ArrayList<String> permissions = new ArrayList<String>();
                for (String oldPerms : ConfigManager.getGroups().getStringList("groups." + group + ".permissions")) {
                    permissions.add(oldPerms);
                    permissions.remove(permission);
                }
                ConfigManager.getGroups().set("groups." + group + ".permissions", permissions);
                permissions.clear();
                ConfigManager.saveGroupsFile();
            }
        }
    }

    // Checks if a group has a certain permission
    public Boolean doesGroupHavePerm(String permission, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups." + group + ".permissions").getKeys(false).contains(permission)) {
            groupHasPerm = true;
        } else {
            groupHasPerm = false;
        }
        return groupHasPerm;
    }

    // Checks if a group exists
    public Boolean doesGroupExist(String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            doesGroupExist = true;
        } else {
            doesGroupExist = false;
        }
        return doesGroupExist;
    }

    // Get the specified group's prefix.
    public String getGroupPrefix(String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            prefix = ConfigManager.getGroups().getString("groups." + group + ".prefix");
        }
        return prefix;
    }

    // Get the specified group's suffix.
    public String getGroupSuffix(String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            suffix = ConfigManager.getGroups().getString("groups." + group + ".suffix");
        }
        return suffix;
    }

    // Set the specified group's prefix.
    public void setGroupPrefix(String prefix, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            ConfigManager.getGroups().set("groups." + group + ".prefix", prefix);
            ConfigManager.saveGroupsFile();
        }
    }

    // Set the specified group's suffix.
    public void setGroupSuffix(String suffix, String group) {
        if (ConfigManager.getGroups().getConfigurationSection("groups").getKeys(false).contains(group)) {
            ConfigManager.getGroups().set("groups." + group + ".suffix", suffix);
            ConfigManager.saveGroupsFile();
        }
    }

    // Checks if the group is default
    public Boolean isGroupDefault(String group) {
        if (ConfigManager.getGroups().getBoolean("groups." + group + ".default")) {
            defaultGroup = true;
        } else {
            defaultGroup = false;
        }
        return defaultGroup;
    }
}
