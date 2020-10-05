package com.frach.minecraft.factions.task;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.helper.ConfigurationHelper;
import com.frach.minecraft.factions.util.Board;
import com.google.common.collect.Lists;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class FactionScoreboardTask extends BukkitRunnable {

    @Override
    public void run() {
        ConfigurationHelper configuration = Factions.getInstance().getConfiguration();

        if(configuration.getBoolean("scoreboard.enabled")) {
            String title = configuration.getString("scoreboard.title");

            boolean placeholder = Factions.getInstance().getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

            List<Board> boards = Factions.getInstance().getBoardController().stream().collect(Collectors.toList());

            for (Board board : boards) {
                FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(board.getReceiver().getUniqueId()).orElse(null);

                if(factionPlayer == null)
                    continue;

                board.setLines(Lists.newArrayList());
                board.setTitle(title);

                List<String> lines = factionPlayer.getFaction() == null ? configuration.getStringList("scoreboard.lines.without_faction") : configuration.getStringList("scoreboard.lines.with_faction");

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);

                    if(placeholder) {
                        line = PlaceholderAPI.setPlaceholders(board.getReceiver(), line);
                    }

                    board.addLine(line);
                }

                board.update();
            }
        }
    }

}