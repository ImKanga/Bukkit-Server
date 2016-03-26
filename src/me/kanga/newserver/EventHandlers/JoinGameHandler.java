package me.kanga.newserver.EventHandlers;

import me.kanga.newserver.Main;
import me.kanga.newserver.player.GamePlayer;
import me.kanga.newserver.server.ServerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class JoinGameHandler implements Listener {

    Main plugin;
    public JoinGameHandler (Main plugin) {
        this.plugin = plugin;
    }


    private HashMap<String, Long> timeJoined = new HashMap<String, Long>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();


        // Sets 'timejoined' to the current time.
        timeJoined.put(p.getUniqueId().toString(), System.currentTimeMillis());

        // Sends MOTD to player.
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', ServerManager.getServerMOTD("Main")));

        // If player isn't in the database, create player.
        if (!GamePlayer.doesPlayerExist(p)) {
            GamePlayer.createPlayer(p);
        }
    }

    public HashMap<String, Long> getTimeJoined() {
        return timeJoined;
    }

}
