/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

public class Reset {
    public static void reset() {
        if (Config.contains("reset.isReset") && Config.getBoolean("reset.isReset")) {
            System.out.println(Config.getBoolean("reset.isReset"));
            FileUtils.deleteFolder("world");
            FileUtils.deleteFolder("world_nether");
            FileUtils.deleteFolder("world_the_end");
            Config.set("reset.isReset", false);
        }
    }
}
