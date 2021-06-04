package dev.just.challenge;

import dev.just.challenge.beta.BetaCommand;
import dev.just.challenge.beta.JoinEvent;
import dev.just.challenge.challenges.*;
import dev.just.challenge.commands.*;
import dev.just.challenge.giveop.ChatListener;
import dev.just.challenge.inventorys.*;
import dev.just.challenge.listeners.*;
import dev.just.challenge.utilities.MathChallenge;
import dev.just.challenge.utilities.Parcour;
import dev.just.challenge.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Set;

public final class Main extends JavaPlugin {
    public static ArrayList<Player> hide = new ArrayList<>();
    public static Location dorfSpawn;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onLoad() {
        Config.setup();
        Reset.reset();
    }

    @Override
    public void onEnable() {
        try {
            if (!LicenseSystem.getLicense(this)) {
                Bukkit.getScheduler().cancelTasks(this);
                Bukkit.getPluginManager().disablePlugin(this);
                System.out.println(ChatColor.YELLOW + "Folgende Fehlermeldung ignorieren - Plugin ist komplett deaktiviert! ");
            }
        } finally {
            getPlugin = this;
            TimerCommand.timer_active = false;
            // Plugin startup logic
            Settings.settings.clear();
            registerEvents();
            registerCommands();
            Settings.setSettings();
            if (Settings.settings.get(Settings.ItemType.PARCOUR).equals(Settings.ItemState.ENABLED)) {
                ScoreboardManager.checkpoints();
            }
            loadTimer();
//        loadSettings();
            Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                @Override
                public void run() {
                    Timer.timer();
                    ForceGleichungen.main();
                }
            }, 10);
            scoreboardManager = new ScoreboardManager();
        }
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.GOLD + "JUtils" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY;
    }
    public static String getErrorPrefix() {
        return "§8┃ §4ERROR §8» §7";
    }
    public static String getCustomPrefix(String customName) {
        return ChatColor.GREEN + customName + ChatColor.GRAY + " » " + ChatColor.DARK_GRAY;
    }
    public static String getNoPlayer() {
        return getErrorPrefix() + "Du musst für diese Aktion ein Spieler sein. ";
    }
    public static String getNoPermission() {
        return getErrorPrefix() + "Du hast nicht die nötigen Berechtigungen. ";
    }

    public static Main getPlugin;

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveTimer();
        if (scoreboardManager != null) scoreboardManager.
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
        getCommand("beta").setExecutor(new BetaCommand());
        getCommand("position").setExecutor(new PositionCommand());

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
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new OneLookChallenge(), this);
        pluginManager.registerEvents(new FacingChallenge(), this);
        pluginManager.registerEvents(new ForceEffectChallenge(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new ForceGleichungen(), this);
        pluginManager.registerEvents(new PingListener(), this);
        pluginManager.registerEvents(new dev.just.challenge.listeners.ChatListener(), this);
        pluginManager.registerEvents(new NoRemoveChallenge(), this);
        pluginManager.registerEvents(new SpecCommand(), this);
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