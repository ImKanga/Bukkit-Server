package me.kanga.newserver.player;

import me.kanga.newserver.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Dylan on 17/03/2016.
 */
public class FirstJoinManager implements Listener {

    Main plugin;
    GamePlayer gamePlayer;
    public FirstJoinManager(Main plugin) {
        this.plugin = plugin;
        gamePlayer = new GamePlayer(plugin);
    }

    private long timeJoined;
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        this.timeJoined = System.currentTimeMillis();
    }

    public long getTimeJoined() {
        return this.timeJoined;
    }
}
