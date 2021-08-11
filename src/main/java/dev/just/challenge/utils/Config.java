/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private static File file;
    private static FileConfiguration configuration;
    public static void setup() {
        File dir = new File("./plugins/JUtils/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file = new File(dir, "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return configuration;
    }
    public static void set(String path, Object value) {
        configuration.set(path, value);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean contains(String path) {
        return configuration.contains(path);
    }
    public static boolean isBoolean(String path) {
        return configuration.isBoolean(path);
    }
    public static boolean getBoolean(String path) {
        return configuration.getBoolean(path);
    }
}
