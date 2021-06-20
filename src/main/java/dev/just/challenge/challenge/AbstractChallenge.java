package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class AbstractChallenge {
    private final String name;
    private final String configName;
    public boolean isEnabled = false;
    public AbstractChallenge(String name) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if ((boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
        }
    }

    public void enable() {
        this.setEnabled(true);
        if (this instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) this, Main.getPlugin(Main.class));
        }
        this.onEnable();
    }
    public void disable() {
        this.setEnabled(false);
        this.onDisable();
    }

    /**
     * Will be executed when the challenge is started
     */
    public abstract void onEnable();
    /**
     * Will be executed when the challenge is stopped
     */
    public abstract void onDisable();

    private void setConfig(String path, Object value) {
        ChallengeConfig.set(this.configName + "." + path, value);
    }
    private boolean containsConfig(String path) {
        return ChallengeConfig.contains(this.configName + "." + path);
    }
    private Object getConfig(String path) {
        return ChallengeConfig.get(this.configName + "." + path);
    }

    private void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        this.setConfig("enabled", enabled);
    }
}
