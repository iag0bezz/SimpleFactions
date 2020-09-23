package com.frach.minecraft.factions.task;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.helper.ConfigurationHelper;
import com.frach.minecraft.factions.util.ScoreboardBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FactionScoreboardTask extends BukkitRunnable {

    @Override
    public void run() {
        ConfigurationHelper configuration = Factions.getInstance().getConfiguration();

        if(configuration.getBoolean("scoreboard.enabled")) {
            String title = configuration.getString("scoreboard.title");

            boolean placeholder = Factions.getInstance().getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

            for (Player player : Bukkit.getOnlinePlayers()) {
                FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(player.getUniqueId()).orElse(null);

                if(factionPlayer == null)
                    continue;

                List<String> lines = factionPlayer.getFaction() == null ? configuration.getStringList("scoreboard.lines.without_faction") : configuration.getStringList("scoreboard.lines.with_faction");

                ScoreboardBuilder scoreboardBuilder = new ScoreboardBuilder(player);

                scoreboardBuilder.setTitle(title);

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);

                    if(placeholder) {
                        line = PlaceholderAPI.setPlaceholders(player, line);
                    }

                    scoreboardBuilder.setLine(line, lines.size() - i);
                }

                scoreboardBuilder.build();
            }
        }
    }

}