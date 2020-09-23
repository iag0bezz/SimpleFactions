package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreateSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public void execute(Player sender, String[] arguments) {
        FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(sender.getUniqueId()).orElse(null);

        if(factionPlayer == null) {
            sender.sendMessage("§cFailed to load your account data, try to log in to the server again.");
            return;
        }

        if(arguments.length < 3) {
            sender.sendMessage(this.getMessage("provide_arguments"));
            return;
        }

        if(factionPlayer.getFaction() != null) {
            sender.sendMessage(this.getMessage("already_have_faction"));
            return;
        }

        String tag = arguments[1];

        String name = "";

        for (int i = 2; i < arguments.length; i++) {
            if(name.isEmpty())
                name = arguments[i];
            else name = name + " " + arguments[i];
        }

        Faction faction = Factions.getInstance().getFactionController().stream().filter(data -> data.getTag() != null && data.getTag().equalsIgnoreCase(tag)).findFirst().orElse(null);

        if(faction != null) {
            sender.sendMessage(this.getMessage("tag_already_used"));
            return;
        }

        String finalName = name;
        faction = Factions.getInstance().getFactionController().stream().filter(data -> data.getName() != null && data.getName().equalsIgnoreCase(finalName)).findFirst().orElse(null);

        if(faction != null) {
            sender.sendMessage(this.getMessage("name_already_used"));
            return;
        }

        faction = new Faction(true);
        faction.setUniqueId(UUID.randomUUID());

        faction.setTag(tag);
        faction.setName(finalName);
        faction.getPlayers().add(factionPlayer);
        faction.setModified(true);

        factionPlayer.setRank(faction.getRank("leader"));
        factionPlayer.setFaction(faction);
        factionPlayer.setModified(true);

        sender.sendMessage(this.getMessage("created"));

        Factions.getInstance().getFactionController().create(faction);
    }

}