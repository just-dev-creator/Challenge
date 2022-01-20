/*
 * Copyright (c) 2021-2022. justCoding
 * All rights reserved.
 * You may not copy, modify, distribute or decompile this code without the written permission of the author.
 */

package dev.just.challenge.commands;

import dev.just.challenge.utils.Timer;
import dev.just.challenge.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {
    public static boolean timer_active = false;
    public static Integer timer_sec = 0;
    public static Integer timer_min = 0;
    public static Integer timer_h = 0;
    private String prefix() {
        return ChatColor.GREEN + "Timer" + ChatColor.GRAY + "» " + ChatColor.DARK_GRAY;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(args.length == 1) && !(args.length == 2)) {
            sender.sendMessage(prefix() + "Bitte benutze: /timer <start/pause/reset>");
            return false;
        }
        else if (args[0].equalsIgnoreCase("start")) {
            sender.sendMessage(prefix() + "Du hast den Timer erfolgreich gestartet!");
            Bukkit.broadcastMessage(prefix() + "Der Timer ist gestartet!");
            timer_active = true;
            Utils.broadcastSound(org.bukkit.Sound.BLOCK_ANVIL_PLACE);
            Timer.sendTimerUpdate();
        }
        else if (args[0].equalsIgnoreCase("pause")) {
            sender.sendMessage(prefix() + "Du hast den Timer erfolgreich pausiert.");
            Utils.broadcastSound(org.bukkit.Sound.ITEM_TOTEM_USE);
            timer_active = false;
            Timer.sendTimerUpdate();
        }
        else if (args[0].equalsIgnoreCase("reset")) {
            sender.sendMessage(prefix() + "Du hast den Timer erfolgreich zurückgesetzt!");
            timer_sec = 0;
            timer_min = 0;
            timer_h = 0;
            Utils.broadcastSound(org.bukkit.Sound.BLOCK_ANVIL_DESTROY);
            timer_active = false;
            Timer.sendTimerUpdate();
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("h") && args.length == 2) {
                timer_h = Integer.parseInt(args[0]);
                sender.sendMessage(prefix() + "Du hast den Timer auf " + args[0] + " Stunden gesetzt!");
            }
            else if (args[1].equalsIgnoreCase("s") && args.length == 2) {
                timer_sec = Integer.parseInt(args[0]);
                sender.sendMessage(prefix() + "Du hast den Timer auf " + args[0] + " Sekunden gesetzt!");
            }
            else if (args[1].equalsIgnoreCase("m") && args.length == 2) {
                timer_min = Integer.parseInt(args[0]);
                sender.sendMessage(prefix() + "Du hast den Timer auf " + args[0] + " Minuten gesetzt!");
            }
            Timer.sendTimerUpdate();
        }
        else {
            return false;
        }
        return false;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("start");
                tabComplete.add("pause");
                tabComplete.add("reset");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("s")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("start");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("p")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("pause");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("r")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("reset");
                return tabComplete;
            }
        }
        return null;
    }
}
