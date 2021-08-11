/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.just.challenge.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PositionCommand implements CommandExecutor, TabCompleter {

    public static HashMap<String, Location> locations = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 1) {

                if(!locations.containsKey(args[0].toLowerCase())) {
                    Location location = player.getLocation();
                    TextComponent component = new TextComponent();
                    component.setText("§2§l[" + args[0].toLowerCase() + "§2§l]");
                    component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/position " + args[0].toLowerCase()));
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        if(player.getName() == all.getName()) {
                            all.spigot().sendMessage(new TextComponent(Main.getCustomPrefix("§aPosition") + "§2Du §8- §a" + args[0].toLowerCase() + " §8[§2" + location.getBlockX() + "§8/§2" + location.getBlockY() + "§8/§2" + location.getBlockZ() + "§8] "), component);
                        } else {
                            all.spigot().sendMessage(new TextComponent(Main.getCustomPrefix("§aPosition") + "§2" + player.getName() + " §8- §a" + args[0].toLowerCase() + " §8[§2" + location.getBlockX() + "§8/§2" + location.getBlockY() + "§8/§2" + location.getBlockZ() + "§8] "), component);
                        }
                    }
                    locations.put(args[0].toLowerCase(), location);
                } else {
                    String id = args[0].toLowerCase();
                    Location location = locations.get(id);
                    player.spigot().sendMessage(new TextComponent(Main.getCustomPrefix("§aPosition") + "§a" + args[0].toLowerCase() + " §8[§2" + location.getBlockX() + "§8/§2" + location.getBlockY() + "§8/§2" + location.getBlockZ() + "§8]"));
                }

            } else {
                String ids;
                if(locations.size() == 0) {
                    ids = "§c§okeine";
                } else {
                    ids = "";
                    for(String id : locations.keySet()) {
                        if(ids.equalsIgnoreCase("")) {
                            ids = ids + "§e" + id;
                        } else {
                            ids = ids + "§8, §e" + id;
                        }
                    }
                }
                player.sendMessage(Main.getErrorPrefix() + "§cVerwendung: §e/position §7(§eID§7)");
                player.sendMessage(Main.getErrorPrefix() + "§aVerf§gbare IDs: §e" + ids);
            }

        } else {
            sender.sendMessage(Main.getErrorPrefix() + "§7Dieser §c§lBefehl §7ist nur §cIngame §7verf§gbar");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();
        for (String entry : locations.keySet()) {
            tabComplete.add(entry);
        }
        return tabComplete;
    }
}
