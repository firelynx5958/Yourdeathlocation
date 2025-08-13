package com.firelynx.playerdeathlocation.command;

import com.firelynx.playerdeathlocation.PlayerDeathLocation;
import com.firelynx.playerdeathlocation.waypoint.Waypoint;
import com.firelynx.playerdeathlocation.waypoint.WaypointManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class WaypointCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
            CommandManager.literal("waypoint")
                .then(CommandManager.literal("create")
                    .then(CommandManager.argument("name", StringArgumentType.greedyString())
                        .executes(WaypointCommand::createWaypoint)))
                .then(CommandManager.literal("list")
                    .executes(WaypointCommand::listWaypoints))
                .then(CommandManager.literal("remove")
                    .then(CommandManager.argument("index", IntegerArgumentType.integer(1))
                        .executes(WaypointCommand::removeWaypoint)))
                .executes(WaypointCommand::showHelp)
        );
        
        PlayerDeathLocation.LOGGER.info("Waypoint command registered");
    }
    
    private static int createWaypoint(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        // Check if the command is run by a player
        if (!source.isExecutedByPlayer()) {
            source.sendError(Text.literal("This command can only be used by players"));
            return 0;
        }
        
        ServerPlayerEntity player = source.getPlayer();
        String name = StringArgumentType.getString(context, "name");
        BlockPos position = player.getBlockPos();
        String dimension = player.getServerWorld().getRegistryKey().getValue().toString();
        
        // Create and add the waypoint
        Waypoint waypoint = new Waypoint(name, position, dimension);
        WaypointManager.addWaypoint(player, waypoint);
        
        return 1;
    }
    
    private static int listWaypoints(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        // Check if the command is run by a player
        if (!source.isExecutedByPlayer()) {
            source.sendError(Text.literal("This command can only be used by players"));
            return 0;
        }
        
        ServerPlayerEntity player = source.getPlayer();
        List<Waypoint> waypoints = WaypointManager.getPlayerWaypoints(player.getUuid());
        
        if (waypoints.isEmpty()) {
            source.sendFeedback(() -> 
                Text.translatable("command.player-death-location.waypoint.no_waypoints")
                    .formatted(Formatting.YELLOW), false);
            return 1;
        }
        
        // Send header
        source.sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoint.list_header")
                .formatted(Formatting.GOLD), false);
        
        // Send each waypoint
        for (int i = 0; i < waypoints.size(); i++) {
            final int index = i; // Create a final copy for use in lambda
            Waypoint waypoint = waypoints.get(index);
            Formatting formatting = waypoint.isDeathPoint() ? Formatting.RED : Formatting.GREEN;
            
            String dimensionName = waypoint.getDimension().replace("minecraft:", "");
            source.sendFeedback(() -> 
                Text.literal(String.format("%d. %s: %d, %d, %d in %s",
                    index + 1, // Display 1-based index to users
                    waypoint.getName(),
                    waypoint.getPosition().getX(),
                    waypoint.getPosition().getY(),
                    waypoint.getPosition().getZ(),
                    dimensionName))
                    .formatted(formatting), false);
        }
        
        return 1;
    }
    
    private static int removeWaypoint(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        // Check if the command is run by a player
        if (!source.isExecutedByPlayer()) {
            source.sendError(Text.literal("This command can only be used by players"));
            return 0;
        }
        
        ServerPlayerEntity player = source.getPlayer();
        int userIndex = IntegerArgumentType.getInteger(context, "index");
        int arrayIndex = userIndex - 1; // Convert from 1-based to 0-based index
        
        Waypoint removed = WaypointManager.removeWaypoint(player, arrayIndex);
        
        if (removed == null) {
            source.sendError(Text.translatable("command.player-death-location.waypoint.invalid_index"));
            return 0;
        }
        
        source.sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoint.removed", removed.getName())
                .formatted(Formatting.GREEN), false);
        
        return 1;
    }
    
    
    private static int showHelp(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        
        source.sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoint.help.header")
                .formatted(Formatting.GOLD), false);
        
        source.sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoint.help.create")
                .formatted(Formatting.YELLOW), false);
        
        source.sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoint.help.list")
                .formatted(Formatting.YELLOW), false);
        
        source.sendFeedback(() -> 
            Text.translatable("command.player-death-location.waypoint.help.remove")
                .formatted(Formatting.YELLOW), false);
        
        return 1;
    }
}