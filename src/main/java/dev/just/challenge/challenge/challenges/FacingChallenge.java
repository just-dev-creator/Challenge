package dev.just.challenge.challenge.challenges;

import dev.just.challenge.challenge.AbstractChallenge;
import dev.just.challenge.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FacingChallenge extends AbstractChallenge implements Listener {
    public FacingChallenge() {
        super("FacingChallenge", Material.COMPASS);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (this.isActive()) {
            if (Utils.yawToFace(event.getFrom().getYaw(), false) != Utils.yawToFace(event.getTo().getYaw(), false)) {
                event.getPlayer().damage(1);
            }
        }
    }
}
