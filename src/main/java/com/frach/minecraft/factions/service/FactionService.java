package com.frach.minecraft.factions.service;

import com.frach.minecraft.factions.data.Faction;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FactionService extends SQLService {

    CompletableFuture<List<Faction>> load();

    void save(Faction... factions);

    void delete(Faction faction);

}