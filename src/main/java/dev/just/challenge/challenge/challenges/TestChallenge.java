package dev.just.challenge.challenge.challenges;

import dev.just.challenge.challenge.AbstractChallenge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TestChallenge extends AbstractChallenge implements Listener {
    public TestChallenge() {
        super("Test", Material.PISTON);
    }

    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("Enabled");
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage("Disabled");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!this.isEnabled) return;
        event.getPlayer().sendMessage("Moin!");
    }
}
