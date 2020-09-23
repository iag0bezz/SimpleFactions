package com.frach.minecraft.factions.adapter;

import com.frach.minecraft.factions.util.LocationUtils;
import com.google.gson.*;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("location", new JsonPrimitive(LocationUtils.toString(location)));

        return jsonObject;
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return LocationUtils.toLocation(jsonObject.get("location").getAsString());
    }

}