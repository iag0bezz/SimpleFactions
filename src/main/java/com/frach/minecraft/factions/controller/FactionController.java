package com.frach.minecraft.factions.controller;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.Faction;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface FactionController {

    CompletableFuture<Void> constructor(Factions factions);

    void destructor(Factions factions);

    Optional<Faction> find(UUID uniqueId);

    void create(Faction faction);

    void delete(Faction faction);

    void forEach(Consumer<Faction> factionConsumer);

    Stream<Faction> stream();

}