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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class ChallengeInventory implements Listener {
    public static Inventory inventory = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Challenges");
    private final ArrayList<Player> safe = new ArrayList<>();
    public static void createInventory() {
        inventory.setItem(0, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(1, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(2, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(3, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(4, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(5, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(6, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(7, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(8, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(9, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(13, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(17, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(18, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(19, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(20, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(21, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(22, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(23, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(24, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(25, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(26, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(27, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(35, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(36, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(37, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(38, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(39, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(40, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(41, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(42, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(43, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));
        inventory.setItem(44, Settings.getMenuItem(Settings.ItemType.PLACEHOLDER, Settings.ItemState.NONE));


        inventory.setItem(11, Settings.getMenuItem(Settings.ItemType.DEATH, Settings.settings.get(Settings.ItemType.DEATH)));
        inventory.setItem(10, Settings.getMenuItem(Settings.ItemType.DRAGON, Settings.settings.get(Settings.ItemType.DRAGON)));

        inventory.setItem(14, Settings.getMenuItem(Settings.ItemType.NOREMOVE, Settings.settings.get(Settings.ItemType.NOREMOVE)));
        inventory.setItem(28, Settings.getMenuItem(Settings.ItemType.NOINV, Settings.settings.get(Settings.ItemType.NOINV)));
        inventory.setItem(29, Settings.getMenuItem(Settings.ItemType.FORCEMATH, Settings.settings.get(Settings.ItemType.FORCEMATH)));
        inventory.setItem(30, Settings.getMenuItem(Settings.ItemType.FORCEBIOME, Settings.settings.get(Settings.ItemType.FORCEBIOME)));
        inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
        inventory.setItem(32, Settings.getMenuItem(Settings.ItemType.FACING, Settings.settings.get(Settings.ItemType.FACING)));
        inventory.setItem(33, Settings.getMenuItem(Settings.ItemType.FORCEEFFECT, Settings.settings.get(Settings.ItemType.FORCEEFFECT)));
        inventory.setItem(34, Settings.getMenuItem(Settings.ItemType.FORCEGLEICHUNG, Settings.settings.get(Settings.ItemType.FORCEGLEICHUNG)));
    }
    @EventHandler
    public void onKlick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Challenges")) {
            event.setCancelled(true);
            Player p = (Player) event.getWhoClicked();
            if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.NOINV, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.NOINV, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.NOINV, Settings.ItemState.DISABLED);
                inventory.setItem(28, Settings.getMenuItem(Settings.ItemType.NOINV, Settings.ItemState.DISABLED));
                p.updateInventory();
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung NoInv deaktiviert");
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.NOINV, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.NOINV, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.NOINV, Settings.ItemState.ENABLED);
                inventory.setItem(28, Settings.getMenuItem(Settings.ItemType.NOINV, Settings.ItemState.ENABLED));
                p.updateInventory();
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung NoInv faktiviert");
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.DRAGON, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.DRAGON, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.DRAGON, Settings.ItemState.DISABLED);
                inventory.setItem(10, Settings.getMenuItem(Settings.ItemType.DRAGON, Settings.ItemState.DISABLED));
                p.updateInventory();
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Ende Enderdrache deaktiviert");
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.DRAGON, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.DRAGON, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.DRAGON, Settings.ItemState.ENABLED);
                inventory.setItem(10, Settings.getMenuItem(Settings.ItemType.DRAGON, Settings.ItemState.ENABLED));
                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                p.updateInventory();
                Bukkit.broadcastMessage(Main.getPrefix() + "Ende Enderdrache aktiviert");
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.DEATH, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.DEATH, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.DEATH, Settings.ItemState.DISABLED);
                inventory.setItem(11, Settings.getMenuItem(Settings.ItemType.DEATH, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Ende Tod deaktiviert");
                p.updateInventory();
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.DEATH, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.DEATH, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.DEATH, Settings.ItemState.ENABLED);
                inventory.setItem(11, Settings.getMenuItem(Settings.ItemType.DEATH, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Bukkit.broadcastMessage(Main.getPrefix() + "Ende Tod aktiviert");
                p.updateInventory();
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.FORCEMATH, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.FORCEMATH, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.FORCEMATH, Settings.ItemState.DISABLED);
                inventory.setItem(29, Settings.getMenuItem(Settings.ItemType.FORCEMATH, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung ForceMath deaktiviert");
                p.updateInventory();
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.FORCEMATH, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.FORCEMATH, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.FORCEMATH, Settings.ItemState.ENABLED);
                inventory.setItem(29, Settings.getMenuItem(Settings.ItemType.FORCEMATH, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung ForceMath aktiviert");
                p.updateInventory();
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.FORCEBIOME, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.FORCEBIOME, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.FORCEBIOME, Settings.ItemState.ENABLED);
                inventory.setItem(30, Settings.getMenuItem(Settings.ItemType.FORCEBIOME, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung ForceBiome aktiviert");
                p.updateInventory();
            } else if (event.getCurrentItem().equals(Settings.getMenuItem(Settings.ItemType.FORCEBIOME, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.FORCEBIOME, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.FORCEBIOME, Settings.ItemState.DISABLED);
                inventory.setItem(30, Settings.getMenuItem(Settings.ItemType.FORCEBIOME, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung ForceBiome deaktiviert");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.FACING, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.FACING, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.FACING, Settings.ItemState.ENABLED);
                inventory.setItem(32, Settings.getMenuItem(Settings.ItemType.FACING, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Facing aktiviert. ");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.FACING, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.FACING, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.FACING, Settings.ItemState.DISABLED);
                inventory.setItem(32, Settings.getMenuItem(Settings.ItemType.FACING, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung Facing deaktivert. ");
                p.updateInventory();
            } /** ONELOOK **/
            else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.ItemLevel.NONE))) {
                if (event.getClick().isLeftClick()) {
                    p.playSound(p.getLocation(), org.bukkit.Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                } else if (event.getClick().isRightClick()) {
                    Settings.levelsettings.put(Settings.ItemType.ONELOOK, Settings.ItemLevel.ONE);
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                    Utils.sendSettingsChange(p,ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde auf Schwierigkeitslevel " + ChatColor.GREEN + "1" + ChatColor.GRAY + " gesetzt. ");
                } else if (event.getClick().isShiftClick()) {
                    Utils.sendSettingsChange(p, ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde " + ChatColor.RED + "deaktiviert. ");
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                }
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.ItemLevel.ONE))) {
                if (event.getClick().isLeftClick()) {
                    Settings.levelsettings.put(Settings.ItemType.ONELOOK, Settings.ItemLevel.NONE);
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                    Utils.sendSettingsChange(p, ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde " + ChatColor.RED + "deaktiviert. ");
                } else if (event.getClick().isRightClick()) {
                    Settings.levelsettings.put(Settings.ItemType.ONELOOK, Settings.ItemLevel.TWO);
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                    Utils.sendSettingsChange(p,ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde auf Schwierigkeitslevel " + ChatColor.GREEN + "2" + ChatColor.GRAY + " gesetzt. ");
                } else if (event.getClick().isShiftClick()) {
                    Utils.sendSettingsChange(p, ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde " + ChatColor.RED + "deaktiviert. ");
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                }
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.ItemLevel.TWO))) {
                if (event.getClick().isLeftClick()) {
                    Settings.levelsettings.put(Settings.ItemType.ONELOOK, Settings.ItemLevel.ONE);
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                    Utils.sendSettingsChange(p,ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde auf Schwierigkeitslevel " + ChatColor.GREEN + "1" + ChatColor.GRAY + " gesetzt. ");
                } else if (event.getClick().isRightClick()) {
                    p.playSound(p.getLocation(), org.bukkit.Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                } else if (event.getClick().isShiftClick()) {
                    Utils.sendSettingsChange(p, ChatColor.YELLOW + "OneLook" + ChatColor.GRAY + " wurde " + ChatColor.RED + "deaktiviert. ");
                    inventory.setItem(31, Settings.getMenuItem(Settings.ItemType.ONELOOK, Settings.levelsettings.get(Settings.ItemType.ONELOOK)));
                }
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.FORCEEFFECT, Settings.ItemState.DISABLED))) {
                Settings.settings.put(Settings.ItemType.FORCEEFFECT, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.FORCEEFFECT, Settings.ItemState.ENABLED);
                inventory.setItem(33, Settings.getMenuItem(Settings.ItemType.FORCEEFFECT, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung ForceEffect aktivert. ");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.FORCEEFFECT, Settings.ItemState.ENABLED))) {
                Settings.settings.put(Settings.ItemType.FORCEEFFECT, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.FORCEEFFECT, Settings.ItemState.DISABLED);
                inventory.setItem(33, Settings.getMenuItem(Settings.ItemType.FORCEEFFECT, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Bukkit.broadcastMessage(Main.getPrefix() + "Einschränkung ForceEffect deaktivert. ");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.DISABLED))) {
//                34
                Settings.settings.put(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.ENABLED);
                inventory.setItem(34, Settings.getMenuItem(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Utils.sendSettingsChange((Player) event.getWhoClicked(), "Einschränkung ForceGleichung aktiviert. ");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.ENABLED))) {
//                34
                Settings.settings.put(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.DISABLED);
                inventory.setItem(34, Settings.getMenuItem(Settings.ItemType.FORCEGLEICHUNG, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Utils.sendSettingsChange((Player) event.getWhoClicked(), "Einschränkung ForceGleichung deaktiviert. ");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.NOREMOVE, Settings.ItemState.ENABLED))) {
//                14
                Settings.settings.put(Settings.ItemType.NOREMOVE, Settings.ItemState.DISABLED);
                Settings.setConfig(Settings.ItemType.NOREMOVE, Settings.ItemState.DISABLED);
                inventory.setItem(14, Settings.getMenuItem(Settings.ItemType.NOREMOVE, Settings.ItemState.DISABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_POWER_SELECT);
                Utils.sendSettingsChange((Player) event.getWhoClicked(), "Einschränkung NoRemove deaktiviert. ");
                p.updateInventory();
            } else if (event.getCurrentItem().isSimilar(Settings.getMenuItem(Settings.ItemType.NOREMOVE, Settings.ItemState.DISABLED))) {
//                14
                Settings.settings.put(Settings.ItemType.NOREMOVE, Settings.ItemState.ENABLED);
                Settings.setConfig(Settings.ItemType.NOREMOVE, Settings.ItemState.ENABLED);
                inventory.setItem(14, Settings.getMenuItem(Settings.ItemType.NOREMOVE, Settings.ItemState.ENABLED));

                Utils.broadcastSound(org.bukkit.Sound.BLOCK_BEACON_ACTIVATE);
                Utils.sendSettingsChange((Player) event.getWhoClicked(), "Einschränkung NoRemove aktiviert. ");
                p.updateInventory();
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        HubInventory.createInventory();
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Challenges")) {
            if (safe.contains(event.getPlayer())) {
                return;
            }
            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(HubInventory.inventory);
                }
            }, 1);
        }
    }
}
