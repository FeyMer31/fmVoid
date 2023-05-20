package ru.feymer.fmvoid;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class FmVoid extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        getLogger().info("Плагин включен!");
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals(getConfig().getString("settings.world"))) {
            if (player.getLocation().getBlockY() < getConfig().getInt("settings.y")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("settings.command").replace("%player%", player.getName()));
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fmvoid.admin")) {
            sender.sendMessage(getConfig().getString(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-perms"))));
            return true;
        }
        if (args.length == 1) {
            if (args[0].equals("reload")) {
                reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.success-reload")));
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.usage")));
        } else if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.usage")));
        }
        return false;
    }


    @Override
    public void onDisable() {
        getLogger().info("Плагин выключен!");
    }
}
