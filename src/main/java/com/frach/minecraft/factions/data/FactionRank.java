package com.frach.minecraft.factions.data;

import lombok.Data;

@Data
public class FactionRank {

    private String name;
    private int priority;

    private boolean kick, blockBreak, blockPlace, invite, disband, fly, setHome;

    public FactionRank(String name, int priority) {
        this(name, priority, false);
    }

    public FactionRank(String name, int priority, boolean admin) {
        this.name = name;
        this.priority = priority;

        if(admin) {
            this.kick = true;
            this.blockBreak = true;
            this.blockPlace = true;
            this.invite = true;
            this.disband = true;
            this.fly = true;
            this.setHome = true;
        }
    }

}