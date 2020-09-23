package com.frach.minecraft.factions.data;

import com.google.common.collect.Lists;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Data
public class FactionPlayer {

    private UUID uniqueId;

    private FactionRank rank;

    private transient Faction faction;
    private transient boolean modified;
    private transient boolean flying;

    private transient List<FactionInvite> invites = Lists.newArrayList();

    public FactionPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public OfflinePlayer getPlayer() {
        Player player = Bukkit.getPlayer(this.uniqueId);

        return player == null ? Bukkit.getOfflinePlayer(this.uniqueId) : player;
    }

}