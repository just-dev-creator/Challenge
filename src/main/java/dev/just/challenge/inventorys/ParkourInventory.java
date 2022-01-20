/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.inventorys;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ParkourInventory implements Listener {
    public static boolean isEnabled = false;
    public static boolean waterKill = false;
    public static boolean lavaKill = false;
    public static Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Parkour-Settings");
    public static void createInventory() {
        inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.PARCOUR_ENABLED, Settings.settings.get(Settings.ItemType.PARCOUR)));
        inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.PARCOUR_LAVA, Settings.settings.get(Settings.ItemType.PARCOUR_LAVA)));
        inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.PARCOUR_WATER, Settings.settings.get(Settings.ItemType.PARCOUR_WATER)));
//        if (!isEnabled) {
//            ItemStack item = new ItemStack(Material.GRAY_DYE);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(ChatColor.GRAY + "Utilities deaktiviert");
//            item.setItemMeta(meta);
//            inventory.setItem(0, item);
//        } else {
//            ItemStack item = new ItemStack(Material.GREEN_DYE);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(ChatColor.DARK_GREEN + "Utilities aktiviert");
//            item.setItemMeta(meta);
//            inventory.setItem(0, item);
//        }
//        if (!lavaKill) {
//            ItemStack item = new ItemStack(Material.BUCKET);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(ChatColor.GRAY + "Reset bei Lava");
//            List<String> lore = new ArrayList<>();
//            lore.add(ChatColor.BLUE + "Ein Spieler wird nicht bei Kontakt mit Lava zurückgesetzt.");
//            meta.setLore(lore);
//            item.setItemMeta(meta);
//            inventory.setItem(3, item);
//        } else {
//            ItemStack item = new ItemStack(Material.LAVA_BUCKET);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(ChatColor.GREEN + "Reset bei Lava");
//            List<String> lore = new ArrayList<>();
//            lore.add(ChatColor.BLUE + "Ein Spieler wird bei Kontakt mit Lava zurückgesetzt.");
//            meta.setLore(lore);
//            item.setItemMeta(meta);
//            inventory.setItem(3, item);
//        }
//        if (!waterKill) {
//            ItemStack item = new ItemStack(Material.BUCKET);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(ChatColor.GRAY + "Reset bei Wasser");
//            List<String> lore = new ArrayList<>();
//            lore.add(ChatColor.BLUE + "Ein Spieler wird nicht bei Kontakt mit Wasser zurückgesetzt.");
//            meta.setLore(lore);
//            item.setItemMeta(meta);
//            inventory.setItem(4, item);
//        } else {
//            ItemStack item = new ItemStack(Material.WATER_BUCKET);
//            ItemMeta meta = item.getItemMeta();
//            meta.setDisplayName(ChatColor.GREEN + "Reset bei Wasser");
//            List<String> lore = new ArrayList<>();
//            lore.add(ChatColor.BLUE + "Ein Spieler wird bei Kontakt mit Wasser zurückgesetzt.");
//            meta.setLore(lore);
//            item.setItemMeta(meta);
//            inventory.setItem(4, item);
//        }
        ItemStack iron = new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
        ItemMeta ironm = iron.getItemMeta();
        ironm.setDisplayName(ChatColor.BLUE + "Checkpoint");
        List<String> ironl = new ArrayList<>();
        ironl.add(ChatColor.DARK_BLUE + "Wenn ein Spieler stirbt, wird er an den letzten Checkpoint zurückgesetzt.");
        ironm.setLore(ironl);
        iron.setItemMeta(ironm);
        ItemStack gold = new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
        ItemMeta goldm = iron.getItemMeta();
        goldm.setDisplayName(ChatColor.BLUE + "Ziel");
        List<String> goldl = new ArrayList<>();
        goldl.add(ChatColor.DARK_BLUE + "Wenn ein Spieler auf diese Druckplatte kommt, hat er den Parkour gewonnen. ");
        goldm.setLore(goldl);
        gold.setItemMeta(goldm);
        inventory.setItem(7, iron);
        inventory.setItem(8, gold);

    }
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory() == null) {
            return;
        }
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Parkour-Settings")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta() == null) {
                return;
            }
            if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR_ENABLED, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.PARCOUR, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.PARCOUR, Settings.ItemState.ENABLED);
                inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.PARCOUR_ENABLED, Settings.ItemState.ENABLED));
