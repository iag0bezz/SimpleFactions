package com.frach.minecraft.factions.adapter;

import com.frach.minecraft.factions.data.Faction;
import com.frach.minecraft.factions.data.FactionRank;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

public class FactionAdapter implements JsonSerializer<Faction>, JsonDeserializer<Faction> {

    @Override
    public JsonElement serialize(Faction src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        if(src != null) {
            jsonObject.add("uniqueId", new JsonPrimitive(src.getUniqueId().toString()));
            jsonObject.add("tag", new JsonPrimitive(src.getTag()));
            jsonObject.add("name", new JsonPrimitive(src.getName()));

            if(src.getLocation() != null) {
                jsonObject.add("home", context.serialize(src.getLocation(), Location.class));
            }

            if(!src.getRanks().isEmpty()) {
                Type type = new TypeToken<List<FactionRank>>(){}.getType();

                if(type != null) {
                    jsonObject.add("ranks", context.serialize(src.getRanks(), type));
                }
            }

            if(!src.getChunks().isEmpty()) {
                Type type = new TypeToken<List<Chunk>>(){}.getType();

                if(type != null) {
                    jsonObject.add("chunks", context.serialize(src.getChunks(), type));
                }
            }
        }

        return jsonObject;
    }

    @Override
    public Faction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        try {
            Faction faction = new Faction(false);
            faction.setUniqueId(UUID.fromString(jsonObject.get("uniqueId").getAsString()));

            faction.setTag(jsonObject.get("tag").getAsString());
            faction.setName(jsonObject.get("name").getAsString());

            JsonElement homeElement = jsonObject.get("home");

            if(homeElement != null) {
                faction.setLocation(context.deserialize(homeElement, Location.class));
            }

            JsonElement ranksElement = jsonObject.get("ranks");

            if(ranksElement != null) {
                Type type = new TypeToken<List<FactionRank>>(){}.getType();

                if(type != null) {
                    faction.setRanks(context.deserialize(ranksElement, type));
                }
            }

            JsonElement chunksElement = jsonObject.get("chunks");

            if(chunksElement != null) {
                Type type = new TypeToken<List<Chunk>>(){}.getType();

                if(type != null) {
                    faction.setChunks(context.deserialize(chunksElement, type));
                }
            }

            return faction;
        }catch(Exception exception) {
            return null;
        }
    }

}