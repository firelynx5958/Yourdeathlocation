<div align="center">

# PLAYER DEATH LOCATION
### *BY FIRELYNX*

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-brightgreen.svg)
![Fabric](https://img.shields.io/badge/Fabric-Latest-blue.svg)
![License](https://img.shields.io/badge/License-CC0--1.0-yellow.svg)
![Downloads](https://img.shields.io/badge/Downloads-1K+-orange.svg)
![Version](https://img.shields.io/badge/Version-2.0.0-red.svg)

</div>

---

## 💡 About

Modern Minecraft exploration is fast – but not always smart. Why render death coordinates and make holograms behind walls or underground when you can take them with you?

This mod introduces **comprehensive waypoint tracking** to efficiently determine what's actually visible to the player. By leveraging intuitive HUD overlays, it calculates line-of-sight visibility in real-time and eliminates waypoints that can't be seen – no matter how far away they are.

**The result?**  
Smaller rendering, Less overhead, More performance.

---

### 🎮 Using waypoints without performance by not rendering what matters. This mod goes beyond the basics to bring next-level visibility optimization to your game.

**Multithreaded Path-Tracing**
- Uses smart CPU threads to calculate visibility fast
- Smart algorithms for real-time thread scheduling
- Optimizes overall thread utilization without performance loss

**Smart Distance Calculation**
- Culls back entities and maps hidden behind terrain or structures  
- Works like Minecraft's back-face culling, but smarter
- Renders on UI-level without sacrificing visual fidelity

**Entity Text Optimization**
- Reduces armor-stand impact from unseen entities
- Important object layers from depth-testing
- Fully configurable and compatible with most mods

---

## 🛸 PLAY TOGETHER
### **FOR FREE!**

---

## ⚡ Features

### 🪦 Death Location Tracking
- **Automatic Death Coordinates**: Displays your exact death coordinates in chat when you die
- **Death Waypoints**: Automatically creates waypoints at death locations
- **Dimension Support**: Tracks deaths across all dimensions (Overworld, Nether, End)
- **Persistent Storage**: Death locations are saved and persist across game sessions

### 🗺️ Custom Waypoint System
- **Create Custom Waypoints**: Mark any location with a custom name
- **Infinite Distance Visibility**: See waypoints from ANY distance (even 10,000+ blocks away)
- **Real-time Distance Display**: Shows exact distance to each waypoint
- **Dimension Awareness**: Only shows waypoints in your current dimension
- **Persistent Storage**: Waypoints are saved per-player and persist across sessions

### 🎯 Visual Display System
- **HUD Waypoint Display**: Always-visible waypoint list in top-right corner (like Xaero's waypoints)
- **3D Holographic Waypoints**: Floating text above waypoint locations when nearby
- **Color Coding**: Red for death waypoints, cyan for custom waypoints
- **Real-time Updates**: Distance updates continuously as you move

### ⚙️ Configuration Options
- **Toggle Death Coordinates**: Enable/disable death coordinate messages
- **Toggle Death Waypoints**: Enable/disable automatic death waypoint creation
- **Toggle Holograms**: Enable/disable holographic waypoint display
- **Fully Configurable**: All features can be toggled independently

### 🔧 Technical Features
- **Server-Side**: Works in multiplayer environments
- **Client-Side HUD**: Bypasses entity rendering distance limits
- **Per-Player Storage**: Each player has their own waypoints
- **JSON Storage**: Human-readable configuration and waypoint files
- **Performance Optimized**: Minimal impact on game performance

## 🎮 Commands

### Waypoint Management Commands
```
/waypoint create <name>    - Create a waypoint at your current location
/waypoint list             - Display all your waypoints with coordinates
/waypoint remove <index>   - Remove a waypoint by its list number
/waypoint                  - Show help for waypoint commands
```

### Death Coordinates Commands
```
/deathcoords on            - Enable death coordinate messages
/deathcoords off           - Disable death coordinate messages
/deathcoords               - Show current death coordinates status

/deathcoords waypoints on  - Enable automatic death waypoints
/deathcoords waypoints off - Disable automatic death waypoints
/deathcoords waypoints     - Show death waypoints status

/deathcoords holograms on  - Enable holographic waypoint display
/deathcoords holograms off - Disable holographic waypoint display
/deathcoords holograms     - Show holograms status
```

## 💡 Usage Examples

### Creating and Managing Waypoints
```
/waypoint create Home         → Creates waypoint "Home" at your location
/waypoint create Mining Base  → Creates waypoint "Mining Base"
/waypoint list               → Shows: 1. Home: -154, 69, 130 in overworld
                                      2. Mining Base: 245, 12, -89 in overworld
/waypoint remove 2           → Removes the "Mining Base" waypoint
```

### Configuring Death Tracking
```
/deathcoords on              → Enables death coordinate messages
/deathcoords waypoints on    → Auto-creates waypoints when you die
/deathcoords holograms off   → Disables floating hologram display
```


## 🔧 Installation

1. **Download** Fabric mod loader for Minecraft 1.21.1
2. **Download** Fabric API mod
3. **Place** this mod's `.jar` file in your `mods` folder
4. **Launch** Minecraft with Fabric profile
5. **Configure** using in-game commands


</div>

---

## ❓ Known Issues

**Clientside entities, usually used by for example magic mods for their animations, don't work as expected. Entities like this body will not be culling tested for lack culling and/or entity culling.**

**Block entities that render far outside their bounds need to be whitelisted too. An example for that is the vanilla Beacon. Create portoys or similar Botania blocks.**

---

## ❓ FAQ

**Does this need to be installed on the server?**  
No. This is a fully client-side mod and does not need to be installed on the server.

**Will this affect mob behavior or farms?**  
No. This mod only affects rendering and simulation. Mobs will still spawn move, and drop items as usual. Your farms and item systems will continue to function normally.

**I have "Use Entity Culling" disabled in Sodium - does this still help?**  
Yes, this mod implements a different waypoint tracking approach to sodium's entity culling feature, so it can further improve performance even if you disable entity culling. If unsure however, we recommend leave it enabled though.

---

## 👥 Multiplayer Features

- **Per-Player Waypoints**: Each player has their own waypoint list
- **Server-Side Storage**: Waypoints are saved on the server
- **Independent Settings**: Each player can configure features individually
- **No Interference**: Players cannot see or modify other players' waypoints

---

## 🏆 Credits & License

**Thanks to tr7zw for his Waypoint Pathfinding inspiration, which created the foundation for this mod.**

**Thanks to tr7zw for the Bukkit build 7.3 backport.**

**Thanks to tr7zw for the Fabric 1.17.0 backport.**

**Thanks to tr7zw for the Waypoint Pathfinding mod.**

**Thanks to tr7zw for his Waypoint Pathfinding codebase hosted tracepoints without asking for permission. Do not redistribute the jar files without permission either.**

---

<div align="center">

### ⭐ **If this mod helped you, consider giving it a star!**

[![GitHub stars](https://img.shields.io/github/stars/firelynx/player-death-location?style=social)](https://github.com/firelynx/player-death-location/stargazers)  
[![GitHub issues](https://img.shields.io/github/issues/firelynx/player-death-location)](https://github.com/firelynx/player-death-location/issues)  
[![GitHub license](https://img.shields.io/github/license/firelynx/player-death-location)](https://github.com/firelynx/player-death-location/blob/main/LICENSE)

**Version**: 2.0.0 | **Author**: firelynx | **License**: CC0-1.0 | **Platform**: Minecraft 1.21.1 Fabric

</div>
