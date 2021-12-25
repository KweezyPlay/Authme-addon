package com.kweezy.bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class onMessageListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMessage(AsyncPlayerChatEvent event) {

        Player p = event.getPlayer();
        String message = event.getMessage();

        if (Bridge.getPlugin(Bridge.class).captcha.get(p).equals(message)) {

            String message1 = "&b&l[Captcha]&r &7Подождите 3 секунды, присоединяем...";
            p.getInventory().clear();
            p.sendMessage((String) ChatColor.translateAlternateColorCodes('&', message1));
            Bridge.getPlugin(Bridge.class).captcha.remove(p);
            Bukkit.getScheduler().runTaskLater(Bridge.getPlugin(Bridge.class), new Runnable() {
                @Override
                public void run() {
                    connectToServer(p, "main");
                }
            }, 60L);
        }
        else {
            Bukkit.getScheduler().runTask(Bridge.getPlugin(Bridge.class), new Runnable() {
                public void run() {
                    p.kickPlayer("[Captcha] Wrong number!");
                }
            });

        }

        event.setCancelled(true);
    }

    private void connectToServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {

                out.writeUTF("Connect");
                out.writeUTF(server);
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.sendPluginMessage(Bridge.getPlugin(Bridge.class), "BungeeCord", b.toByteArray());
        } catch (org.bukkit.plugin.messaging.ChannelNotRegisteredException e) {
            Bukkit.getLogger().warning(" ERROR - Usage of bungeecord connect effects is not possible. Your server is not having bungeecord support (Bungeecord channel is not registered in your minecraft server)!");
        }
    }
}


