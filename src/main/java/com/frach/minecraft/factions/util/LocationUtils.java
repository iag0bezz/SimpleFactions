package com.frach.minecraft.factions.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String prettify(Location location) {
        return location.getWorld().getName() + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ();
    }

    public static String toString(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    public static Location toLocation(String string) {
        if(string.isEmpty()) return null;

        try {
            String[] split = string.split(";");

            String worldName = split[0];
            int x = Integer.parseInt(split[1]);
            int y = Integer.parseInt(split[2]);
            int z = Integer.parseInt(split[3]);
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);

            return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }catch(Exception exception) {
            return null;
        }
    }

}