package com.frach.minecraft.factions.task;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.FactionInvite;
import com.frach.minecraft.factions.data.FactionPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class FactionInviteTask extends BukkitRunnable {

    @Override
    public void run() {
        List<FactionPlayer> players = Factions.getInstance().getFactionPlayerController().stream().filter(data -> !data.getInvites().isEmpty()).collect(Collectors.toList());

        for (FactionPlayer player : players) {
            List<FactionInvite> invites = player.getInvites().stream().filter(FactionInvite::isExpired).collect(Collectors.toList());

            player.getInvites().removeAll(invites);
        }
    }

}