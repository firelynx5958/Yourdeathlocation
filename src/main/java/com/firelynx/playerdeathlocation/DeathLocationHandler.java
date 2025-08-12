package com.firelynx.playerdeathlocation;

import com.firelynx.playerdeathlocation.config.ModConfig;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class DeathLocationHandler {
    
    public static void register() {
        // Register the death event handler
        ServerLivingEntityEvents.AFTER_DEATH.register(DeathLocationHandler::onPlayerDeath);
        PlayerDeathLocation.LOGGER.info("Death location handler registered successfully!");
    }
    
    private static void onPlayerDeath(LivingEntity entity, DamageSource damageSource) {
        // Check if the entity that died is a player
        if (entity instanceof ServerPlayerEntity player) {
            // Check if death coordinates are enabled in config
            if (!ModConfig.getInstance().isDeathCoordsEnabled()) {
                PlayerDeathLocation.LOGGER.debug("Death coordinates are disabled in config");
                return;
            }
            
            // Get the death location
            BlockPos deathPos = player.getBlockPos();
            String dimensionName = player.getServerWorld().getRegistryKey().getValue().toString();
            
            // Send the death location message to the player using translation key
            player.sendMessage(
                Text.translatable("death.player-death-location.message",
                    deathPos.getX(), deathPos.getY(), deathPos.getZ(), dimensionName)
                    .formatted(Formatting.RED), false);
            
            // Log the death location
            PlayerDeathLocation.LOGGER.info("Player {} died at coordinates: {}, {}, {} in dimension: {}", 
                player.getName().getString(),
                deathPos.getX(), 
                deathPos.getY(), 
                deathPos.getZ(), 
                dimensionName);
        }
    }
}
