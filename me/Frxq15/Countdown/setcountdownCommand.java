package me.Frxq15.Countdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class setcountdownCommand implements CommandExecutor {
    private BukkitTask bukkitTask;
    private BossBar bossBar;
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;

        if(!p.hasPermission("countdown.startcountdown")) {
            p.sendMessage(Main.formatMsg("NO_PERMISSION"));
            return true;
        }

        if(strings.length == 1) {
            Integer time = Integer.parseInt(strings[0]);
            if(time <= 1) {
                p.sendMessage(Main.formatMsg("INVALID_TIME"));
                return true;
            }
            p.sendMessage(Main.formatMsg("COUNTDOWN_STARTED").replace("%time%", time+""));
            runTimer(time);
            return true;
        }
//usage
        return true;
    }
    public static String getFinalArg(String[] args, int start) {
        StringBuilder bldr = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start) {
                bldr.append(" ");
            }
            bldr.append(args[i]);
        }
        return bldr.toString();
    }
    public void runTimer(Integer time) {
        final int[] count = {time};
        BarColor color = BarColor.valueOf(Main.getInstance().getConfig().getString("COLOR").toUpperCase());
        BarStyle style = BarStyle.valueOf(Main.getInstance().getConfig().getString("STYLE").toUpperCase());
        bossBar = Bukkit.createBossBar(Main.formatMsg("BOSSBAR_MSG").replace("%time%", count[0]+""),
                color, style);
        float subtract = 1F / count[0];
        bossBar.setProgress(1);

        List<String> worlds = new ArrayList<String>(Main.getInstance().getConfig().getStringList("ENABLED_WORLDS"));

        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (count[0] == 0) {
                    // Starting
                    bossBar.removeAll();
                    bossBar = Bukkit.createBossBar(Main.formatMsg("BOSSBAR_ENDED"), color, style);
                    bossBar.setProgress(0);
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (worlds.contains(all.getWorld().getName())) {
                            bossBar.addPlayer(all);

                            for(String commands : Main.getInstance().getConfig().getStringList("END_COMMANDS")) {
                                commands = commands.replace("%player%", all.getName());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands);
                            }

                            //commands
                        }
                    }
                    cancel(); // Cancels the timer

                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            bossBar.removeAll();
                        }
                    }, 20L * 5);


                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (worlds.contains(all.getWorld().getName())) {

                        }
                    }
                } else {
                    //counting down
                    bossBar.setProgress(bossBar.getProgress() - subtract);
                    bossBar.setTitle(Main.formatMsg("BOSSBAR_MSG").replace("%time%", count[0]+""));
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (worlds.contains(all.getWorld().getName())) {
                                bossBar.addPlayer(all);
                        }
                    }
                    count[0]--;
                }
            }
        }.runTaskTimer(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Countdown")), 20L, 20L);
        }
    }
