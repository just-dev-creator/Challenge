/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge.inventory;

import dev.just.challenge.Main;
import dev.just.challenge.challenge.AbstractChallenge;
import dev.just.challenge.challenge.AbstractOptionChallenge;
import dev.just.challenge.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage implements Listener {
    private final int number;
    private final String title;
    private InventoryPage before;
    private InventoryPage after;
    private List<AbstractChallenge> entries = new ArrayList<>();
    private final int slots;
    private final ItemStack page_before = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
            .setName(ChatColor.GREEN + "Gehe eine Seite zurück")
            .toItemStack();
    private final ItemStack page_after = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
            .setName(ChatColor.GREEN + "Gehe eine Seite vor")
            .toItemStack();
    private final ItemStack page_error = new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
            .setName(ChatColor.RED + "Es gibt keine weiteren Seiten")
            .toItemStack();

    /**
     * @param pageNumber The number of the page. F. ex. 1 or 3
     * @param title The title of the inventory
     * @param before The page that comes before this page. Can be null.
     * @param after The page that comes after this page. Cam be null
     */
    public InventoryPage(int pageNumber, String title, InventoryPage before, InventoryPage after) {
        this.number = pageNumber;
        this.title = title;
        this.before = before;
        this.after = after;
        this.slots = 18;
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin(Main.class));
    }

    /**
     * @param pageNumber The number of the page. F. ex. 1 or 3
     * @param title The title of the inventory
     * @param before The page that comes before this page. Can be null.
     * @param after The page that comes after this page. Cam be null
     * @param slots The amount of slots that you can control
     */
    public InventoryPage(int pageNumber, String title, InventoryPage before, InventoryPage after, int slots) {
        this.number = pageNumber;
        this.title = title;
        this.before = before;
        this.after = after;
        this.slots = slots;
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin(Main.class));
    }
    /**
     * @param pageNumber The number of the page. F. ex. 1 or 3
     * @param title The title of the inventory
     * @param before The page that comes before this page. Can be null.
     * @param after The page that comes after this page. Cam be null
     * @param entries The challenges that will be displayed. May not be more than 18
     */
    public InventoryPage(int pageNumber, String title, InventoryPage before, InventoryPage after, List<AbstractChallenge> entries) {
        this.number = pageNumber;
        this.title = title;
        this.before = before;
        this.after = after;
        this.entries = entries;
        this.slots = 18;
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin(Main.class));
    }
    /**
     * @param pageNumber The number of the page. F. ex. 1 or 3
     * @param title The title of the inventory
     * @param before The page that comes before this page. Can be null.
     * @param after The page that comes after this page. Cam be null
     * @param entries The challenges that will be displayed. May not be more than the given max slots
     * @param slots The amount of slots that you can control
     */
    public InventoryPage(int pageNumber, String title, InventoryPage before, InventoryPage after, List<AbstractChallenge> entries, int slots) {
        this.number = pageNumber;
        this.title = title;
        this.before = before;
        this.after = after;
        this.entries = entries;
        this.slots = slots;
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin(Main.class));
    }

    public void setEntries(List<AbstractChallenge> entries) {
        this.entries = entries;
    }
    public List<AbstractChallenge> getEntries() {
        return entries;
    }

    public void addEntry(AbstractChallenge entry) {
        this.entries.add(entry);
    }
    public void removeEntry(AbstractChallenge entry) {
        this.entries.remove(entry);
    }

    public void setBefore(InventoryPage before) {
        this.before = before;
    }
    public void setAfter(InventoryPage after) {
        this.after = after;
    }

    public String getTitle() {
        return title;
    }
    public int getNumber() {
        return number;
    }

    public void open(Player player) throws Exception {
        player.openInventory(getInventory());
    }
    public Inventory getInventory() throws Exception {
        if (this.entries.size() > this.slots) throw new Exception("Too many items!");
        Inventory inventory = Bukkit.createInventory(null, slots + 9, this.title);
        int i = 0;
        for (AbstractChallenge challenge : this.entries) {
            inventory.setItem(i, challenge.getMenuItem());
            i++;
        }
        if (this.before == null) {
            inventory.setItem(this.slots, this.page_error);
        } else {
            inventory.setItem(this.slots, this.page_before);
        }
        if (this.after == null) {
            inventory.setItem(this.slots + 8, this.page_error);
        } else {
            inventory.setItem(this.slots + 8, this.page_after);
        }
        return inventory;
    }
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(this.title)) return;
        if (event.getCurrentItem() == null) return;
        HumanEntity player = event.getWhoClicked();
        event.setCancelled(true);
        if (event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getLore() != null &&
                event.getCurrentItem().getItemMeta().
                getLore().get(0).contains(" | ")) {
            for (AbstractChallenge entry : this.entries) {
                if (!(entry instanceof AbstractOptionChallenge)) return;
                AbstractOptionChallenge challenge = (AbstractOptionChallenge) entry;
                if (event.getCurrentItem().isSimilar(challenge.getMenuItem())) {
                    if (event.getClick().isLeftClick()) {
                        challenge.changeState(AbstractOptionChallenge.StateChangeType.DECREASE);
                    } else if (event.getClick().isRightClick()) {
                        challenge.changeState(AbstractOptionChallenge.StateChangeType.INCREASE);
                    }
                }
            }
        } else {
            for (AbstractChallenge entry : this.entries) {
                if (event.getCurrentItem().isSimilar(entry.getMenuItem())) {
                    if (entry.isEnabled) entry.disable();
                    else entry.enable();
                    try {
                        player.openInventory(this.getInventory());
                    } catch (Exception ignored) {}
                }
            }
        }
        if (event.getCurrentItem().isSimilar(this.page_before)) {
            try {
                player.openInventory(before.getInventory());
            } catch (Exception ignored) {}
        }
        if (event.getCurrentItem().isSimilar(this.page_after)) {
            try {
                player.openInventory(after.getInventory());
            } catch (Exception ignored) {}
        }
    }
}
