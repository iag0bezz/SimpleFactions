package com.frach.minecraft.factions.helper;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.JDK14LoggerAdapter;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

public abstract class PluginHelper extends JavaPlugin {

    @Getter
    private final Logger log = createLoggerFromJDK(getLogger());

    @Override
    public void onEnable() {
        this.enable();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.disable();
        super.onDisable();
    }

    @Override
    public void onLoad() {
        this.load();
        super.onLoad();
    }

    public abstract void enable();

    public abstract void disable();

    public abstract void load();

    public <T> T getService(Class<T> service) {
        Objects.requireNonNull(service, "clazz");

        return Optional
                .ofNullable(Bukkit.getServicesManager().getRegistration(service))
                .map(RegisteredServiceProvider::getProvider)
                .orElseThrow(() -> new IllegalStateException("No registration present for service '" + service.getName() + "'"));
    }

    public <T> T provideService(Class<T> clazz, T instance, ServicePriority priority) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(instance, "instance");
        Objects.requireNonNull(priority, "priority");

        Bukkit.getServicesManager().register(clazz, instance, this, priority);

        return instance;
    }

    public <T> T provideService(Class<T> clazz, T instance) {
        provideService(clazz, instance, ServicePriority.Normal);
        return instance;
    }

    public void register(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    @SneakyThrows
    protected HikariDataSource getDataSourceFromConfig() {
        final FileConfiguration fileConfiguration = getConfig();
        final HikariDataSource dataSource = new HikariDataSource();

        String host = fileConfiguration.getString("mysql.host");
        String database = fileConfiguration.getString("mysql.database");
        int port = fileConfiguration.getInt("mysql.port");
        String username = fileConfiguration.getString("mysql.username");
        String password = fileConfiguration.getString("mysql.password");

        dataSource.setMaximumPoolSize(20);
        dataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.addDataSourceProperty("useSSL", "false");
        dataSource.addDataSourceProperty("autoReconnect", "true");
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");

        return dataSource;
    }

    private Logger createLoggerFromJDK(java.util.logging.Logger parent) {
        try {
            parent.setLevel(Level.ALL);

            Class<JDK14LoggerAdapter> adapterClass = JDK14LoggerAdapter.class;
            Constructor<JDK14LoggerAdapter> cons = adapterClass.getDeclaredConstructor(java.util.logging.Logger.class);
            cons.setAccessible(true);
            return cons.newInstance(parent);
        } catch (ReflectiveOperationException reflectEx) {
            parent.log(Level.WARNING, "Cannot create slf4j logging adapter", reflectEx);
            parent.log(Level.WARNING, "Creating logger instance manually...");
            return LoggerFactory.getLogger(parent.getName());
        }
    }

}