# Player Death Location Mod - Quick Reference

## 🎯 Main Features
- **Death Coordinate Tracking** - Shows where you died in chat
- **Automatic Death Waypoints** - Creates waypoints when you die
- **Custom Waypoints** - Mark any location with custom names
- **Infinite Distance HUD** - Always see waypoints on screen (like Xaero's)
- **3D Holograms** - Floating text above nearby waypoints
- **Multi-Dimension Support** - Works in Overworld, Nether, End
- **Per-Player Storage** - Everyone has their own waypoints

## 📋 Commands Quick Reference

### Waypoint Commands
| Command | Description | Example |
|---------|-------------|---------|
| `/waypoint create <name>` | Create waypoint at current location | `/waypoint create Home` |
| `/waypoint list` | Show all your waypoints | `/waypoint list` |
| `/waypoint remove <index>` | Remove waypoint by number | `/waypoint remove 1` |
| `/waypoint` | Show help | `/waypoint` |

### Death Tracking Commands
| Command | Description | Example |
|---------|-------------|---------|
| `/deathcoords on/off` | Toggle death coordinate messages | `/deathcoords on` |
| `/deathcoords waypoints on/off` | Toggle automatic death waypoints | `/deathcoords waypoints on` |
| `/deathcoords holograms on/off` | Toggle holographic waypoint display | `/deathcoords holograms on` |

## 🎨 Visual Display

### HUD Display (Always Visible)
```
┌─────────────────────┐
│ HOME - 1,234m      │  ← Top-right corner
│ MINING BASE - 567m │  ← Real-time distance
│ Death Point - 89m  │  ← Color coded
└─────────────────────┘
```

### 3D Holograms (When Close)
```
▲ WAYPOINT_NAME [123m] ▲
```

## 🎨 Color Coding
- 🔴 **Red** - Death waypoints
- 🔵 **Cyan** - Custom waypoints  
- 🟡 **Yellow** - Distances
- 🟢 **Green** - Success messages

## ⚙️ Configuration Files
- **Main Config**: `.minecraft/config/player-death-location.json`
- **Waypoints**: `.minecraft/config/player-death-location/waypoints/<player-uuid>.json`

---
**Quick Setup**: Install mod → `/deathcoords on` → `/deathcoords waypoints on` → `/waypoint create Home` → Done!
