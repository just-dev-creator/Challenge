/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;


public class ResetCommand implements CommandExecutor {
    public static HashMap<Player, Integer> indentification = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
        sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Du musst ein Spieler sein. ");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            Random random = new Random();
            int indent =(100 + random.nextInt(899));
            indentification.put(player, indent);
            player.sendMessage(Main.getPrefix() + "Bitte benutze zum bestätigen /reset " + indentification.get(player));
            return false;
        }
        if (args.length == 1) {
            int input = Integer.parseInt(args[0]);
            if (!indentification.containsKey(player)) {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "Nutze /reset");
                return false;
            }
            int ident = indentification.get(player);
            if (input == ident) {
                Config.set("reset.isReset", true);
                System.out.println(Config.getBoolean("reset.isReset"));
                Bukkit.broadcastMessage(Main.getCustomPrefix("Reset") + "Ein Reset wurde von " + ChatColor.GREEN + player.getName() + ChatColor.DARK_GRAY + " ausgeführt.");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.kickPlayer(ChatColor.GRAY + "✘ " + ChatColor.BLUE + "JUtils" + ChatColor.GRAY + " ✘ \n" +
                    ChatColor.DARK_GRAY + "Ein Reset wurde von " + ChatColor.BLUE + player.getName() + ChatColor.DARK_GRAY + " ausgeführt. \n" +
                    "Du kannst vermutlich in " + ChatColor.BLUE + "1 Minute" + ChatColor.DARK_GRAY + " wieder connecten. ");
                }
                Bukkit.spigot().restart();
                return false;
            } else {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "Falsche Eingabe.");
            }
        }
        else {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Falsche Eingabe. ");
        }
        return false;
    }
}
