package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.FactionPlayer;
import org.bukkit.entity.Player;

public class HomeSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "home";
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

        if(factionPlayer.getFaction().getLocation() == null) {
            sender.sendMessage(this.getMessage("no_home"));
            return;
        }

        sender.teleport(factionPlayer.getFaction().getLocation());

        sender.sendMessage(this.getMessage("teleported"));
    }

}