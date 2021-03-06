/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge;

import dev.just.challenge.challenge.inventory.InventoryCommand;
import dev.just.challenge.challenges.*;
import dev.just.challenge.commands.*;
import dev.just.challenge.giveop.ChatListener;
import dev.just.challenge.inventorys.ChallengeInventory;
import dev.just.challenge.inventorys.HubInventory;
import dev.just.challenge.inventorys.ParkourInventory;
import dev.just.challenge.inventorys.UtilsInventory;
import dev.just.challenge.listeners.*;
import dev.just.challenge.utilities.MathChallenge;
import dev.just.challenge.utilities.Parcour;
import dev.just.challenge.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {
    public static ArrayList<Player> hide = new ArrayList<>();
    public static Location dorfSpawn;

    @Override
    public void onLoad() {
        Config.setup();
        Reset.reset();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        TimerCommand.timer_active = false;
        Settings.settings.clear();
        registerEvents();
        registerCommands();
        Settings.setSettings();
        if (Settings.settings.get(Settings.ItemType.PARCOUR).equals(Settings.ItemState.ENABLED)) {
            ScoreboardManager.checkpoints();
        }
        loadTimer();
        findVillage();
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                Timer.timer();
                ForceGleichungen.main();
            }
        }, 10);
        GithubTests.stopServer();
        ChallengeAPI.onLoad();
    }

    private void findVillage() {
        if (!Settings.settings.get(Settings.ItemType.DORFSPAWN).equals(Settings.ItemState.ENABLED)) return;
        dorfSpawn = Bukkit.getWorld("world")
                .locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(),
                        StructureType.VILLAGE, 10000, true);
        for (int i = 50; i < 256; i++) {
            if (Bukkit.getWorld("world").getBlockAt(dorfSpawn.getBlockX(), i + 1, dorfSpawn.getBlockY()).getType().isAir()) {
                if (Bukkit.getWorld("world").getBlockAt(dorfSpawn.getBlockX(), i + 2, dorfSpawn.getBlockY()).getType().isAir()) {
                    dorfSpawn.setY(i);
                    break;
                }
            }
        }
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.GOLD + "JUtils" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY;
    }
    public static String getErrorPrefix() {
        return "??8??? ??4ERROR ??8?? ??7";
    }
    public static String getCustomPrefix(String customName) {
        return ChatColor.GREEN + customName + ChatColor.GRAY + " ?? " + ChatColor.DARK_GRAY;
    }
    public static String getNoPlayer() {
        return getErrorPrefix() + "Du musst f??r diese Aktion ein Spieler sein. ";
    }
    public static String getNoPermission() {
        return getErrorPrefix() + "Du hast nicht die n??tigen Berechtigungen. ";
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveTimer();
    }

    private void registerCommands() {
        getCommand("events").setExecutor(new EventsCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("spec").setExecutor(new SpecCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("test").setExecutor(new TestCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("dimension").setExecutor(new DimensionCommand());
        getCommand("position").setExecutor(new PositionCommand());
        getCommand("oldevents").setExecutor(new InventoryCommand());

        getCommand("events").setTabCompleter(new EventsCommand());
        getCommand("heal").setTabCompleter(new HealCommand());
        getCommand("timer").setTabCompleter(new TimerCommand());
        getCommand("test").setTabCompleter(new TestCommand());
        getCommand("gamemode").setTabCompleter(new GamemodeCommand());
        getCommand("gm").setTabCompleter(new GamemodeCommand());
        getCommand("dimension").setTabCompleter(new DimensionCommand());
        getCommand("nick").setTabCompleter(new NickCommand());
        getCommand("spec").setTabCompleter(new SpecCommand());
        getCommand("position").setTabCompleter(new PositionCommand());
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new ChallengeInventory(), this);
        pluginManager.registerEvents(new HubInventory(), this);
        pluginManager.registerEvents(new UtilsInventory(), this);
        pluginManager.registerEvents(new NoInvChallenge(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ParkourInventory(), this);
        pluginManager.registerEvents(new Parcour(), this);
        pluginManager.registerEvents(new MathChallenge(), this);
        pluginManager.registerEvents(new dev.just.challenge.challenges.MathChallenge(), this);
        pluginManager.registerEvents(new ForceBiome(), this);
        pluginManager.registerEvents(new OneLookChallenge(), this);
        pluginManager.registerEvents(new FacingChallenge(), this);
        pluginManager.registerEvents(new ForceEffectChallenge(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new ForceGleichungen(), this);
        pluginManager.registerEvents(new PingListener(), this);
        pluginManager.registerEvents(new dev.just.challenge.listeners.ChatListener(), this);
        pluginManager.registerEvents(new NoRemoveChallenge(), this);
        pluginManager.registerEvents(new SpecCommand(), this);
        pluginManager.registerEvents(new dev.just.challenge.challenge.inventory.ChallengeInventory(), this);
    }

    private void loadTimer() {
        if (Config.contains("timer.m")) {
            TimerCommand.timer_min = Config.get().getInt("timer.m");
        } if (Config.contains("timer.s")) {
            TimerCommand.timer_sec = Config.get().getInt("timer.s");
        } if (Config.contains("timer.h")) {
            TimerCommand.timer_h = Config.get().getInt("timer.h");
        }
    }
    private void saveTimer() {
        Config.set("timer.s", TimerCommand.timer_sec);
        Config.set("timer.m", TimerCommand.timer_min);
        Config.set("timer.h", TimerCommand.timer_h);
    }
}