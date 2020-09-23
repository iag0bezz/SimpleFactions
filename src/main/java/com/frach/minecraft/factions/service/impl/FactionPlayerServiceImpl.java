package com.frach.minecraft.factions.service.impl;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.service.FactionPlayerService;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class FactionPlayerServiceImpl implements FactionPlayerService {

    private Factions factions;

    public FactionPlayerServiceImpl(Factions factions) {
        this.factions = factions;

        try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(FACTION_PLAYER_TABLE)) {
                preparedStatement.executeUpdate();

                connection.close();
                this.factions.getLog().info("Table {} successfully loaded", FACTION_PLAYER_TABLE_NAME);
            }
        } catch(SQLException exception) {
            this.factions.getLog().info("Could not create table {}", FACTION_PLAYER_TABLE_NAME);
        }
    }

    @Override
    public CompletableFuture<List<FactionPlayer>> load() {
        return CompletableFuture.supplyAsync(() -> {
            List<FactionPlayer> players = Lists.newArrayList();

            try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
                try(PreparedStatement preparedStatement = connection.prepareStatement(PlayerQuery.SELECT)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {
                        FactionPlayer player = new FactionPlayer(UUID.fromString(resultSet.getString("id")));

                        String faction_id = resultSet.getString("faction_id");

                        if(faction_id != null && !faction_id.isEmpty()) {
                            Faction faction = this.factions.getFactionController().find(UUID.fromString(faction_id)).orElse(null);

                            if(faction != null) {
                                player.setFaction(faction);
                                player.setRank(faction.getRank(resultSet.getString("faction_rank")));

                                faction.getPlayers().add(player);
                            }
                        }

                        players.add(player);
                    }
                }
            }catch(Exception exception) {
                exception.printStackTrace();
                this.factions.getLog().info("Could not load player factions.");
            }

            return players;
        });
    }

    @Override
    public void save(FactionPlayer... players) {
        try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(PlayerQuery.INSERT_OR_UPDATE)) {
                for(FactionPlayer player : players) {
                    if(!player.isModified())
                        continue;

                    preparedStatement.setString(1, player.getUniqueId().toString());

                    String faction_id = player.getFaction() == null ? "" : player.getFaction().getUniqueId().toString();
                    String faction_rank = player.getFaction() == null ? ""   : player.getRank().getName();

                    preparedStatement.setString(2, faction_id);
                    preparedStatement.setString(3, faction_rank);

                    preparedStatement.setString(4, faction_id);
                    preparedStatement.setString(5, faction_rank);

                    preparedStatement.addBatch();
                    preparedStatement.clearParameters();
                }

                preparedStatement.executeBatch();
                connection.close();
            }
        }catch(SQLException exception) {
            this.factions.getLog().info("Could not save {} accounts", players.length);
        }
    }

    @Override
    public void delete(FactionPlayer players) {
        try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(PlayerQuery.DELETE)) {
                preparedStatement.setString(1, players.getUniqueId().toString());

                preparedStatement.executeUpdate();
                connection.close();
            }
        }catch(SQLException exception) {
            this.factions.getLog().info("Could not delete player {}", players.getUniqueId().toString());
        }
    }

}