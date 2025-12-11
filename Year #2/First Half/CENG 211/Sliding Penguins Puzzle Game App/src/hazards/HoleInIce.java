package src.hazards;

/**
 * HoleInIce Class
 * 
 * Represents a hole in the ice hazard on the icy terrain.
 * Anything that slides into a HoleInIce falls into it and is removed from the game.
 * 
 * Behavior:
 * - Naturally, HoleInIce cannot move or slide
 * - If a penguin falls into a HoleInIce, that penguin is removed from the game
 *   (but collected food still counts for scoring)
 * - If a LightIceBlock or SeaLion slides into a HoleInIce, they fall and plug it
 * - When a HoleInIce is plugged, any sliding objects can pass through without issues
 * 
 * Implements only IHazard (not IMovable) since it cannot slide.
 */
public class HoleInIce implements IHazard {
    
    // Current row position on the grid
    private int row;
    
    // Current column position on the grid
    private int column;
    
    // Whether this hazard has been removed from the game
    private boolean removed;
    
    // Whether this hole has been plugged by a LightIceBlock or SeaLion
    private boolean plugged;
    
    /**
     * Constructor for HoleInIce.
     * 
     * @param row the initial row position
     * @param column the initial column position
     */
    public HoleInIce(int row, int column) {
        this.row = row;
        this.column = column;
        this.removed = false;
        this.plugged = false;
    }
    
    // ==================== IHazard Implementation ====================
    
    /**
     * Gets the hazard type.
     * 
     * @return HazardType.HOLE_IN_ICE
     */
    @Override
    public HazardType getHazardType() {
        return HazardType.HOLE_IN_ICE;
    }
    
    /**
     * Checks if this hazard can slide.
     * 
     * @return false (HoleInIce cannot slide)
     */
    @Override
    public boolean canSlide() {
        return false;
    }
    
    /**
     * Gets the display name of this hazard.
     * 
     * @return "HoleInIce"
     */
    @Override
    public String getHazardName() {
        return "HoleInIce";
    }
    
    // ==================== HoleInIce Specific Methods ====================
    
    /**
     * Checks if this hole has been plugged.
     * A plugged hole is safe to pass through.
     * 
     * @return true if the hole is plugged
     */
    public boolean isPlugged() {
        return plugged;
    }
    
    /**
     * Plugs this hole.
     * Called when a LightIceBlock or SeaLion falls into it.
     * A plugged hole displays "PH" instead of "HI".
     */
    public void plug() {
        this.plugged = true;
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
     * Returns "PH" if plugged, "HI" otherwise.
     * 
     * @return "PH" if plugged, "HI" otherwise
     */
    @Override
    public String getDisplaySymbol() {
        if (plugged) {
            return "PH";
        }
        return HazardType.HOLE_IN_ICE.getDisplaySymbol();
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
        return "HoleInIce at (" + row + ", " + column + ")" + (plugged ? " [PLUGGED]" : "");
    }
}
