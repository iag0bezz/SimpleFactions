package com.frach.minecraft.factions.controller.impl;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.controller.BoardController;
import com.frach.minecraft.factions.util.Board;
import com.sun.media.jfxmediaimpl.HostUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BoardControllerImpl implements BoardController {

    private HashSet<Board> boards = new HashSet<>();

    @Override
    public void constructor(Factions factions) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            factions.getServer().getScheduler().runTaskLater(factions, () -> {
                if(online.isOnline()) {
                    create(online);
                }
            }, 2L);
        }
    }

    @Override
    public void destructor(Factions factions) {
        Bukkit.getOnlinePlayers().forEach(this::remove);
    }

    @Override
    public Optional<Board> find(Player player) {
        return this.stream().filter(data -> data != null && data.getReceiver().equals(player)).findFirst();
    }

    @Override
    public void create(Player player) {
        this.find(player).ifPresent(Board::reset);

        if(player.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        this.boards.add(new Board(player));
    }

    @Override
    public void remove(Player player) {
        Board board = this.find(player).orElse(null);

        if(board != null) {
            board.reset();

            this.find(player).ifPresent(this.boards::remove);
        }
    }

    @Override
    public void forEach(Consumer<Board> boardConsumer) {
        this.stream().forEach(boardConsumer);
    }

    @Override
    public Stream<Board> stream() {
        return this.boards.stream();
    }

}