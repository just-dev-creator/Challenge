package dev.just.challenge.challenges;

import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FacingChallenge implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Timer.isRunning() && Settings.settings.get(Settings.ItemType.FACING).equals(Settings.ItemState.ENABLED)) {
            if (Utils.yawToFace(event.getFrom().getYaw(), false) != Utils.yawToFace(event.getTo().getYaw(), false)) {
                event.getPlayer().damage(1);
            }
        }
    }
}
