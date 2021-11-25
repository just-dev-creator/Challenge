/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        GameMode mode = GameMode.SURVIVAL;
        String name = "Survival";

        if(!sender.isOp() && !(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(Main.getErrorPrefix() + "Du hast keine Berechtigung, diesen Befehl zu verwenden.");
            return true;
        }

        if(args.length >= 1) {

            if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                mode = GameMode.SURVIVAL;
                name = "Survival";
            } else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                mode = GameMode.CREATIVE;
                name = "Creative";
            } else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                mode = GameMode.ADVENTURE;
                name = "Adventure";
            } else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spec") || args[0].equalsIgnoreCase("spectator")) {
                mode = GameMode.SPECTATOR;
                name = "Spectator";
            }  else {
                sender.sendMessage(Main.getErrorPrefix() + "Verwendung: /gamemode (0, 1, 2, 3, survival, creative, adventure, spectator) [User]");
                return true;
            }

        } else {
            sender.sendMessage(Main.getErrorPrefix() + "Verwendung: /gamemode (0, 1, 2, 3, survival, creative, adventure, spectator) [User]");
            return true;
        }

        if(args.length == 2) {

            if(args[1].equalsIgnoreCase("@a")) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    all.setGameMode(mode);
                    all.sendMessage(Main.getCustomPrefix("Gamemode") + "Dein Spielmodus wurde zu " + name + " geändert");
                    sender.sendMessage(Main.getCustomPrefix(("Gamemode") + "Der Spielmodus von " + all.getName() + " wurde zu " + name + " geändert"));
                }
            } else if(Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
                Bukkit.getPlayer(args[1]).setGameMode(mode);
                Bukkit.getPlayer(args[1]).sendMessage(Main.getCustomPrefix(("Gamemode") + "Dein Spielmodus wurde zu " + name + " geändert"));
                sender.sendMessage(Main.getCustomPrefix(("Gamemode") + "Der Spielmodus von " + Bukkit.getPlayer(args[1]).getName() + " wurde zu " + name + " geändert"));
            } else {
                sender.sendMessage(Main.getErrorPrefix() + "Dieser Spieler ist nicht online.");
            }

        } else {
            if(sender instanceof Player) {
                ((Player) sender).setGameMode(mode);
                sender.sendMessage(Main.getCustomPrefix("Gamemode") + "Dein Spielmodus wurde zu " + name + " geändert");
            } else {
                sender.sendMessage(Main.getErrorPrefix() + "Dein Spielmodus kann nicht geändert werden.");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.isOp() && !(sender instanceof ConsoleCommandSender))
            return null;

        if(args.length == 1) {
            if(args[0].toLowerCase().equalsIgnoreCase("")) {
                List<String> tabComplete = new ArrayList<String>();
                tabComplete.add("0");
                tabComplete.add("1");
                tabComplete.add("2");
                tabComplete.add("3");
                tabComplete.add("survival");
                tabComplete.add("creative");
                tabComplete.add("adventure");
                tabComplete.add("spectator");
                return tabComplete;
            } else if(args[0].toLowerCase().equalsIgnoreCase("s")) {
                List<String> tabComplete = new ArrayList<String>();
                tabComplete.add("survival");
                tabComplete.add("spectator");
                return tabComplete;
            } else if(args[0].toLowerCase().startsWith("a")) {
                List<String> tabComplete = new ArrayList<String>();
                tabComplete.add("adventure");
                return tabComplete;
            } else if(args[0].toLowerCase().startsWith("c")) {
                List<String> tabComplete = new ArrayList<String>();
                tabComplete.add("creative");
                return tabComplete;
            } else if(args[0].toLowerCase().startsWith("su")) {
                List<String> tabComplete = new ArrayList<String>();
                tabComplete.add("survival");
                return tabComplete;
            } else if(args[0].toLowerCase().startsWith("sp")) {
                List<String> tabComplete = new ArrayList<String>();
                tabComplete.add("spectator");
                return tabComplete;
            } else {
                return null;
            }
        } else if(args.length == 2) {
            List<String> tabComplete = new ArrayList<String>();
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(!player.getUniqueId().toString().equalsIgnoreCase("b301b324-8ab6-49af-8018-726dab83d615")) {
                    tabComplete.add(player.getDisplayName());
                }
            }
            return tabComplete;
        }

        return null;
    }

}
