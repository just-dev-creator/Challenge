/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import dev.just.challenge.ChallengeAPI;
import dev.just.challenge.Main;
import dev.just.challenge.challenge.AbstractForceChallenge;
import dev.just.challenge.commands.TimerCommand;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static dev.just.challenge.commands.TimerCommand.*;


public class Timer {
    public static void timer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin, new Runnable() {
            @Override
            public void run() {
                if (timer_active) {
                    timer_sec++;
                    if (timer_sec.equals(60)) {
                        timer_sec = 0;
                        timer_min++;
                    }
                    if (timer_min.equals(60)) {
                        timer_min = 0;
                        timer_h++;
                    }
                    String h;
                    if (timer_h < 10) {
                        h = "0" + timer_h.toString();
                    } else {
                        h = timer_h.toString();
                    }
                    String m;
                    if (timer_min < 10) {
                        m = "0" + timer_min.toString();
                    } else {
                        m = timer_min.toString();
                    }
                    String s;
                    if (timer_sec < 10) {
                        s = "0" + timer_sec.toString();
                    } else {
                        s = timer_sec.toString();
                    }
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (players.isFlying() && players.getGameMode().equals(GameMode.SURVIVAL) && !Settings.uuids.contains(players.getUniqueId().toString())) {
                            players.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 1, true, false));
                            players.setFlying(false);
                            players.setAllowFlight(false);
                        } else if (!players.getAllowFlight() && Settings.uuids.contains(players.getUniqueId().toString())) {
                            players.setFlying(true);
                            players.setAllowFlight(true);
                        } else if (players.getAllowFlight() && players.getGameMode().equals(GameMode.SURVIVAL) && !Settings.uuids.contains(players.getUniqueId().toString())) {
                            players.setFlying(false);
                            players.setAllowFlight(false);
                        }
                        if (h.equals(null) || m.equals(null) || s.equals(null)){
                            players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Es ist ein Fehler aufgetreten! [Timer - 0]"));
                            return;
                        }
                        int sec = ((timer_h * 60 * 60) + (timer_min * 60) + timer_sec);
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_AQUA + ShortInteger.run(sec)));
                    }
                }
                if (!timer_active) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.AQUA + "Timer pausiert!"));
                        if (!players.getAllowFlight()) {
                            players.setAllowFlight(true);
                            players.setFlying(true);
                        }
                    }
                }
            }
        }, 20, 20);
    }
    public static void endChallenge(String cause, Player loser) {
        timer_active = false;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setGameMode(GameMode.SPECTATOR);
                    if (!players.equals(loser)) {
                        players.teleport(loser);
                    }
                }
            }
        }.runTask(Main.getPlugin(Main.class));
        sendEndMessage(cause);
    }
    public static void endChallenge(String cause) {
        timer_active = false;
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setGameMode(GameMode.SPECTATOR);
                }
            }
        }.runTask(Main.getPlugin(Main.class));
        sendEndMessage(cause);
    }
    private static void sendEndMessage(String cause) {
        String h;
        if (timer_h < 10) {
            h = "0" + timer_h.toString();
        } else {
            h = timer_h.toString();
        }
        String m;
        if (timer_min < 10) {
            m = "0" + timer_min.toString();
        } else {
            m = timer_min.toString();
        }
        String s;
        if (timer_sec < 10) {
            s = "0" + timer_sec.toString();
        } else {
            s = timer_sec.toString();
        }
        Bukkit.broadcastMessage(Main.getPrefix() + cause);
        Bukkit.broadcastMessage(Main.getPrefix() + "Damit ist die Challenge beendet!");
        Bukkit.broadcastMessage(Main.getPrefix() + "Zeit verschwendet: " + h + ":" + m + ":" + s);
        Sound.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_DEACTIVATE);
    }
    public static void finishChallenge(String cause) {
        TimerCommand.timer_active = false;
        String h;
        if (timer_h < 10) {
            h = "0" + timer_h.toString();
        } else {
            h = timer_h.toString();
        }
        String m;
        if (timer_min < 10) {
            m = "0" + timer_min.toString();
        } else {
            m = timer_min.toString();
        }
        String s;
        if (timer_sec < 10) {
            s = "0" + timer_sec.toString();
        } else {
            s = timer_sec.toString();
        }
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + cause);
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + "Damit ist die Challenge beendet!");
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + "Zeit benötigt: " + h + ":" + m + ":" + s);
    }
    public static void finishChallengeCausedOfPlayer(String cause, Player player) {
        TimerCommand.timer_active = false;
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setGameMode(GameMode.SPECTATOR);
            if (!players.equals(player)) {
                players.teleport(player);
            }
        }
        String h;
        if (timer_h < 10) {
            h = "0" + timer_h.toString();
        } else {
            h = timer_h.toString();
        }
        String m;
        if (timer_min < 10) {
            m = "0" + timer_min.toString();
        } else {
            m = timer_min.toString();
        }
        String s;
        if (timer_sec < 10) {
            s = "0" + timer_sec.toString();
        } else {
            s = timer_sec.toString();
        }
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + player.getName() + ChatColor.GREEN + " hat es geschafft");
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + cause);
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + "Damit ist die Challenge beendet!");
        Bukkit.broadcastMessage(Main.getPrefix() + ChatColor.GREEN + "Zeit benötigt: " + h + ":" + m + ":" + s);
    }

    public static boolean isRunning() {
        return timer_active;
    }

    public static void sendTimerUpdate() {
        ChallengeAPI.challenges.forEach(abstractChallenge -> {
            if (abstractChallenge instanceof AbstractForceChallenge) {
                ((AbstractForceChallenge) abstractChallenge).onTimerUpdate();
            }
        });
    }
}
