package dev.just.challenge.challenge;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ChallengeConfig {
    private static File file;
    private static YamlConfiguration configuration;
    public static void setup() {
        File dir = new File("./plugins/JUtils/");
        if (!dir.exists()) dir.mkdirs();
        file = new File(dir, "challenges.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static YamlConfiguration getConfiguration() {
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
    public static Object get(String path) {
        return configuration.get(path);
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
