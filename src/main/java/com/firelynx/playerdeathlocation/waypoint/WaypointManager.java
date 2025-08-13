package com.firelynx.playerdeathlocation.waypoint;

import com.firelynx.playerdeathlocation.PlayerDeathLocation;
import com.firelynx.playerdeathlocation.config.ModConfig;
import com.firelynx.playerdeathlocation.hologram.ArmorStandHologram;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages waypoints for players
 */
public class WaypointManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File WAYPOINTS_DIR = FabricLoader.getInstance().getConfigDir().resolve(PlayerDeathLocation.MOD_ID + "/waypoints").toFile();
    
    private static final Map<UUID, List<Waypoint>> playerWaypoints = new HashMap<>();
    
    /**
     * Initializes the waypoint manager
     */
    public static void init() {
        if (!WAYPOINTS_DIR.exists()) {
            WAYPOINTS_DIR.mkdirs();
        }
        PlayerDeathLocation.LOGGER.info("Waypoint manager initialized");
    }
    
    /**
     * Adds a waypoint for a player
     * 
     * @param player The player
     * @param waypoint The waypoint to add
     */
    public static void addWaypoint(ServerPlayerEntity player, Waypoint waypoint) {
        UUID playerId = player.getUuid();
        
        // Load player's waypoints if not already loaded
        if (!playerWaypoints.containsKey(playerId)) {
            loadPlayerWaypoints(playerId);
        }
        
        // Add the waypoint
        List<Waypoint> waypoints = playerWaypoints.get(playerId);
        waypoints.add(waypoint);
        playerWaypoints.put(playerId, waypoints);
        
        // Save the waypoints
        savePlayerWaypoints(playerId);
        
        // Create hologram if enabled
        if (ModConfig.getInstance().isHolographicWaypointsEnabled()) {
            ArmorStandHologram.createHologram(player.getServerWorld(), waypoint, player.getPos());
        }
        
        // Notify the player and log the action
        if (waypoint.isDeathPoint()) {
            player.sendMessage(
                Text.translatable("waypoint.player-death-location.death_waypoint_added",
                    waypoint.getPosition().getX(),
                    waypoint.getPosition().getY(),
                    waypoint.getPosition().getZ())
                    .formatted(Formatting.GOLD), false);
            PlayerDeathLocation.LOGGER.info("Added death waypoint for player {} at {}", 
                player.getName().getString(), waypoint.toString());
        } else {
            player.sendMessage(
                Text.translatable("waypoint.player-death-location.waypoint_added",
                    waypoint.getName(),
                    waypoint.getPosition().getX(),
                    waypoint.getPosition().getY(),
                    waypoint.getPosition().getZ())
                    .formatted(Formatting.GREEN), false);
            PlayerDeathLocation.LOGGER.info("Added custom waypoint '{}' for player {} at {}", 
                waypoint.getName(), player.getName().getString(), waypoint.toString());
        }
    }
    
    /**
     * Gets all waypoints for a player
     * 
     * @param playerId The player's UUID
     * @return A list of the player's waypoints
     */
    public static List<Waypoint> getPlayerWaypoints(UUID playerId) {
        if (!playerWaypoints.containsKey(playerId)) {
            loadPlayerWaypoints(playerId);
        }
        return playerWaypoints.get(playerId);
    }
    
    /**
     * Adds a waypoint for a player (client-side version)
     * 
     * @param player The player
     * @param waypoint The waypoint to add
     */
    @Environment(EnvType.CLIENT)
    public static void addWaypointClient(PlayerEntity player, Waypoint waypoint) {
        UUID playerId = player.getUuid();
        
        // Load player's waypoints if not already loaded
        if (!playerWaypoints.containsKey(playerId)) {
            loadPlayerWaypoints(playerId);
        }
        
        // Add the waypoint
        List<Waypoint> waypoints = playerWaypoints.get(playerId);
        waypoints.add(waypoint);
        playerWaypoints.put(playerId, waypoints);
        
        // Save the waypoints
        savePlayerWaypoints(playerId);
    }
    
    /**
     * Removes a waypoint for a player
     * 
     * @param player The player
     * @param index The index of the waypoint to remove
     * @return The removed waypoint, or null if the index was invalid
     */
    public static Waypoint removeWaypoint(ServerPlayerEntity player, int index) {
        UUID playerId = player.getUuid();
        
        // Load player's waypoints if not already loaded
        if (!playerWaypoints.containsKey(playerId)) {
            loadPlayerWaypoints(playerId);
        }
        
        List<Waypoint> waypoints = playerWaypoints.get(playerId);
        
        // Check if the index is valid
        if (index < 0 || index >= waypoints.size()) {
            return null;
        }
        
        // Remove the waypoint
        Waypoint removed = waypoints.remove(index);
        playerWaypoints.put(playerId, waypoints);
        
        // Save the waypoints
        savePlayerWaypoints(playerId);
        
        return removed;
    }
    
    /**
     * Creates a death waypoint for a player
     * 
     * @param player The player who died
     * @param position The death position
     * @param dimension The dimension where the death occurred
     */
    public static void createDeathWaypoint(ServerPlayerEntity player, BlockPos position, String dimension) {
        Waypoint deathWaypoint = Waypoint.createDeathWaypoint(position, dimension);
        addWaypoint(player, deathWaypoint);
    }
    
    /**
     * Loads waypoints for a player from disk
     * 
     * @param playerId The player's UUID
     */
    private static void loadPlayerWaypoints(UUID playerId) {
        File playerWaypointsFile = new File(WAYPOINTS_DIR, playerId + ".json");
        
        if (playerWaypointsFile.exists()) {
            try (FileReader reader = new FileReader(playerWaypointsFile)) {
                Type type = new TypeToken<ArrayList<Waypoint>>(){}.getType();
                List<Waypoint> waypoints = GSON.fromJson(reader, type);
                playerWaypoints.put(playerId, waypoints);
                PlayerDeathLocation.LOGGER.info("Loaded {} waypoints for player {}", waypoints.size(), playerId);
            } catch (IOException e) {
                PlayerDeathLocation.LOGGER.error("Failed to load waypoints for player {}: {}", playerId, e.getMessage());
                playerWaypoints.put(playerId, new ArrayList<>());
            }
        } else {
            playerWaypoints.put(playerId, new ArrayList<>());
        }
    }
    
    /**
     * Saves waypoints for a player to disk
     * 
     * @param playerId The player's UUID
     */
    private static void savePlayerWaypoints(UUID playerId) {
        File playerWaypointsFile = new File(WAYPOINTS_DIR, playerId + ".json");
        
        try {
            if (!playerWaypointsFile.exists()) {
                playerWaypointsFile.getParentFile().mkdirs();
                playerWaypointsFile.createNewFile();
            }
            
            try (FileWriter writer = new FileWriter(playerWaypointsFile)) {
                List<Waypoint> waypoints = playerWaypoints.get(playerId);
                GSON.toJson(waypoints, writer);
                PlayerDeathLocation.LOGGER.info("Saved {} waypoints for player {}", waypoints.size(), playerId);
            }
        } catch (IOException e) {
            PlayerDeathLocation.LOGGER.error("Failed to save waypoints for player {}: {}", playerId, e.getMessage());
        }
    }
}