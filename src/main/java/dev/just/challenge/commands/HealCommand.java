/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HealCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Dieser Commannd ist nur für Spieler");
                return true;
            }
            Player player = (Player) sender;
            player.setFoodLevel(20);
            player.setHealth(player.getMaxHealth());
            player.sendMessage(Main.getPrefix() + ChatColor.GREEN +  "Du hast dich erfolgreich geheilt!");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("@a")) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GREEN +  "Du hast alle Spieler geheilt.");
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setHealth(players.getMaxHealth());
                    players.setFoodLevel(20);
                    players.sendMessage(Main.getPrefix() + ChatColor.GREEN +  sender.getName() + " hat dich geheilt!");
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                target.setHealth(target.getMaxHealth());
                target.setFoodLevel(20);
                sender.sendMessage(Main.getPrefix() + ChatColor.GREEN +  "Du hast erfolgreich " + target.getName() + " geheilt!");
                target.sendMessage(Main.getPrefix() + ChatColor.GREEN + sender.getName() + " hat dich geheilt!");
            }
        }
        return false;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        tabComplete.add("all");
        for (Player player:Bukkit.getOnlinePlayers()) {
            if (Main.hide.contains(player)) {
                tabComplete.add(player.getName());
            }
        }
        return tabComplete;
    }
}
