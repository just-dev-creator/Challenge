package dev.just.challenge.challenges;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;


public class NoInvChallenge implements Listener {
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (Settings.settings.get(Settings.ItemType.NOINV).equals(Settings.ItemState.ENABLED) && TimerCommand.timer_active) {
            if(event.getView().getTitle().equals(ChatColor.GOLD + "Einstellungen") || event.getView().getTitle().equals(ChatColor.GREEN + "Challanges") || event.getView().getTitle().equals(ChatColor.YELLOW + "Utilities") || event.getView().getTitle().equals(ChatColor.GREEN + "Parkour-Settings")) {
                return;
            } else if (!Main.hide.contains(event.getPlayer())) {
                return;
            }
            Timer.endChallenge("Der Spieler " + player.getName() + " hat das Inventar " + event.getView().getTitle() + ChatColor.DARK_GRAY + " geöffnet!", (Player) event.getPlayer());
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Challenges")) {
            return;
        } else if (event.getView().getTitle().equals(ChatColor.GOLD + "Einstellungen")) {
            return;
        } else if (event.getView().getTitle().equals(ChatColor.YELLOW + "Utilities")) {
            return;
        } else if (!Main.hide.contains(event.getWhoClicked())) {
            return;
        } else if (event.getView().getTitle().equals(ChatColor.GREEN + "Parkour-Settings")) {
            return;
        }
        else if (Settings.settings.get(Settings.ItemType.NOINV).equals(Settings.ItemState.ENABLED) && TimerCommand.timer_active) {
            Timer.endChallenge("Der Spieler " + event.getWhoClicked().getName() + " hat sein Inventar genutzt!", (Player) event.getWhoClicked());
            event.setCancelled(true);
        }
    }
}
