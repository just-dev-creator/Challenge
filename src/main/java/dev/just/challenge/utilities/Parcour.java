/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utilities;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.ScoreboardManager;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Sound;
import dev.just.challenge.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class Parcour implements Listener {
    public static HashMap<Player, World> world = new HashMap<>();
    public static HashMap<Player, Integer> x = new HashMap<>();
    public static HashMap<Player, Integer> y = new HashMap<>();
    public static HashMap<Player, Integer> z = new HashMap<>();
    public static HashMap<Player, Integer> checkpoints = new HashMap<>();
//    private ArrayList<Player> safe = new ArrayList<>();
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!TimerCommand.timer_active) {
            return;
        }
//        if (safe.contains(player)) {
//            return;
//        }
        int p_x = player.getLocation().getBlockX();
        int p_y = player.getLocation().getBlockY();
        int p_z = player.getLocation().getBlockZ();
        if (world.containsKey(player) && x.containsKey(player) && y.containsKey(player) && z.containsKey(player)) {
            if (world.get(player).equals(player.getWorld()) && x.get(player).equals(p_x) && y.get(player).equals(p_y) && z.get(player).equals(p_z)) {
                return;
            }
        }
        if (Settings.settings.get(Settings.ItemType.PARCOUR).equals(Settings.ItemState.ENABLED)) {
            if (player.getLocation().getBlock().getRelative(BlockFace.SELF).getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                player.sendMessage(Main.getCustomPrefix("Parkour") + "Checkpoint erreicht!");
                if (checkpoints.containsKey(player)) {
                    checkpoints.put(player, checkpoints.get(player)+1);
                } else {
                    checkpoints.put(player, 1);
                }
                x.put(player, player.getLocation().getBlockX());
                y.put(player, player.getLocation().getBlockY());
                z.put(player, player.getLocation().getBlockZ());
                world.put(player, player.getWorld());
                ScoreboardManager.checkpoints();
                Sound.playSound(org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, player);
            } else if (player.getLocation().getBlock().getRelative(BlockFace.SELF).getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
                player.sendMessage(Main.getCustomPrefix("Parkour") + "Ziel erreicht!");
                Sound.playSound(org.bukkit.Sound.ENTITY_ENDER_DRAGON_DEATH, player);
                Timer.finishChallengeCausedOfPlayer("Er hat den Parkour geschafft!", player);
            }
            if (Settings.settings.get(Settings.ItemType.PARCOUR_LAVA).equals(Settings.ItemState.ENABLED)) {
                if (player.getLocation().getBlock().getRelative(BlockFace.SELF).getType().equals(Material.LAVA)) {
//             || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.LAVA) && ParkourInventory.lavaKill
                    if (!world.containsKey(player)) {
                        player.damage(40);
                        player.sendMessage(Main.getCustomPrefix("Parkour") + "Du darfst Lava nicht berühren!");
                    } else {
                        player.teleport(new Location(world.get(player), x.get(player), y.get(player), z.get(player)));
                        player.sendMessage(Main.getCustomPrefix("Parkour") + "Du darfst Lava nicht berühren!");
                        player.setFireTicks(1);
                        player.setHealth(player.getMaxHealth());
                        player.setFoodLevel(20);
                    }
                }
            }
            if (Settings.settings.get(Settings.ItemType.PARCOUR_WATER).equals(Settings.ItemState.ENABLED)) {
                if (player.getLocation().getBlock().getRelative(BlockFace.SELF).getType().equals(Material.WATER)) {
//             || player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WATER) && ParkourInventory.lavaKill
                    if (!world.containsKey(player)) {
                        player.damage(40);
                        player.sendMessage(Main.getCustomPrefix("Parkour") + "Du darfst Wasser nicht berühren!");
                    }
                    else {
                        player.teleport(new Location(world.get(player), x.get(player), y.get(player), z.get(player)));
                        player.sendMessage(Main.getCustomPrefix("Parkour") + "Du darfst Wasser nicht berühren!");
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (Settings.settings.get(Settings.ItemType.PARCOUR).equals(Settings.ItemState.ENABLED)) {
            event.setDeathMessage("");
            Bukkit.getScheduler().runTaskLater(Main.getPlugin, new Runnable() {
                @Override
                public void run() {
                    event.getEntity().spigot().respawn();
                }
            }, 1);
//            Bukkit.getScheduler().runTaskLater(Main.getPlugin, new Runnable() {
//                @Override
//                public void run() {
//                    Player player = event.getEntity();
//                    event.getEntity().teleport(new Location(world.get(player), x.get(player), y.get(player), z.get(player)));
//                }
//            }, 5);
        }
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (Settings.settings.get(Settings.ItemType.PARCOUR).equals(Settings.ItemState.ENABLED))
        if (!world.containsKey(event.getPlayer())) {
            event.getPlayer().sendMessage(Main.getCustomPrefix("Parkour") + "Du hast noch keinen Checkpoint aktiviert und wurdest daher getötet.");
        }
        else {
            Player player = event.getPlayer();
            event.setRespawnLocation(new Location(world.get(player), x.get(player), y.get(player), z.get(player)));
            Bukkit.getConsoleSender().sendMessage(event.getRespawnLocation().toString());
        }
    }
}
