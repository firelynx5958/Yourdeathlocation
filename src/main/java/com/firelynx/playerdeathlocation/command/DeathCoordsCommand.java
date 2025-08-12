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
}