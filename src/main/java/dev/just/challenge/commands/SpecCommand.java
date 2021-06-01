package dev.just.challenge.commands;

import dev.just.challenge.Main;
import dev.just.challenge.utils.Config;
import dev.just.challenge.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpecCommand implements CommandExecutor, Listener, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage(Main.getNoPlayer());
        else if (!sender.isOp()) sender.sendMessage(Main.getNoPermission());
        else {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    String players = ChatColor.RED + "§oniemand";
                    for (String uuid : Settings.uuids) {
                        if (players == "§c§oniemand") {
                            players = "§e" + uuid;
                            continue;
                        }
                        players = String.valueOf(players) + "§8, §e" + uuid;
                    }
                    sender.sendMessage(Main.getCustomPrefix("Hide") + ChatColor.YELLOW + "Versteckt sind: " + ChatColor.GRAY + players);
                } else if (args[0].equalsIgnoreCase("add")) {
                    Player target = (Player) sender;
                    if (!Settings.uuids.contains(target.getUniqueId().toString())) {
                        Settings.uuids.add(target.getUniqueId().toString());
                        for (Player every : Bukkit.getOnlinePlayers()) {
                            if (Settings.uuids.contains(every.getUniqueId().toString())) {
                                target.showPlayer(Main.getPlugin(Main.class), every);
                                continue;
                            }
                            every.hidePlayer(Main.getPlugin(Main.class), target);
                        }
                        sender.sendMessage(Main.getCustomPrefix("Hide") + "Du wirst nun versteckt! ");
                    } else {
                        sender.sendMessage(Main.getErrorPrefix() + "Du wirst bereits versteckt! ");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    Player target = (Player) sender;
                    if (Settings.uuids.contains(target.getUniqueId().toString())) {
                        Settings.uuids.remove(target.getUniqueId().toString());
                        for (Player every : Bukkit.getOnlinePlayers()) {
                            if (Settings.uuids.contains(every.getUniqueId().toString())) {
                                target.hidePlayer(Main.getPlugin(Main.class), every);
                                continue;
                            }
                            every.showPlayer(Main.getPlugin(Main.class), target);
                        }
                        sender.sendMessage(Main.getCustomPrefix("Hide") + "Du wirst nicht weiter versteckt! ");
                    } else {
                        sender.sendMessage(Main.getErrorPrefix() + "Du wirst nicht versteckt! ");
                    }
                }
                else {
                    sender.sendMessage(getUsage());
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("add")) {
                    if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (!Settings.uuids.contains(target.getUniqueId().toString())) {
                            Settings.uuids.add(target.getUniqueId().toString());
                            for (Player every : Bukkit.getOnlinePlayers()) {
                                if (Settings.uuids.contains(every.getUniqueId().toString())) {
                                    target.showPlayer(Main.getPlugin(Main.class), every);
                                    continue;
                                }
                                every.hidePlayer(Main.getPlugin(Main.class), target);
                            }
                            sender.sendMessage(Main.getCustomPrefix("Hide") + "Der Spieler " + ChatColor.GREEN + target.getDisplayName() + ChatColor.DARK_GRAY + " wird nun versteckt. ");
                        } else {
                            sender.sendMessage(Main.getErrorPrefix() + "Der Spieler wird bereits versteckt");
                        }
                    } else if (Bukkit.getOfflinePlayer(args[1]) != null) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (!Settings.uuids.contains(target.getUniqueId().toString())) {
                            Settings.uuids.add(target.getUniqueId().toString());
                            sender.sendMessage(Main.getCustomPrefix("Hide") + "Der Spieler " + ChatColor.GREEN + target.getName() + ChatColor.DARK_GRAY + " wird nun versteckt. ");
                        } else {
                            sender.sendMessage(Main.getErrorPrefix() + "Der Spieler wird bereits versteckt");
                        }
                    } else {
                        sender.sendMessage(Main.getErrorPrefix() + "Der Spieler hat diesen Server noch nie betreten! ");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (Settings.uuids.contains(target.getUniqueId().toString())) {
                            Settings.uuids.remove(target.getUniqueId().toString());
                            for (Player every : Bukkit.getOnlinePlayers()) {
                                if (Settings.uuids.contains(every.getUniqueId().toString())) {
                                    target.hidePlayer(Main.getPlugin(Main.class), every);
                                    continue;
                                }
                                every.showPlayer(Main.getPlugin(Main.class), target);
                            }
                            sender.sendMessage(Main.getCustomPrefix("Hide") + "Der Spieler " + ChatColor.GREEN + target.getDisplayName() + ChatColor.DARK_GRAY + " wird nun nicht weiter versteckt. ");
                        } else {
                            sender.sendMessage(Main.getErrorPrefix() + "Der Spieler wird nicht versteckt");
                        }
                    } else if (Bukkit.getOfflinePlayer(args[1]) != null) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (Settings.uuids.contains(target.getUniqueId().toString())) {
                            Settings.uuids.remove(target.getUniqueId().toString());
                            sender.sendMessage(Main.getCustomPrefix("Hide") + "Der Spieler " + ChatColor.GREEN + target.getName() + ChatColor.DARK_GRAY + " wird nun nicht weiter versteckt. ");
                        }
                    } else {
                        sender.sendMessage(Main.getErrorPrefix() + "Der Spieler hat diesen Server noch nie betreten! ");
                    }
                } else {
                    sender.sendMessage(getUsage());
                }
            } else {
                sender.sendMessage(getUsage());
            }
        }
        return true;
    }

    private String getUsage() {
        return Main.getErrorPrefix() + ChatColor.RED + "Verwende:" + ChatColor.YELLOW + "/hide <list/add/remove> [<User>]";
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (Settings.uuids.contains(player.getUniqueId().toString())) {
            player.setGameMode(GameMode.SPECTATOR);
            player.setFlying(true);
            event.setJoinMessage(null);
            for (Player every : Bukkit.getOnlinePlayers()) {
                if (player.getUniqueId() != every.getUniqueId() && !Settings.uuids.contains(every.getUniqueId().toString())) {
                    every.hidePlayer(Main.getPlugin(Main.class), player);
                } else {
                    every.sendMessage(ChatColor.GRAY + "+ " + ChatColor.DARK_GRAY + player.getDisplayName());
                }
            }
            player.sendMessage(Main.getCustomPrefix("Hide") + "Dein Join war für alle sichtbaren Spieler unsichtbar. ");
        } else {
            player.setGameMode(Bukkit.getDefaultGameMode());
            for (Player every : Bukkit.getOnlinePlayers()) {
                if (Settings.uuids.contains(every.getUniqueId().toString())) {
                    player.hidePlayer(Main.getPlugin(Main.class), every);
                }
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (!(sender instanceof Player)) return null;
        else {
            Player player = (Player) sender;
            List<String> tabcomplete = new ArrayList<>();
            if (args[0].equalsIgnoreCase("")) {
                tabcomplete.add("add");
                tabcomplete.add("remove");
            } else if (args[0].toLowerCase().startsWith("a") && args.length == 1) {
                tabcomplete.add("add");
            } else if (args.length == 1 && args[0].toLowerCase().startsWith("r")) {
                tabcomplete.add("remove");
            } else if (args.length == 2) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    tabcomplete.add(all.getName());
                }
            }
            return tabcomplete;
        }
    }
}
