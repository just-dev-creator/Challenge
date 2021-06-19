package dev.just.challenge.challenge;

import org.bukkit.inventory.ItemStack;

public interface IChallenge {

    boolean isEnabled();

    void restoreDefaults();

    void handleShutdown();

    String getName();

    MenuType getType();

    ItemStack getDisplayItem();

    ItemStack getSettingsItem();
}
