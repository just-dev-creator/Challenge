/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenges;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.ShortInteger;
import dev.just.challenge.utils.ShortString;
import dev.just.challenge.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ForceBiome implements Listener {
    public static String biomName;
    BossBar bossBar;
    int startTime;
    int time;
    public static Biome biome;
    public static ForceState phase;
    Biome upcoming;
    ArrayList<Biome> biomes;
    Random random = new Random();

    public ForceBiome() {
        biome = null;
        this.upcoming = null;
        this.time = (random.nextInt(600) + 600);
        phase = ForceState.LOADING;
        run();
    }

    public enum ForceState {
        LOADING,
        GENERATED,
        SET
    }

    public enum ForceEnd {
        TRUE,
        FALSE,
        NONE
    }

    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin, new Runnable() {
            @Override
            public void run() {
                if (Settings.settings.get(Settings.ItemType.FORCEBIOME).equals(Settings.ItemState.ENABLED)) {
                    ForceBiome.this.updateBossbar();
                    if (TimerCommand.timer_active) {
                        if (phase == ForceState.LOADING) {
                            Bukkit.broadcastMessage(Main.getCustomPrefix("ForceBiome") + "Erste Aufgabe in frühestens " + ShortInteger.run(600));
                            ForceBiome.this.generateUpcoming();
                        }
                        if (ForceBiome.this.time == 0) {
                            if (ForceBiome.this.upcoming != null) {
                                boolean succes = true;
                                Player failed = null;
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (Main.hide.contains(player) && player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ()) != biome) {
                                        succes = false;
                                        failed = player;
                                    }
                                }
                                if (succes) {
                                    biome = null;
                                    biomName = null;
                                    ForceBiome.this.upcoming = null;
                                    ForceBiome.this.time = (random.nextInt(600) + 600);
                                    ForceBiome.this.generateUpcoming();
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceBiome") + ChatColor.DARK_GREEN + "Es haben alle Spieler die Aufgabe korrekt beantwortet. Nächste Aufgabe in frühestens " + ShortInteger.run(600) + ".");
                                } else {
                                    Timer.endChallenge(ChatColor.RED + failed.getName() + " war nicht im richtigen Biom.", failed);
                                }
                            } else {
                                ForceBiome.this.setNewBlock();
                            }
                        }
                        ForceBiome.this.time--;
                    }
                } else {
                    ForceBiome.this.removeBossbar();
                }
            }
        }, 20L, 20L);
    }

    public void generateUpcoming() {
        if (this.upcoming == null) {
            this.biomes = new ArrayList<>();
            byte b1;
            int j;
            Biome[] arrayofBiome;
            for (j = (arrayofBiome = Biome.values()).length, b1 = 0; b1 < j; ) {
                Biome biom = arrayofBiome[b1];
                if (biom != Biome.TALL_BIRCH_FOREST && biom != Biome.TALL_BIRCH_HILLS && biom != Biome.END_BARRENS && biom != Biome.END_HIGHLANDS && biom != Biome.END_MIDLANDS)
                    biomes.add(Biome.valueOf(biom.toString()));
                b1++;
            }
            biome = (this.biomes.get(random.nextInt(this.biomes.size())));
            phase = ForceState.GENERATED;
            this.biomes.remove(upcoming);
            String biomeNameBefore = biome.getKey().getKey();
            String[] biomeNameSplit = biomeNameBefore.split("_");
            biomName = ShortString.run(biomeNameSplit, true, true);
        }
    }

    public void setNewBlock() {
        this.time = (random.nextInt(900) + 420);
        phase = ForceState.SET;
        this.startTime = this.time;
        this.upcoming = biome;
        Bukkit.broadcastMessage(Main.getCustomPrefix("ForceBiome") + ChatColor.DARK_GRAY + "NEUE ANWEISUNG! " + ChatColor.GRAY + "Gehe in folgendes Biom: " + ChatColor.GREEN + biomName);
    }

    public void updateBossbar() {
        if (this.bossBar == null) {
            this.bossBar = Bukkit.createBossBar(ChatColor.GOLD + "Loading...", BarColor.WHITE, BarStyle.SOLID);
        }
        if (!TimerCommand.timer_active) {
            this.bossBar.setTitle(ChatColor.GOLD + "Der Timer ist " + "pausiert");
            this.bossBar.setColor(BarColor.RED);
            this.bossBar.setProgress(1.0D);
        } else if (this.upcoming == null) {
            this.bossBar.setTitle(ChatColor.GOLD + "Warten auf neue Anweisung");
            this.bossBar.setColor(BarColor.WHITE);
            this.bossBar.setProgress(1.0D);
        } else {
            this.bossBar.setTitle(ChatColor.DARK_GREEN + "Nächstes Biom: " + ChatColor.RED + biomName + ChatColor.DARK_GRAY + " | " + ShortInteger.run(this.time));
            this.bossBar.setProgress(Double.valueOf((this.time * 100 / this.startTime) * 0.01D).doubleValue());
            if (this.bossBar.getProgress() > 0.66D) {
                this.bossBar.setColor(BarColor.GREEN);
            } else if (this.bossBar.getProgress() > 0.33D) {
                this.bossBar.setColor(BarColor.YELLOW);
            } else {
                this.bossBar.setColor(BarColor.RED);
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (this.bossBar.getTitle().equals(ChatColor.DARK_GREEN + "Nächstes Biom: " + ChatColor.RED + biomName + ChatColor.DARK_GRAY + " | " + ShortInteger.run(this.time)) &&
                    all.getLocation().getWorld().getBiome(all.getLocation().getBlockX(), all.getLocation().getBlockY(), all.getLocation().getBlockZ()).equals(biome)) {
                this.bossBar.setTitle(ChatColor.DARK_GREEN + "Nächstes Biom: " + ChatColor.GREEN + biomName + ChatColor.DARK_GRAY + " | " + ShortInteger.run(this.time));
            }
            this.bossBar.addPlayer(all);
        }
    }

    public void removeBossbar() {
        try {
            for (Player all : Bukkit.getOnlinePlayers()) {
                this.bossBar.removePlayer(all);
                this.bossBar = null;
            }
        } catch (Exception e) {

        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!TimerCommand.timer_active || Settings.settings.get(Settings.ItemType.FORCEBIOME).equals(Settings.ItemState.DISABLED)) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("!force set")) {
            this.time = 2;
            event.getPlayer().sendMessage(Main.getCustomPrefix("ForceBiome") + "Die Zeit wurde auf 2 gesetzt.");
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force time")) {
            player.sendMessage(Main.getCustomPrefix("ForceBiome") + "Die Zeit ist: " + ShortInteger.run(this.time));
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force upcoming")) {
            player.sendMessage(Main.getCustomPrefix("ForceBiome") + "Das nächste Biom ist: " + ChatColor.GREEN + biome.toString());
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force state")) {
            player.sendMessage(Main.getCustomPrefix("ForceBiome") + "Der aktuelle Status ist: " + phase.toString());
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force bar")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix("ForceBiome") + "Die Bossbar wird neu generiert.");
            this.updateBossbar();
        } else if (event.getMessage().equalsIgnoreCase("!force debug")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix("ForceBiome") + this.biomes.toString());
        }
    }
}

 /** && biome != Biome.BADLANDS && biom != Biome.BADLANDS_PLATEAU && biom != Biome.BAMBOO_JUNGLE && biom != Biome.BAMBOO_JUNGLE_HILLS &&
         biom != Biome.BEACH && biom != Biome.BIRCH_FOREST && biom != Biome.BIRCH_FOREST_HILLS && biom != Biome.COLD_OCEAN && biom != Biome.DARK_FOREST && biom != Biome.DARK_FOREST_HILLS && biom != Biome.DEEP_COLD_OCEAN
         && biom != Biome.DEEP_FROZEN_OCEAN  && biom != Biome.DEEP_LUKEWARM_OCEAN && biom != Biome.DEEP_OCEAN && biom != Biome.DEEP_WARM_OCEAN && biom != Biome.DESERT && biom != Biome.DESERT_HILLS && biom != Biome.DESERT_LAKES &&
         biom != Biome.END_BARRENS && biom != Biome.END_HIGHLANDS && biom != Biome.END_MIDLANDS  && biom != Biome.ERODED_BADLANDS  && biom != Biome.FLOWER_FOREST && biom != Biome.FOREST && biom != Biome.FROZEN_OCEAN && biom != Biome.FROZEN_RIVER
         && biom != Biome.GIANT_SPRUCE_TAIGA && biom != Biome.GIANT_SPRUCE_TAIGA_HILLS && biom != Biome.GIANT_TREE_TAIGA && biom != Biome.GIANT_TREE_TAIGA_HILLS && biom != Biome.GRAVELLY_MOUNTAINS && biom != Biome.ICE_SPIKES && biom != Biome.JUNGLE
         && biom != Biome.JUNGLE_EDGE && biom != Biome.JUNGLE_HILLS && biom != Biome.LUKEWARM_OCEAN && biom != Biome.MODIFIED_BADLANDS_PLATEAU && biom != Biome.MODIFIED_GRAVELLY_MOUNTAINS && biom != Biome.MODIFIED_JUNGLE
         && biom != Biome.MODIFIED_JUNGLE_EDGE && biom != Biome.MODIFIED_WOODED_BADLANDS_PLATEAU && biom != Biome.MOUNTAIN_EDGE  && biom != Biome.MOUNTAINS && biom != Biome.MUSHROOM_FIELD_SHORE && biom != Biome.MUSHROOM_FIELDS
         && biom != Biome.NETHER && biom != Biome.OCEAN && biom != Biome.PLAINS && biom != Biome.RIVER  && biom != Biome.SAVANNA && biom != Biome.SAVANNA_PLATEAU && biom != Biome.SHATTERED_SAVANNA && biom != Biome.SHATTERED_SAVANNA_PLATEAU
         && biom != Biome.SMALL_END_ISLANDS && biom != Biome.SNOWY_BEACH && biom != Biome.SNOWY_MOUNTAINS && biom != Biome.SNOWY_TAIGA && biom != Biome.SNOWY_TAIGA_HILLS && biom != Biome.SNOWY_TAIGA_MOUNTAINS && biom != Biome.SNOWY_TUNDRA
         && biom != Biome.STONE_SHORE && biom != Biome.SUNFLOWER_PLAINS && biom != Biome.SWAMP && biom != Biome.SWAMP_HILLS  && biom != Biome.TAIGA && biom != Biome.TAIGA_HILLS && biom != Biome.TAIGA_MOUNTAINS && biom != Biome.TALL_BIRCH_FOREST
         && biom != Biome.THE_END && biom != Biome.THE_VOID  && biom != Biome.WARM_OCEAN  && biom != Biome.WOODED_BADLANDS_PLATEAU  && biom != Biome.WOODED_HILLS && biom != Biome.WOODED_MOUNTAINS **/