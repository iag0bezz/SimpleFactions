package com.frach.minecraft.factions.command.sub;

import com.frach.minecraft.factions.command.SubCommand;
import com.frach.minecraft.factions.inventory.ListInventory;
import org.bukkit.entity.Player;

public class ListSubCommand implements SubCommand<Player> {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public void execute(Player sender, String[] arguments) {
        ListInventory.open(sender, 0);
    }

}