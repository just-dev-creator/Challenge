package dev.just.challenge.challenges;

import dev.just.challenge.utils.Settings;
import dev.just.challenge.utils.Timer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NoRemoveChallenge implements Listener {
    /**
    private HashMap<UUID, List<Block>> locked = new HashMap<>();
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED) && Timer.isRunning()) {
            event.setCancelled(true);
            Timer.endChallenge( event.getPlayer().getDisplayName() + " hat ein Item gedroppet", event.getPlayer());
        }
    }

    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        if (Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED) && Timer.isRunning()) {
            event.getPlayer().getInventory().addItem(event.getBrokenItem());
            Timer.endChallenge(event.getPlayer().getDisplayName() + " hat ein Item zerstört", event.getPlayer());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED) && Timer.isRunning()) {
            if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(null)) {
                event.setCancelled(true);
                Timer.endChallenge(event.getPlayer().getDisplayName() + " hat einen Stack aufgebraucht", event.getPlayer());
            } else if (event.getBlockPlaced().getType().equals(Material.CHEST) || event.getBlockPlaced().getType().equals(Material.FURNACE)) {
                if (!locked.containsKey(event.getPlayer().getUniqueId())) locked.put(event.getPlayer().getUniqueId(), new ArrayList<>());
                List<Block> old = locked.get(event.getPlayer().getUniqueId());
                if (old == null) old = new ArrayList<>();
                old.add(event.getBlockPlaced());
                locked.put(event.getPlayer().getUniqueId(), old);
            }
        }
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED) && Timer.isRunning()) {
            if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(null)) {
                event.setCancelled(true);
                Timer.endChallenge(event.getPlayer().getDisplayName() + " hat einen Stack aufgebraucht", event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED) && Timer.isRunning()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Block block = event.getClickedBlock();
                if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.FURNACE)) {
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    List<Block> allowed = locked.get(uuid);
                    if (!allowed.contains(block)) {
                        event.setCancelled(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED) && Timer.isRunning()) {
            if (event.getCurrentItem().isSimilar(null)) {
                Timer.endChallenge(((Player) event.getWhoClicked()).getDisplayName() + " hat einen Stack aufgebraucht", (Player) event.getWhoClicked());
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (locked.get(event.getPlayer().getUniqueId()).contains(event.getBlock())) {
            List<Block> blocks;
            if (locked.containsKey(event.getPlayer().getUniqueId())) {
                blocks = locked.get(event.getPlayer().getUniqueId());
            } else {
                blocks  = new ArrayList<>();
            }
            blocks.remove(event.getBlock());
            locked.put(event.getPlayer().getUniqueId(), blocks);
        } else if (Timer.isRunning() && Settings.settings.get(Settings.ItemType.NOREMOVE).equals(Settings.ItemState.ENABLED)) {
            event.setCancelled(true);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1, 1);
        }
    }
    **/
}
