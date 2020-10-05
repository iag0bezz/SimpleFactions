package com.frach.minecraft.factions.inventory;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.controller.FactionController;
import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.util.BannerAlphabetic;
import com.frach.minecraft.factions.util.ItemStackBuilder;
import com.google.common.collect.Lists;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.stream.Collectors;

public class ListInventory implements InventoryProvider {

    public static void open(Player player, int page) {
        SmartInventory.builder()
                .title("§7Listing all factions")
                .provider(new ListInventory())
                .size(5, 9)
                .build().open(player, page);
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
        FactionController factionController = Factions.getInstance().getFactionController();

        Pagination pagination = contents.pagination();
        List<Faction> factions = factionController.stream().collect(Collectors.toList());

        ClickableItem[] items = new ClickableItem[factions.size()];

        for (int i = 0; i < items.length; i++) {
            Faction faction = factions.get(i);

            items[i] = ClickableItem.empty(new ItemStackBuilder(BannerAlphabetic.getBannerByLetter(faction.getName().substring(0, 1)).getBanner())
                    .setName("§e" + faction.getName())
                    .setLore(Lists.newArrayList())
                    .addFlag(ItemFlag.values())
                    .build());
        }

        pagination.setItemsPerPage(21);
        pagination.setItems(items);

        SlotIterator slotIterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1);

        slotIterator.blacklist(1, 8);
        slotIterator.blacklist(2, 8);
        slotIterator.blacklist(3, 8);

        slotIterator.blacklist(2, 0);
        slotIterator.blacklist(3, 0);

        pagination.addToIterator(slotIterator);

        contents.set(new SlotPos(2, 0), ClickableItem.of(new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE).setName("§c<< Previous Page").build(), e-> {
            open(player, pagination.previous().getPage());
        }));

        contents.set(new SlotPos(2, 8), ClickableItem.of(new ItemStackBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("§aNext Page >>").build(), e-> {
            open(player, pagination.next().getPage());
        }));
    }

}