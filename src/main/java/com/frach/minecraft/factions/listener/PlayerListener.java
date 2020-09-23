package com.frach.minecraft.factions.listener;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.controller.FactionPlayerController;
import com.frach.minecraft.factions.data.FactionPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final FactionPlayerController factionPlayerController;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FactionPlayer factionPlayer = this.factionPlayerController.find(player.getUniqueId()).orElse(null);

        if(factionPlayer == null) {
            this.factionPlayerController.create(new FactionPlayer(player.getUniqueId()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.factionPlayerController.find(player.getUniqueId()).ifPresent(Factions.getInstance().getFactionPlayerService()::save);
    }

}