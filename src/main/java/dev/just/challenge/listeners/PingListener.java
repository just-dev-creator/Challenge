/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.listeners;

import dev.just.challenge.utils.Config;
import dev.just.challenge.utils.Settings;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {
    @EventHandler
    public void onPing(ServerListPingEvent event) {
//        if (!Config.contains("partymode")) Config.set("partymode", false);
//        if (Config.getBoolean("partymode")) {
//            event.setMotd(ChatColor.YELLOW + "Willkommen zur " + ChatColor.BOLD + "MINECRAFT PARTY \n" + ChatColor.RESET + ChatColor.BLUE + "Hab deine IP und DDose dich jz: " + ChatColor.BLUE + event.getAddress().getHostAddress());
//        } else {
            event.setMotd(ChatColor.BLUE + "Willkommen zu der gediegenen \nMinecraft-Unterhaltung. Deine IP: " + event.getAddress().getHostAddress());
//        }
    }
}
