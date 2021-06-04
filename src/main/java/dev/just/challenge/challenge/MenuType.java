package dev.just.challenge.challenge;

/**
 * @author justCoding
 * @author github.com/anweisen
 * @author github.com/kxmischesdomi
 * Heavily inspired by the people listed above
 */
public enum MenuType {

    TIMER("Timer", false),
    GOAL("Goal"),
    DAMAGE("Damage"),
    ITEMS_BLOCKS("Items & Blocks"),
    CHALLENGES("Challenges"),
    SETTINGS("Settings");

    private final String name;
    private final boolean usable;

    MenuType(String name, boolean usable) {
        this.name = name;
        this.usable = usable;
    }

    MenuType(String name) {
        this(name, true);
    }

    public String getName() {
        return this.name;
    }

    public boolean isUsable() {
        return usable;
    }
}
