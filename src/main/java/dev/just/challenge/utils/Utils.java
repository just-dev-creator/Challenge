/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class Utils {
    public static void sendSettingsChange(Player player, String message) {
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all != player)
                all.sendTitle(" ", message, 10, 75, 10);
            all.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§e§lSettings §8» " + message));
        }
    }

    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections)
            return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();

        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }

    private static final BlockFace[] axis = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    private static final BlockFace[] radial = { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST };

    /**
     * @param b Boolean to process
     * @return Returns the German representation of yes/no
     */
    public static String booleanToGermanString(boolean b) {
        if (b) {
            return "ja";
        } else {
            return "nein";
        }
    }

    /**
     * @param b Boolean to process
     * @return Returns the German representation of yes/no
     */
    public static String booleanToUppcercaseGermanString(boolean b) {
        if (b) {
            return "Ja";
        } else {
            return "Nein";
        }
    }

    /**
     * Plays a song for all players online
     * @param sound Sound to play
     */
    public static void broadcastSound(Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 10, 1);
        }
    }

    /**
     * Plays a song for a player online
     * @param sound Sound to play
     * @param player Player to play the sound for
     */
    public static void playSound(Sound sound, Player player) {
        player.playSound(player.getLocation(), sound, 10, 1);
    }
}
