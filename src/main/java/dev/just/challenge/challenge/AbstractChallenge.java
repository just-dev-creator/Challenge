package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import dev.just.challenge.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractChallenge {
    public final String name;
    private final String configName;
    public final ItemStack icon;
    public boolean isEnabled = false;
    public AbstractChallenge(String name) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if (!this.containsConfig("enabled") || (boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
        }
        this.icon = new ItemStack(Material.STRUCTURE_VOID);
    }
    public AbstractChallenge(String name, ItemStack icon) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if (!this.containsConfig("enabled") || (boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
        }
        this.icon = icon;
    }
    public AbstractChallenge(String name, Material icon) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if (!this.containsConfig("enabled") || (boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
        }
        this.icon = new ItemStack(icon, 1);
    }

    public void enable() {
        this.setEnabled(true);
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

    public ItemStack getMenuItem() {
        System.out.println(this.isEnabled);
        if (this.isEnabled) {
            System.out.println("Returning true");
            return(new ItemBuilder(this.icon)
                    .addEnchant(Enchantment.ARROW_DAMAGE, 1)
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addFlag(ItemFlag.HIDE_ENCHANTS)
                    .setName(ChatColor.GREEN + this.name)
                    .toItemStack());
        } else {
            System.out.println("Returning false");
            return(new ItemBuilder(this.icon)
                    .setName(ChatColor.RED + this.name)
                    .removeEnchantment(Enchantment.ARROW_DAMAGE)
                    .toItemStack());
        }
    }

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
