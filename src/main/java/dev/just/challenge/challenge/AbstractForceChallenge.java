/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Timer;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Helper to create "force challenges" easily
 */
abstract public class AbstractForceChallenge extends AbstractChallenge implements Listener {

    private BossBar bossBar;
    public Object upcoming;
    private Object next;
    private int time;
    private int startTime;
    private ForceState state = ForceState.LOADING;
    private final Random random = new Random();
    private boolean isTaskStarted = false;


    /**
     * @return The time that at least needs to pass before the first task is given
     */
    public abstract int firstTaskMinTime();
    /**
     * @return @return The time that at maximum needs to pass before the first task is given
     */
    public abstract int firstTaskMaxTime();

    /**
     * @return The time that at least needs to pass before the next task is given
     */
    public abstract int midTaskMinTime();
    /**
     * @return @return The time that at maximum needs to pass before the next task is given
     */
    public abstract int midTaskMaxTime();

    /**
     * @return The time that at least needs to pass before the task needs to be finished
     */
    public abstract int finishTaskMinTime();
    /**
     * @return @return The time that at maximum needs to pass before the task needs to be finished
     */
    public abstract int finishTaskMaxTime();

    /**
     * @return All possible things that could be given as a task
     */
    public abstract ArrayList allPosibilities();

    public AbstractForceChallenge(String name) {
        super(name);
    }

    public AbstractForceChallenge(String name, ItemStack icon) {
        super(name, icon);
    }

    public AbstractForceChallenge(String name, Material icon) {
        super(name, icon);
    }

    @Override
    public void enable() {
        super.enable();
        run();
    }

    private void updateBossBar() {
        if (this.bossBar == null) {
            this.bossBar = Bukkit.createBossBar(ChatColor.GOLD + "Loading...", BarColor.WHITE, BarStyle.SOLID);
        }
        if (!this.isActive()) {
            this.bossBar.setTitle(ChatColor.GOLD + "Der Timer ist " + "pausiert");
            this.bossBar.setColor(BarColor.RED);
            this.bossBar.setProgress(1.0D);
        } else if (this.upcoming == null) {
            this.bossBar.setTitle(ChatColor.GOLD + "Warten auf neue Anweisung");
            this.bossBar.setColor(BarColor.WHITE);
            this.bossBar.setProgress(1.0D);
        } else {
            this.bossBar.setTitle(getTitle(this.upcoming) + ChatColor.DARK_GRAY + " | " + Utils.shortInteger(this.time));
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
            this.bossBar.addPlayer(all);
        }
    }

    private void removeBossbar() {
        if (this.bossBar == null) return;
        this.bossBar.removeAll();
        this.bossBar = null;
    }

    /**
     * @param upcoming The thing that will be shown next time
     * @return The text that will be displayed before the time in the bossbar
     */
    public abstract String getTitle(Object upcoming);
    /**
     * @param upcoming The thing that will be shown next time
     * @return The display name of the thing
     */
    public abstract String getObjectName(Object upcoming);
    /**
     * @param upcomingName The display name of the thing that will be shown
     * @return The text that will be send to all players to inform them about the new task.
     * Should contain the name of the upcoming thing
     */
    public abstract String getTaskMessage(String upcomingName);

    /**
     * @param currentTask The thing that is shown currently
     * @return If the challenge was finished
     */
    public abstract boolean isFinished(Object currentTask);

    private enum ForceState {
        LOADING,
        GENERATED,
        SET
    }

