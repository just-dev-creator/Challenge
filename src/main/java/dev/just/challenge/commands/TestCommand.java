package dev.just.challenge.commands;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Config;
import dev.just.challenge.utils.ScoreboardManager;
import dev.just.challenge.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Folgende Argumente sind verfügbar: \n •hidelist \n •hideflylist \n •confighide <Name>");
            return false;
        }
        else if (args[0].equalsIgnoreCase("hidelist")) {
            sender.sendMessage(Main.hide.toString());
        } else if (args[0].equalsIgnoreCase("confighide") && args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            sender.sendMessage(target.getName() + " |UUID| " + target.getUniqueId() + " |BOOLEAN| " + Config.get().get("hide." + target.getUniqueId()));
        } else if (args[0].equalsIgnoreCase("name")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getErrorPrefix() + "Du musst ein Spieler sein. ");
                return false;
            } else {
                Player player = (Player) sender;
                sender.sendMessage(player.getName());
                return true;
            }
        } else if (args[0].equalsIgnoreCase("score")) {
            ScoreboardManager.checkpoints();
            sender.sendMessage("setted");
        } else if (args[0].equalsIgnoreCase("reset")) {
            Settings.settings.clear();
            sender.sendMessage("cleared");
        } else if (args[0].equalsIgnoreCase("help")) {
            Settings.settings.put(Settings.ItemType.PARCOUR, Settings.ItemState.DISABLED);
            Settings.settings.put(Settings.ItemType.FORCEMATH, Settings.ItemState.DISABLED);
            Settings.settings.put(Settings.ItemType.NOINV, Settings.ItemState.DISABLED);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("hidelist");
                tabComplete.add("hideflylist");
                tabComplete.add("confighide");
                tabComplete.add("name");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("hidel")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("hidelist");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("hidef")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("hideflylist");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("h")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("hidelist");
                tabComplete.add("hideflylist");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("c")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("confighide");
                return tabComplete;
            } else if (args[0].toLowerCase().startsWith("n")) {
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("name");
                return tabComplete;
            }
        }
        return null;
    }
}
