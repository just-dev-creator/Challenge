/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            event.setFormat(ChatColor.GRAY + player.getDisplayName() + " " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + event.getMessage());
        } else if (player.isOp()) {
            event.setFormat(ChatColor.GREEN + player.getDisplayName() + " " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + event.getMessage());
        } else {
            event.setFormat(ChatColor.BLUE + player.getDisplayName() + " " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + event.getMessage());
        }
    }
}