    private void run() {
        if (this.isTaskStarted) return;
        if (!AbstractForceChallenge.this.isEnabled) return;
        this.isTaskStarted = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!AbstractForceChallenge.this.isEnabled) {
                    removeBossbar();
                    AbstractForceChallenge.this.isTaskStarted = false;
                    cancel();
                }
                updateBossBar();
                if (!AbstractForceChallenge.this.isActive()) return;
                if (AbstractForceChallenge.this.state == ForceState.LOADING) {
                    AbstractForceChallenge.this.time = AbstractForceChallenge.this.random.nextInt(
                            AbstractForceChallenge.this.firstTaskMaxTime() -
                                    AbstractForceChallenge.this.firstTaskMinTime()
                    ) + AbstractForceChallenge.this.firstTaskMinTime();
                    Bukkit.broadcastMessage(Main.getCustomPrefix(AbstractForceChallenge.this.name) + "Erste Aufgabe " +
                            "in frühestens " + Utils.shortInteger(AbstractForceChallenge.this.firstTaskMinTime()));
                    AbstractForceChallenge.this.generateUpcoming();
                }
                if (AbstractForceChallenge.this.upcoming != null) {
                    if (AbstractForceChallenge.this.isFinished(AbstractForceChallenge.this.upcoming)) {
                        AbstractForceChallenge.this.upcoming = null;
                        AbstractForceChallenge.this.time =
                                (random.nextInt(AbstractForceChallenge.this.midTaskMaxTime() -
                                        AbstractForceChallenge.this.midTaskMinTime()) +
                                        AbstractForceChallenge.this.midTaskMinTime());
                        AbstractForceChallenge.this.generateUpcoming();
                        Bukkit.broadcastMessage(Main.getCustomPrefix(AbstractForceChallenge.this.name) +
                                ChatColor.DARK_GREEN + "Die Aufgabe wurde erfolgreich geschafft!");
                    }
                }
                if (AbstractForceChallenge.this.time == 0) {
                    if (AbstractForceChallenge.this.upcoming != null) {
                        Timer.endChallenge("Die Aufgabe wurde nicht geschafft.");
                        AbstractForceChallenge.this.state = ForceState.LOADING;
                    } else {
                        AbstractForceChallenge.this.setNewObject();
                    }
                }
                AbstractForceChallenge.this.time--;
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 20, 20);
    }

    private void generateUpcoming() {
        if (this.upcoming == null) {
            this.next = this.allPosibilities().get(this.random.nextInt(this.allPosibilities().size()));
            this.state = ForceState.GENERATED;
        }
    }

    private void setNewObject() {
        this.time = random.nextInt(this.finishTaskMaxTime() - this.finishTaskMinTime()) +
                this.finishTaskMinTime();
        this.state = ForceState.SET;
        this.startTime = this.time;
        this.upcoming = this.next;
        Bukkit.broadcastMessage(Main.getCustomPrefix(this.name) + ChatColor.DARK_GRAY + "NEUE ANWEISUNG: " +
                getTaskMessage(this.getObjectName(this.next)));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!this.isActive()) return;
        if (event.getMessage().equalsIgnoreCase("!force set")) {
            this.time = 2;
            player.sendMessage(Main.getCustomPrefix(this.name) + "Die Zeit wurde auf 2 Sekunden " +
                    "gesetzt.");
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force time")) {
            player.sendMessage(Main.getCustomPrefix(this.name) + "Die Zeit ist: " + Utils.shortInteger(this.time));
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force upcoming")) {
            player.sendMessage(Main.getCustomPrefix(this.name) + "Das nächste Objekt ist: " +
                    this.getObjectName(this.upcoming));
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force state")) {
            player.sendMessage(Main.getCustomPrefix(this.name) + "Der aktuelle Status ist: " +
                    this.state.toString());
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force bar")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix(this.name) + "Die Bossbar wird neu generiert.");
            this.updateBossBar();
        } else if (event.getMessage().equalsIgnoreCase("!force debug")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix(this.name) + "Raw-Wert des Objektes: " +
            this.upcoming.toString());
        } else if (event.getMessage().equalsIgnoreCase("!force next")) {
            player.sendMessage(Main.getCustomPrefix(this.name) + "Das nächste Objekt ist: " +
                    this.getObjectName(this.next));
            event.setCancelled(true);
        }
    }
    public void onTimerUpdate() {
        run();
    }
}
