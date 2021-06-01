package dev.just.challenge.listeners;

import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (Settings.settings.get(Settings.ItemType.PARCOUR).equals(Settings.ItemState.ENABLED)) {
            return;
        }
        if (Settings.settings.get(Settings.ItemType.DEATH).equals(Settings.ItemState.DISABLED)) {
            return;
        }
        Timer.endChallenge("Der Spieler " + player.getName() + " hat es geschafft, zu sterben!", event.getEntity());
//        TimerCommand.timer_active = false;
//        for (Player players : Bukkit.getOnlinePlayers()) {
//            players.setGameMode(GameMode.SPECTATOR);
//            if (!players.equals(player)) {
//                players.teleport(player);
//            }
//        }
//        String h;
//        if (timer_h < 10) {
//            h = "0" + timer_h.toString();
//        } else {
//            h = timer_h.toString();
//        }
//        String m;
//        if (timer_min < 10) {
//            m = "0" + timer_min.toString();
//        } else {
//            m = timer_min.toString();
//        }
//        String s;
//        if (timer_sec < 10) {
//            s = "0" + timer_sec.toString();
//        } else {
//            s = timer_sec.toString();
//        }
//        Bukkit.broadcastMessage(Main.getPrefix() + "Der Spieler " + player.getName() + " hat es geschafft, zu sterben!");
//        Bukkit.broadcastMessage(Main.getPrefix() + "Damit ist die Challenge beendet!");
//        Bukkit.broadcastMessage(Main.getPrefix() + "Zeit verschwendet: " + h + ":" + m + ":" + s);
//        event.setDeathMessage("");
//        Sound.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_DEACTIVATE);
    }
}
