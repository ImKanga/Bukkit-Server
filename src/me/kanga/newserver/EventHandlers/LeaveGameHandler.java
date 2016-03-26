package me.kanga.newserver.EventHandlers;

import me.kanga.newserver.Main;
import me.kanga.newserver.server.permissions.PermissionHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;

public class LeaveGameHandler implements Listener {

    Main plugin;
    PermissionHandler ph;
    public LeaveGameHandler(Main plugin) {
        this.plugin = plugin;
        ph = new PermissionHandler(plugin);
    }

    private HashMap<String, Long> timeDisconnect = new HashMap<String, Long>();
    private PermissionAttachment pa = ph.getPermissionAttachment();

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        // Sets 'timeDisconnect' to the current time.
        timeDisconnect.put(p.getUniqueId().toString(), System.currentTimeMillis());

        // Remove the player's attachment that we set when the player joins.
        p.removeAttachment(pa);
    }

    public HashMap<String, Long> getTimeDisconnect() {
        return timeDisconnect;
    }
}
