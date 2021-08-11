/*
 * Copyright (c) 2021. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Settings {
    public static HashMap<ItemType, ItemState> settings = new HashMap<>();
    public static HashMap<ItemType, ItemLevel> levelsettings = new HashMap<>();
    public static List<String> uuids = new ArrayList<>();

    /**
     * @param type Type of the item
     * @param state State of the item
     * @return Item for an inventory
     */
    public static ItemStack getMenuItem(ItemType type, ItemState state) {
        ItemStack itemStack = new ItemStack(Material.BEDROCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "An internal error occured.");
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(ChatColor.RED + "Bitte informiere mich über den Bug auf Discord und gib folgendes an: ");
        itemLore.add(ChatColor.RED + "Type: " + type.toString());
        itemLore.add(ChatColor.RED + "State: " + state.toString());
//        itemLore.add(ChatColor.RED + "Bitte melde den Bug an bugreport@jutils.de");
        if(type == ItemType.CALLDMG) {
            itemLore.clear();
            itemStack.setType(Material.BELL);
            if(state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "Schadensanzeige");
                itemLore.add(ChatColor.GRAY + "Schaden wird nicht im Chat angezeigt. ");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "Schadensanzeige");
                itemLore.add(ChatColor.GRAY + "Schaden wird im Chat angezeigt. ");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.DRAGON) {
            itemLore.clear();
            itemStack.setType(Material.DRAGON_HEAD);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "Drachentod");
                itemLore.add(ChatColor.GRAY + "Die Challenge ist nicht beim Tod des Enderdrachens abgeschlossen.");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "Drachentod");
                itemLore.add(ChatColor.GRAY + "Die Challenge ist beim Tod des Enderdrachens abgeschlossen.");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.PARCOUR) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.SLIME_BALL);
                itemMeta.setDisplayName(ChatColor.RED + "Parkour-Utils");
                itemLore.add(ChatColor.GRAY + "Die Parkour-Utilities sind nicht aktiv.");
                itemLore.add(" ");
                itemLore.add("§7§o[Klick] §eMenü öffnen");
                itemLore.add("§7§o[Shift-Klick] §aAktivieren");
            } else {
                itemMeta.setDisplayName(ChatColor.RED + "Parkour-Utils");
                itemStack.setType(Material.SLIME_BLOCK);
                itemLore.add(ChatColor.GRAY + "Die Parkour-Utilities sind aktiv.");
                itemLore.add(" ");
                itemLore.add("§7§o[Klick] §eMenü öffnen");
                itemLore.add("§7§o[Shift-Klick]" + ChatColor.RED +" Deaktivieren");
                itemLore.add(" ");
                if (settings.get(ItemType.PARCOUR_WATER).equals(ItemState.ENABLED)) {
                    itemLore.add(ChatColor.GRAY + "Ein Spieler wird bei Kontakt mit Wasser zurückgesetzt");
                } else {
                    itemLore.add(ChatColor.GRAY + "Ein Spieler wird nicht bei Kontakt mit Wasser zurückgesetzt");
                }
                if (settings.get(ItemType.PARCOUR_LAVA).equals(ItemState.ENABLED)) {
                    itemLore.add(ChatColor.GRAY + "Ein Spieler wird bei Kontakt mit Lava zurückgesetzt");
                } else {
                    itemLore.add(ChatColor.GRAY + "Ein Spieler wird nicht bei Kontakt mit Lava zurückgesetzt");
                }
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.PARCOUR_LAVA) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.BUCKET);
                itemMeta.setDisplayName(ChatColor.RED + "Lava-Tod");
                itemLore.add(ChatColor.GRAY + "Ein Spieler wird nicht bei Kontakt mit Lava zurückgesetzt");
            } else {
                itemStack.setType(Material.LAVA_BUCKET);
                itemMeta.setDisplayName(ChatColor.GREEN + "Lava-Tod");
                itemLore.add(ChatColor.GRAY + "Ein Spieler wird bei Kontakt mit Lava zurückgesetzt");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.DEATH) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.RED_DYE);
                itemMeta.setDisplayName(ChatColor.RED + "Ende bei Tod");
                itemLore.add(ChatColor.GRAY + "Die Challenge ist nicht beendet, wenn ein Spieler stirbt.");
            } else {
                itemStack.setType(Material.RED_DYE);
                itemMeta.setDisplayName(ChatColor.RED + "Ende bei Tod");
                itemLore.add(ChatColor.GRAY + "Die Challenge ist beendet, wenn ein Spieler stirbt.");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.PARCOUR_WATER) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.BUCKET);
                itemMeta.setDisplayName(ChatColor.RED + "Wasser-Tod");
                itemLore.add(ChatColor.GRAY + "Ein Spieler wird nicht bei Kontakt mit Wasser zurückgesetzt");
            } else {
                itemStack.setType(Material.WATER_BUCKET);
                itemMeta.setDisplayName(ChatColor.RED + "Wasser-Tod");
                itemLore.add(ChatColor.GRAY + "Ein Spieler wird bei Kontakt mit Wasser zurückgesetzt");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.PARCOUR_ENABLED) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.GRAY_DYE);
                itemMeta.setDisplayName(ChatColor.RED + "Parkour-Utils");
                itemLore.add(ChatColor.GRAY + "Die Parkour-Utilities sind nicht aktiv.");
            } else {
                itemStack.setType(Material.LIME_DYE);
                itemMeta.setDisplayName(ChatColor.GREEN + "Parkour-Utils");
                itemLore.add(ChatColor.GRAY + "Die Parkour-Utilities sind aktiv.");
            }
        }
        else if (type == ItemType.NOINV) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.CHEST);
                itemMeta.setDisplayName(ChatColor.RED + "NoInventory");
                itemLore.add(ChatColor.GRAY + "Ein Spieler stirbt nicht, wenn er ein Inventar öffnet oder es benutzt.");
            } else {
                itemStack.setType(Material.ENDER_CHEST);
                itemMeta.setDisplayName(ChatColor.GREEN + "NoInventory");
                itemLore.add(ChatColor.GRAY + "Ein Spieler stirbt, wenn er ein Inventar öffnet oder es benutzt.");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.PLACEHOLDER) {
            itemLore.clear();
            itemStack.setType(Material.GRAY_STAINED_GLASS_PANE);
            itemMeta.setDisplayName(" ");
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        else if (type == ItemType.FORCEMATH) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.MOJANG_BANNER_PATTERN);
                itemMeta.setDisplayName(ChatColor.RED + "MathForce");
                itemLore.add(ChatColor.GRAY + "Es wird nicht in zufälligen Abständen eine Matheaufgabe gestellt");
            } else {
                itemStack.setType(Material.MOJANG_BANNER_PATTERN);
                itemMeta.setDisplayName(ChatColor.GREEN + "MathForce");
                itemLore.add(ChatColor.GRAY + "Es wird in zufälligen Abständen eine Matheaufgabe gestellt");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.FORCEBIOME) {
            itemLore.clear();
            if (state == ItemState.DISABLED) {
                itemStack.setType(Material.GRASS_BLOCK);
                itemMeta.setDisplayName(ChatColor.RED + "ForceBiome");
                itemLore.add(ChatColor.GRAY + "Es werden nicht zufällige Anweisungen gestellt.");
            } else {
                itemStack.setType(Material.GRASS_BLOCK);
                itemMeta.setDisplayName(ChatColor.GREEN + "ForceBiome");
                itemLore.add(ChatColor.GRAY + "Es werden zufällige Anweisungen gestellt.");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.USERSIDEYOU) {
            itemLore.clear();
            itemStack.setType(Material.PLAYER_HEAD);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "Userseitiges Du");
                itemLore.add(ChatColor.GRAY + "Der Spielername ist für alle User sichtbar");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "Userseitiges Du");
                itemLore.add(ChatColor.GRAY + "User werden statt ihrem Spielernamen geduzt");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.FACING) {
            itemLore.clear();
            itemStack.setType(Material.COMPASS);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "Facing");
                itemLore.add(ChatColor.GRAY + "Du verlierst kein halbes Herz, wenn du dein Facing änderst. ");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "Facing");
                itemLore.add(ChatColor.GRAY + "Du verlierst ein halbes Herz, wenn du dein Facing änderst. ");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.FORCEEFFECT) {
            itemLore.clear();
            itemStack.setType(Material.GLASS_BOTTLE);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "ForceEffect");
                itemLore.add(ChatColor.GRAY + "Es werden keine zufällige Anweisungen gestellt. ");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "ForceEffect");
                itemLore.add(ChatColor.GRAY + "Es werden zufällige Anweisungen gestellt. ");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.FORCEGLEICHUNG) {
            itemLore.clear();
            itemStack.setType(Material.PAPER);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "ForceGleichung");
                itemLore.add(ChatColor.GRAY + "Es müssen keine zufälligen Gleichungen gelöst werden. ");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "ForceGleichung");
                itemLore.add(ChatColor.GRAY + "Es müssen zufällige Gleichungen gelöst werden. ");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.NOREMOVE) {
            itemLore.clear();
            itemStack.setType(Material.LEAD);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "NoRemove");
                itemLore.add(ChatColor.GRAY + "Es dürfen Items entfernt werden. ");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "NoRemove");
                itemLore.add(ChatColor.GRAY + "Es dürfen keine Items entfernt werden. ");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        else if (type == ItemType.DORFSPAWN) {
            itemLore.clear();
            itemStack.setType(Material.OAK_PLANKS);
            if (state == ItemState.DISABLED) {
                itemMeta.setDisplayName(ChatColor.RED + "DorfSpawn");
                itemLore.add(ChatColor.GRAY + "Es wird nicht jeder Spieler immer in einem Dorf spawnen. ");
            } else {
                itemMeta.setDisplayName(ChatColor.GREEN + "DorfSpawn");
                itemLore.add(ChatColor.GRAY + "Es wird jeder Spieler immer in einem Dorf spawnen. ");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getMenuItem(ItemType type, ItemLevel level) {
        ItemStack itemStack = new ItemStack(Material.BEDROCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "An internal error occured.");
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(ChatColor.RED + "Bitte informiere mich über den Bug auf Discord und gib folgendes an: ");
        itemLore.add(ChatColor.RED + "Type: " + type.toString());
        itemLore.add(ChatColor.RED + "State: " + level.toString());

        if (type == ItemType.ONELOOK) {
            if (level == ItemLevel.NONE) {
                itemLore.clear();
                itemStack.setType(Material.GLASS);
                itemMeta.setDisplayName(ChatColor.RED + "OneLook");
                itemLore.add(ChatColor.GRAY + "Du darfst deinen Blickwinkel ändern");
            } else if (level == ItemLevel.TWO) {
                itemLore.clear();
                itemStack.setType(Material.GLASS);
                itemMeta.setDisplayName(ChatColor.GREEN + "OneLook");
                itemLore.add(ChatColor.GRAY + "Du stirbst, wenn du deinen Blickwinkel änderst");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else if (level == ItemLevel.ONE) {
                itemLore.clear();
                itemStack.setType(Material.GLASS);
                itemMeta.setDisplayName(ChatColor.GREEN + "OneLook");
                itemLore.add(ChatColor.GRAY + "Du darfst deinen Blickwinkel nicht ändern");
                itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }

        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     *
     * @param type Type of the item
     * @param state State to set into the config
     */

    public static void setConfig(ItemType type, ItemState state) {
        String path = null;
        if (type == ItemType.PARCOUR_WATER) {
            path = "utils.parcour.water";
        } else if (type == ItemType.PARCOUR) {
            path = "utils.parcour.enabled";
        } else if (type == ItemType.PARCOUR_LAVA) {
            path = "utils.parcour.lava";
        } else if (type == ItemType.DRAGON) {
            path = "challenge.dragon";
        } else if (type == ItemType.NOINV) {
            path = "challenge.inventory";
        } else if (type == ItemType.CALLDMG) {
            path = "utils.callDamage";
        } else if (type == ItemType.DEATH) {
            path = "challenge.death";
        } else if (type == ItemType.NOINV) {
            path = "challenge.inventory";
        } else if (type == ItemType.FORCEMATH) {
            path = "challenge.forcemath";
        } else if (type == ItemType.FORCEBIOME) {
            path = "challenge.forcebiome";
        } else if (type == ItemType.USERSIDEYOU) {
            path = "utils.you";
        } else if (type == ItemType.FORCEEFFECT) {
            path = "challenge.forceeffect";
        } else if (type == ItemType.FORCEGLEICHUNG) {
            path = "challenge.forcegleichungen";
        } else if (type == ItemType.NOREMOVE) {
            path = "challenge.noremove";
        } else if (type == ItemType.DORFSPAWN) {
            path = "utils.dorfspawn";
        }
        try {
            Config.get().set(path, toBoolean(state));
            Config.save();
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("An internal error occured. Please report this to me on discord.");
            Bukkit.getLogger().warning(type.toString());
            Bukkit.getLogger().warning(state.toString());
        }
    }

    public static void setConfig(ItemType type, ItemLevel level) {
        String path = null;
        if (type == ItemType.ONELOOK) {
            path = "challenge.onelook";
        }

        try {
            Config.get().set(path, getInt(level));
            Config.save();
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("An internal error occured. Please report this to me on discord.");
            Bukkit.getLogger().warning(type.toString());
            Bukkit.getLogger().warning(level.toString());
        }
    }

    public enum ItemType {
        CALLDMG,
        DRAGON,
        NOINV,
        PARCOUR,
        PARCOUR_ENABLED,
        PARCOUR_WATER,
        PARCOUR_LAVA,
        DEATH,
        PLACEHOLDER,
        FORCEBLOCK,
        FORCEMATH,
        FORCEBIOME,
        BINGO,
        USERSIDEYOU,
        ONELOOK,
        FACING,
        FORCEEFFECT,
        FORCEGLEICHUNG,
        NOREMOVE,
        DORFSPAWN;
    }
    public enum ItemState {
        NONE,
        DISABLED,
        ENABLED
    }
    public enum ItemLevel {
        NONE(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        TWENTY(20),
        THIRTY(30),
        FOURTY(40),
        FIVTY(50),
        SIXTY(60),
        ERROR(-1);

        int level;

        ItemLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     *
     * @param i Integer of the level
     * @return the ItemLevel
     */

    public static ItemLevel getItemLevel(int i) {
        if (i == 0) {
            return ItemLevel.NONE;
        } else if (i == 1) {
            return ItemLevel.ONE;
        } else if (i == 2) {
            return ItemLevel.TWO;
        } else if (i == 3) {
            return ItemLevel.THREE;
        } else if (i == 4) {
            return ItemLevel.FOUR;
        } else if (i == 5) {
            return ItemLevel.FIVE;
        } else if (i == 6) {
            return ItemLevel.SIX;
        } else if (i == 7) {
            return ItemLevel.SEVEN;
        } else if (i == 8) {
            return ItemLevel.EIGHT;
        } else if (i == 9) {
            return ItemLevel.NINE;
        } else if (i == 10) {
            return ItemLevel.TEN;
        } else if (i == 20) {
            return ItemLevel.TWENTY;
        } else if (i == 30) {
            return  ItemLevel.THIRTY;
        } else if (i == 40) {
            return ItemLevel.FOURTY;
        } else if (i == 50) {
            return ItemLevel.FIVTY;
        } else if (i == 60) {
            return ItemLevel.SIXTY;
        } else {
            return ItemLevel.ERROR;
        }
    }

    public static int getInt(ItemLevel level) {
        if (level == ItemLevel.NONE) {
            return 0;
        } else if (level == ItemLevel.ONE) {
            return 1;
        } else if (level == ItemLevel.TWO) {
            return 2;
        } else if (level == ItemLevel.THREE) {
            return 3;
        } else if (level == ItemLevel.FOUR) {
            return 4;
        } else if (level == ItemLevel.FIVE) {
            return 5;
        } else if (level == ItemLevel.SIX) {
            return 6;
        } else if (level == ItemLevel.SEVEN) {
            return 7;
        } else if (level == ItemLevel.EIGHT) {
            return 8;
        } else if (level == ItemLevel.NINE) {
            return 9;
        } else if (level == ItemLevel.TEN) {
            return 10;
        } else if (level == ItemLevel.TWENTY) {
            return 20;
        } else if (level == ItemLevel.THIRTY) {
            return 30;
        } else if (level ==ItemLevel.FOURTY) {
            return 40;
        } else if (level == ItemLevel.FIVTY) {
            return 50;
        } else if (level == ItemLevel.SIXTY) {
            return 60;
        } else {
            return -1;
        }
    }

    public static void setSettings() {

        if (!Config.contains("utils.callDamage")) {
            Config.set("utils.callDamage", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.CALLDMG, toState(Config.get().getBoolean("utils.callDamage")));
        } catch (ClassCastException e) {
            Config.set("utils.callDamage", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }

        if (!Config.contains("utils.parcour.enabled")) {
            Config.set("utils.parcour.enabled", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.PARCOUR, toState(Config.get().getBoolean("utils.parcour.enabled")));
        } catch (ClassCastException e) {
            Config.set("utils.parcour.enabled", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }
        if (!Config.contains("utils.parcour.lava")) {
            Config.set("utils.parcour.lava", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.PARCOUR_LAVA, toState(Config.get().getBoolean("utils.parcour.lava")));
        } catch (ClassCastException e) {
            Config.set("utils.parcour.lava", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }
        if (!Config.contains("utils.parcour.water")) {
            Config.set("utils.parcour.water", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.PARCOUR_WATER, toState(Config.get().getBoolean("utils.parcour.water")));
        } catch (ClassCastException e) {
            Config.set("utils.parcour.water", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }

//        DORFSPAWN
        if (!Config.contains("utils.dorfspawn")) {
            Config.set("utils.dorfspawn", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.DORFSPAWN, toState(Config.get().getBoolean("utils.dorfspawn")));
        } catch (ClassCastException e) {
            Config.set("utils.dorfspawn", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }

//        DEATH

        if (!Config.contains("challenge.death")) {
            Config.set("challenge.death", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.DEATH, toState(Config.get().getBoolean("challenge.death")));
        } catch (ClassCastException e) {
            Config.set("challenge.death", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }


        if (!Config.contains("challenge.dragon")) {
            Config.set("challenge.dragon", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.DRAGON, toState(Config.get().getBoolean("challenge.dragon")));
        } catch (ClassCastException e) {
            Config.set("challenge.dragon", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }


        if (!Config.contains("challenge.inventory")) {
            Config.set("challenge.inventory", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.NOINV, toState(Config.get().getBoolean("challenge.inventory")));
        } catch (ClassCastException e) {
            Config.set("challenge.inventory", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }


        if (!Config.contains("challenge.forcemath")) {
            Config.set("challenge.forcemath", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.FORCEMATH, toState(Config.get().getBoolean("challenge.forcemath")));
        } catch (ClassCastException e) {
            Config.set("challenge.forcemath", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }


        if (!Config.contains("challenge.forcebiome")) {
            Config.set("challenge.forcebiome", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.FORCEBIOME, toState(Config.get().getBoolean("challenge.forcebiome")));
        } catch (ClassCastException e) {
            Config.set("challenge.forcebiome", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }


        if (!Config.contains("utils.you")) {
            Config.set("utils.you", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.USERSIDEYOU, toState(Config.get().getBoolean("utils.you")));
        } catch (ClassCastException e) {
            Config.set("utils.you", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }

        if (!Config.contains("challenge.onelook")) {
            Config.set("challenge.onelook", getInt(ItemLevel.NONE));
            Config.save();
        }
        try {
            levelsettings.put(ItemType.ONELOOK, getItemLevel(Config.get().getInt("challenge.onelook")));
        } catch (ClassCastException e) {
            Config.set("challenge.onelook", getInt(ItemLevel.NONE));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }

        if (!Config.contains("challenge.facing")) {
            Config.set("challenge.facing", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.FACING, toState(Config.get().getBoolean("challenge.facing")));
        } catch (ClassCastException e) {
            Config.set("challenge.facing", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }


        if (!Config.contains("challenge.forceeffect")) {
            Config.set("challenge.forceeffect", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.FORCEEFFECT, toState(Config.get().getBoolean("challenge.forceeffect")));
        } catch (ClassCastException e) {
            Config.set("challenge.forceeffect", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }

//        ForceGleichungen
        if (!Config.contains("challenge.forcegleichungen")) {
            Config.set("challenge.forcegleichungen", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.FORCEGLEICHUNG, toState(Config.get().getBoolean("challenge.forcegleichungen")));
        } catch (ClassCastException e) {
            Config.set("challenge.forcegleichungen", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }
//        NoRemove
        if (!Config.contains("challenge.noremove")) {
            Config.set("challenge.noremove", toBoolean(ItemState.DISABLED));
            Config.save();
        }
        try {
            settings.put(ItemType.NOREMOVE, toState(Config.get().getBoolean("challenge.noremove")));
        } catch (ClassCastException e) {
            Config.set("challenge.noremove", toBoolean(ItemState.DISABLED));
            Bukkit.getLogger().warning("Falscher Wert in Config!");
        }
    }

    public static boolean toBoolean(ItemState state) {
        if (state.equals(ItemState.ENABLED)) {
            return true;
        } else if (state.equals(ItemState.DISABLED)){
            return false;
        } else {
            return false;
        }
    }
    public static ItemState toState(boolean b) {
        if (b) {
            return ItemState.ENABLED;
        } else {
            return ItemState.DISABLED;
        }
    }
}