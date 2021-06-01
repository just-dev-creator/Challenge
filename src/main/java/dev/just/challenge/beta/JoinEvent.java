package dev.just.challenge.beta;

import dev.just.challenge.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(Main.getCustomPrefix("Beta") + "Öffne " + ChatColor.GREEN + "/events" + ChatColor.DARK_GRAY + " für die Einstellungsmöglichkeiten. ");
        player.sendMessage(Main.getCustomPrefix("Beta") + "Beachte " + ChatColor.GREEN + "/beta" + ChatColor.DARK_GRAY + " für Informationen zur Beta und zur Mithilfe beim Plugin \n (Du erhälst dafür Boni)");
    }
}
