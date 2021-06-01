package dev.just.challenge.utils;

import dev.just.challenge.utilities.Parcour;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    static org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();

    public static void checkpoints() {
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("checkpoint", "dummy", ChatColor.DARK_GRAY + "✗ " + ChatColor.GRAY + "Checkpoints" + ChatColor.DARK_GRAY + " ✗");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Player all : Bukkit.getOnlinePlayers()) {
            Score score = objective.getScore(ChatColor.GRAY + all.getName());
            if (!Parcour.checkpoints.containsKey(all)) {
                score.setScore(0);
            } else {
                score.setScore(Parcour.checkpoints.get(all));
            }
        }
        for (Player player : Bukkit.getOnlinePlayers())
            player.setScoreboard(scoreboard);
    }
}
