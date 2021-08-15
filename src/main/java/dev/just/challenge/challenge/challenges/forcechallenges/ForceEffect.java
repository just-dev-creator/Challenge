/*
 * Copyright (c) 2021-2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.challenge.challenges.forcechallenges;

import dev.just.challenge.challenge.AbstractForceChallenge;
import dev.just.challenge.utils.ShortString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ForceEffect extends AbstractForceChallenge {
    public ForceEffect() {
        super("ForceEfect", Material.GLASS_BOTTLE);
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.YELLOW + "Enabld");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.YELLOW + "Disabled");
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
        ArrayList<PotionEffectType> effectTypes = new ArrayList<>();
        byte b1;
        int j;
        PotionEffectType[] arrayofEffects;
        for (j = (arrayofEffects = PotionEffectType.values()).length, b1 = 0; b1 < j; ) {
            PotionEffectType effect = arrayofEffects[b1];
            effectTypes.add(effect);
            b1++;
        }
        return effectTypes;
    }

    @Override
    public String getTitle(Object upcoming) {
        return ChatColor.GRAY + "Nächster Effekt: " + ChatColor.GREEN + getObjectName(upcoming);
    }

    @Override
    public String getObjectName(Object upcoming) {
        String effectNameBefore = ((PotionEffectType) upcoming).getName();
        String[] effectNameSplit = effectNameBefore.split("_");
        return ShortString.run(effectNameSplit, false, true);
    }

    @Override
    public String getTaskMessage(String upcomingName) {
        return ChatColor.GRAY + "Erhalte den Effekt " + ChatColor.GREEN + upcomingName;
    }

    @Override
    public boolean isFinished(Object currentTask) {
        PotionEffectType effectType = (PotionEffectType) currentTask;
        AtomicBoolean someoneHas = new AtomicBoolean(false);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getActivePotionEffects().forEach(potionEffect -> {
                if (potionEffect.getType().equals(effectType)) {
                    someoneHas.set(true);
                }
            });
        }
        return someoneHas.get();
    }
}