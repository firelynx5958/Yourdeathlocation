package com.firelynx.playerdeathlocation.client;

import com.firelynx.playerdeathlocation.PlayerDeathLocation;
import com.firelynx.playerdeathlocation.config.ModConfig;
import com.firelynx.playerdeathlocation.waypoint.Waypoint;
import com.firelynx.playerdeathlocation.waypoint.WaypointManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.UUID;

/**
 * Client-side initialization for the mod
 * Renders waypoints on HUD for infinite distance visibility
 */
@Environment(EnvType.CLIENT)
public class PlayerDeathLocationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register HUD renderer for waypoints
        HudRenderCallback.EVENT.register(this::renderWaypointHUD);
        
        PlayerDeathLocation.LOGGER.info("Player Death Location client initialized with HUD waypoint rendering!");
    }
    
    private void renderWaypointHUD(DrawContext drawContext, net.minecraft.client.render.RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.player == null || client.world == null) {
            return;
        }
        
        if (!ModConfig.getInstance().isHolographicWaypointsEnabled()) {
            return;
        }
        
        // Get player waypoints
        UUID playerId = client.player.getUuid();
        List<Waypoint> waypoints = WaypointManager.getPlayerWaypoints(playerId);
        
        if (waypoints == null || waypoints.isEmpty()) {
            return;
        }
        
        // Get player position and dimension
        Vec3d playerPos = client.player.getPos();
        String playerDimension = client.world.getRegistryKey().getValue().toString();
        
        // Render each waypoint on the HUD
        int yOffset = 10;
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getDimension().equals(playerDimension)) {
                renderWaypointOnHUD(drawContext, waypoint, playerPos, yOffset);
                yOffset += 15;
            }
        }
    }
    
    private void renderWaypointOnHUD(DrawContext drawContext, Waypoint waypoint, Vec3d playerPos, int yOffset) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        
        // Calculate distance
        BlockPos waypointPos = waypoint.getPosition();
        double distance = Math.sqrt(
            Math.pow(playerPos.x - waypointPos.getX() - 0.5, 2) +
            Math.pow(playerPos.y - waypointPos.getY(), 2) +
            Math.pow(playerPos.z - waypointPos.getZ() - 0.5, 2)
        );
        
        // Create display text
        String distanceText = String.format("%.0fm", distance);
        String displayText = waypoint.getName() + " - " + distanceText;
        
        // Choose color based on waypoint type
        int color = waypoint.isDeathPoint() ? 0xFF5555 : 0x55FFFF; // Red for death, cyan for custom
        
        // Render text on HUD (top-right corner)
        int screenWidth = client.getWindow().getScaledWidth();
        int textWidth = textRenderer.getWidth(displayText);
        int x = screenWidth - textWidth - 10;
        int y = yOffset;
        
        // Draw semi-transparent background
        drawContext.fill(x - 2, y - 1, x + textWidth + 2, y + 9, 0x80000000);
        
        // Draw the text
        drawContext.drawText(textRenderer, displayText, x, y, color, true);
    }
}
