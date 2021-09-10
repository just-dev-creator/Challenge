/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.inventorys;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Sound;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class UtilsInventory implements Listener {
    public static Inventory inventory = Bukkit.createInventory(null, 9, ChatColor.YELLOW + "Utilities");
    private final ArrayList<Player> safe = new ArrayList<>();
    public static void createInventory(Player p) {
            inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.CALLDMG, Settings.settings.get(Settings.ItemType.CALLDMG)));
            inventory.setItem(2, Settings.getMenuItem(Settings.ItemType.PARCOUR, Settings.settings.get(Settings.ItemType.PARCOUR)));
            inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.USERSIDEYOU, Settings.settings.get(Settings.ItemType.USERSIDEYOU)));
            inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.DORFSPAWN, Settings.settings.get(Settings.ItemType.DORFSPAWN)));
//            ItemStack damage_call = new ItemStack(Material.BELL);
//            ItemMeta damage_call_m = damage_call.getItemMeta();
//            damage_call_m.setDisplayName(ChatColor.GOLD + "Schadens-Hinweis");
//            damage_call.setItemMeta(damage_call_m);
//            inventory.setItem(0, damage_call);
//        } else if (!callDamage) {
//            ItemStack damage_call = new ItemStack(Material.BELL);
//            ItemMeta damage_call_m = damage_call.getItemMeta();
//            damage_call_m.setDisplayName(ChatColor.GRAY + "Schadens-Hinweis");
//            damage_call.setItemMeta(damage_call_m);
//            inventory.setItem(0, damage_call);
//        }
        if (p.getWorld().getGameRuleValue(GameRule.NATURAL_REGENERATION)) {
            ItemStack uhc = new ItemStack(Material.GOLDEN_APPLE);
            ItemMeta uhc_m = uhc.getItemMeta();
            uhc_m.setDisplayName(ChatColor.GRAY + "Keine Regeneration");
            uhc.setItemMeta(uhc_m);
            inventory.setItem(1, uhc);
        } else if (!p.getWorld().getGameRuleValue(GameRule.NATURAL_REGENERATION)) {
            ItemStack uhc = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
            ItemMeta uhc_m = uhc.getItemMeta();
            uhc_m.setDisplayName(ChatColor.GOLD + "Keine Regeneration");
            uhc.setItemMeta(uhc_m);
            inventory.setItem(1, uhc);
        }
    }
    @EventHandler
    public void onKlick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getView().getTitle().equals(ChatColor.YELLOW + "Utilities")) {
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.CALLDMG, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.CALLDMG, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.CALLDMG, Settings.ItemState.ENABLED);
                inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.CALLDMG, Settings.ItemState.ENABLED));
                player.updateInventory();
                Bukkit.broadcastMessage(Main.getPrefix() + "Schadens-Hinweis aktiviert!");
//                callDamage = false;
//                Config.get().set("utils.callDamage", false);
//                Config.save();
//                ItemStack damage_call = new ItemStack(Material.BELL);
//                ItemMeta damage_call_m = damage_call.getItemMeta();
//                damage_call_m.setDisplayName(ChatColor.GRAY + "Schadens-Hinweis");
//                damage_call.setItemMeta(damage_call_m);
//                event.getInventory().setItem(0, damage_call);
            }
            else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.CALLDMG, Settings.ItemState.ENABLED))){
                Settings.settings.put(Settings.ItemType.CALLDMG, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.CALLDMG, Settings.ItemState.DISABLED);
                inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.CALLDMG, Settings.ItemState.DISABLED));
                player.updateInventory();
                Bukkit.broadcastMessage(Main.getPrefix() + "Schadens-Hinweis deaktiviert!");
