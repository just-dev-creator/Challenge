package dev.just.challenge.challenge;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractChallenge {
    private final String name;
    private final ItemStack menuItem;
    private boolean isEnabled;

    protected AbstractChallenge(String challengeName, ItemStack item) {
        this.name = challengeName;
        this.menuItem = item;
    }
    protected AbstractChallenge(String challengeName, Material material) {
        this.name = challengeName;
        this.menuItem = new ItemStack(material);
    }

    public void onEnable() {

    }
    public void onDisable() {

    }

    public String getName() {
        return this.name;
    }

    public ItemStack getMenuItem() {
        return this.menuItem;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
