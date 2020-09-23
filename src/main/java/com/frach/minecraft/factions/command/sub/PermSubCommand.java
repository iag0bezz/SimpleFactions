package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.data.FactionRank;
import com.frach.minecraft.factions.inventory.PermissionInventory;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.util.List;

public class PermSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "perm";
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

        if(arguments.length < 2) {
            sender.sendMessage(this.getMessage("provide_arguments"));
            return;
        }

        String name = arguments[1];

        if(!factionPlayer.getRank().getName().equalsIgnoreCase("leader")) {
            sender.sendMessage(this.getMessage("no_have_permission"));
            return;
        }

        FactionRank rank = factionPlayer.getFaction().getRank(name);

        if(rank == null) {
            sender.sendMessage(this.getMessage("invalid_rank"));
            return;
        }

        PermissionInventory.open(sender, rank);
    }

    @Override
    public List<String> getTabComplete() {
        return Lists.newArrayList("leader", "co-leader", "moderator", "member");
    }

}