package dev.just.challenge.challenges;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

// Nur zur Info: Beginn am 4.9.2020 / 19:19
public class ForceEffectChallenge implements Listener {
    public static String effectName;
    BossBar bossBar;
    int startTime;
    int time;
    public static PotionEffectType effectType;
    public static ForceEffectChallenge.ForceState phase;
    PotionEffectType upcoming;
    ArrayList<PotionEffectType> effectTypes;
    Random random = new Random();
    boolean isEnabled;

    public ForceEffectChallenge() {
        if (isEnabled) {
            return;
        } else {
            isEnabled = true;
            effectType = null;
            this.upcoming = null;
            this.time = (random.nextInt(240) + 180);
            phase = ForceState.LOADING;
            run();
        }
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
                if (Settings.settings.get(Settings.ItemType.FORCEEFFECT).equals(Settings.ItemState.ENABLED)) {
                    ForceEffectChallenge.this.updateBossbar();
                    if (TimerCommand.timer_active) {
                        if (phase == ForceState.LOADING) {
                            Bukkit.broadcastMessage(Main.getCustomPrefix("ForceEffect") + "Erste Aufgabe in frühestens " + ShortInteger.run(240));
                            ForceEffectChallenge.this.generateUpcoming();
                        }
                        if (ForceEffectChallenge.this.upcoming != null) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (all.hasPotionEffect(ForceEffectChallenge.this.upcoming)) {
                                    effectType = null;
                                    effectName = null;
                                    ForceEffectChallenge.this.upcoming = null;
                                    ForceEffectChallenge.this.time = (random.nextInt(180) + 120);
                                    ForceEffectChallenge.this.generateUpcoming();
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceEffect") + ChatColor.DARK_GREEN + "Der Spieler " + ChatColor.GREEN + all.getName() + ChatColor.DARK_GREEN + " hat den Effekt erhalten. Nächste Aufgabe in frühestens " + ShortInteger.run(180) + ".");
                                }
                            }
                        }
                        if (ForceEffectChallenge.this.time == 0) {
                            if (ForceEffectChallenge.this.upcoming != null) {
                                Player failed = null;
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (!player.hasPotionEffect(ForceEffectChallenge.this.upcoming)) {
                                        failed = player;
                                    }
                                }
                                Timer.endChallenge(ChatColor.RED + "Kein Spieler hatte den richtigen Effekt", failed);
                                /** boolean succes = true;
                                Player failed = null;
                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (!all.hasPotionEffect(ForceEffectChallenge.this.upcoming)) {
                                        failed = all;
                                        succes = false;
                                    }
                                }
                                if (succes) {
                                    effectType = null;
                                    effectName = null;
                                    ForceEffectChallenge.this.upcoming = null;
                                    ForceEffectChallenge.this.time = (random.nextInt(180) + 120);
                                    ForceEffectChallenge.this.generateUpcoming();
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceEffect") + ChatColor.DARK_GREEN + "Es haben alle Spieler die Aufgabe korrekt beantwortet. Nächste Aufgabe in frühestens " + ShortInteger.run(180) + ".");
                                } else {
                                    Timer.endChallenge(ChatColor.RED + failed.getName() + " war nicht im richtigen Biom.", failed);
                                } **/
                            } else {
                                ForceEffectChallenge.this.setNewBlock();
                            }
                        }
                        ForceEffectChallenge.this.time--;
                    }
                } else {
                    ForceEffectChallenge.this.removeBossbar();
                }
            }
        }, 20L, 20L);
    }

    public void generateUpcoming() {
        if (this.upcoming == null) {
            this.effectTypes = new ArrayList<>();
            byte b1;
            int j;
            PotionEffectType[] arrayofEffects;
            for (j = (arrayofEffects = PotionEffectType.values()).length, b1 = 0; b1 < j; ) {
                PotionEffectType effect = arrayofEffects[b1];
                effectTypes.add(effect);
                b1++;
            }
            effectType = (this.effectTypes.get(random.nextInt(this.effectTypes.size())));
            phase = ForceState.GENERATED;
            this.effectTypes.remove(upcoming);
            String effectNameBefore = effectType.getName();
            String[] effectNameSplit = effectNameBefore.split("_");
            effectName = ShortString.run(effectNameSplit, false, true);
        }
    }

    public void setNewBlock() {
        this.time = (random.nextInt(120) + 120);
        phase = ForceState.SET;
        this.startTime = this.time;
        this.upcoming = effectType;
        Bukkit.broadcastMessage(Main.getCustomPrefix("ForceEffect") + ChatColor.DARK_GRAY + "NEUE ANWEISUNG! " + ChatColor.GRAY + "Erhalte folgenden Effekt: " + ChatColor.GREEN + effectName);
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
            this.bossBar.setTitle(ChatColor.DARK_GREEN + "Nächster Effekt: " + ChatColor.GREEN + effectName + ChatColor.DARK_GRAY + " | " + ShortInteger.run(this.time));
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
        if (!TimerCommand.timer_active || Settings.settings.get(Settings.ItemType.FORCEEFFECT).equals(Settings.ItemState.DISABLED)) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("!force set")) {
            this.time = 2;
            event.getPlayer().sendMessage(Main.getCustomPrefix("ForceEffect") + "Die Zeit wurde auf 2 gesetzt.");
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force time")) {
            player.sendMessage(Main.getCustomPrefix("ForceEffect") + "Die Zeit ist: " + ShortInteger.run(this.time));
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force upcoming")) {
            player.sendMessage(Main.getCustomPrefix("ForceEffect") + "Der nächste Effekt ist: " + ChatColor.GREEN + effectName);
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force state")) {
            player.sendMessage(Main.getCustomPrefix("ForceEffect") + "Der aktuelle Status ist: " + phase.toString());
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force bar")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix("ForceEffect") + "Die Bossbar wird neu generiert.");
            this.updateBossbar();
        } else if (event.getMessage().equalsIgnoreCase("!force debug")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix("ForceEffect") + effectType.toString());
        } else if (event.getMessage().equalsIgnoreCase("!force give")) {
            event.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.addPotionEffect(new PotionEffect(ForceEffectChallenge.this.upcoming, 60, 1));
                }
            }.runTask(Main.getPlugin(Main.class));
            player.sendMessage(Main.getCustomPrefix("ForceEffect") + "Du hast den Effekt " + ChatColor.GREEN + effectName + ChatColor.DARK_GRAY + " bekommen. ");
        }
    }

}
