/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.giveop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class BlockConsole implements Listener {
    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        if (ChatListener.blockConsole) event.setCancelled(true);
    }
}
