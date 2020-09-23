package com.frach.minecraft.factions.service.impl;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.service.FactionService;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FactionServiceImpl implements FactionService {

    private Factions factions;

    public FactionServiceImpl(Factions factions) {
        this.factions = factions;

        try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(FACTION_TABLE)) {
                preparedStatement.executeUpdate();

                connection.close();
                this.factions.getLog().info("Table {} successfully loaded", FACTION_TABLE_NAME);
            }
        } catch(SQLException exception) {
            this.factions.getLog().info("Could not create table {}", FACTION_TABLE_NAME);
        }
    }

    @Override
    public CompletableFuture<List<Faction>> load() {
        return CompletableFuture.supplyAsync(() -> {
            List<Faction> factions = Lists.newArrayList();

            try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
                try(PreparedStatement preparedStatement = connection.prepareStatement(FactionQuery.SELECT)) {
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()) {
                        String data = resultSet.getString("data");

                        if(data != null) {
                            Faction faction = this.factions.getGson().fromJson(data, Faction.class);

                            if(faction != null) {
                                factions.add(faction);
                            }
                        }
                    }
                }
            }catch(SQLException exception) {
                this.factions.getLog().info("Could not load factions.");
            }

            return factions;
        });
    }

    @Override
    public void save(Faction... factions) {
        try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(FactionQuery.INSERT_OR_UPDATE)) {
                for(Faction faction : factions) {
                    if(!faction.isModified())
                        continue;

                    preparedStatement.setString(1, faction.getUniqueId().toString());

                    String data = this.factions.getGson().toJson(faction, Faction.class);

                    preparedStatement.setString(2, data);
                    preparedStatement.setString(3, data);

                    preparedStatement.addBatch();
                    preparedStatement.clearParameters();
                }

                preparedStatement.executeBatch();
                connection.close();
            }
        }catch(SQLException exception) {
            this.factions.getLog().info("Could not save {} accounts", factions.length);
        }
     }

    @Override
    public void delete(Faction faction) {
        try(Connection connection = this.factions.getHikariDataSource().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(FactionQuery.DELETE)) {
                preparedStatement.setString(1, faction.getUniqueId().toString());

                preparedStatement.executeUpdate();
                connection.close();
            }
        }catch(SQLException exception) {
            this.factions.getLog().info("Could not save faction {}", faction.getUniqueId().toString());
        }
    }

}