package com.frach.minecraft.factions.data;

import com.google.common.collect.Lists;
import lombok.Data;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Faction {

    private UUID uniqueId;

    private String tag;
    private String name;
    private Location location;

    private List<FactionRank> ranks = Lists.newArrayList();
    private List<Chunk> chunks = Lists.newArrayList();

    private transient List<FactionPlayer> players = Lists.newArrayList();
    private transient boolean modified;

    public Faction(boolean first) {
        if (first) {
            this.ranks.add(new FactionRank("Leader", 4, true));
            this.ranks.add(new FactionRank("Co-Leader", 3));
            this.ranks.add(new FactionRank("Moderator", 2));
            this.ranks.add(new FactionRank("Member", 1));
        }
    }

    public FactionRank getRank(String name) {
        return this.ranks.stream().filter(data -> data.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<FactionPlayer> getOnlinePlayers() {
        return this.players.stream().filter(data -> data.getPlayer().isOnline()).collect(Collectors.toList());
    }

}