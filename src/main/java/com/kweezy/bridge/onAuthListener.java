package com.kweezy.bridge;

import fr.xephi.authme.events.LoginEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import static com.kweezy.bridge.Utils.connectToServer;

public class onAuthListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAuth(LoginEvent event) {
        Player p = event.getPlayer();

        ItemStack i = new ItemStack(Material.MAP, 1);

        MapView map = Bukkit.createMap(p.getWorld());
        for(MapRenderer renderer : map.getRenderers())
            map.removeRenderer(renderer);
        Random rng = new Random();

        String code = Integer.toString(getRandomNumberUsingNextInt(100, 1000));
        Bridge.getPlugin(Bridge.class).captcha.put(p, code);

        String message = "&b&l[Captcha]&r &7Введите число с картинки";
        p.sendMessage("===========================");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL("https://rasphost.com/test-contact/captcha/image.php?code=" + code));
            map.addRenderer(new Renderer(image));

            i.setDurability(map.getId());
            p.getInventory().setHeldItemSlot(0);
            p.getInventory().setItem(0, i);

            Location plocation = p.getLocation();
            plocation.setPitch(90);
            p.teleport(plocation);

        } catch (IOException e) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[Captcha]&r &cВнутренняя ошибка, сообщите администратору!"));
            Bukkit.getScheduler().runTaskLater(Bridge.getPlugin(Bridge.class), new Runnable() {
                @Override
                public void run() {
                    connectToServer(p, "main");
                }
            }, 60L);
            //p.kickPlayer("System exception. Try again later.");
        }

    }
    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
