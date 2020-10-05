package com.frach.minecraft.factions.controller;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.util.Board;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface BoardController {

    void constructor(Factions factions);

    void destructor(Factions factions);

    Optional<Board> find(Player player);

    void create(Player player);

    void remove(Player player);

    void forEach(Consumer<Board> boardConsumer);

    Stream<Board> stream();

}