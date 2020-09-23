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
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PermissionInventory implements InventoryProvider {

    public static void open(Player player, FactionRank rank) {
        SmartInventory.builder()
                .title("§7Permissions: §e" + rank.getName())
                .id(rank.getName())
                .provider(new PermissionInventory())
                .size(3, 9)
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

        FactionRank rank = faction.getRank(contents.inventory().getId());

        if(rank == null) {
            player.closeInventory();
            return;
        }

        contents.set(new SlotPos(1, 1), ClickableItem.of(new ItemStackBuilder(Material.BARREL)
                .setName("§eKick")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to kick other players");
                    add("§7State: " + (rank.isKick() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setKick(!rank.isKick());

            faction.setModified(true);
        }));

        contents.set(new SlotPos(1, 2), ClickableItem.of(new ItemStackBuilder(Material.DIAMOND_PICKAXE)
                .setName("§eBlock Break")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to break block inside the base.");
                    add("§7State: " + (rank.isBlockBreak() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setBlockBreak(!rank.isBlockBreak());

            faction.setModified(true);
        }));

        contents.set(new SlotPos(1, 3), ClickableItem.of(new ItemStackBuilder(Material.COBBLESTONE)
                .setName("§eBlock Place")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to place block inside the base.");
                    add("§7State: " + (rank.isBlockPlace() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setBlockPlace(!rank.isBlockPlace());

            faction.setModified(true);
        }));

        contents.set(new SlotPos(1, 4), ClickableItem.of(new ItemStackBuilder(Material.PAPER)
                .setName("§eInvite")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to invite players.");
                    add("§7State: " + (rank.isInvite() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setInvite(!rank.isInvite());

            faction.setModified(true);
        }));

        contents.set(new SlotPos(1, 5), ClickableItem.of(new ItemStackBuilder(Material.DARK_OAK_DOOR)
                .setName("§eDisband")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to disband faction.");
                    add("§7State: " + (rank.isDisband() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setDisband(!rank.isDisband());

            faction.setModified(true);
        }));

        contents.set(new SlotPos(1, 6), ClickableItem.of(new ItemStackBuilder(Material.FEATHER)
                .setName("§eFly")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to fly inside the base.");
                    add("§7State: " + (rank.isFly() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setFly(!rank.isFly());

            faction.setModified(true);
        }));

        contents.set(new SlotPos(1, 7), ClickableItem.of(new ItemStackBuilder(Material.EMERALD)
                .setName("§eSet Home")
                .setLore(new ArrayList<String>() {{
                    add("");
                    add("§7Allows access to define base home.");
                    add("§7State: " + (rank.isSetHome() ? "§aON" : "§cOFF"));
                    add("");
                }})
                .build(), e-> {
            rank.setSetHome(!rank.isSetHome());

            faction.setModified(true);
        }));
    }

}