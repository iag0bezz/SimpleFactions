package com.frach.minecraft.factions.command;

import com.frach.minecraft.factions.Factions;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SubCommand<T extends CommandSender> {

    String getName();

    void execute(T sender, String[] arguments);

    default String getPermission() {
        return "factions.commands." + this.getName();
    }

    default boolean allowConsole() {
        return false;
    }

    default String getMessage(String path, HashMap<String, String> placeholders) {
        String message = Factions.getInstance().getConfiguration().getString("messages.commands." + this.getName() + "." + path);

        if(placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
        }

        return message;
    }

    default String getMessage(String path) {
        return this.getMessage(path, null);
    }

    default List<String> getTabComplete () {
        return Lists.newArrayList();
    }

}