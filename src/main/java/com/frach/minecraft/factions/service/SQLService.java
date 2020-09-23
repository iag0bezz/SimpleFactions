package com.frach.minecraft.factions.service;

public interface SQLService {

    String FACTION_TABLE_NAME = "simplefactions_faction";
    String FACTION_PLAYER_TABLE_NAME = "simplefactions_players";

    interface FactionQuery {

        String INSERT_OR_UPDATE =  "INSERT INTO\n" +
                "  `" + FACTION_TABLE_NAME + "` (id, data)\n" +
                "VALUES\n" +
                "  (?, ?) ON DUPLICATE KEY\n" +
                "UPDATE\n" +
                "  data = ?";

        String SELECT = "SELECT * FROM `" + FACTION_TABLE_NAME + "`;";

        String DELETE = "DELETE FROM `" + FACTION_TABLE_NAME + "` WHERE `id` = ?";

    }

    interface PlayerQuery {

        String INSERT_OR_UPDATE = "INSERT INTO\n" +
                "  `" + FACTION_PLAYER_TABLE_NAME + "` (`id`, `faction_id`, `faction_rank`)\n" +
                "VALUES\n" +
                "  (?, ?, ?) ON DUPLICATE KEY\n" +
                "UPDATE\n" +
                "  `faction_id` = ?,\n" +
                "  `faction_rank` = ?\n";

        String SELECT = "SELECT * FROM `" + FACTION_PLAYER_TABLE_NAME + "`;";

        String DELETE = "DELETE FROM `" + FACTION_PLAYER_TABLE_NAME + "` WHERE `id` = ?";

    }

    String FACTION_TABLE = "CREATE TABLE IF NOT EXISTS `" + FACTION_TABLE_NAME + "` (\n" +
            "  `id` VARCHAR(36) NOT NULL,\n" +
            "  `data` TEXT NOT NULL,\n" +
            "  PRIMARY KEY(`id`)\n" +
            ")\n";

    String FACTION_PLAYER_TABLE = "CREATE TABLE IF NOT EXISTS `" + FACTION_PLAYER_TABLE_NAME + "` (\n" +
            "  `id` VARCHAR(36) NOT NULL,\n" +
            "  `faction_id` VARCHAR(36) NOT NULL,\n" +
            "  `faction_rank` VARCHAR(36) NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ")\n";

}