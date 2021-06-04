package dev.just.challenge.challenge;

import dev.just.challenge.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public final class ChallengeBossBar {

    public final class BossBarInstance {
        private String title = "";
        private double progress = 1;
        private BarColor color = BarColor.WHITE;
        private BarStyle style = BarStyle.SOLID;
        private boolean visible = true;

        private BossBarInstance() {}

        public BossBarInstance setTitle(String title) {
            this.title = title;
            return this;
        }

        public BossBarInstance setProgress(double progress) {
            if (progress < 0 || progress > 1) throw new IllegalArgumentException("Progress must be between 0 and 1; Got " + progress);
            this.progress = progress;
            return this;
        }

        public BossBarInstance setColor(BarColor color) {
            this.color = color;
            return this;
        }

        public BossBarInstance setStyle(BarStyle style) {
            this.style = style;
            return this;
        }

        public BossBarInstance setVisible(boolean visible) {
            this.visible = visible;
            return this;
        }
    }

    private final Map<Player, BossBar> bossbars = new ConcurrentHashMap<>();
    private BiConsumer<BossBarInstance, Player> content = (bossbar, player) -> {};

    private BossBar createBossBar(BossBarInstance instance) {
        BossBar bossBar = Bukkit.createBossBar(instance.title, instance.color, instance.style);
        bossBar.setProgress(instance.progress);
        return bossBar;
    }

    private void apply(BossBar bossBar, BossBarInstance instance) {
        bossBar.setTitle(instance.title);
        bossBar.setColor(instance.color);
        bossBar.setStyle(instance.style);
        bossBar.setProgress(instance.progress);
        bossBar.setVisible(instance.visible);
    }

    private void setContent(BiConsumer<BossBarInstance, Player> content) {
        this.content = content;
    }

    public void applyHide(Player player) {
        BossBar bossBar = bossbars.get(player);
        if (bossBar == null) return;
        bossBar.removePlayer(player);
    }

    public void update() {
        Bukkit.getOnlinePlayers().forEach(this::update);
    }

    public void update(Player player) {
        if (!isShown()) {
            Bukkit.getLogger().warning("Tried to update a bossbar which isnt shown");
            return;
        }

        try {

            BossBarInstance instance = new BossBarInstance();
            content.accept(instance, player);

            BossBar bossBar = bossbars.computeIfAbsent(player, key -> createBossBar(instance));
            apply(bossBar, instance);

            if (!bossBar.getPlayers().contains(player)) bossBar.addPlayer(player);
        } catch (Exception e) {
            Bukkit.getLogger().warning(ChatColor.RED + "Unable to update bossbar for " + player.getName() + " " + e);
        }
    }

    public final void show() {

    }
}
