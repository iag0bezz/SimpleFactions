package com.frach.minecraft.factions.adapter;

import com.google.common.collect.Lists;
import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ChunkAdapter implements JsonSerializer<List<Chunk>>, JsonDeserializer<List<Chunk>> {

    private static final String NAMED_CHUNK_PROPERTY = "FACTION_CHUNK_BASE";

    @Override
    public JsonElement serialize(List<Chunk> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        for(Chunk chunk : src) {
            if(chunk != null) {
                jsonObject.add(chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ(), new JsonPrimitive(NAMED_CHUNK_PROPERTY));
            }
        }

        return jsonObject;
    }

    @Override
    public List<Chunk> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Chunk> chunks = Lists.newArrayList();

        JsonObject jsonObject = json.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if(entry.getValue().getAsString().equals(NAMED_CHUNK_PROPERTY)) {
                String[] chunkParts = entry.getKey().split(",");

                String worldName = chunkParts[0];
                int x = Integer.parseInt(chunkParts[1]);
                int z = Integer.parseInt(chunkParts[2]);

                World bukkitWorld = Bukkit.getWorld(worldName);

                if(bukkitWorld != null) {
                    chunks.add(bukkitWorld.getChunkAt(x, z));
                }
            }
        }

        return chunks;
    }

}