package fr.litopia;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    @Override
    public void onLoad(){
        System.out.println("[LitopiaLink] is loading !");
    }

    @Override
    public void onEnable(){
        System.out.println("[LitopiaLink] is Enable !");
    }

    @Override
    public void onDisable(){
        System.out.println("[LitopiaLink] is Disable !");
    }
}
