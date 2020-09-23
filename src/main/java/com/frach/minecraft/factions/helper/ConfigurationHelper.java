package com.frach.minecraft.factions.helper;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

public class ConfigurationHelper {

    private File file;
    private FileConfiguration fileConfiguration;
    String fileName;

    public ConfigurationHelper(final Plugin plugin, String fileName) {
        this(plugin, null, fileName, null);
    }

    public ConfigurationHelper(Plugin plugin, String folder, String fileName){
        this(plugin, folder, fileName, null);
    }

    public ConfigurationHelper(Plugin plugin, String folder, String fileName, String defaults) {
        this.fileName = fileName;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        if (!fileName.isEmpty()) {
            fileName = (fileName.endsWith(".yml") ? fileName : (fileName + ".yml"));
        }

        new File(plugin.getDataFolder() + (folder == null ? "" : File.separator + folder)).mkdirs();

        if(folder == null) {
            this.file = new File(plugin.getDataFolder(), fileName.isEmpty() ? "config.yml" : fileName);
        } else {
            this.file = new File(plugin.getDataFolder(), fileName.isEmpty() ? "config.yml" : folder + File.separator + fileName);
        }

        try{
            if(!file.exists()) {
                if (folder == null) {
                    plugin.saveResource(fileName, false);
                } else {
                    if (!this.file.exists()) {
                        createNewFile();
                    }
                }
            }
            this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
            this.fileConfiguration.loadFromString(Files.toString(file, Charset.forName("UTF-8")));

            if(defaults != null && !defaults.isEmpty()) {
                Reader defaultConfigStream = new InputStreamReader(plugin.getResource(defaults), "UTF-8");

                if(defaultConfigStream != null) {
                    YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);

                    this.fileConfiguration.setDefaults(defaultConfig);
                    this.fileConfiguration.options().copyDefaults(true);

                    this.save();
                }
            }
        }catch(IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void createNewFile() throws IOException {
        this.file.createNewFile();
    }

    public Object get(final String path, Object defaultValue) {
        Object object = this.fileConfiguration.get(path);

        return object == null ? defaultValue : object;
    }

    public Object get(final String path) {
        return this.get(path, null);
    }

    public String getString(final String path) {
        return ChatColor.translateAlternateColorCodes('&', this.fileConfiguration.getString(path));
    }

    public int getInt(final String path) {
        return (int) this.get(path,0);
    }

    public List<?> getList(final String path) {
        return (List<?>)this.get(path, Lists.newArrayList());
    }

    public List<String> getStringList(final String path) {
        List<String> list = Lists.newArrayList();
        for (String s : (List<String>) this.get(path, Lists.newArrayList())) {
            list.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return list;
    }

    public EntityType getEntityType(final String path){
        return EntityType.valueOf(getString(path));
    }

    public double getDouble(final String path) {
        return (double) this.get(path, 0.0);
    }

    public float getFloat(final String path) {
        return (float) this.get(path, 0.0);
    }

    public List<Integer> getIntegetList(final String path) {
        return (List<Integer>)this.get(path, Lists.newArrayList());
    }

    public List<Double> getDoubleList(final String path) {
        return (List<Double>)this.get(path, Lists.newArrayList());
    }

    public List<Float> getFloatList(final String path) {
        return (List<Float>) this.get(path, Lists.newArrayList());
    }

    public boolean getBoolean(final String path) {
        return (Boolean) this.get(path, false);
    }

    public void set(final String path, final Object value) {
        this.fileConfiguration.set(path, value);
    }

    public ConfigurationSection getConfigurationSection(final String path) {
        return this.fileConfiguration.getConfigurationSection(path);
    }

    public boolean contains(final String path) {
        return this.get(path) != null;
    }

    public void setLocation(String path, Location location) {
        if(location == null) {
            this.set(path, null);
            return;
        }
        String locationString = location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
        this.set(path, locationString);
    }

    public Location getLocation(String path) {
        String locationString = (String) this.get(path, null);

        if(locationString == null) {
            return null;
        }

        String[] locationSplit = locationString.split(";");

        String world = locationSplit[0];
        double x = Double.parseDouble(locationSplit[1]);
        double y = Double.parseDouble(locationSplit[2]);
        double z = Double.parseDouble(locationSplit[3]);
        float yaw = Float.parseFloat(locationSplit[4]);
        float pitch = Float.parseFloat(locationSplit[5]);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        this.save();
    }
    
    public void save() {
        try {
            this.fileConfiguration.save(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}