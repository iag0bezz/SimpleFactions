package com.frach.minecraft.factions.hook;

import com.frach.minecraft.factions.Factions;
import com.frach.minecraft.factions.data.FactionPlayer;
import com.frach.minecraft.factions.util.LocationUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "factions";
    }

    @Override
    public String getAuthor() {
        return StringUtils.join(Factions.getInstance().getDescription().getAuthors(), ", ");
    }

    @Override
    public String getVersion() {
        return Factions.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        FactionPlayer factionPlayer = Factions.getInstance().getFactionPlayerController().find(p.getUniqueId()).orElse(null);

        if(factionPlayer != null && factionPlayer.getFaction() != null) {
            if(params.equalsIgnoreCase("tag")) {
                return factionPlayer.getFaction().getTag();
            }
            if(params.equalsIgnoreCase("name")) {
                return factionPlayer.getFaction().getName();
            }
            if(params.equalsIgnoreCase("rank")) {
                return factionPlayer.getRank().getName();
            }
            if(params.equalsIgnoreCase("chunks")) {
                return factionPlayer.getFaction().getChunks().size() + "";
            }
            if(params.equalsIgnoreCase("players")) {
                return factionPlayer.getFaction().getPlayers().size() + "";
            }
            if(params.equalsIgnoreCase("players_online")) {
                return factionPlayer.getFaction().getOnlinePlayers().size() + "";
            }
            if(params.equalsIgnoreCase("base")) {
                if(factionPlayer.getFaction().getLocation() != null) {
                    return LocationUtils.prettify(factionPlayer.getFaction().getLocation());
                } else return "§cNot defined";
            }
        }
        return "";
    }

}