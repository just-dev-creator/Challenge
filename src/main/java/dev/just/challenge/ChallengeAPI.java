package dev.just.challenge;

import dev.just.challenge.challenge.AbstractChallenge;
import dev.just.challenge.challenge.ChallengeConfig;
import dev.just.challenge.challenge.challenges.TestChallenge;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ChallengeAPI {
    public static List<AbstractChallenge> challenges = new ArrayList<>();
    public static void onLoad() {
        ChallengeConfig.setup();
        setup();
        register();
    }
    private static void setup() {
        challenges.add(new TestChallenge());
    }
    private static void register() {
        for (AbstractChallenge challenge : challenges) {
            challenge.enable();
        }
    }
}
