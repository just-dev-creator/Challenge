package dev.just.challenge.utilities;

import dev.just.challenge.commands.PositionCommand;
import dev.just.challenge.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.StructureType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class DorfSpawn {
    /**
     * Finds a village for a village spawn if selected
     */
    public static Location villageLocation;
    public static void generateIfNeeded() {
        if (Settings.settings.get(Settings.ItemType.DORFSPAWN).equals(Settings.ItemState.ENABLED)) {
            villageLocation = Bukkit.getWorld("world").locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.VILLAGE, Integer.MAX_VALUE, true);
            for (int i = villageLocation.getBlockY(); i<256; i++) {
                Block block = new Location(Bukkit.getWorld("world"), villageLocation.getBlockX(), i, villageLocation.getBlockZ()).getBlock();
                if (block.getType().equals(Material.AIR) && block.getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                    villageLocation = new Location(Bukkit.getWorld("world"), villageLocation.getX(), i-1, villageLocation.getZ());
                    break;
                }
            }
            PositionCommand.locations.put("dorfspawn", villageLocation);
        }
    }
}
