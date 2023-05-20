package ru.feymer.fmvoid;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class FmVoid extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Плагин включен!");
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals(getConfig().getString("world"))) {
            if (player.getLocation().getBlockY()<getConfig().getInt("y")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("command").replace("%player%", player.getName()));
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Плагин выключен!");
    }
}
