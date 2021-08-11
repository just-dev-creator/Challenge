/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utilities;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MathChallenge implements Listener {
    public static HashMap<Player, Integer> solve = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!Config.get().contains("math." + player.getUniqueId())) {
            Config.set("math." + player.getUniqueId(), true);
            Config.save();
        }
        if (Config.get().getBoolean("math." + player.getUniqueId())) {
            Random random = new Random();
            int z1 = random.nextInt(20);
            int z2 = random.nextInt(40);
            int lösung = z1 * z2;
            player.sendMessage(Main.getPrefix() + "Zum nutzen des Plugins gib die Lösung folgender Aufgabe an: \n" + z1 + "*" + z2);
            solve.put(player, lösung);
        }
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!solve.containsKey(player)) {
            if (message.equalsIgnoreCase("freiheit")) {
                event.setCancelled(true);
                Config.get().set("math." + player.getUniqueId(), false);
                Config.save();
                player.sendMessage(ChatColor.GREEN + "Du bist nun frei. ");
            }
            return;
        }
        event.setCancelled(true);
        int lösung = solve.get(player);
        int input;
        try {
            input = Integer.parseInt(message);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Du musst eine Zahl angeben!");
            return;
        }
        if (lösung != input) {
            player.sendMessage(ChatColor.RED + "Dein Ergebnis ist falsch.");
        } else {
            solve.remove(player);
            player.sendMessage(ChatColor.GREEN + "Deine Antwort ist richtig. Damit bist du frei.");
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (solve.containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
