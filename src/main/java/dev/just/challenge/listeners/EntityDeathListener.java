package dev.just.challenge.listeners;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class EntityDeathListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            if (Settings.settings.get(Settings.ItemType.DRAGON).equals(Settings.ItemState.ENABLED)) {
                Timer.finishChallenge("Der Enderdrache ist gestorben!");
            }
        }
    }
}
