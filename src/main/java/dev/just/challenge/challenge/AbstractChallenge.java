package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author justCoding
 * @author github.com/anweisen
 * @author github.com/kxmischesdomi
 * Heavily inspired by the people listed above
 */
public abstract class AbstractChallenge {
    protected static final Main plugin = Main.getPlugin;

    private static final Map<Class<? extends AbstractChallenge>, AbstractChallenge> firstInstanceByClass = new HashMap<>();
    private static final boolean irgnoreCreativePlayers;
    private static final boolean ignoreSpectatorPlayers;

    protected final MenuType menu;

    private String name;

    static {
        irgnoreCreativePlayers = true;
        ignoreSpectatorPlayers = true;
    }

    public AbstractChallenge(MenuType menu) {
        this.menu = menu;
        firstInstanceByClass.put(this.getClass(), this);
    }

    public MenuType getType() {
        return menu;
    }

    public ItemStack getDisplayItem() {
        return
    }
}