//                callDamage = true;
//                Config.get().set("utils.callDamage", true);
//                Config.save();
//                ItemStack damage_call = new ItemStack(Material.BELL);
//                ItemMeta damage_call_m = damage_call.getItemMeta();
//                damage_call_m.setDisplayName(ChatColor.GOLD + "Schadens-Hinweis");
//                damage_call.setItemMeta(damage_call_m);
//                event.getInventory().setItem(0, damage_call);
//                player.updateInventory();
//                Bukkit.broadcastMessage(Main.getPrefix() + "Schadens-Hinweis aktiviert!");
            }
            else if (event.getCurrentItem().getType() == Material.GOLDEN_APPLE && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Keine Regeneration")) {
                player.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
                ItemStack uhc = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
                ItemMeta uhc_m = uhc.getItemMeta();
                uhc_m.setDisplayName(ChatColor.GOLD + "Keine Regeneration");
                uhc.setItemMeta(uhc_m);
                event.getInventory().setItem(1, uhc);
                player.updateInventory();
                Bukkit.broadcastMessage(Main.getPrefix() + "UHC aktiviert!");
            }
            else if (event.getCurrentItem().getType() == Material.ENCHANTED_GOLDEN_APPLE && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Keine Regeneration")) {
                player.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, true);
                ItemStack uhc = new ItemStack(Material.GOLDEN_APPLE);
                ItemMeta uhc_m = uhc.getItemMeta();
                uhc_m.setDisplayName(ChatColor.GRAY + "Keine Regeneration");
                uhc.setItemMeta(uhc_m);
                event.getInventory().setItem(1, uhc);
                player.updateInventory();
                Bukkit.broadcastMessage(Main.getPrefix() + "UHC deaktiviert!");
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED))) {
                if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                    Settings.settings.put(Settings.ItemType.PARCOUR, Settings.ItemState.ENABLED);
                    Settings.setConfig(Settings.ItemType.PARCOUR, Settings.ItemState.ENABLED);
                    inventory.setItem(2, Settings.getMenuItem(Settings.ItemType.PARCOUR, Settings.ItemState.ENABLED));
                    player.updateInventory();
                    Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Parkour aktiviert");
                    Sound.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                    event.setCancelled(true);
                    return;
                }
                safe.add(player);
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                    @Override
                    public void run() {
                        safe.remove(player);
                    }
                }, 2);
                ParkourInventory.createInventory();
                player.openInventory(ParkourInventory.inventory);
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.PARCOUR, Settings.ItemState.ENABLED))) {
                if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                    Settings.settings.put(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED);
                    Settings.setConfig(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED);
                    inventory.setItem(2, Settings.getMenuItem(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED));
                    player.updateInventory();
                    Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Parkour deaktiviert");
                    Sound.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                    event.setCancelled(true);
                    return;
                }
                safe.add(player);
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                    @Override
                    public void run() {
                        safe.remove(player);
                    }
                }, 2);
                ParkourInventory.createInventory();
                player.openInventory(ParkourInventory.inventory);
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.USERSIDEYOU, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.USERSIDEYOU, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.USERSIDEYOU, Settings.ItemState.ENABLED);
                inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.USERSIDEYOU, Settings.ItemState.ENABLED));
                player.updateInventory();

                player.sendMessage(Main.getPrefix() + "User-Seitiges Du aktiviert! ");
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.USERSIDEYOU, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.USERSIDEYOU, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.USERSIDEYOU, Settings.ItemState.DISABLED);
                inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.USERSIDEYOU, Settings.ItemState.DISABLED));
                player.updateInventory();

                player.sendMessage(Main.getPrefix() + "User-Seitiges Du deaktiviert! ");
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.DORFSPAWN, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.DORFSPAWN, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.DORFSPAWN, Settings.ItemState.ENABLED);
                inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.DORFSPAWN, Settings.ItemState.ENABLED));
                player.updateInventory();
                Utils.sendSettingsChange(player, "Dorfspawn aktiviert");
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.DORFSPAWN, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.DORFSPAWN, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.DORFSPAWN, Settings.ItemState.DISABLED);
                inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.DORFSPAWN, Settings.ItemState.DISABLED));
                player.updateInventory();
                Utils.sendSettingsChange(player, "Dorfspawn deaktiviert");
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.YELLOW + "Utilities")) {
            if (safe.contains(event.getPlayer())) {
                return;
            }
            HubInventory.createInventory();
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(HubInventory.inventory);
                }
            }, 1);
        }
    }
}
