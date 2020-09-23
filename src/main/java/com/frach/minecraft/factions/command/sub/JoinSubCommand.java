package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.FactionInvite;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class JoinSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public void execute(Player sender, String[] arguments) {
        FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(sender.getUniqueId()).orElse(null);

        if(factionPlayer == null) {
            sender.sendMessage("§cFailed to load your account data, try to log in to the server again.");
            return;
        }

        if(arguments.length < 2) {
            sender.sendMessage(this.getMessage("provide_arguments"));
            return;
        }

        if(factionPlayer.getFaction() != null) {
            sender.sendMessage(this.getMessage("already_have_faction"));
            return;
        }

        String tag = arguments[1];

        FactionInvite invite = factionPlayer.getInvites().stream().filter(data -> data.getFaction().getTag().equals(tag) && !data.isExpired()).findFirst().orElse(null);

        if(invite == null) {
            sender.sendMessage(this.getMessage("no_invite"));
            return;
        }

        invite.getFaction().getPlayers().add(factionPlayer);

        factionPlayer.setFaction(invite.getFaction());
        factionPlayer.setRank(invite.getFaction().getRank("member"));
        factionPlayer.setModified(true);

        factionPlayer.getInvites().remove(invite);

        sender.sendMessage(this.getMessage("accepted", new HashMap<String, String>() {{
            put("%faction%", invite.getFaction().getName());
        }}));
    }

    @Override
    public List<String> getTabComplete() {
        List<String> tags = Lists.newArrayList();

        Factions.getInstance().getFactionController().forEach(faction -> tags.add(faction.getTag()));

        return tags;
    }
}