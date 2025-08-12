# Your Death Location Mod Documentation

## Overview

The "Your Death Location" mod displays a message in the chat with the player's death coordinates whenever they die in the game. This feature can be toggled on or off using commands, and the setting is saved in a configuration file.

## Features

- Displays death coordinates in chat when a player dies
- Coordinates are shown with the dimension name
- Toggle feature on/off with commands
- Settings persist between game sessions

## Components

### Java Source Files

- `src/main/java/com/firelynx/playerdeathlocation/PlayerDeathLocation.java`
  - Main mod class that initializes the mod components

- `src/main/java/com/firelynx/playerdeathlocation/DeathLocationHandler.java`
  - Handles player death events and displays death coordinates

- `src/main/java/com/firelynx/playerdeathlocation/config/ModConfig.java`
  - Manages the mod configuration (saving/loading settings)

- `src/main/java/com/firelynx/playerdeathlocation/command/DeathCoordsCommand.java`
  - Implements the commands to toggle death coordinates display

- `src/main/java/com/firelynx/playerdeathlocation/mixin/ExampleMixin.java`
  - Example mixin class (not actively used in this mod)

### Resource Files

- `src/main/resources/fabric.mod.json`
  - Mod metadata and entry points

- `src/main/resources/player-death-location.mixins.json`
  - Mixin configuration

- `src/main/resources/assets/player-death-location/lang/en_us.json`
  - English language translations

- `src/main/resources/assets/player-death-location/icon.png`
  - Mod icon

## How It Works

### Initialization

When the game starts:
1. The mod loads its configuration from the config file
2. Registers the death event handler
3. Registers the commands

### Death Coordinate Display

When a player dies:
1. The `DeathLocationHandler` checks if the death coordinates feature is enabled
2. If enabled, it gets the player's death position and dimension
3. Formats a message with this information
4. Sends the message to the player's chat

### Commands

The mod adds the following commands:
- `/deathcoords on` - Enables death coordinates display
- `/deathcoords off` - Disables death coordinates display
- `/deathcoords` - Shows the current status

### Configuration

Settings are stored in a JSON file at `.minecraft/config/player-death-location.json`. The file is created automatically when the mod first runs, and it's updated whenever the player changes settings using commands.

## Usage

1. Install the mod in your Minecraft Fabric installation
2. Die in the game to see your death coordinates
3. Use `/deathcoords off` to disable the feature if desired
4. Use `/deathcoords on` to re-enable it