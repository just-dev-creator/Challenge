/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Sound {
    public static void broadcastSound(org.bukkit.Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 10, 1);
        }
    }
    public static void playSound(org.bukkit.Sound sound, Player player) {
        player.playSound(player.getLocation(), sound, 10, 1);
    }
}
