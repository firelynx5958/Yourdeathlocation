package com.firelynx.playerdeathlocation;

import com.firelynx.playerdeathlocation.command.DeathCoordsCommand;
import com.firelynx.playerdeathlocation.command.WaypointCommand;
import com.firelynx.playerdeathlocation.config.ModConfig;
import com.firelynx.playerdeathlocation.waypoint.WaypointManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerDeathLocation implements ModInitializer {
	public static final String MOD_ID = "player-death-location";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Load the config
		ModConfig.loadConfig();
		
		// Initialize the waypoint manager
		WaypointManager.init();
		
		// Register the death location handler
		DeathLocationHandler.register();
		
		// Register the commands
		CommandRegistrationCallback.EVENT.register(DeathCoordsCommand::register);
		CommandRegistrationCallback.EVENT.register(WaypointCommand::register);
		
		LOGGER.info("Player Death Location mod initialized!");
	}
}