package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ClaimSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public void execute(Player sender, String[] arguments) {
        FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(sender.getUniqueId()).orElse(null);

        if(factionPlayer == null) {
            sender.sendMessage("§cFailed to load your account data, try to log in to the server again.");
            return;
        }

        if(factionPlayer.getFaction() == null) {
            sender.sendMessage(this.getMessage("no_have_faction"));
            return;
        }

        Chunk chunk = sender.getLocation().getChunk();

        Faction faction = Factions.getInstance().getFactionController().stream().filter(data -> data.getChunks().contains(chunk)).findFirst().orElse(null);

        if(faction != null) {
            sender.sendMessage(this.getMessage("already_claimed", new HashMap<String, String>() {{
                put("%faction%", faction.getName());
            }}));
            return;
        }

        factionPlayer.getFaction().getChunks().add(chunk);
        factionPlayer.getFaction().setModified(true);

        sender.sendMessage(this.getMessage("claimed", new HashMap<String, String>() {{
            put("%chunk%", "X: " + chunk.getX() + " Z: " + chunk.getZ());
        }}));
    }

}