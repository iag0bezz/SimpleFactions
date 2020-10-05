package com.frach.minecraft.factions.command;

import com.frach.minecraft.factions.Factions;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FactionCommand implements CommandExecutor, TabCompleter {

    private final Factions factions;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length < 1 || args[0].equalsIgnoreCase("help")) {
            for(String message : this.factions.getConfiguration().getStringList("messages.help")) {
                commandSender.sendMessage(message);
            }
            return true;
        }

        SubCommand subCommand = this.factions.getSubCommandManager().find(args[0]);

        if(subCommand != null) {
            if(!commandSender.hasPermission(subCommand.getPermission()) && !commandSender.hasPermission("factions.commands.*")) {
                commandSender.sendMessage(this.factions.getConfiguration().getString("messages.no_permission"));
                return true;
            }

            if(!(commandSender instanceof Player) && !subCommand.allowConsole()) {
                commandSender.sendMessage("§cCommand exclusive to players.");
                return true;
            } else subCommand.execute(commandSender, args);
        } else commandSender.sendMessage(this.factions.getConfiguration().getString("messages.invalid_subcommand"));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = Lists.newArrayList();

        if(args.length == 1) {
            if(args[0].isEmpty()) {
                return this.factions.getSubCommandManager().getCommandsName();
            } else return this.factions.getSubCommandManager().getCommandsName().stream().filter(data -> data.toLowerCase().startsWith(args[0])).collect(Collectors.toList());
        } else if (args.length > 1) {
            SubCommand subCommand = this.factions.getSubCommandManager().find(args[0]);

            if(subCommand != null) {
                completions.addAll(subCommand.getTabComplete());
            }
        }

        return completions;
    }

}