/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DimensionCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getErrorPrefix() + "Du musst ein Spieler sein!");
            return false;
        } else if (!(args.length == 1)) {
            sender.sendMessage(Main.getErrorPrefix() + "Benutze /dimension <overworld/nether/end>");
        } else if (args[0].equalsIgnoreCase("overworld")) {
            ((Player) sender).teleport(Bukkit.getWorld("world").getSpawnLocation());
        } else if (args[0].equalsIgnoreCase("nether")) {
            ((Player) sender).teleport(Bukkit.getWorld("world_nether").getSpawnLocation());
        } else if (args[0].equalsIgnoreCase("end")) {
            ((Player) sender).teleport(Bukkit.getWorld("world_the_end").getSpawnLocation());
        } else {
            sender.sendMessage(Main.getErrorPrefix() + "Benutze /dimension <overworld/nether/end>");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args[0].equalsIgnoreCase("")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("overworld");
            tabComplete.add("nether");
            tabComplete.add("end");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("n")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("nether");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("o")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("overworld");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("e")) {
        List<String> tabComplete = new ArrayList<>();
        tabComplete.add("end" + "");
        return tabComplete;
        }
        return null;
    }
}
