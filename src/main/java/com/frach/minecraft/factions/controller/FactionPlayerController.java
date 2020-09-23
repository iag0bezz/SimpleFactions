package com.frach.minecraft.factions.controller;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.FactionPlayer;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface FactionPlayerController {

    CompletableFuture<Void> constructor(Factions factions);

    void destructor(Factions factions);

    Optional<FactionPlayer> find(UUID uniqueId);

    void create(FactionPlayer faction);

    void delete(FactionPlayer faction);

    void forEach(Consumer<FactionPlayer> factionPlayerConsumer);

    Stream<FactionPlayer> stream();

}