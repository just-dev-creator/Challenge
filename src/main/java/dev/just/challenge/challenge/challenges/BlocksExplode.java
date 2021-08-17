/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge.challenges;

import dev.just.challenge.challenge.AbstractChallenge;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class BlocksExplode extends AbstractChallenge implements Listener {
    private List<Material> forbiddenBlocks = new ArrayList<>();

    public BlocksExplode() {
        super("BlocksExplode", Material.TNT_MINECART);
    }

    @Override
    public void onEnable() {
        setDefaults();
        List<String> forbiddenBlockNames = (List<String>) this.getConfig("forbiddenBlocks");
        forbiddenBlockNames.forEach(name -> {
            try {
                forbiddenBlocks.add(Material.getMaterial(name));
            } catch (Exception ignored) {}
        });
    }

    @Override
    public void onDisable() {
    }

    private void setDefaults() {
        if (!this.containsConfig("forbiddenBlocks")) {
            List<String> blockNames = new ArrayList<>();
            List<Material> blocks = new ArrayList<>();
            blocks.add(Material.GRASS_BLOCK);
            blocks.add(Material.GRANITE);
            blocks.add(Material.DIORITE);
            blocks.add(Material.ANDESITE);
            blocks.add(Material.OBSIDIAN);
            blocks.forEach(block -> {
                blockNames.add(block.name());
            });
            this.setConfig("forbiddenBlocks", blockNames);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.isActive() || !event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) return;
        if (this.forbiddenBlocks.contains(event.getPlayer().getLocation().getBlock().
                getRelative(BlockFace.DOWN).getType())) {
            event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.PRIMED_TNT);
        }
    }
}
