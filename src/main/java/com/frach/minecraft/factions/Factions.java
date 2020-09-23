package com.frach.minecraft.factions;

import com.frach.minecraft.factions.adapter.ChunkAdapter;
import com.frach.minecraft.factions.adapter.FactionAdapter;
import com.frach.minecraft.factions.adapter.LocationAdapter;
import com.frach.minecraft.factions.command.FactionCommand;
import com.frach.minecraft.factions.command.SubCommandManager;
import com.frach.minecraft.factions.controller.FactionController;
import com.frach.minecraft.factions.controller.FactionPlayerController;
import com.frach.minecraft.factions.controller.impl.FactionControllerImpl;
import com.frach.minecraft.factions.controller.impl.FactionPlayerControllerImpl;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.helper.ConfigurationHelper;
import com.frach.minecraft.factions.helper.PluginHelper;
import com.frach.minecraft.factions.hook.PlaceholderAPIHook;
import com.frach.minecraft.factions.listener.FactionListener;
import com.frach.minecraft.factions.listener.PlayerListener;
import com.frach.minecraft.factions.service.FactionPlayerService;
import com.frach.minecraft.factions.service.FactionService;
import com.frach.minecraft.factions.service.impl.FactionPlayerServiceImpl;
import com.frach.minecraft.factions.service.impl.FactionServiceImpl;
import com.frach.minecraft.factions.task.FactionInviteTask;
import com.frach.minecraft.factions.task.FactionScoreboardTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Getter
public final class Factions extends PluginHelper {

    private HikariDataSource hikariDataSource;

    private final ForkJoinPool executor = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Faction.class, new FactionAdapter())
            .registerTypeAdapter(new TypeToken<List<Chunk>>(){}.getType(), new ChunkAdapter())
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();

    private FactionController factionController;
    private FactionPlayerController factionPlayerController;

    private ConfigurationHelper configuration;
    private SubCommandManager subCommandManager;

    @Override
    public void load() {
        this.configuration = new ConfigurationHelper(this, "config.yml");

        this.hikariDataSource = this.getDataSourceFromConfig();

        this.provideService(FactionService.class, new FactionServiceImpl(this));
        this.provideService(FactionPlayerService.class, new FactionPlayerServiceImpl(this));
    }

    @Override
    public void enable() {
        this.factionController = new FactionControllerImpl();
        this.factionPlayerController = new FactionPlayerControllerImpl();

        if(this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.getLog().info("Successfully hooked into PlaceholderAPI");

            new PlaceholderAPIHook().register();
        }

        this.factionController.constructor(this).thenAcceptAsync(unused -> {
            this.getLog().info("Successfully loaded all factions.");
            this.factionPlayerController.constructor(this).thenAcceptAsync(unused1 -> {
                this.getLog().info("Successfully loaded all players.");

                this.subCommandManager = new SubCommandManager();

                this.getServer().getPluginManager().registerEvents(new PlayerListener(this.factionPlayerController), this);
                this.getServer().getPluginManager().registerEvents(new FactionListener(this), this);

                this.getCommand("faction").setExecutor(new FactionCommand(this));

                new FactionInviteTask().runTaskTimer(this, 20L, 20L);
                new FactionScoreboardTask().runTaskTimer(this, 20L, 20L);
            }, this.getExecutor());
        }, this.getExecutor());
    }

    @Override
    public void disable() {
        this.factionController.destructor(this);
        this.factionPlayerController.destructor(this);
    }

    public FactionService getFactionService() {
        return getService(FactionService.class);
    }

    public FactionPlayerService getFactionPlayerService() {
        return getService(FactionPlayerService.class);
    }

    public static Factions getInstance() {
        return getPlugin(Factions.class);
    }

}