package dev.just.challenge;

import dev.just.challenge.challenge.AbstractChallenge;
import dev.just.challenge.challenge.ChallengeConfig;
import dev.just.challenge.challenge.challenges.FacingChallenge;
import dev.just.challenge.challenge.challenges.TestChallenge;

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
        challenges.add(new FacingChallenge());
    }
    private static void register() {
    }
}
