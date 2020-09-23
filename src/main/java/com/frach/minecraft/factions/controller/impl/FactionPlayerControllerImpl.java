package com.frach.minecraft.factions.controller.impl;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.controller.FactionPlayerController;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.service.FactionPlayerService;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FactionPlayerControllerImpl implements FactionPlayerController {

    private HashSet<FactionPlayer> players = new HashSet<>();

    private FactionPlayerService service;

    @Override
    public CompletableFuture<Void> constructor(Factions factions) {
        this.service = factions.getFactionPlayerService();

        factions.getLog().info("Loading all players...");
        return this.service.load().thenAcceptAsync(find -> this.players.addAll(find), factions.getExecutor());
    }

    @Override
    public void destructor(Factions factions) {
        FactionPlayer[] toArray = new FactionPlayer[this.players.size()];
        toArray = this.players.toArray(toArray);

        this.service.save(toArray);
    }

    @Override
    public Optional<FactionPlayer> find(UUID uniqueId) {
        return this.stream().filter(data -> data.getUniqueId().equals(uniqueId)).findFirst();
    }

    @Override
    public void create(FactionPlayer faction) {
        this.players.add(faction);

        faction.setModified(true);
        this.service.save(faction);
    }

    @Override
    public void delete(FactionPlayer faction) {
        this.players.remove(faction);

        this.service.delete(faction);
    }

    @Override
    public void forEach(Consumer<FactionPlayer> factionPlayerConsumer) {
        this.stream().forEach(factionPlayerConsumer);
    }

    @Override
    public Stream<FactionPlayer> stream() {
        return this.players.stream();
    }

}