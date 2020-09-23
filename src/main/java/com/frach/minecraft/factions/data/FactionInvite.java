package com.frach.minecraft.factions.data;

import lombok.Getter;

@Getter
public class FactionInvite {

    private Faction faction;
    private long expire = System.currentTimeMillis() + (1000L * 30);

    public FactionInvite(Faction faction) {
        this.faction = faction;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= this.expire;
    }

}