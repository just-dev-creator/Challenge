/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.swing.text.PlainDocument;
import java.util.ArrayList;
import java.util.List;

public class NickCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "Dein Nickname ist nun CONSOLE.");
        }
        Player player = (Player) sender;
        if (Settings.uuids.contains(player.getUniqueId().toString())) {
            if (args.length == 1 && !args[0].equalsIgnoreCase("reset")) {
                player.setDisplayName(args[0]);
                player.setCustomName(args[0]);
                player.setPlayerListName(args[0]);
                player.sendMessage(Main.getPrefix() + "Dein neuer Nickname lautet nun " + args[0] + ".");
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
                player.setDisplayName(player.getName());
                player.setCustomName(player.getName());
                player.setPlayerListName(player.getName());
                player.sendMessage(Main.getPrefix() + "Du hast deinen Nicknamen erfolgreich zurückgesetzt");
            } else {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "Nutze /nick <Name>");
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (sender instanceof Player && Settings.uuids.contains(((Player) sender).getUniqueId().toString())) {
            tabComplete.add("reset");
            return tabComplete;
        } else {
            return null;
        }
    }
}
