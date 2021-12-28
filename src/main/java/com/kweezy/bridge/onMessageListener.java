package com.kweezy.bridge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static com.kweezy.bridge.Utils.connectToServer;

public class onMessageListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMessage(AsyncPlayerChatEvent event) {

        Player p = event.getPlayer();
        String message = event.getMessage();

        if (Bridge.getPlugin(Bridge.class).captcha.get(p).equals(message)) {

            String message1 = "&b&l[Captcha]&r &7Подождите 3 секунды, присоединяем...";
            p.getInventory().clear();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message1));
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
}


