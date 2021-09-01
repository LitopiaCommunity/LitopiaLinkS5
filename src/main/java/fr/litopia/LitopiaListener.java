package fr.litopia;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashMap;

public class LitopiaListener implements Listener {

    private HashMap<Player, LitoStat> playerStatistics = new HashMap<Player, LitoStat>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LitopiaLink] "+ event.getPlayer().getName() + " has joined the Server. Updating Statistics." );
        playerStatistics.put(p, new LitoStat(p));
        playerStatistics.get(p).exportInNewFile();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player p = event.getPlayer();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LitopiaLink] "+ event.getPlayer().getName() + " has left the Server. Updating Statistics." );
        playerStatistics.get(p).update();
        playerStatistics.get(p).exportInNewFile();
    }
    
}