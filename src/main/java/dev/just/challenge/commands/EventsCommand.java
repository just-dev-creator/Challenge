package dev.just.challenge.commands;

import com.google.common.collect.Lists;
import dev.just.challenge.Main;
import dev.just.challenge.challenges.ForceBiome;
import dev.just.challenge.inventorys.HubInventory;
import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.ShortString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EventsCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Nur Spieler können diesen Befehl ausführen!");
            return false;
        }
        else if (args.length == 0) {
            HubInventory.createInventory();
            Player p = (Player) sender;
            p.openInventory(HubInventory.inventory);
            return false;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.GREEN +  "✘ Hilfestellung zur Friends-Version von JUtils. ✘");
            sender.sendMessage(ChatColor.RED + "✘ Challenges ✘");
            sender.sendMessage(ChatColor.GRAY + "Enderdrache-Tod: " + ChatColor.DARK_GRAY + "Die Challenge ist beim Tod des Enderdrachens abgeschlossen.");
            sender.sendMessage(ChatColor.GRAY + "NoInv: " + ChatColor.DARK_GRAY + "Die Challenge ist beim benutzen seines Inventares oder dem öffnen eines anderen beendet.");
            sender.sendMessage(ChatColor.RED + "✘ Utilities ✘");
            sender.sendMessage(ChatColor.GRAY + "Schadens-Hinweis: " + ChatColor.DARK_GRAY + "Jeder Schaden wird im Schaden geloggt.");
            sender.sendMessage(ChatColor.GRAY + "Keine Regeneration: " + ChatColor.DARK_GRAY + "Kein Spieler regeneriert mehr natürlich. ");
            sender.sendMessage(ChatColor.DARK_PURPLE + "» Informationen zu Commands mit /help Challenge «");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("setMaxHP")) {
            Double max = Double.valueOf(args[1]);
            int numberOfPlayers = 0;
            for (Player HP : Bukkit.getOnlinePlayers()) {
                if (Main.hide.contains(HP)) {
                    HP.setMaxHealth(max);
                    numberOfPlayers++;
                }
            }
            sender.sendMessage(Main.getPrefix() + "Maximales Leben von " + numberOfPlayers + " Spielern auf " + max + " gesetzt.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("setCurrentHP")) {
            Double max = Double.valueOf(args[1]);
            if (max > 1024) {
                sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
                Bukkit.getLogger().info(ChatColor.RED + "Do not report this to the author. Its not a bug! ");
                Bukkit.getLogger().info(ChatColor.RED + "(To high double to set it as the health from a player)");
                return false;
            }
            int numberOfPlayers = 0;
            for (Player HP : Bukkit.getOnlinePlayers()) {
                if (Main.hide.contains(HP)) {
                    HP.setHealth(max);
                    numberOfPlayers++;
                }
            }
            sender.sendMessage(Main.getPrefix() + "Leben von " + numberOfPlayers + " Spielern auf " + max + " gesetzt.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("setBiome")) {
            if (!Settings.settings.get(Settings.ItemType.FORCEBIOME).equals(Settings.ItemState.ENABLED)) {
                sender.sendMessage(Main.getCustomPrefix("ForceBiome") + "Die dazugehörige Challenge ist nicht aktiviert.");
            } else if (ForceBiome.phase.equals(ForceBiome.ForceState.SET)) {
                sender.sendMessage(Main.getCustomPrefix("ForceBiome") + "Ein Biom wurde bereits gesetzt.");
            } else {
                try {
                    Biome target = Biome.valueOf(args[1].toUpperCase());
                    ForceBiome.biome = Biome.valueOf(args[1].toUpperCase());
                    if (!ForceBiome.phase.equals(ForceBiome.ForceState.GENERATED)) {
                        ForceBiome.phase = ForceBiome.ForceState.GENERATED;
                    }
                    String biomeNameBefore = target.getKey().getKey();
                    String[] biomeNameSplit = biomeNameBefore.split("_");
                    ForceBiome.biomName = ShortString.run(biomeNameSplit, true, true);
                    sender.sendMessage(Main.getCustomPrefix("ForceBiome") + "Biom erfolgreich auf " + ChatColor.GREEN + ForceBiome.biomName + ChatColor.DARK_GRAY + " geändert.");
                } catch (Exception e) {
                    sender.sendMessage(Main.getCustomPrefix("ForceBiome") + "Du hast kein Biom angegeben.");
                }
            }
        }
        return false;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("setMaxHP");
            tabComplete.add("setCurrentHP");
            tabComplete.add("help");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("h")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("help");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("setm")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("setMaxHP");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("setc")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("setCurrentHP");
            return tabComplete;
        } else if (args[0].toLowerCase().startsWith("s")) {
            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("setCurrentHP");
            tabComplete.add("setMaxHP");
            return tabComplete;
        }
        return null;
    }
}