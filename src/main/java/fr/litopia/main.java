package fr.litopia;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;


public class main extends JavaPlugin {

    // Instantiating
    @Override
    public void onLoad(){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[LitopiaLink] is loading !");
    }

    @Override
    public void onEnable(){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[LitopiaLink] is Enable !");
        // Event Listener
        getServer().getPluginManager().registerEvents(new LitopiaListener(), this);
    }

    @Override
    public void onDisable(){
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_BLUE + "[LitopiaLink] is Disable !");
    }

}
