package src.hazards;

/**
 * HeavyIceBlock Class
 * 
 * Represents a heavy ice block hazard on the icy terrain.
 * This ice block cannot be moved - anything that collides with it stops.
 * 
 * Behavior:
 * - Cannot be moved by any collision
 * - The colliding penguin loses the lightest food item they are carrying as a penalty
 * - If the penguin is not carrying any food item, it is unaffected
 * 
 * Implements only IHazard (not IMovable) since it cannot slide.
 */
public class HeavyIceBlock implements IHazard {
    
    // Current row position on the grid
    private int row;
    
    // Current column position on the grid
    private int column;
    
    // Whether this hazard has been removed from the game
    private boolean removed;
    
    /**
     * Constructor for HeavyIceBlock.
     * 
     * @param row the initial row position
     * @param column the initial column position
     */
    public HeavyIceBlock(int row, int column) {
        this.row = row;
        this.column = column;
        this.removed = false;
    }
    
    // ==================== IHazard Implementation ====================
    
    /**
     * Gets the hazard type.
     * 
     * @return HazardType.HEAVY_ICE_BLOCK
     */
    @Override
    public HazardType getHazardType() {
        return HazardType.HEAVY_ICE_BLOCK;
    }
    
    /**
     * Checks if this hazard can slide.
     * 
     * @return false (HeavyIceBlock cannot slide)
     */
    @Override
    public boolean canSlide() {
        return false;
    }
    
    /**
     * Gets the display name of this hazard.
     * 
     * @return "HeavyIceBlock"
     */
    @Override
    public String getHazardName() {
        return "HeavyIceBlock";
    }
    
    // ==================== ITerrainObject Implementation ====================
    
    @Override
    public int getRow() {
        return row;
    }
    
    @Override
    public int getColumn() {
        return column;
    }
    
    @Override
    public void setRow(int row) {
        this.row = row;
    }
    
    @Override
    public void setColumn(int column) {
        this.column = column;
    }
    
    @Override
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Gets the display symbol for the grid.
     * 
     * @return "HB"
     */
    @Override
    public String getDisplaySymbol() {
        return HazardType.HEAVY_ICE_BLOCK.getDisplaySymbol();
    }
    
    @Override
    public boolean isRemoved() {
        return removed;
    }
    
    @Override
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
    
    @Override
    public String toString() {
        return "HeavyIceBlock at (" + row + ", " + column + ")";
    }
}
