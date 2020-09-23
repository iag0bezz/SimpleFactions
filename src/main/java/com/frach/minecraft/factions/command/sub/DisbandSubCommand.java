package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DisbandSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "disband";
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

        if(!factionPlayer.getRank().getName().equalsIgnoreCase("leader")) {
            sender.sendMessage(this.getMessage("no_have_permission"));
            return;
        }

        Faction faction = factionPlayer.getFaction();

        for (FactionPlayer player : faction.getPlayers()) {
            player.setFaction(null);
            player.setRank(null);

            player.setModified(true);
        }

        sender.sendMessage(this.getMessage("disbanded", new HashMap<String, String>() {{
            put("%faction%", factionPlayer.getFaction().getName());
        }}));

        Factions.getInstance().getFactionController().delete(faction);
    }

}