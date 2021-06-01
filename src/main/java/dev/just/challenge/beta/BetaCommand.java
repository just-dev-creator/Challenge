package dev.just.challenge.beta;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BetaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "✗ " + ChatColor.BLUE + "JUtils - Beta-Programm" + ChatColor.GRAY + " ✗");
        sender.sendMessage(ChatColor.DARK_GRAY + "Vielen Dank für die Teilnahme an der Beta-Testphase");
        sender.sendMessage(ChatColor.DARK_GRAY + "Diese ist sehr hilfreich zum auffinden von Bugs etc. ");
        sender.sendMessage(ChatColor.DARK_GRAY + "Bitte gieb Feedback bzw. Bugs nur unter den Folgenden Links an.");
        sender.sendMessage(ChatColor.GRAY + "✗ " + ChatColor.BLUE + "JUtils - Offizielle Links" + ChatColor.GRAY + " ✗");
        TextComponent c1 = setBoldLink(new TextComponent(ChatColor.BLUE + "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAMAANM9Eu5UNDJQVTZZSFo1QkRZWlIySUNTR0JaMDBVNS4u"), "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAMAANM9Eu5UNDJQVTZZSFo1QkRZWlIySUNTR0JaMDBVNS4u");
        sender.spigot().sendMessage(new TextComponent(ChatColor.GRAY + "• " + ChatColor.DARK_GRAY + "Bugtracker: "), c1);
        TextComponent c2 = setBoldLink(new TextComponent(ChatColor.BLUE + "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAMAANM9Eu5UMDRGODBBWVYyUFIyTzVGU0kwVTJESTVXSy4u"), "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAMAANM9Eu5UMDRGODBBWVYyUFIyTzVGU0kwVTJESTVXSy4u");
        sender.spigot().sendMessage(new TextComponent(ChatColor.GRAY + "• " + ChatColor.DARK_GRAY + "Challenge-Vorschläge: "), c2);
        TextComponent c3 = setBoldLink(new TextComponent(ChatColor.BLUE + "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAMAANM9Eu5UQlBFVE5JQ1c1WDNIMzJZMk1BOTVYNUQ0Mi4u"), "https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAMAANM9Eu5UQlBFVE5JQ1c1WDNIMzJZMk1BOTVYNUQ0Mi4u");
        sender.spigot().sendMessage(new TextComponent(ChatColor.GRAY + "• " + ChatColor.DARK_GRAY + "Allgemeines Feedback: "), c3);
        return false;
    }
    private TextComponent setBoldLink(TextComponent component, String url) {
        TextComponent textComponent = component;
        textComponent.setBold(true);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return textComponent;
    }
}
