package dev.just.challenge.challenge.inventory;

import dev.just.challenge.ChallengeAPI;
import dev.just.challenge.challenge.AbstractChallenge;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ChallengeInventory implements Listener {
    public static Inventory getInventory() {
        ArrayList<AbstractChallenge> challenges1 = new ArrayList<>();
        challenges1.add(ChallengeAPI.challenges.get(0));
        try {
            List<InventoryPage> pages = new ArrayList<>();
            pages.add(new InventoryPage(0, "Challenges | 1", null, null, challenges1));
            pages.add(new InventoryPage(1, "Challenges | 2", null, null));
            pages.add(new InventoryPage(2, "Challenges | 3", null, null));
            pages.add(new InventoryPage(3, "Challenges | 4", null, null));
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
