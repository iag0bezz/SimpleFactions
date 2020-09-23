package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TagSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "tag";
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

        if(!factionPlayer.getRank().getName().equalsIgnoreCase("leader")) {
            sender.sendMessage(this.getMessage("no_have_permission"));
            return;
        }

        String tag = arguments[1];

        Faction faction = Factions.getInstance().getFactionController().stream().filter(data -> data.getTag().equalsIgnoreCase(tag)).findFirst().orElse(null);

        if(faction != null) {
            sender.sendMessage(this.getMessage("tag_already_used"));
            return;
        }

        factionPlayer.getFaction().setTag(tag);
        factionPlayer.getFaction().setModified(true);

        sender.sendMessage(this.getMessage("changed", new HashMap<String, String>() {{
            put("%tag%", tag);
        }}));
    }

}