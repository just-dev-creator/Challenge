package dev.just.challenge.listeners;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + event.getPlayer().getName());
        if (Settings.settings.get(Settings.ItemType.DORFSPAWN).equals(Settings.ItemState.ENABLED) && !Timer.isRunning()) {
            event.getPlayer().teleport(Main.dorfSpawn);
        }
    }
}
