package dev.just.challenge.challenges;

import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OneLookChallenge implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
            if (event.getFrom().getYaw() != event.getTo().getYaw() && Timer.isRunning()) {
                if (Settings.levelsettings.get(Settings.ItemType.ONELOOK).equals(Settings.ItemLevel.TWO)) {
                    event.setCancelled(true);
                    Timer.endChallenge(event.getPlayer().getName() + "hat seinen Blickwinkel geändert! ", event.getPlayer());
                } else if (Settings.levelsettings.get(Settings.ItemType.ONELOOK).equals(Settings.ItemLevel.ONE)) {
                    event.setCancelled(true);
                }
        }
    }
}