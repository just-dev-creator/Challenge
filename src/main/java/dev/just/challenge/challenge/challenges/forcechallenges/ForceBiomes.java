/*
 * Copyright (c) 2022-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge.challenges.forcechallenges;

import dev.just.challenge.challenge.AbstractForceChallenge;
import dev.just.challenge.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ForceBiomes extends AbstractForceChallenge {
    public ForceBiomes() {
        super("ForceBiomes", Material.GRASS_BLOCK);
    }

    @Override
    public void onEnable() {
        this.log("Die Challenge wurde aktiviert. ");
    }

    @Override
    public void onDisable() {
        this.log("Die Challenge wurde deaktiviert. ");
    }

    @Override
    public int firstTaskMinTime() {
        return 120;
    }

    @Override
    public int firstTaskMaxTime() {
        return 180;
    }

    @Override
    public int midTaskMinTime() {
        return 90;
    }

    @Override
    public int midTaskMaxTime() {
        return 120;
    }

    @Override
    public int finishTaskMinTime() {
        return 200;
    }

    @Override
    public int finishTaskMaxTime() {
        return 300;
    }

    @Override
    public ArrayList allPosibilities() {
        ArrayList<Biome> biomeTypes = new ArrayList<>();
        byte b1;
        int j;
        Biome[] arrayofBiomes;
        for (j = (arrayofBiomes = Biome.values()).length, b1 = 0; b1 < j; ) {
            Biome biome = arrayofBiomes[b1];
            if (biome != Biome.END_BARRENS &&
                    biome != Biome.END_HIGHLANDS && biome != Biome.END_MIDLANDS)
                biomeTypes.add(Biome.valueOf(biome.toString()));
            b1++;
        }
        return biomeTypes;
    }

    @Override
    public String getTitle(Object upcoming) {
        if (upcoming == null) return null;
        return ChatColor.GRAY + "Nächstes Biom: " + ChatColor.GREEN + getObjectName(upcoming);
    }

    @Override
    public String getObjectName(Object upcoming) {
        if (upcoming == null) return null;
        String biomeNameBefore = ((Biome) upcoming).getKey().getKey();
        String[] biomeNameSplit = biomeNameBefore.split("_");
        return Utils.shortString(biomeNameSplit, true, true);
    }

    @Override
    public String getTaskMessage(String upcomingName) {
        if (upcomingName == null) return null;
        return ChatColor.GRAY + "Gehe in das Biom " + ChatColor.GREEN + upcomingName;
    }

    @Override
    public boolean isFinished(Object currentTask) {
        if (currentTask == null) return false;
        Biome biomeType = (Biome) currentTask;
        for (Player player : Utils.getActivePlayers()) {
            if (!player.getLocation().getWorld().getBiome(player.getLocation().getBlockX(),
                    player.getLocation().getBlockY(), player.getLocation().getBlockZ()).equals(biomeType)) {
                return false;
            }
        }
        return true;
    }
}