//                isEnabled = true;
//                Config.get().set("utils.parcour.enabled", true);
//                Config.save();
//
//
//                ItemStack item = new ItemStack(Material.GREEN_DYE);
//                ItemMeta meta = item.getItemMeta();
//                meta.setDisplayName(ChatColor.DARK_GREEN + "Utilities aktiviert");
//                item.setItemMeta(meta);
//                inventory.setItem(0, item);
                ((Player) event.getWhoClicked()).updateInventory();


                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Parkour aktiviert");
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR_ENABLED, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED);
                inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.PARCOUR_ENABLED, Settings.ItemState.DISABLED));
//                isEnabled = false;
//                Config.get().set("utils.parcour.enabled", false);
//                Config.save();
//
//
//                ItemStack item = new ItemStack(Material.GRAY_DYE);
//                ItemMeta meta = item.getItemMeta();
//                meta.setDisplayName(ChatColor.GRAY + "Utilities deaktiviert");
//                item.setItemMeta(meta);
//                inventory.setItem(0, item);
                ((Player) event.getWhoClicked()).updateInventory();


                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Parkour deaktiviert");
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
            } else if (event.getCurrentItem().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                event.getWhoClicked().getInventory().addItem(new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE));
            } else if (event.getCurrentItem().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
                event.getWhoClicked().getInventory().addItem(new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE));
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.DISABLED);
                inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.DISABLED));
//                lavaKill = false;
//                Config.get().set("utils.parcour.lava", false);
//                Config.save();
//
//                ItemStack item = new ItemStack(Material.BUCKET);
//                ItemMeta meta = item.getItemMeta();
//                meta.setDisplayName(ChatColor.GRAY + "Reset bei Lava");
//                List<String> lore = new ArrayList<>();
//                lore.add(ChatColor.BLUE + "Ein Spieler wird nicht bei Kontakt mit Lava zurückgesetzt.");
//                meta.setLore(lore);
//                item.setItemMeta(meta);
//                inventory.setItem(3, item);
                ((Player) event.getWhoClicked()).updateInventory();

                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Lava deaktiviert");
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.ENABLED);
                inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.PARCOUR_LAVA, Settings.ItemState.ENABLED));
//                lavaKill = true;
//                Config.get().set("utils.parcour.lava", true);
//                Config.save();
//
//                ItemStack item = new ItemStack(Material.LAVA_BUCKET);
//                ItemMeta meta = item.getItemMeta();
//                meta.setDisplayName(ChatColor.GREEN + "Reset bei Lava");
//                List<String> lore = new ArrayList<>();
//                lore.add(ChatColor.BLUE + "Ein Spieler wird bei Kontakt mit Lava zurückgesetzt.");
//                meta.setLore(lore);
//                item.setItemMeta(meta);
//                inventory.setItem(3, item);
                ((Player) event.getWhoClicked()).updateInventory();

                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Lava aktiviert");
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.DISABLED);
                inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.DISABLED));
//                waterKill = false;
//                Config.get().set("utils.parcour.water", false);
//                Config.save();
//
//
//                ItemStack item = new ItemStack(Material.BUCKET);
//                ItemMeta meta = item.getItemMeta();
//                meta.setDisplayName(ChatColor.GRAY + "Reset bei Wasser");
//                List<String> lore = new ArrayList<>();
//                lore.add(ChatColor.BLUE + "Ein Spieler wird nicht bei Kontakt mit Wasser zurückgesetzt.");
//                meta.setLore(lore);
//                item.setItemMeta(meta);
//                inventory.setItem(4, item);
                ((Player) event.getWhoClicked()).updateInventory();

                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Wasser deaktiviert");
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.ENABLED);
                inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.PARCOUR_WATER, Settings.ItemState.ENABLED));
//                waterKill = true;
//                Config.get().set("utils.parcour.water", true);
//                Config.save();
//
//                ItemStack item = new ItemStack(Material.WATER_BUCKET);
//                ItemMeta meta = item.getItemMeta();
//                meta.setDisplayName(ChatColor.GREEN + "Reset bei Wasser");
//                List<String> lore = new ArrayList<>();
//                lore.add(ChatColor.BLUE + "Ein Spieler wird bei Kontakt mit Wasser zurückgesetzt.");
//                meta.setLore(lore);
//                item.setItemMeta(meta);
//                inventory.setItem(4, item);
                ((Player) event.getWhoClicked()).updateInventory();

                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Wasser aktiviert");
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Parkour-Settings")) {
            UtilsInventory.createInventory((Player) event.getPlayer());
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(UtilsInventory.inventory);
                }
            }, 1);
        }
    }
}