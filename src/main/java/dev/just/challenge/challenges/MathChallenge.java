package dev.just.challenge.challenges;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.ShortInteger;
import dev.just.challenge.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Random;

public class MathChallenge implements Listener {
    HashMap<Player, ForceEnd> end = new HashMap<>();
    BossBar bossBar;
    int startTime;
    int time;
    int z1;
    int z2;
    int solve;
    ForceState phase;
    String upcoming;
    Random random = new Random();

    public MathChallenge() {
        this.upcoming = null;
        this.time = (random.nextInt(120) + 120);
        this.phase = ForceState.LOADING;
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
                if (Settings.settings.get(Settings.ItemType.FORCEMATH).equals(Settings.ItemState.ENABLED)) {
                    MathChallenge.this.updateBossbar();
                    if (TimerCommand.timer_active) {
                        if (MathChallenge.this.phase == ForceState.LOADING) {
                            Bukkit.broadcastMessage(Main.getCustomPrefix("ForceMath") + "Erste Aufgabe in frühestens " + ShortInteger.run(120));
                            MathChallenge.this.generateUpcoming();
                        }
                        if (MathChallenge.this.time == 0) {
                            if (MathChallenge.this.upcoming != null) {
                                boolean succes = true;
                                Player failed = null;
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    if (!end.containsKey(player)) {
                                        end.put(player, ForceEnd.FALSE);
                                    }
                                    if (Main.hide.contains(player) && end.get(player).equals(ForceEnd.FALSE)) {
                                        succes = false;
                                        failed = player;
                                    }
                                }
                                if (succes) {
                                    MathChallenge.this.z1 = 0;
                                    MathChallenge.this.z2 = 0;
                                    MathChallenge.this.upcoming = null;
                                    MathChallenge.this.solve = 0;
                                    MathChallenge.this.time = (random.nextInt(120) + 120);
                                    MathChallenge.this.end.clear();
                                    MathChallenge.this.generateUpcoming();
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceMath") + ChatColor.DARK_GREEN + "Es haben alle Spieler die Aufgabe korrekt beantwortet. Nächste Aufgabe in frühestens " + ShortInteger.run(120) + ".");
                                } else if (failed != null) {
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceMath") + "Richtig wäre gewesen: " + ChatColor.GREEN + solve);
                                    Timer.endChallenge(ChatColor.RED + failed.getName() + " hat die Aufgabe nicht richtig beantwortet.", failed);
                                }
                            } else {
                                MathChallenge.this.setNewBlock();
                            }
                        }
                        MathChallenge.this.time--;
                    }
                } else {
                    MathChallenge.this.removeBossbar();
                }
            }
        }, 20L, 20L);
    }

    public void generateUpcoming() {
        if (this.upcoming == null) {
            this.z1 = (random.nextInt(15) + 5);
            this.z2 = (random.nextInt(15) + 5);
            this.solve = z1 * z2;
            phase = ForceState.GENERATED;
//            this.upcoming = z1 + "•" + z2;
        }
    }

    public void setNewBlock() {
        this.upcoming = z1 + " • " + z2;
        this.time = (random.nextInt(70) + 20);
        this.phase = ForceState.SET;
        this.startTime = this.time;
        Bukkit.broadcastMessage(Main.getCustomPrefix("ForceMath") + ChatColor.DARK_GRAY + "NEUE AUFGABE! " + ChatColor.GRAY + "Löse folgende Aufgabe: " + ChatColor.GREEN + this.upcoming);
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
            this.bossBar.setTitle(ChatColor.GOLD + "Warten auf neue Aufgabe");
            this.bossBar.setColor(BarColor.WHITE);
            this.bossBar.setProgress(1.0D);
        } else {
            this.bossBar.setTitle(ChatColor.DARK_GREEN + "Nächste Rechnung: " + ChatColor.GREEN + this.upcoming + ChatColor.DARK_GRAY + " | " + ShortInteger.run(this.time));
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
        if (!TimerCommand.timer_active || Settings.settings.get(Settings.ItemType.FORCEMATH).equals(Settings.ItemState.DISABLED)) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("!force set")) {
            this.time = 2;
            event.getPlayer().sendMessage(Main.getCustomPrefix("ForceMath") + "Die Zeit wurde auf 2 gesetzt.");
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force time")) {
            player.sendMessage(Main.getCustomPrefix("ForceMath") + "Die Zeit ist: " + ShortInteger.run(this.time));
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force upcoming")) {
            player.sendMessage(Main.getCustomPrefix("ForceMath") + "Die nächste Aufgabe ist: " + ChatColor.GREEN + this.z1 + " • " + this.z2);
            player.sendMessage(ChatColor.RED + Main.getCustomPrefix("ForceMath") + "Debug: " + this.upcoming);
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force state")) {
            player.sendMessage(Main.getCustomPrefix("ForceMath") + "Der aktuelle Status ist: " + this.phase.toString());
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force bar")) {
            player.sendMessage(Main.getCustomPrefix("ForceMath") + "Die Bossbar wird neu generiert.");
            this.updateBossbar();
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force solve")) {
            player.sendMessage(Main.getCustomPrefix("ForceMath") + "Die Lösung der nächsten Aufgabe ist: " + ChatColor.GREEN + this.solve);
            event.setCancelled(true);
        } else if (!Main.hide.contains(event.getPlayer())) {
        } else if (!(this.upcoming == null)){
            String message = event.getMessage();
            try {
                int lösung = Integer.parseInt(message);
                if (lösung == this.solve) {
                    end.put(player, ForceEnd.TRUE);
                    player.sendMessage(Main.getCustomPrefix("ForceMath") + "Deine Antwort wurde erfolgreich eingeloggt. ");
                } else {
                    player.sendMessage(Main.getCustomPrefix("ForceMath") + "Deine Antwort wurde erfolgreich eingeloggt. ");
                    end.put(player, ForceEnd.FALSE);
                }
                event.setCancelled(true);
            } catch (Exception e) {
                return;
            }
        }
    }
}

