package com.frach.minecraft.factions.inventory;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.data.FactionRank;
import com.frach.minecraft.factions.util.ItemStackBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;

public class RankInventory implements InventoryProvider {

    public static void open(Player player) {
        SmartInventory.builder()
                .title("§7Managing Ranks")
                .provider(new RankInventory())
                .size(4, 9)
                .build().open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        this.items(player, contents);
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        this.items(player, contents);
    }

    private void items(Player player, InventoryContents contents) {
        FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(player.getUniqueId()).orElse(null);

        if(factionPlayer == null || factionPlayer.getFaction() == null) {
            player.closeInventory();
            return;
        }

        if(!factionPlayer.getRank().getName().equalsIgnoreCase("leader")) {
            player.closeInventory();
            return;
        }

        Faction faction = factionPlayer.getFaction();

        ClickableItem[] items = new ClickableItem[faction.getPlayers().size()];
        Pagination pagination = contents.pagination();

        for (int i = 0; i < faction.getPlayers().size(); i++) {
            FactionPlayer fp = faction.getPlayers().get(i);

            if(fp == null || fp.getFaction() == null || fp.equals(factionPlayer))
                continue;

            ItemStackBuilder itemStackBuilder = new ItemStackBuilder(Material.PLAYER_HEAD).setOwner(fp.getPlayer().getName())
                    .setLore(new ArrayList<String>() {{
                        add("");
                        add("§7Left Click - §eClick to promote this player.");
                        add("§7Right Click - §eClick to demote this player");
                        add("");
                        add("§7Role: §e" + fp.getRank().getName());
                        add("");
                    }})
                    .setName("§e" + fp.getPlayer().getName());

            items[i] = ClickableItem.of(itemStackBuilder.build(), e -> {
                if(e.getClick() == ClickType.LEFT) {
                    FactionRank next = faction.getRanks().stream().filter(data -> data.getPriority() == (fp.getRank().getPriority() + 1)).findFirst().orElse(null);

                    if(next == null) {
                        player.sendMessage(Factions.getInstance().getConfiguration().getString("messages.inventory.manager.maximum_rank"));
                        return;
                    }

                    fp.setRank(next);
                } else if(e.getClick() == ClickType.RIGHT) {
                    FactionRank previous = faction.getRanks().stream().filter(data -> data.getPriority() == (fp.getRank().getPriority() - 1)).findFirst().orElse(null);

                    if(previous == null) {
                        player.sendMessage(Factions.getInstance().getConfiguration().getString("messages.inventory.manager.minimum_rank"));
                        return;
                    }

                    fp.setRank(previous);
                }
            });
        }

        pagination.setItemsPerPage(14);
        pagination.setItems(items);

        SlotIterator iterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1);
        iterator.blacklist(1, 0);
        iterator.blacklist(1, 8);
        iterator.blacklist(2, 0);
        iterator.blacklist(2, 8);

        pagination.addToIterator(iterator);
    }

}