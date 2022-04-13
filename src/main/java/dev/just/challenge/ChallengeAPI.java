/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge;

import dev.just.challenge.challenge.AbstractChallenge;
import dev.just.challenge.challenge.ChallengeConfig;
import dev.just.challenge.challenge.challenges.BlocksExplode;
import dev.just.challenge.challenge.challenges.FacingChallenge;
import dev.just.challenge.challenge.challenges.forcechallenges.ForceBiomes;
import dev.just.challenge.challenge.challenges.forcechallenges.ForceEffect;

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
        challenges.add(new FacingChallenge());
        challenges.add(new ForceEffect());
        challenges.add(new BlocksExplode());
        challenges.add(new ForceBiomes());
    }
    private static void register() {
    }
}
