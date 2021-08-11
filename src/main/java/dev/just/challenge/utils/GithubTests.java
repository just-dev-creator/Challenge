/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;

import java.io.File;

public class GithubTests {
    public static void stopServer() {
        File file = new File("isGitHub");
        if (file.exists()) {
            Bukkit.getLogger().warning(Main.getCustomPrefix("Testing") + "You're executing in a" +
                    " test environment. This will stop the server in a moment!");
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getLogger().info(Main.getCustomPrefix("Testing") + "The test" +
                            " was finished successfully. This does not mean that there aren't any bugs " +
                            "in the challenges as this is not covered by this test.");
                    Bukkit.getLogger().warning(Main.getCustomPrefix("Testing") + "The server " +
                            "will stop in a moment.");
                    Bukkit.shutdown();
                }
            }, 200);
        }
    }
}
