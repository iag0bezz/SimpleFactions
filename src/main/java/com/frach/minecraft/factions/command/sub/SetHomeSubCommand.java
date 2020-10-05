package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.util.LocationUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SetHomeSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "sethome";
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

        if(!factionPlayer.getRank().isSetHome()) {
            sender.sendMessage(this.getMessage("no_have_permission"));
            return;
        }

        factionPlayer.getFaction().setLocation(sender.getLocation());
        factionPlayer.getFaction().setModified(true);

        sender.sendMessage(this.getMessage("modified", new HashMap<String, String>() {{
            put("%location%", LocationUtils.prettify(sender.getLocation()));
        }}));
    }

}