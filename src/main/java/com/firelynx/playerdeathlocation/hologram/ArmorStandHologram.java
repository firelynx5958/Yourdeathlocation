package com.firelynx.playerdeathlocation.hologram;

import com.firelynx.playerdeathlocation.PlayerDeathLocation;
import com.firelynx.playerdeathlocation.waypoint.Waypoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Creates holograms using invisible armor stands with custom names
 */
public class ArmorStandHologram {
    private static final Map<String, UUID> waypointHolograms = new HashMap<>();
    
    /**
     * Creates or updates a hologram for a waypoint
     */
    public static void createHologram(ServerWorld world, Waypoint waypoint, Vec3d playerPos) {
        try {
            // Generate a unique key for this waypoint
            String key = waypoint.getName() + "_" + waypoint.getPosition().toString();
            
            // Remove existing hologram if it exists
            removeHologram(world, key);
            
            // Calculate distance (for display only, no distance limits)
            BlockPos waypointPos = waypoint.getPosition();
            double distance = playerPos.distanceTo(new Vec3d(waypointPos.getX() + 0.5, waypointPos.getY(), waypointPos.getZ() + 0.5));
            
            // Create armor stand
            ArmorStandEntity armorStand = new ArmorStandEntity(EntityType.ARMOR_STAND, world);
            
            // Position it higher above the waypoint for better visibility
            double x = waypointPos.getX() + 0.5;
            double y = waypointPos.getY() + 3.0; // Much higher above the block
            double z = waypointPos.getZ() + 0.5;
            armorStand.setPosition(x, y, z);
            
            // Make it invisible and non-interactable
            armorStand.setInvisible(true);
            armorStand.setNoGravity(true);
            armorStand.setInvulnerable(true);
            armorStand.setSilent(true);
            
            // Set custom name with waypoint info - make it VERY visible
            String nameText = waypoint.getName().toUpperCase(); // Make name uppercase for visibility
            String distanceText = String.format("[%.0fm]", distance);
            
            // Create a more prominent display
            Text customName = Text.literal("▲ " + nameText + " " + distanceText + " ▲")
                .formatted(waypoint.isDeathPoint() ? Formatting.RED : Formatting.AQUA)
                .formatted(Formatting.BOLD);
            
            armorStand.setCustomName(customName);
            armorStand.setCustomNameVisible(true);
            
            // Make it glow to increase visibility from long distances
            armorStand.setGlowing(true);
            
            // DON'T set it to small - keep it normal size for better visibility
            // armorStand.setSmall(true);
            
            // Spawn the entity
            world.spawnEntity(armorStand);
            
            // Store reference for later removal
            waypointHolograms.put(key, armorStand.getUuid());
            
            PlayerDeathLocation.LOGGER.info("Created hologram for waypoint: {} at {}", waypoint.getName(), armorStand.getPos());
            
        } catch (Exception e) {
            PlayerDeathLocation.LOGGER.error("Failed to create hologram: {}", e.getMessage());
        }
    }
    
    /**
     * Removes a hologram
     */
    public static void removeHologram(ServerWorld world, String key) {
        UUID hologramId = waypointHolograms.get(key);
        if (hologramId != null) {
            Entity entity = world.getEntity(hologramId);
            if (entity instanceof ArmorStandEntity) {
                entity.remove(Entity.RemovalReason.DISCARDED);
                PlayerDeathLocation.LOGGER.info("Removed armor stand hologram: {}", key);
            }
            waypointHolograms.remove(key);
        }
    }
    
    /**
     * Updates all holograms for waypoints near a player
     */
    public static void updateHolograms(ServerWorld world, Vec3d playerPos) {
        // This would be called periodically to update distances
        // Implementation depends on how you want to manage hologram lifecycle
    }
    
    /**
     * Cleans up all holograms
     */
    public static void cleanup(ServerWorld world) {
        for (String key : waypointHolograms.keySet()) {
            removeHologram(world, key);
        }
        waypointHolograms.clear();
    }
}
