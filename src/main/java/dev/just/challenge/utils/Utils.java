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

import java.util.Arrays;
import java.util.Collection;

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

    /**
     * @param duration Time in seconds
     * @return The specified time in 24 hour format
     */
    public static String shortInteger(int duration) {
        String string = "";
        int seconds = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        if (duration / 60 / 60 / 24 >= 1) {
            days = duration / 60 / 60 / 24;
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1)
            seconds = duration;
        if (days >= 1)
            if (days == 1) {
                string = days + " Tag";
            } else {
                string = days + " Tage";
            }
        if (hours != 0)
            if (hours <= 9) {
                string = string + "0" + hours + ":";
            } else {
                string = string + hours + ":";
            }
        if (minutes <= 9) {
            string = string + "0" + minutes + ":";
        } else {
            string = string + minutes + ":";
        }
        if (seconds <= 9) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }

    public static String shortString(String[] input, boolean uppercase, boolean removeLetters) {
        String string;
        if (input.length == 1) {
            string = Arrays.toString(input);
        } else if (input.length == 2) {
            string = input[0] + " " + input[1];
        } else if (input.length == 3) {
            string = input[0] + " " + input[1] + " " + input[2];
        } else if (input.length == 4) {
            string = input[0] + " " + input[1] + " " + input[2] + " " + input[3];
        } else if (input.length == 5) {
            string = input[0] + " " + input[1] + " " + input[2] + " " + input[3] + input[4];
        } else if (input.length >= 6) {
            string = "String too long";
        } else {
            string = null;
        }
        if (uppercase) {
            assert string != null;
            string = string.toUpperCase();
        }
        if(removeLetters) {
            assert string != null;
            string = string.replace("[", "");
            string = string.replace("]", "");
        }
        return string;
    }

    public static Collection<? extends Player> getActivePlayers() {
        return Bukkit.getOnlinePlayers();
    }
}
