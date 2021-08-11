/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

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
