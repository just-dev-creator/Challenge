/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.listeners;

import dev.just.challenge.Main;
import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.BroadcastMessage;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
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
        if (damageCause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            cause = "eine Explosion";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.CONTACT)) {
            cause = "einen Kontakt";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.CRAMMING)) {
            cause = "Entity-Cramming";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.CUSTOM) &&  Settings.settings.get(Settings.ItemType.CALLDMG) == Settings.ItemState.ENABLED) {
//            Bukkit.broadcastMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
            if (Settings.settings.get(Settings.ItemType.USERSIDEYOU).equals(Settings.ItemState.ENABLED)) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (all.equals(player)) {
                        all.sendMessage(Main.getPrefix() + ChatColor.GOLD + "Du " + ChatColor.DARK_GRAY + "hast durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                    } else {
                        all.sendMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                    }
                    Bukkit.getLogger().info(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
                }
            } else {
                Bukkit.broadcastMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch ein undefinierbares Ereignis " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
            }
            return;
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.DRAGON_BREATH)) {
            cause = "den Drachen-Atem";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.DROWNING)) {
            cause = "Ertrinken";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.DRYOUT)) {
            cause = "Ersticken";
        } else  if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            cause = "einen Angriff";
            return;
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            cause = "eine Creeper-Explosion";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.FALL)) {
            cause = "Fallschaden";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.FALLING_BLOCK)) {
            cause = "einen fallenden Block";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.FIRE) || damageCause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
            cause = "Feuer";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.HOT_FLOOR)) {
            cause = "Magma-Blöcke";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.LAVA)) {
            cause = "Lava";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.LIGHTNING)) {
            cause = "einen Blitz";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.MAGIC)) {
            cause = "Magie";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.MELTING)) {
            cause = "einen Schneemann";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.POISON)) {
            cause = "Vergiftung";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            cause = "ein Projektil";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.STARVATION)) {
            cause = "Hunger";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            cause = "Erstickung";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.SUICIDE) &&  Settings.settings.get(Settings.ItemType.CALLDMG) == Settings.ItemState.ENABLED) {
            Bukkit.broadcastMessage(Main.getPrefix() + player.getName() + " hat den leichtesten Ausweg gewählt");
            return;
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.THORNS)) {
            cause = "Dornen";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.VOID)) {
            cause = "nichts";
        } else if (damageCause.equals(EntityDamageEvent.DamageCause.WITHER)) {
            cause = "den Wither";
        }
        if (Settings.settings.get(Settings.ItemType.CALLDMG) == Settings.ItemState.ENABLED) {
//            Bukkit.broadcastMessage(Main.getPrefix() + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.DARK_GRAY + " hat durch " + cause + " " + ChatColor.GOLD + damage + ChatColor.DARK_GRAY + " Herzen Schaden genommen!");
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
