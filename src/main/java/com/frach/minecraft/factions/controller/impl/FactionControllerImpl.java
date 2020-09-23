package com.frach.minecraft.factions.controller.impl;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.controller.FactionController;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.service.FactionService;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FactionControllerImpl implements FactionController {

    private HashSet<Faction> factions = new HashSet<>();

    private FactionService service;

    @Override
    public CompletableFuture<Void> constructor(Factions factions) {
        this.service = factions.getFactionService();

        factions.getLog().info("Loading all factions...");
        return this.service.load().thenAcceptAsync(find -> this.factions.addAll(find), factions.getExecutor());
    }

    @Override
    public void destructor(Factions factions) {
        Faction[] toArray = new Faction[this.factions.size()];
        toArray = this.factions.toArray(toArray);

        this.service.save(toArray);
    }

    @Override
    public Optional<Faction> find(UUID uniqueId) {
        return this.stream().filter(data -> data.getUniqueId().equals(uniqueId)).findFirst();
    }

    @Override
    public void create(Faction faction) {
        this.factions.add(faction);

        this.service.save(faction);
    }

    @Override
    public void delete(Faction faction) {
        this.factions.remove(faction);

        this.service.delete(faction);
    }

    @Override
    public void forEach(Consumer<Faction> factionConsumer) {
        this.stream().forEach(factionConsumer);
    }

    @Override
    public Stream<Faction> stream() {
        return this.factions.stream();
    }

}