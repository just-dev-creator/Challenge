package dev.just.challenge.beta;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BetaInformation {
    public static void sendInformation() {
        Bukkit.getLogger().warning(Main.getCustomPrefix("Beta") + "Öffne " + ChatColor.GREEN + "/events" + ChatColor.DARK_GRAY + " für die Einstellungsmöglichkeiten. ");
        Bukkit.getLogger().warning(Main.getCustomPrefix("Beta") + "Beachte " + ChatColor.GREEN + "/beta" + ChatColor.DARK_GRAY + " für Informationen zur Beta und zur Mithilfe beim Plugin \n (Du erhälst dafür Boni)");
    }
}
