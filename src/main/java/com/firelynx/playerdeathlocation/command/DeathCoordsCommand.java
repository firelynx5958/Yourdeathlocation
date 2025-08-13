package com.firelynx.playerdeathlocation.command;

import com.firelynx.playerdeathlocation.PlayerDeathLocation;
import com.firelynx.playerdeathlocation.config.ModConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DeathCoordsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
            CommandManager.literal("deathcoords")
                .then(CommandManager.literal("on")
                    .executes(DeathCoordsCommand::enableDeathCoords))
                .then(CommandManager.literal("off")
                    .executes(DeathCoordsCommand::disableDeathCoords))
                .then(CommandManager.literal("waypoints")
                    .then(CommandManager.literal("on")
                        .executes(DeathCoordsCommand::enableDeathWaypoints))
                    .then(CommandManager.literal("off")
                        .executes(DeathCoordsCommand::disableDeathWaypoints))
                    .executes(DeathCoordsCommand::getWaypointsStatus))
                .then(CommandManager.literal("holograms")
                    .then(CommandManager.literal("on")
                        .executes(DeathCoordsCommand::enableHolograms))
                    .then(CommandManager.literal("off")
                        .executes(DeathCoordsCommand::disableHolograms))
                    .executes(DeathCoordsCommand::getHologramsStatus))
                .executes(DeathCoordsCommand::getStatus)
        );
        
        PlayerDeathLocation.LOGGER.info("Death coordinates command registered");
    }
    
    private static int enableDeathCoords(CommandContext<ServerCommandSource> context) {
        ModConfig.getInstance().setDeathCoordsEnabled(true);
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.enabled")
                .formatted(Formatting.GREEN), false);
        return 1;
    }
    
    private static int disableDeathCoords(CommandContext<ServerCommandSource> context) {
        ModConfig.getInstance().setDeathCoordsEnabled(false);
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.disabled")
                .formatted(Formatting.RED), false);
        return 1;
    }
    
    private static int getStatus(CommandContext<ServerCommandSource> context) {
        boolean enabled = ModConfig.getInstance().isDeathCoordsEnabled();
        String statusKey = enabled ? 
            "command.player-death-location.status.enabled" : 
            "command.player-death-location.status.disabled";
        
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.status", 
                Text.translatable(statusKey).formatted(enabled ? Formatting.GREEN : Formatting.RED))
                .formatted(Formatting.GOLD), false);
        return 1;
    }
    
    private static int enableDeathWaypoints(CommandContext<ServerCommandSource> context) {
        ModConfig.getInstance().setDeathWaypointsEnabled(true);
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoints.enabled")
                .formatted(Formatting.GREEN), false);
        return 1;
    }
    
    private static int disableDeathWaypoints(CommandContext<ServerCommandSource> context) {
        ModConfig.getInstance().setDeathWaypointsEnabled(false);
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoints.disabled")
                .formatted(Formatting.RED), false);
        return 1;
    }
    
    private static int getWaypointsStatus(CommandContext<ServerCommandSource> context) {
        boolean enabled = ModConfig.getInstance().isDeathWaypointsEnabled();
        String statusKey = enabled ? 
            "command.player-death-location.status.enabled" : 
            "command.player-death-location.status.disabled";
        
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoints.status", 
                Text.translatable(statusKey).formatted(enabled ? Formatting.GREEN : Formatting.RED))
                .formatted(Formatting.GOLD), false);
        return 1;
    }
    
    private static int enableHolograms(CommandContext<ServerCommandSource> context) {
        ModConfig.getInstance().setHolographicWaypointsEnabled(true);
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.holograms.enabled")
                .formatted(Formatting.GREEN), false);
        return 1;
    }
    
    private static int disableHolograms(CommandContext<ServerCommandSource> context) {
        ModConfig.getInstance().setHolographicWaypointsEnabled(false);
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.holograms.disabled")
                .formatted(Formatting.RED), false);
        return 1;
    }
    
    private static int getHologramsStatus(CommandContext<ServerCommandSource> context) {
        boolean enabled = ModConfig.getInstance().isHolographicWaypointsEnabled();
        String statusKey = enabled ? 
            "command.player-death-location.status.enabled" : 
            "command.player-death-location.status.disabled";
        
        context.getSource().sendFeedback(() -> 
            Text.translatable("command.player-death-location.holograms.status", 
                Text.translatable(statusKey).formatted(enabled ? Formatting.GREEN : Formatting.RED))
                .formatted(Formatting.GOLD), false);
        return 1;
    }
}