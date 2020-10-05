package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.inventory.RankInventory;
import org.bukkit.entity.Player;

public class ManagerSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "manager";
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

        RankInventory.open(sender);
    }

}