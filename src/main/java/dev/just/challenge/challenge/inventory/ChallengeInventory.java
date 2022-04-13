/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge.inventory;

import dev.just.challenge.challenge.AbstractChallenge;
import dev.just.challenge.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static dev.just.challenge.ChallengeAPI.challenges;

public class ChallengeInventory implements Listener {
    public static Inventory getInventory() {
        try {
            List<InventoryPage> pages = new ArrayList<>();
            pages.add(new InventoryPage(0, "Challenges | 1", null, null));
            int j = 1;
            int cpage = 0;
            for (AbstractChallenge challenge : challenges) {
                if (j > 18) {
                    cpage++;
                    pages.add(new InventoryPage(cpage, "Challenges | " + (cpage + 1), null, null));
                    j = 1;
                }
                pages.get(cpage).addEntry(challenge);
                j++;
            }
            int i = 0;
            for (InventoryPage page : pages) {
                if (i >= 1) {
                    page.setBefore(pages.get(i - 1));
                    pages.set(i, page);
                }
                if (pages.size() - 1 >= i + 1 ) {
                    page.setAfter(pages.get(i + 1));
                    pages.set(i, page);
                }
                i++;
            }
            return pages.get(0).getInventory();
        } catch (Exception e) {
            Inventory errorInventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Es ist ein " +
                    "Fehler aufgetreten!");
            ItemStack errorInfo = new ItemBuilder(Material.RED_DYE)
                    .setName(ChatColor.RED + "Es ist ein Fehler beim Laden des Inventars aufgetreten!")
                    .addLoreLine(ChatColor.GRAY + e.getMessage())
                    .toItemStack();
            ItemStack placeholder = new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                    .setName("")
                    .toItemStack();
            errorInventory.setItem(0, placeholder);
            errorInventory.setItem(1, placeholder);
            errorInventory.setItem(2, placeholder);
            errorInventory.setItem(3, placeholder);
            errorInventory.setItem(4, errorInfo);
            errorInventory.setItem(5, placeholder);
            errorInventory.setItem(6, placeholder);
            errorInventory.setItem(7, placeholder);
            errorInventory.setItem(8, placeholder);
            return errorInventory;
        }
    }
}
