package com.frach.minecraft.factions.service;

import com.frach.minecraft.factions.data.FactionPlayer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FactionPlayerService extends SQLService {

    CompletableFuture<List<FactionPlayer>> load();

    void save(FactionPlayer... players);

    void delete(FactionPlayer players);

}