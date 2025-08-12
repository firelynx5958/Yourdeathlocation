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
}