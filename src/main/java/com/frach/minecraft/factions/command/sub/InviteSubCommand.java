package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.FactionInvite;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class InviteSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "invite";
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

        if(factionPlayer.getFaction() == null) {
            sender.sendMessage(this.getMessage("no_have_faction"));
            return;
        }

        if(!factionPlayer.getRank().isInvite()) {
            sender.sendMessage(this.getMessage("no_permission"));
            return;
        }

        Player target = Bukkit.getPlayer(arguments[1]);

        if(target == null) {
            sender.sendMessage(this.getMessage("target_offline"));
            return;
        }

        if(target == sender) {
            sender.sendMessage(this.getMessage("yourself"));
            return;
        }

        FactionPlayer targetFP = Factions.getInstance().getFactionPlayerController().find(target.getUniqueId()).orElse(null);

        if(targetFP == null) {
            sender.sendMessage(this.getMessage("invalid_session"));
            return;
        }

        FactionInvite invite = targetFP.getInvites().stream().filter(data -> data.getFaction().equals(factionPlayer.getFaction()) && !data.isExpired()).findFirst().orElse(null);

        if(invite != null) {
            sender.sendMessage(this.getMessage("already_have_invited"));
            return;
        }

        invite = new FactionInvite(factionPlayer.getFaction());

        targetFP.getInvites().add(invite);

        sender.sendMessage(this.getMessage("invited", new HashMap<String, String>() {{
            put("%target%", target.getName());
        }}));

        target.sendMessage(this.getMessage("invited_other", new HashMap<String, String>() {{
            put("%faction", factionPlayer.getFaction().getTag());
        }}));
    }

    @Override
    public List<String> getTabComplete() {
        List<String> names = Lists.newArrayList();

        Bukkit.getOnlinePlayers().forEach(online -> names.add(online.getName()));

        return names;
    }

}