package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class KickSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "kick";
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

        if(!factionPlayer.getRank().isKick()) {
            sender.sendMessage(this.getMessage("no_have_permission"));
            return;
        }

        Player target = Bukkit.getPlayer(name);

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

        Faction faction = factionPlayer.getFaction();

        if(!faction.getPlayers().contains(targetFP)) {
            sender.sendMessage(this.getMessage("invalid_target"));
            return;
        }

        if(targetFP.getRank().getPriority() >= factionPlayer.getRank().getPriority()) {
            sender.sendMessage(this.getMessage("invalid_kick_superior"));
            return;
        }

        faction.getPlayers().remove(targetFP);
        faction.setModified(true);

        targetFP.setFaction(null);
        targetFP.setRank(null);
        targetFP.setModified(true);

        sender.sendMessage(this.getMessage("kicked", new HashMap<String, String>() {{
            put("%target%", target.getName());
        }}));
    }

    @Override
    public List<String> getTabComplete() {
        List<String> names = Lists.newArrayList();

        Bukkit.getOnlinePlayers().forEach(online -> names.add(online.getName()));

        return names;
    }

}