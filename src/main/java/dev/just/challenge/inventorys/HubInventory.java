/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HubInventory implements Listener {
    public static Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Einstellungen");
    public static void createInventory() {
        ItemStack challanges = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta challenges_m = challanges.getItemMeta();
        challenges_m.setDisplayName(ChatColor.GREEN + "Challenges");
        challanges.setItemMeta(challenges_m);
        inventory.setItem(16, challanges);
        ItemStack utils = new ItemStack(Material.MAP);
        ItemMeta utils_m = utils.getItemMeta();
        utils_m.setDisplayName(ChatColor.YELLOW + "Utilities");
        utils.setItemMeta(utils_m);
        inventory.setItem(15, utils);
    }
    @EventHandler
    public void onKlick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getView().getTitle().equals(ChatColor.GOLD + "Einstellungen")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
                ChallengeInventory.createInventory();
                event.getWhoClicked().openInventory(ChallengeInventory.inventory);
            }
            if (event.getCurrentItem().getType() == Material.MAP) {
                UtilsInventory.createInventory((Player) event.getWhoClicked());
                event.getWhoClicked().openInventory(UtilsInventory.inventory);
            }
        }
    }
}
