package com.firelynx.playerdeathlocation.waypoint;

import net.minecraft.util.math.BlockPos;

/**
 * Represents a waypoint in the game world
 */
public class Waypoint {
    private final String name;
    private final BlockPos position;
    private final String dimension;
    private final boolean isDeathPoint;
    
    /**
     * Creates a new waypoint
     * 
     * @param name The name of the waypoint
     * @param position The position of the waypoint
     * @param dimension The dimension of the waypoint
     * @param isDeathPoint Whether this is a death point
     */
    public Waypoint(String name, BlockPos position, String dimension, boolean isDeathPoint) {
        this.name = name;
        this.position = position;
        this.dimension = dimension;
        this.isDeathPoint = isDeathPoint;
    }
    
    /**
     * Creates a new custom waypoint (not a death point)
     * 
     * @param name The name of the waypoint
     * @param position The position of the waypoint
     * @param dimension The dimension of the waypoint
     */
    public Waypoint(String name, BlockPos position, String dimension) {
        this(name, position, dimension, false);
    }
    
    /**
     * Creates a new death waypoint
     * 
     * @param position The position of the death
     * @param dimension The dimension of the death
     */
    public static Waypoint createDeathWaypoint(BlockPos position, String dimension) {
        return new Waypoint("Death Point", position, dimension, true);
    }
    
    public String getName() {
        return name;
    }
    
    public BlockPos getPosition() {
        return position;
    }
    
    public String getDimension() {
        return dimension;
    }
    
    public boolean isDeathPoint() {
        return isDeathPoint;
    }
    
    @Override
    public String toString() {
        return String.format("%s at [%d, %d, %d] in %s", 
                name, 
                position.getX(), 
                position.getY(), 
                position.getZ(), 
                dimension);
    }
}