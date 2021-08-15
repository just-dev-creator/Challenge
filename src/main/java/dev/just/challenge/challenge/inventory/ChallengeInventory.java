/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge.inventory;

import dev.just.challenge.challenge.AbstractChallenge;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

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
                System.out.println(page.getTitle() + " - " + i);
                if (i >= 1) {
                    page.setBefore(pages.get(i - 1));
                    pages.set(i, page);
                    System.out.println("Set before");
                }
                if (pages.size() - 1 >= i + 1 ) {
                    System.out.println("Setting after");
                    System.out.println(pages.get(i + 1).getTitle());
                    page.setAfter(pages.get(i + 1));
                    pages.set(i, page);
                    System.out.println("Set after");
                }
                System.out.println(pages.size() + " | " + pages.get(i).getTitle());
                i++;
            }
            return pages.get(0).getInventory();
        } catch (Exception e) {
            Bukkit.getLogger().warning(e.toString());
            return null;
        }
    }
}
