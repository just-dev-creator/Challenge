/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.listeners;

import dev.just.challenge.commands.TimerCommand;
import dev.just.challenge.utils.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!TimerCommand.timer_active && !Settings.uuids.contains(event.getPlayer().getUniqueId().toString())) {
            event.setCancelled(true);
        }
    }
}
