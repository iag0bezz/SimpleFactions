package com.frach.minecraft.factions.listener;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class FactionListener implements Listener {

    private final Factions factions;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Chunk chunk = event.getBlock().getChunk();

        Faction faction = this.factions.getFactionController().stream().filter(data -> data.getChunks().contains(chunk)).findFirst().orElse(null);

        if (faction != null) {
            FactionPlayer factionPlayer = this.factions.getFactionPlayerController().find(event.getPlayer().getUniqueId()).orElse(null);

            if(factionPlayer != null && factionPlayer.getFaction().equals(faction)) {
                if(!factionPlayer.getRank().isBlockBreak()) {
                    event.setCancelled(true);
                }
            } else event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Chunk chunk = event.getBlock().getChunk();

        Faction faction = this.factions.getFactionController().stream().filter(data -> data.getChunks().contains(chunk)).findFirst().orElse(null);

        if (faction != null) {
            FactionPlayer factionPlayer = this.factions.getFactionPlayerController().find(event.getPlayer().getUniqueId()).orElse(null);

            if(factionPlayer != null && factionPlayer.getFaction().equals(faction)) {
                if(!factionPlayer.getRank().isBlockBreak()) {
                    event.setCancelled(true);
                }
            } else event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();

        if(to.getX() != from.getX() || to.getZ() != from.getZ()) {
            Chunk chunk = event.getPlayer().getLocation().getChunk();

            FactionPlayer factionPlayer = this.factions.getFactionPlayerController().find(event.getPlayer().getUniqueId()).orElse(null);

            if(factionPlayer == null)
                return;

            Faction faction = this.factions.getFactionController().stream().filter(data -> data.getChunks().contains(chunk)).findFirst().orElse(null);

            if (faction != null) {
                if (factionPlayer.getFaction().equals(faction)) {
                    event.getPlayer().setAllowFlight(factionPlayer.getRank().isFly());

                    if(factionPlayer.getRank().isFly()) {
                        factionPlayer.setFlying(true);
                    }
                }
            } else if(factionPlayer.isFlying()) {
                event.getPlayer().setAllowFlight(false);
                event.getPlayer().setFlying(false);

                factionPlayer.setFlying(false);
            }
        }
    }

}