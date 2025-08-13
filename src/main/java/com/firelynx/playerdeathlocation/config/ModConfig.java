package com.firelynx.playerdeathlocation.config;

import com.firelynx.playerdeathlocation.PlayerDeathLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(PlayerDeathLocation.MOD_ID + ".json").toFile();
    
    private static ModConfig INSTANCE;
    
    private boolean deathCoordsEnabled = true; // Enabled by default
    private boolean deathWaypointsEnabled = true; // Death waypoints enabled by default
    private boolean waypointsEnabled = true; // Custom waypoints enabled by default
    private boolean holographicWaypointsEnabled = true; // Holographic waypoint display enabled by default
    private int waypointRenderDistance = Integer.MAX_VALUE; // No distance limit for waypoint rendering
    
    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            loadConfig();
        }
        return INSTANCE;
    }
    
    public static void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
                PlayerDeathLocation.LOGGER.info("Config loaded successfully");
            } catch (IOException e) {
                PlayerDeathLocation.LOGGER.error("Failed to load config: {}", e.getMessage());
                INSTANCE = new ModConfig();
            }
        } else {
            INSTANCE = new ModConfig();
            saveConfig();
        }
    }
    
    public static void saveConfig() {
        try {
            if (!CONFIG_FILE.exists()) {
                CONFIG_FILE.getParentFile().mkdirs();
                CONFIG_FILE.createNewFile();
            }
            
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(INSTANCE, writer);
                PlayerDeathLocation.LOGGER.info("Config saved successfully");
            }
        } catch (IOException e) {
            PlayerDeathLocation.LOGGER.error("Failed to save config: {}", e.getMessage());
        }
    }
    
    public boolean isDeathCoordsEnabled() {
        return deathCoordsEnabled;
    }
    
    public void setDeathCoordsEnabled(boolean deathCoordsEnabled) {
        this.deathCoordsEnabled = deathCoordsEnabled;
        saveConfig();
    }
    
    public boolean isDeathWaypointsEnabled() {
        return deathWaypointsEnabled;
    }
    
    public void setDeathWaypointsEnabled(boolean deathWaypointsEnabled) {
        this.deathWaypointsEnabled = deathWaypointsEnabled;
        saveConfig();
    }
    
    public boolean isWaypointsEnabled() {
        return waypointsEnabled;
    }
    
    public void setWaypointsEnabled(boolean waypointsEnabled) {
        this.waypointsEnabled = waypointsEnabled;
        saveConfig();
    }
    
    public boolean isHolographicWaypointsEnabled() {
        return holographicWaypointsEnabled;
    }
    
    public void setHolographicWaypointsEnabled(boolean holographicWaypointsEnabled) {
        this.holographicWaypointsEnabled = holographicWaypointsEnabled;
        saveConfig();
    }
    
    public int getWaypointRenderDistance() {
        return waypointRenderDistance;
    }
    
    public void setWaypointRenderDistance(int waypointRenderDistance) {
        this.waypointRenderDistance = waypointRenderDistance;
        saveConfig();
    }
}