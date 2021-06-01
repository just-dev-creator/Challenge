package dev.just.challenge.challenges;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.ShortInteger;
import dev.just.challenge.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ForceGleichungen implements Listener {
    private static String gleichung;
    private static BossBar bar;
    private static BossBar bar1;
    private static int starttime;
    private static int time;
    private static String upcoming;
    private static HashMap<String, Integer> gleichungen = new HashMap<>();
    private static List<String> strings = new ArrayList<>();
    private static Random random = new Random();
    private static boolean fileexists;
    private static Player needsToAnswer;
    private static State state;
    private static boolean success;

    private enum State {
        LOADING,
        GENERATED,
        SET
    }

    public static void main() {
        time = (random.nextInt(240) + 180);
        state = State.LOADING;
        load();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Settings.settings.get(Settings.ItemType.FORCEGLEICHUNG).equals(Settings.ItemState.ENABLED)) {
                    if (!fileexists) {
                        Settings.settings.put(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.DISABLED);
                        Settings.setConfig(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.DISABLED);
                        Bukkit.getLogger().warning("Die Datei mit den Gleichungen existiert nicht. ");
                        Bukkit.getLogger().warning("Die Challenge wird daher deaktiviert. ");
                        Bukkit.getLogger().info("Bitte die Datei gleichungen.txt in den Ordner JUtils ziehen. ");
                        return;
                    }
                    updateBossbar();
                    if (Timer.isRunning()) {
                        if (state == State.LOADING) {
                            Bukkit.broadcastMessage(Main.getCustomPrefix("ForceGleichung") + "Erste Aufgabe in frühestens " + ShortInteger.run(600));
                            generateUpcoming();
                        }
                        if (time == 0) {
                            if (upcoming != null) {
                                if (success) {
                                    strings.remove(upcoming);
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceGleichung") + ChatColor.DARK_GREEN + "Der Spieler hat die Aufgabe korrekt beantwortet. Nächste Aufgabe in frühestens " + ShortInteger.run(600) + ".");
                                    upcoming = null;
                                    gleichung = null;
                                    time = (random.nextInt(180) + 120);
                                    bar1.removeAll();
                                    bar1 = null;
                                    needsToAnswer = null;
                                    generateUpcoming();
                                } else {
                                    Timer.endChallenge(ChatColor.RED + needsToAnswer.getName() + " hat die Aufgabe falsch beantwortet. ", needsToAnswer);
                                    Bukkit.broadcastMessage(Main.getCustomPrefix("ForceGleichung") + "Die richtige Antwort wäre gewesen: " + ChatColor.GREEN + gleichungen.get(gleichung));
                                }
                            } else {
                                setNew();
                            }
                        }
                        time--;
                    }
                } else {
                    removeBossbar();
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 20, 20);
    }

    private static void load() {
        File dir = new File("./plugins/JUtils/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "gleichungen.txt");
        fileexists = file.exists();
        if (!fileexists) return;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                String[] args = line.split(";");
                if (args.length != 3) return;
                String a0 = args[0];
                String a2 = args[2];
                Integer a2l = Integer.valueOf(a2);
                gleichungen.put(a0, a2l);
                strings.add(args[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void updateBossbar() {
        if (bar == null) {
            bar = Bukkit.createBossBar(ChatColor.GOLD + "Lädt...", BarColor.WHITE, BarStyle.SOLID);
        } if (bar1 == null) {
            bar1 = Bukkit.createBossBar(ChatColor.GOLD + "Lädt...", BarColor.WHITE, BarStyle.SOLID);
        }
        if (!Timer.isRunning()) {
            bar.setTitle(ChatColor.GOLD + "Der Timer ist " + "pausiert");
            bar.setColor(BarColor.RED);
            bar.setProgress(1.0D);
            bar1 = bar;
        } else if (upcoming == null) {
            bar.setTitle(ChatColor.GOLD + "Warten auf eine neue Gleichung");
            bar.setColor(BarColor.WHITE);
            bar.setProgress(1.0D);
            bar1 = bar;
        } else {
            bar.setTitle(ChatColor.GOLD + needsToAnswer.getName() + ChatColor.GRAY + " muss antworten " + ChatColor.DARK_GRAY + "| " + ChatColor.GOLD + time);
            bar.setProgress(Double.valueOf((time * 100 / starttime) * 0.01D).doubleValue());
            bar.setColor(BarColor.YELLOW);

//            Bar for player who answers
            bar1.setTitle(ChatColor.GOLD + upcoming);
            bar1.setProgress(Double.valueOf((time * 100 / starttime) * 0.01D).doubleValue());
            if (bar1.getProgress() > 0.66D) {
                bar1.setColor(BarColor.GREEN);
            } else if (bar1.getProgress() > 0.33D) {
                bar1.setColor(BarColor.YELLOW);
            } else {
                bar1.setColor(BarColor.RED);
            }
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (bar1 != null && all.equals(needsToAnswer)) {
                bar1.addPlayer(all);
            } else if (bar != null){
                bar.addPlayer(all);
            } else {
                Bukkit.getLogger().warning(ChatColor.RED + "Keine Bossbar vorhanden! ");
            }
        }
    }

    private static void removeBossbar() {
        if (bar != null) {
            bar.removeAll();
            bar = null;
        }
//        Bar for player who answers
        if (bar1 != null) {
            bar1.removeAll();
            bar1 = null;
        }
    }

    private static void generateUpcoming() {
        if (upcoming == null) {
            String next = strings.get(random.nextInt(strings.size()));
            gleichung = next;
            state = State.GENERATED;
        }
    }

    private static void setNew() {
        if (upcoming == null) {
            time = random.nextInt(900) + 420;
            state = State.SET;
            starttime = time;
            upcoming = gleichung;
            if (upcoming == null) {
                TimerCommand.timer_active = false;
                Bukkit.getLogger().warning(Main.getErrorPrefix() + "Es wurden bereits alle Gleichungen beantwortet. ");
            }
            Player player = null;
            while (player == null || !Main.hide.contains(player)) {
                player = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];
                if (player == null) return;
            }
            needsToAnswer = player;
            player.sendMessage(Main.getCustomPrefix("ForceGleichung") + "Schreibe die Antwort auf folgende Gleichung: " + ChatColor.GREEN + gleichung + ChatColor.DARK_GRAY + " in den Chat. ");
        }
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!TimerCommand.timer_active || Settings.settings.get(Settings.ItemType.FORCEGLEICHUNG).equals(Settings.ItemState.DISABLED)) {
            return;
        }
        if (event.getMessage().equalsIgnoreCase("!force set")) {
            event.getPlayer().sendMessage(Main.getCustomPrefix("ForceGleichung") + "Die Zeit wurde auf 2 gesetzt.");
            time = 2;
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force time")) {
            player.sendMessage(Main.getCustomPrefix("ForceGleichung") + "Die Zeit ist: " + ShortInteger.run(time));
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force upcoming")) {
            player.sendMessage(Main.getCustomPrefix("ForceGleichung") + "Die nächste Gleichung ist: " + ChatColor.GREEN + gleichung);
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force state")) {
            player.sendMessage(Main.getCustomPrefix("ForceGleichung") + "Der aktuelle Status ist: " + state.toString());
            event.setCancelled(true);
        } else if (event.getMessage().equalsIgnoreCase("!force bar")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix("ForceGleichung") + "Die Bossbar wird neu generiert.");
            updateBossbar();
        } else if (event.getMessage().equalsIgnoreCase("!force debug")) {
            event.setCancelled(true);
            player.sendMessage(Main.getCustomPrefix("ForceGleichung") + gleichungen.toString());
        } else if (event.getPlayer().equals(needsToAnswer)) {
            event.setCancelled(true);
            try {
                if (Integer.valueOf(event.getMessage()).equals(gleichungen.get(upcoming))) {
                    success = true;
                }
                player.sendMessage(Main.getCustomPrefix("ForceGleichung") + "Deine Antwort wurde eingelogt. ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

// Finally - just.de, 16.9.2020, 20:12
// bar1 addes, tests can now begin - justde, 17.09.2020, 17:53