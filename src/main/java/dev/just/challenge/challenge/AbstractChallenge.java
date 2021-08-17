/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import dev.just.challenge.utils.ItemBuilder;
import dev.just.challenge.utils.Timer;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Helper to create challenges more easily
 */
public abstract class AbstractChallenge {
    public final String name;
    private final String configName;
    public final ItemStack icon;
    public boolean isEnabled = false;
    public AbstractChallenge(String name) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if (this.containsConfig("enabled") && (boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
            if (this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) this, Main.getPlugin(Main.class));
            }
        }
        this.icon = new ItemStack(Material.STRUCTURE_VOID);
    }
    public AbstractChallenge(String name, ItemStack icon) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if (this.containsConfig("enabled") && (boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
            if (this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) this, Main.getPlugin(Main.class));
            }
        }
        this.icon = icon;
    }
    public AbstractChallenge(String name, Material icon) {
        this.name = name;
        this.configName = name.toLowerCase().replace(" ", "_");
        if (this.containsConfig("enabled") && (boolean) this.getConfig("enabled")) {
            this.isEnabled = true;
            if (this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) this, Main.getPlugin(Main.class));
            }
        }
        this.icon = new ItemStack(icon, 1);
    }

    public void enable() {
        if (this instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) this, Main.getPlugin(Main.class));
        }
        this.setEnabled(true);
        this.onEnable();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Utils.sendSettingsChange(player, "Die Challenge " + ChatColor.YELLOW + this.name + ChatColor.DARK_GRAY + " wurde aktiviert.");
        }
    }
    public void disable() {
        this.setEnabled(false);
        this.onDisable();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Utils.sendSettingsChange(player, "Die Challenge " + ChatColor.YELLOW + this.name + ChatColor.DARK_GRAY + " wurde deaktiviert.");
        }
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
        if (this.isEnabled) {
            return(new ItemBuilder(this.icon)
                    .addEnchant(Enchantment.ARROW_DAMAGE, 1)
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addFlag(ItemFlag.HIDE_ENCHANTS)
                    .setName(ChatColor.GREEN + this.name)
                    .toItemStack());
        } else {
            return(new ItemBuilder(this.icon)
                    .setName(ChatColor.RED + this.name)
                    .removeEnchantment(Enchantment.ARROW_DAMAGE)
                    .toItemStack());
        }
    }

    protected void setConfig(String path, Object value) {
        ChallengeConfig.set(this.configName + "." + path, value);
    }
    protected boolean containsConfig(String path) {
        return ChallengeConfig.contains(this.configName + "." + path);
    }
    protected Object getConfig(String path) {
        return ChallengeConfig.get(this.configName + "." + path);
    }

    protected void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        this.setConfig("enabled", enabled);
    }

    public boolean isActive() {
        return this.isEnabled && Timer.isRunning();
    }
}
