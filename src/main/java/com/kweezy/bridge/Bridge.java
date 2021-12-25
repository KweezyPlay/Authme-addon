package com.kweezy.bridge;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Bridge extends JavaPlugin {
    private static Bridge instance;
    public Map<Player, String> captcha = new HashMap<>();
    @Override
    public void onEnable() {
        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(new onAuthListener(), this);
        getServer().getPluginManager().registerEvents(new onMessageListener(), this);
        getServer().getPluginManager().registerEvents(new onQuitListener(), this);
    }

    public static Bridge getPlugin(Class<Bridge> bridgeClass){
        return instance;
    }
}
