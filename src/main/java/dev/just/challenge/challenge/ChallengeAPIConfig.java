package dev.just.challenge.challenge;

import dev.just.challenge.ChallengeAPI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ChallengeAPIConfig {
    private final static ChallengeAPIConfig instance = new ChallengeAPIConfig();
    private static File file;
    private static YamlConfiguration configuration;

    private ChallengeAPIConfig() {}

    public static ChallengeAPIConfig getInstance() {
        return instance;
    }
    private String key(AbstractChallenge challenge, String fieldName) {
        return "challenge." + challenge.getName() + ".settings." + fieldName;
    }

    public void register (File folder) {
        file = new File(folder, "challengeConfig.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
        configuration.addDefault("kit.amount", 1);
        configuration.addDefault("debug", true);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void add(AbstractChallenge challenge) {

    }
}
