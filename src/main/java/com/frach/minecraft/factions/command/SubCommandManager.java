package com.frach.minecraft.factions.command;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.command.sub.*;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class SubCommandManager {

    private List<SubCommand<?>> commands = Lists.newArrayList();

    public SubCommandManager() {
        this.commands.add(new CreateSubCommand());
        this.commands.add(new WhoSubCommand());
        this.commands.add(new ClaimSubCommand());
        this.commands.add(new TagSubCommand());
        this.commands.add(new PermSubCommand());
        this.commands.add(new JoinSubCommand());
        this.commands.add(new InviteSubCommand());
        this.commands.add(new KickSubCommand());
        this.commands.add(new SetHomeSubCommand());
        this.commands.add(new HomeSubCommand());
        this.commands.add(new DisbandSubCommand());
        this.commands.add(new ManagerSubCommand());
        this.commands.add(new ListSubCommand());

        this.commands.forEach(command -> Factions.getInstance().getLog().info("Successfully loaded '" + command.getName() + "' sub command."));
    }

    public SubCommand<?> find(String name) {
        return this.commands.stream().filter(data -> data.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<String> getCommandsName() {
        return new ArrayList<String>() {{
           commands.forEach(command -> add(command.getName()));
        }};
    }

}