package me.kanga.newserver.EventHandlers;

import me.kanga.newserver.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

public class KickPlayerHandler {

    Main plugin;
    public KickPlayerHandler(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player p = e.getPlayer();


    }
}
