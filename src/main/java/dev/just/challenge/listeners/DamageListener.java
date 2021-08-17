/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.listeners;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!TimerCommand.timer_active) {
            event.setCancelled(true);
            return;
        }
        Player player = (Player) event.getEntity();
        double damage = (event.getFinalDamage()/2);
        EntityDamageEvent.DamageCause damageCause = event.getCause();
        String cause = null;
        switch (damageCause) {
            case BLOCK_EXPLOSION: {
                cause = "eine Explosion";
            } case CONTACT: {
                cause = "einen Kontakt";
            } case CRAMMING: {
                cause = "Entity-Cramming";
            } case CUSTOM: {
                if (Settings.settings.get(Settings.ItemType.CALLDMG) == Settings.ItemState.ENABLED) {
                    if (Settings.settings.get(Settings.ItemType.USERSIDEYOU).equals(Settings.ItemState.ENABLED)) {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (all.equals(player)) {
                                all.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Du " + ChatColor.DARK_GRAY +
                                        "hast durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage +
                                        ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                            } else {
                                all.sendMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD +
                                        player.getName() + ChatColor.DARK_GRAY +
                                        " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage +
                                        ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                            }
                            Bukkit.getLogger().info(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD +
                                    player.getName() + ChatColor.DARK_GRAY +
                                    " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage +
                                    ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                        }
                    } else {
                        Bukkit.broadcastMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() +
                                ChatColor.DARK_GRAY + " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD +
                                damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                    }
                }
            } case DRAGON_BREATH: {
                cause = "den Drachen-Atem";
            } case DROWNING: {
                cause = "Ertrinken";
            } case DRYOUT: {
                cause = "Ersticken";
            } case ENTITY_ATTACK: {
                cause = "einen Angriff";
            } case ENTITY_EXPLOSION: {
                cause = "eine Explosion";
            } case FALL: {
                cause = "Fallschaden";
            } case FALLING_BLOCK: {
                cause = "einen fallenden Block";
            } case FIRE: {
                cause = "Feuer";
            } case FIRE_TICK: {
                cause = "Verbrennung";
            } case HOT_FLOOR: {
                cause = "Magma-Blöcke";
            } case LAVA: {
                cause = "Lava";
            } case LIGHTNING: {
                cause = "einen Blitz";
            } case MAGIC: {
                cause = "Magie";
            } case MELTING: {
                cause = "einen Schneemann";
            } case POISON: {
                cause = "Vergiftung";
            } case PROJECTILE: {
                cause = "ein Projektil";
            } case STARVATION: {
                cause = "Hunger";
            } case SUFFOCATION: {
                cause = "Erstickung";
            } case SUICIDE: {
                if (Settings.settings.get(Settings.ItemType.CALLDMG).equals(Settings.ItemState.ENABLED)) {
                    Bukkit.broadcastMessage(Main.getPrefix() + player.getName() + " hat den leichtesten Ausweg gewählt");
                    return;
                }
            } case THORNS: {
                cause = "Dornen";
            } case VOID: {
                cause = "das Nichts";
            } case WITHER: {
                cause = "den Wither";
            } default: {
                cause = damageCause.name();
            }
        }
        if (Settings.settings.get(Settings.ItemType.CALLDMG) == Settings.ItemState.ENABLED) {
            if (Settings.settings.get(Settings.ItemType.USERSIDEYOU).equals(Settings.ItemState.ENABLED)) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.equals(player)) {
                        all.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Du" + ChatColor.DARK_GRAY + " hast durch " + cause + " " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                    } else {
                        all.sendMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch " + cause + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                    }
                    Bukkit.getLogger().info(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch " + cause + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                }
            } else {
                Bukkit.broadcastMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch " + cause + " " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
            }
        }
    }
}
