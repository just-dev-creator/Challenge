package dev.just.challenge.challenge.challenges;

import dev.just.challenge.challenge.AbstractChallenge;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class TestChallenge extends AbstractChallenge implements Listener {
    public static final TestChallenge instance = new TestChallenge();
    private TestChallenge() {
        super("Test", Material.ANVIL);
    }

    @Override
    public void onEnable() {

    }
}
