package dev.just.challenge.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastMessage {
    public static void userSideYou(Player player, ChatColor playerColor, ChatColor messageColor, String part1, String part2, boolean uppercase) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.equals(player)) {
                String form = "du";
                if (uppercase) {
                    form = "Du";
                }
                all.sendMessage(messageColor + part1 + playerColor + form + messageColor + part2);
            } else {
                all.sendMessage(messageColor + part1 + playerColor + player.getName() + messageColor + part2);
            }
        }
    }
    public static void normal(Player player, ChatColor playerColor, ChatColor messageColor, String part1, String part2, boolean uppercase) {
        if (Settings.settings.get(Settings.ItemType.USERSIDEYOU).equals(Settings.ItemState.ENABLED)) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.equals(player)) {
                    String form = "du";
                    if (uppercase) {
                        form = "Du";
                    }
                    all.sendMessage(messageColor + part1 + playerColor + form + messageColor + part2);
                } else {
                    all.sendMessage(messageColor + part1 + playerColor + player.getName() + messageColor + part2);
                }
            }
        } else {
            Bukkit.broadcastMessage(messageColor + part1 + playerColor + player.getName() + messageColor + part2);
        }
    }
}
