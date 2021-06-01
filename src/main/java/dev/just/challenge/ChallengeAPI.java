package dev.just.challenge;

import dev.just.challenge.challenge.AbstractChallenge;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ChallengeAPI {
    private final static ChallengeAPI instance = new ChallengeAPI();
    private JavaPlugin plugin;
    private final List<AbstractChallenge> challenges;
    private ChallengeAPI() {
        this.challenges = new ArrayList<>();
    }

    public static ChallengeAPI getInstance() {
        return instance;
    }

    private void registerChallenges(List<AbstractChallenge> challenges) {
        for (AbstractChallenge challenge : challenges) {
            if (challenge instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) challenge, plugin);
            }
        }
    }

    public void register(JavaPlugin plugin) {
        this.plugin = plugin;
        registerChallenges(this.challenges);
    }
}
