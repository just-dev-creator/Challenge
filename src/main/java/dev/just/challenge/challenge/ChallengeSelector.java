package dev.just.challenge.challenge;

import dev.just.challenge.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class ChallengeSelector implements Listener {
    protected final int max_amount_of_challenges;
    protected final String title;
    protected final ItemStack last_page_item;
    protected final ItemStack next_page_item;
    protected List<ItemStack> challengeSelectorItems;
    protected List<List<Inventory>> pages;

    public ChallengeSelector(String title) {
        this.title = title;
        this.max_amount_of_challenges = 35;
        this.last_page_item = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName(ChatColor.RED + "<-").toItemStack();
        this.next_page_item = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName(ChatColor.GREEN + "->").toItemStack();
        this.pages = new ArrayList<>();
        this.challengeSelectorItems = new ArrayList<>();
    }

    protected abstract void onSelectorClick(PlayerInteractEvent event);
    protected abstract void onInventoryClick(InventoryClickEvent event);

    public void load() {
        challengeSelectorItems.clear();

    }
}
