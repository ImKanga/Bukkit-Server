package me.kanga.newserver.player;

import me.kanga.newserver.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Dylan on 19/03/2016.
 */
public class LeaveGameManager implements Listener {

    Main plugin;
    public LeaveGameManager(Main plugin) {
        this.plugin = plugin;
    }

    private long timeDisconnect;
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        this.timeDisconnect = System.currentTimeMillis();
    }

    public long getTimeDisconnect() {
        return timeDisconnect;
    }
}
