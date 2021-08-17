/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import dev.just.challenge.utils.ItemBuilder;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractOptionChallenge extends AbstractChallenge {

    private final int possibilitiesAmount;
    public int state;

    public AbstractOptionChallenge(String name, Material icon, int possibilitiesAmount) {
        super(name, icon);
        this.possibilitiesAmount = possibilitiesAmount;
        this.state = 0;
        if (this.containsConfig("enabled")) {
            this.state = (int) this.getConfig("enabled");
        }
    }

    public AbstractOptionChallenge(String name, ItemStack icon, int possibilitiesAmount) {
        this(name, icon.getType(), possibilitiesAmount);
    }

    public void setState(int state) {
        this.state = state;
        this.setConfig("enabled", state);
        if (this.state > 0) {
            if (this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) this, Main.getPlugin(Main.class));
            }
            this.onEnable();
            for (Player player : Bukkit.getOnlinePlayers()) {
                Utils.sendSettingsChange(player, "Die Challenge " + ChatColor.YELLOW + this.name + ChatColor.DARK_GRAY + " wurde aktiviert.");
            }
        } else if (this.state == 0) {
            this.onDisable();
            for (Player player : Bukkit.getOnlinePlayers()) {
                Utils.sendSettingsChange(player, "Die Challenge " + ChatColor.YELLOW + this.name + ChatColor.DARK_GRAY + " wurde deaktiviert.");
            }
        }
    }

    @Override
    public ItemStack getMenuItem() {
        if (this.state == 0) {
            return(new ItemBuilder(this.icon)
                    .setName(ChatColor.RED + this.name)
                    .removeEnchantment(Enchantment.ARROW_DAMAGE)
                    .addLoreLine(ChatColor.GRAY + ChatColor.ITALIC.toString() + "[Rechtsklick] " +
                            ChatColor.BLUE + "Ändere um +1")
                    .toItemStack());
        } else {
            return(new ItemBuilder(this.icon)
                    .addEnchant(Enchantment.ARROW_DAMAGE, 1)
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .addFlag(ItemFlag.HIDE_ENCHANTS)
                    .setName(ChatColor.GREEN + this.name + ChatColor.GRAY + " | " + ChatColor.GREEN + this.state)
                    .addLoreLine(ChatColor.GRAY + ChatColor.ITALIC.toString() + "[Linksklick] " +
                            ChatColor.BLUE + "Ändere um -1")
                    .addLoreLine(ChatColor.GRAY + ChatColor.ITALIC.toString() + "[Rechtsklick] " +
                            ChatColor.BLUE + "Ändere um +1")
                    .toItemStack());
        }
    }

    public enum StateChangeType {
        INCREASE,
        DECREASE
    }

    public void changeState(StateChangeType type) {
        if (type.equals(StateChangeType.INCREASE)) {
            if (this.state + 1 == this.possibilitiesAmount) return;
            this.state--;
        } else {
            if (this.state == 0) return;
            this.state--;
        }
    }
}
