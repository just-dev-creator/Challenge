package dev.just.challenge.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + event.getPlayer().getName());
    }
}
