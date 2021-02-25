package me.Frxq15.Countdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
    private static Main instance;
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"[Countdown] Enabled plugin version 1.0 by Frxq15 successfully");
        getCommand("setcountdown").setExecutor(new setcountdownCommand());
    }
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA+"[Countdown] Plugin disabled successfully");
    }
    public static Main getInstance() {
        return instance;
    }

    public static String formatMsg(String input) {
        return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString(input));
    }

    public static String colourize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
